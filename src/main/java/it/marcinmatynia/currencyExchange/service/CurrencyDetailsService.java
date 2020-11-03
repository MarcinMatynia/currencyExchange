package it.marcinmatynia.currencyExchange.service;

import it.marcinmatynia.currencyExchange.Currency;
import it.marcinmatynia.currencyExchange.client.NbpApiClient;
import it.marcinmatynia.currencyExchange.dto.CurrencyDetailsDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CurrencyDetailsService {
    private final NbpApiClient nbpApiClient;

    public List<CurrencyDetailsDTO> getExchangeRatesForAvailableCurrencies() {
        var currencyDetailsDTOList = new ArrayList<CurrencyDetailsDTO>();
        Arrays.stream(Currency.values())
                .filter(currency -> currency != Currency.PLN)
                .forEach(currency -> {
                    var rates = nbpApiClient.getExchangeRate(currency);
                    var currencyDetailsDTO = new CurrencyDetailsDTO(currency.toString(), rates.getBid(), rates.getAsk());
                    currencyDetailsDTOList.add(currencyDetailsDTO);
                });
        return currencyDetailsDTOList;
    }
}
