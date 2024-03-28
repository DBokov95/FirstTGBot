package com.bokov.FirstTGBot.services;

import com.bokov.FirstTGBot.models.BTC;
import com.bokov.FirstTGBot.repositores.BTCRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.util.Date;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class BTCService {
    private final BTCRepository btcRepository;
    @Autowired
    private Environment environment;


    @Autowired
    public BTCService(BTCRepository btcRepository) {
        this.btcRepository = btcRepository;
    }


    public List<BTC> findAllByRegistrationDateBetween(Date date, Date date2 ){
        return btcRepository.findAllByDateOfRequestBetween(date, date2);
    }

    @Transactional
    public void save(BTC btc) {
    btcRepository.save(btc);
    }
}
