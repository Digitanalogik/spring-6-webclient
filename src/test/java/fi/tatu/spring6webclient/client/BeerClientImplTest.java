package fi.tatu.spring6webclient.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class BeerClientImplTest {


    @Autowired
    BeerClient client;

    @Test
    void listBeers() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeers()
                .subscribe(response -> {
                    log.info(response);
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeersMap() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeersMap()
                .subscribe(response -> {
                    System.out.println(response);
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }
}