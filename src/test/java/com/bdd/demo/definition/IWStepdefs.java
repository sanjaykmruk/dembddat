package com.bdd.demo.definition;

import com.bdd.demo.model.InstantWin;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.bson.Document;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
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

    @Before
    public void resetDB(){
        mongoTemplate.dropCollection(InstantWin.class);
    }

    @Given("database is empty")
    public void databaseIsEmpty() {
        mongoTemplate.dropCollection(InstantWin.class);
    }

    @Given("IW collection contains a document with storeId {int} and transaction id {int} and counter as {int}")
    public void iwCollectionContainsADocumentWithStoreIdAndTransactionIdAndCounterAs(Integer storeId, Integer txnId, Integer counter) {

        var iwData = new InstantWin(storeId, txnId, counter);
        mongoTemplate.insert(iwData);
    }

    @When("User makes get call with storeId {int} and txnId {int}")
    public void userMakesGetCallWithStoreIdAndTxnId(int storeId, int txnId) {
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

    @Then("User should be not awarded IW")
    public void userShouldBeNotAwardedIW() {
        assertFalse(instantWinResponse);
    }


}
