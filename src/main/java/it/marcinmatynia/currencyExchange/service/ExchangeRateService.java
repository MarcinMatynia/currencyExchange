package it.marcinmatynia.currencyExchange.service;

import it.marcinmatynia.currencyExchange.Currency;
import it.marcinmatynia.currencyExchange.client.NbpApiClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ExchangeRateService {
    private final NbpApiClient nbpApiClient;
    //TODO : refactor ?!
    public Map<Object, ?> getExchangeRateForAvailableCurrencies() {
//        var rates = new HashMap<String, BigDecimal>();
        var currencies = new HashMap<>();
        Arrays.stream(Currency.values())
                .forEach(currency -> {
                    if (currency != Currency.PLN) {
                        var rates = new HashMap<String, BigDecimal>();
                        rates.put("Bid: ", nbpApiClient.getExchangeRate(currency, true));
                        rates.put("Ask: ", nbpApiClient.getExchangeRate(currency, false));
                        currencies.put(currency, rates);
                    }
                });
        return currencies;
    }
}
