package it.marcinmatynia.currencyExchange.repository;

import it.marcinmatynia.currencyExchange.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlExchangeRateRepository extends ExchangeRateRepository, JpaRepository<ExchangeRate, Integer> {
}
