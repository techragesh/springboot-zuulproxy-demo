package com.techragesh.dbservice.resource;

import com.techragesh.dbservice.domain.Quote;

import com.techragesh.dbservice.domain.Quotes;
import com.techragesh.dbservice.repository.QuotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
public class DbServiceResource {

    @Autowired
    private QuotesRepository quotesRepository;

    @GetMapping("/{username}")
    public List<String> getQuotes(@PathVariable String username){
        return getQuotesByUserName(username);
    }

    private List<String> getQuotesByUserName(String username) {
        return quotesRepository.findByUserName(username)
                .stream()
                .map(Quote::getQuote)
                .collect(Collectors.toList());
    }

    @PostMapping("/addquote")
    public List<String> addQuote(@RequestBody final Quotes quotes){
        quotes.getQuotes()
                .stream()
                .map(s -> new Quote(quotes.getUserName(),s))
                .forEach(quotesRepository::save);
        return getQuotesByUserName(quotes.getUserName());
    }

    @PostMapping("/delete/{username}")
    public List<String> delete(@PathVariable("username") final String username) {

        List<Quote> quotes = quotesRepository.findByUserName(username);
        quotesRepository.delete(quotes);

        return getQuotesByUserName(username);
    }
}
