package com.dlapiper.com.currencyconverter;

import com.dlapiper.com.currencyconverter.service.RatesSVC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class CurrencyConverterApplication implements CommandLineRunner {

    private Scanner scanner = new Scanner(System.in);
    @Autowired
    private RatesSVC ratesSVC;

    public static void main(String[] args) {
        System.setProperty("ratesFileToRead", "exchange/february-rates.csv");
        System.setProperty("decimalPlace", "0.00");
        System.setProperty("defaultTargetCountryName", "United Kingdom");
        System.setProperty("defaultTargetCountryCurrencyCode", "GBP");


        SpringApplication.run(CurrencyConverterApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("*****Currency Converter*****");
        System.out.println("*. Step 1: Type in an amount");
        System.out.println("*. Step 2: Type in a currency code you're converting from");
        System.out.println("*. Step 3: Type in a currency code you're converting to");

        try {
            System.out.println("*.  Type in an amount");
            String sourceAmount = scanner.next();
            System.out.println("*.  Convert from");
            String sourceCurrencyCode = scanner.next();
            System.out.println("*.  Convert to");
            String targetCurrencyCode = scanner.next();
            String response = ratesSVC.getExchangeRate(sourceCurrencyCode, sourceAmount, targetCurrencyCode);
            System.out.println(response);
        } catch (Exception e) {
            System.out.println("Conversion error");
        }

    }
}
