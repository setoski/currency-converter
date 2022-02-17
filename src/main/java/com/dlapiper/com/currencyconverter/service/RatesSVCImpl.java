package com.dlapiper.com.currencyconverter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RatesSVCImpl implements RatesSVC {
    private ObjectMapper mapper = new ObjectMapper();
    private List<Map<String, String>> rates;
    private final DecimalFormat decimalFormat = new DecimalFormat(System.getProperty("decimalPlace"));

    @Autowired
    public RatesSVCImpl(List<Map<String, String>> rates) {
        this.rates = rates;
    }

    @Override
    public String getExchangeRate(String sourceCurrencyCode, String sourceAmount, String targetCurrencyCode) {

        try {
            for (Map<String, String> rate : rates) {

                if (rate.get("code").equals(isNullEmpty(sourceCurrencyCode))) {

                    Map<String, Object> response = new HashMap();
                    response.put(rate.get("country"), sourceAmount + sourceCurrencyCode);
                    response.put(System.getProperty("defaultTargetCountryName"), rate.get("rate") + isNullEmpty(targetCurrencyCode));
                    response.put("Amount", rateConversion(sourceAmount, rate.get("rate")));

                    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
                }
            }
        } catch (JsonProcessingException e) {
            System.out.println("Conversion error");

        }
        return "Exchange rate not available for " + sourceCurrencyCode;
    }

    private float rateConversion(String sourceAmount, String targetAmount) {
        float source = Float.parseFloat(sourceAmount);
        float target = Float.parseFloat(targetAmount);
        float rate = source / target;
        return roundDecimal(rate);

    }

    private float roundDecimal(double amount) {
       float parsedAmount = 0;
        try {
            decimalFormat.setMaximumFractionDigits(2);

           parsedAmount = Float.parseFloat(decimalFormat.format(amount));

        } catch (Exception exception) {
            System.out.println("Conversion error");
            System.exit(0);
        }
        return parsedAmount;
    }

    private String isNullEmpty(String str) {

        if (str == null) {
            System.out.println("None of the arguments should be empty or null");
            System.exit(0);
        } else if(str.isEmpty()){
            System.out.println("None of the arguments should be empty or null");
            System.exit(0);
        }
        return str;
    }

}
