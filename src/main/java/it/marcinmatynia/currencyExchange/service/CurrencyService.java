package it.marcinmatynia.currencyExchange.service;

import it.marcinmatynia.currencyExchange.Currency;
import it.marcinmatynia.currencyExchange.model.LogCallCurrencies;
import it.marcinmatynia.currencyExchange.repository.CurrencyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public List<Currency> getAvailableCurrencies() {
        var currencies = new LogCallCurrencies(LocalDateTime.now());
        currencyRepository.save(currencies);
        return Arrays.asList(Currency.values());
    }
}
