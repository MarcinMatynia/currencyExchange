package it.marcinmatynia.currencyExchange.controller;

import it.marcinmatynia.currencyExchange.Currency;
import it.marcinmatynia.currencyExchange.dto.CurrencyDetailsDTO;
import it.marcinmatynia.currencyExchange.dto.ExchangeDTO;
import it.marcinmatynia.currencyExchange.model.Exchange;
import it.marcinmatynia.currencyExchange.service.LogCallCurrenciesService;
import it.marcinmatynia.currencyExchange.service.CurrencyDetailsService;
import it.marcinmatynia.currencyExchange.service.ExchangeService;
import it.marcinmatynia.currencyExchange.tools.HasLogger;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/currencies")
class MainController implements HasLogger {
    private final LogCallCurrenciesService logCallCurrenciesService;
    private final CurrencyDetailsService currencyDetailsService;
    private final ExchangeService exchangeService;

    @GetMapping
    ResponseEntity<List<Currency>> getAvailableCurrencies() {
        var currencies = logCallCurrenciesService.getAvailableCurrencies();
        getLogger().info("Exposing all currencies.");
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @GetMapping(value = "/rate")
    ResponseEntity<List<CurrencyDetailsDTO>> getExchangeRatesForAvailableCurrencies() {
        var exchangeRates = currencyDetailsService.getExchangeRatesForAvailableCurrencies();
        getLogger().info("Exposing all exchange rate.");
        return new ResponseEntity<>(exchangeRates, HttpStatus.OK);
    }

    @PostMapping(value = "/exchange")
    @Transactional
    ResponseEntity<Exchange> currencyExchange(@RequestBody ExchangeDTO exchangeDTO, BindingResult bindingResult) {
        var exchange = exchangeService.currencyExchange(exchangeDTO, bindingResult);
        getLogger().info("Currency exchange " + exchangeDTO.getAmount() + " " + exchangeDTO.getFromCurrency()
                + " -> " + exchangeDTO.getToCurrency());
        return new ResponseEntity<>(exchange, HttpStatus.CREATED);
    }
}
