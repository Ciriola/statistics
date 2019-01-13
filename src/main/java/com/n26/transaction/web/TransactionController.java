package com.n26.transaction.web;

import com.n26.transaction.service.ConcurrentTransactionService;
import com.n26.transaction.service.InvalidTransactionDataException;
import com.n26.transaction.service.PastTimestampException;
import com.n26.transaction.model.TransactionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);
    private final ConcurrentTransactionService service;
    private final TransactionParser parser;

    @Autowired
    TransactionController(ConcurrentTransactionService service, TransactionParser parser) {
        this.service = service;
        this.parser = parser;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void addTransaction(@RequestBody TransactionDTO request) throws InvalidTransactionDataException, PastTimestampException {
        TransactionDO parsedRequest = parser.parseTransaction(request);
        service.addTransaction(parsedRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteTransactions() {
        service.deleteTransactions();
    }
}
