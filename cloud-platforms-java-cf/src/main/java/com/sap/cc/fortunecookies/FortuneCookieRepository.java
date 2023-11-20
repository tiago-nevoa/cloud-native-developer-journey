package com.sap.cc.fortunecookies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface FortuneCookieRepository extends Repository<Quote, Integer> {

    @Query("SELECT q FROM Quote q ORDER BY random()")
    Page<Quote> getRandomQuotes(Pageable pageable);

}
