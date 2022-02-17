package com.dlapiper.com.currencyconverter.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class ExchangeRepo implements ExchangeRepoDAO{

    private Logger logger = LoggerFactory.getLogger(ExchangeRepo.class);
    private final String[] KEY = {"country", "name", "code", "rate"};



    @Bean("rates")
    @Override
    public List<Map<String, String>> rates() {
        List<Map<String, String>> rates = new ArrayList<>();
        try {
            InputStream inputStream = new ClassPathResource(System.getProperty("ratesFileToRead")).getInputStream();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                List<String[]> records = bufferedReader.lines()
                        .map(l -> l.split(","))
                        .collect(Collectors.toList());

                for (String[] record : records) {
                    rates.add(new HashMap<String, String>() {
                        {
                            put(KEY[0], Objects.toString(record[0].trim(), ""));
                            put(KEY[1], Objects.toString(record[1].trim(), ""));
                            put(KEY[2], Objects.toString(record[2].trim(), ""));
                            put(KEY[3], Objects.toString(record[3].trim(), ""));
                        }
                    });
                }
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getCause().getLocalizedMessage());
            logger.error(ioException.getMessage());
            System.exit(0);
        }
        return rates;
    }
}
