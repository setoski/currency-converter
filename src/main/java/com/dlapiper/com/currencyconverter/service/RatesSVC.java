package com.dlapiper.com.currencyconverter.service;

public interface RatesSVC {
    String getExchangeRate(String sourceCurrencyCode, String sourceAmount, String targetCurrencyCode);

}
