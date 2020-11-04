package it.marcinmatynia.currencyExchange.repository;

import it.marcinmatynia.currencyExchange.model.Exchange;

public interface ExchangeRepository {
    Exchange save(Exchange entity);
}
