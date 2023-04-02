package com.bdd.demo.service;

import org.springframework.stereotype.Service;

public interface InstantWinService {

    Boolean isInstantWin(int storeId, int txnId);
}
