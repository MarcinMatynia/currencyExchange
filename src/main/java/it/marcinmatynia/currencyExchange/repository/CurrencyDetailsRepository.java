package it.marcinmatynia.currencyExchange.repository;

import it.marcinmatynia.currencyExchange.model.CurrencyDetails;

public interface CurrencyDetailsRepository {
    CurrencyDetails save(CurrencyDetails entity);
}
