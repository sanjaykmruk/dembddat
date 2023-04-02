package com.bdd.demo.service;

import com.bdd.demo.model.InstantWin;
import org.springframework.stereotype.Service;

public interface InstantWinRepositoryService {
    InstantWin incrementCounter(int storeId);
}
