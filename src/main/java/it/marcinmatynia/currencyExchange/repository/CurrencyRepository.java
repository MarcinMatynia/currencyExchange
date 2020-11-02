package it.marcinmatynia.currencyExchange.repository;

import it.marcinmatynia.currencyExchange.model.LogCallCurrencies;

public interface CurrencyRepository {
    LogCallCurrencies save(LogCallCurrencies entity);
}
