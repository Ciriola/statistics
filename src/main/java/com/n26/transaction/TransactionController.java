package com.n26.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService service;
    private final TransactionParser parser;

    @Autowired
    TransactionController(TransactionService service, TransactionParser parser) {
        this.service = service;
        this.parser = parser;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity addTransaction(@RequestBody Transaction request) throws InvalidTransactionDataException {
        TransactionDO parsedRequest = parser.parseTransaction(request);
        service.addTransaction(parsedRequest);
        return null;

    }
}
