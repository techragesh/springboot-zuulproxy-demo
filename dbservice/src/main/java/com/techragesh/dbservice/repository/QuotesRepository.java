package com.techragesh.dbservice.repository;

import com.techragesh.dbservice.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuotesRepository extends JpaRepository<Quote,Integer>{
    List<Quote> findByUserName(String username);
}
