package fi.tatu.spring6webclient.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class BeerClientImpl implements BeerClient {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String API_URL = "/api/v3/beer";
    private final WebClient webClient;

    public BeerClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(BASE_URL)
                .build();
    }

    @Override
    public Flux<String> listBeers() {
        return webClient.get()
                .uri(API_URL, String.class)
                .retrieve()
                .bodyToFlux(String.class);
    }
}
