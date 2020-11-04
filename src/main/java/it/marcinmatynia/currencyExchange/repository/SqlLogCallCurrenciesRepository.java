package it.marcinmatynia.currencyExchange.repository;

import it.marcinmatynia.currencyExchange.model.LogCallCurrencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlLogCallCurrenciesRepository extends LogCallCurrenciesRepository, JpaRepository<LogCallCurrencies, Integer> {
}
