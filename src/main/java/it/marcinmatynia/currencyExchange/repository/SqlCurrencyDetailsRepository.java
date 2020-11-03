package it.marcinmatynia.currencyExchange.repository;

import it.marcinmatynia.currencyExchange.model.CurrencyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlCurrencyDetailsRepository extends CurrencyDetailsRepository, JpaRepository<CurrencyDetails, Integer> {
}
