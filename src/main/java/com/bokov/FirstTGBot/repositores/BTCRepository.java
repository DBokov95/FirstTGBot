package com.bokov.FirstTGBot.repositores;

import com.bokov.FirstTGBot.models.BTC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface BTCRepository extends JpaRepository<BTC, Integer> {
    List<BTC> findAllByDateOfRequestBetween(Date date, Date date2);
}
