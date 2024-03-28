package com.bokov.FirstTGBot.bot;

import com.bokov.FirstTGBot.models.BTC;
import com.bokov.FirstTGBot.services.BTCService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Log4j2
@PropertySource("classpath:application.properties")
public class BtcPriceClient {
    @Autowired
    private Environment environment;
    @Autowired
    private BTCService btcService;



    public void fetchBitcoinPriceFromExchange() {
        WebClient webClient = WebClient.create();
        webClient.post()
                .uri(environment.getProperty("jsonBtc"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(null)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new CryptoExchangeException("Crypto Exchange API Exception")))
                .toEntity(String.class)
                .doOnSuccess(stringResponseEntity -> {
                    try {
                        processResponse(stringResponseEntity);
                    } catch (JsonProcessingException e) {
                        log.error("");
                        throw new RuntimeException(e);
                    }
                })
                .doOnError(this::handleClientError)
                .subscribe();
    }

    private void handleClientError(Throwable throwable) {

    }

    private void processResponse(ResponseEntity<String> stringResponseEntity) throws JsonProcessingException {
        BTC btc = new BTC();
        String response = stringResponseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode obj = objectMapper.readTree(response);
        double price = obj.get("lprice").asDouble();
        btc.setPrise(price);
        btc.setDateOfRequest(new Date());
        btcService.save(btc);
    }

}
