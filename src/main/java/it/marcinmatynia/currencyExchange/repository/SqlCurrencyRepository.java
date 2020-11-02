package it.marcinmatynia.currencyExchange.repository;

import it.marcinmatynia.currencyExchange.model.Currencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlCurrencyRepository extends CurrencyRepository, JpaRepository<Currencies, Integer> {
}
