package com.n26.concurrency;

import com.n26.statistics.web.StatisticsDTO;
import com.n26.transaction.web.TransactionDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ConcurrencyTestIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String hostname = "localhost";

    @LocalServerPort
    private int port;

    @Test
    public void addTransactionsConcurrentlyAndGetStats_statsAreConsistent() throws URISyntaxException, InterruptedException {

        final Collection<Callable<Void>> callables = new ArrayList<>();

        final String basePath = "http://" + hostname + ":" + port;

        final URI transactionsUri = new URI(basePath + "/transactions");
        final URI statisticsUri = new URI(basePath + "/statistics");

        for(int i = 0; i< 1000; i++) {
            final TransactionDTO transaction = new TransactionDTO(String.valueOf(i), Instant.now().toString());
            final HttpEntity<TransactionDTO> httpTransaction = new HttpEntity<>(transaction);

            callables.add(() -> restTemplate.postForObject(transactionsUri, httpTransaction, Void.class));
        }

        ExecutorService executor = Executors.newWorkStealingPool();
        executor.invokeAll(callables);

        ResponseEntity<StatisticsDTO> statistics = restTemplate.getForEntity(statisticsUri, StatisticsDTO.class);
        StatisticsDTO body = statistics.getBody();

        Assert.assertNotNull(body);

        Assert.assertEquals(1000, body.getCount());
        Assert.assertEquals("499500.00", body.getSum());
        Assert.assertEquals("499.50", body.getAvg());
        Assert.assertEquals("999.00", body.getMax());
        Assert.assertEquals("0.00", body.getMin());
    }
}
