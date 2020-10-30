package it.marcinmatynia.currencyExchange.repository;

import it.marcinmatynia.currencyExchange.model.Currencies;

public interface CurrencyRepository {
    Currencies save(Currencies entity);
}
