package com.bdd.demo.definition;

import com.bdd.demo.config.CucumberSpringConfig;
import com.bdd.demo.model.InstantWin;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;



public class IWStepdefs {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RestTemplate restTemplate;

    @LocalServerPort
    private String serverPort;

    Boolean instantWinResponse;

    MongoDatabase database;
    MongoCollection<Document> iwCollection;

    @Autowired
    ApplicationContext context;

    @Given("IW collection contains a document with storeId {int} and transaction id {int} and counter as {int}")
    public void iwCollectionContainsADocumentWithStoreIdAndTransactionIdAndCounterAs(Integer storeId, Integer txnId, Integer counter) {
        var iwData = new InstantWin(storeId, txnId, counter);
        mongoTemplate.insert(iwData);
    }

    @When("User makes get call with storeId {int} and number {int}")
    public void userMakesGetCallWithStoreIdAndNumber(int storeId, int txnId) {
        String uri = "http://localhost:" + serverPort + "/instantwin?storeId=" + +storeId + "&txnId=" + txnId;
        instantWinResponse = restTemplate.getForEntity(uri, Boolean.class).getBody();
    }

    @Then("User should be awarded IW")
    public void userShouldBeAwardedIW() {
        assertTrue(instantWinResponse);
    }

    @And("Counter should increment to {int}")
    public void counterShouldIncrementTo(int incrementedCounter) {
        var iwList = mongoTemplate.findAll(InstantWin.class);
        assertEquals(incrementedCounter, iwList.get(0).getCounter());
    }

}
