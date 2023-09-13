package fi.tatu.spring6webclient.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class BeerClientImplTest {


    @Autowired
    BeerClient client;

    @Test
    void listBeers() {

        client.listBeers()
                .subscribe(response -> {
                    log.info(response);
                });
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}