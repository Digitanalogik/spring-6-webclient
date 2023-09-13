package fi.tatu.spring6webclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import fi.tatu.spring6webclient.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
public class BeerClientImpl implements BeerClient {

    private static final String API_URL = "/api/v3/beer";
    private static final String API_URL_ID = API_URL + "/{beerId}";

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

    @Override
    public Mono<BeerDTO> getBeerById(String beerId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(API_URL_ID).build(beerId))
                .retrieve()
                .bodyToMono(BeerDTO.class);
    }

    @Override
    public Flux<BeerDTO> getBeersByBeerStyle(String beerStyle) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(API_URL)
                        .queryParam("beerStyle", beerStyle)
                        .build())
                .retrieve()
                .bodyToFlux(BeerDTO.class);
    }

    @Override
    public Mono<BeerDTO> createBeer(BeerDTO beerDTO) {
        return webClient.post()
                .uri(API_URL)
                .body(Mono.just(beerDTO), BeerDTO.class)
                .retrieve()
                .toBodilessEntity()
                .flatMap(voidResponseEntity -> Mono.just(voidResponseEntity.getHeaders().get(HttpHeaders.LOCATION).get(0)))
                .map(path -> path.split("/")[path.split("/").length - 1])
                .flatMap(this::getBeerById);
    }

    @Override
    public Mono<BeerDTO> updateBeer(BeerDTO beerDTO) {
        return webClient.put()
                .uri(uriBuilder -> uriBuilder.path(API_URL_ID).build(beerDTO.getId()))
                .body(Mono.just(beerDTO), BeerDTO.class)
                .retrieve()
                .toBodilessEntity()
                .flatMap(voidResponseEntity -> getBeerById(beerDTO.getId()));
    }
}
