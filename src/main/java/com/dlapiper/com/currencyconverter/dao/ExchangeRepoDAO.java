package com.dlapiper.com.currencyconverter.dao;

import java.util.List;
import java.util.Map;

public interface ExchangeRepoDAO {
    public List<Map<String, String>> rates();
}
