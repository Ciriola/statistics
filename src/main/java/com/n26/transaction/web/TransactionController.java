package com.n26.transaction.web;

import com.n26.error.UncaughtExceptionHandler;
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

    /**
     * This method is saving the transaction if it is correct.
     * In the other cases, it is throwing exceptions,
     * all managed by the class {@link UncaughtExceptionHandler}
     *
     * @param request the transaction that possibly will be saved
     * @throws InvalidTransactionDataException if transaction fields are
     * invalid or transaction timestamp is in the future
     * @throws PastTimestampException if transaction timestamp is in the past
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void addTransaction(@RequestBody TransactionDTO request) throws InvalidTransactionDataException, PastTimestampException {
        TransactionDO parsedRequest = parser.parseTransaction(request);
        service.addTransaction(parsedRequest);
        LOG.debug("Transaction with amount {} and timestamp {} added", parsedRequest.getAmount(), parsedRequest.getTimestamp());
    }

    /**
     * This method is cleaning all the saved transactions
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteTransactions() {
        service.deleteTransactions();
    }
}
