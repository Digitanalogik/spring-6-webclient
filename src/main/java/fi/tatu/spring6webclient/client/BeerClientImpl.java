package fi.tatu.spring6webclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import fi.tatu.spring6webclient.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Map;

@Slf4j
@Service
public class BeerClientImpl implements BeerClient {

    private static final String API_URL = "/api/v3/beer";
    private final WebClient webClient;

    public BeerClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public Flux<String> listBeers() {
        return webClient.get()
                .uri(API_URL)
                .retrieve()
                .bodyToFlux(String.class);
    }

    @Override
    public Flux<Map> listBeersMap() {
        return webClient.get()
                .uri(API_URL)
                .retrieve()
                .bodyToFlux(Map.class);}

    @Override
    public Flux<JsonNode> listBeersJsonNode() {
        return webClient.get()
                .uri(API_URL)
                .retrieve()
                .bodyToFlux(JsonNode.class);
    }

    @Override
    public Flux<BeerDTO> listBeersDto() {
        return webClient.get()
                .uri(API_URL)
                .retrieve()
                .bodyToFlux(BeerDTO.class);
    }
}
