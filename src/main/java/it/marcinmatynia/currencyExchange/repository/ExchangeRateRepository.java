package it.marcinmatynia.currencyExchange.repository;

import it.marcinmatynia.currencyExchange.model.ExchangeRate;

public interface ExchangeRateRepository {
    ExchangeRate save(ExchangeRate entity);
}
