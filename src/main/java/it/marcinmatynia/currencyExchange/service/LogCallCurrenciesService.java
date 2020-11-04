package it.marcinmatynia.currencyExchange.service;

import it.marcinmatynia.currencyExchange.Currency;
import it.marcinmatynia.currencyExchange.model.LogCallCurrencies;
import it.marcinmatynia.currencyExchange.repository.LogCallCurrenciesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class LogCallCurrenciesService {
    private final LogCallCurrenciesRepository logCallCurrenciesRepository;

    public List<Currency> getAvailableCurrencies() {
        var currencies = new LogCallCurrencies(LocalDateTime.now());
        logCallCurrenciesRepository.save(currencies);
        return Arrays.asList(Currency.values());
    }
}
