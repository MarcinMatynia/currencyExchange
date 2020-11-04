package it.marcinmatynia.currencyExchange.repository;

import it.marcinmatynia.currencyExchange.model.LogCallCurrencies;

public interface LogCallCurrenciesRepository {
    LogCallCurrencies save(LogCallCurrencies entity);
}
