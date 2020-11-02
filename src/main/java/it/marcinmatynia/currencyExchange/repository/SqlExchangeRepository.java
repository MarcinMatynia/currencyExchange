package it.marcinmatynia.currencyExchange.repository;

import it.marcinmatynia.currencyExchange.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlExchangeRepository extends ExchangeRepository, JpaRepository<Exchange, Integer> {
}
