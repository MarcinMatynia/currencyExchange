package it.marcinmatynia.currencyExchange.controller;

import it.marcinmatynia.currencyExchange.Currency;
import it.marcinmatynia.currencyExchange.dto.CurrencyExchangeDTO;
import it.marcinmatynia.currencyExchange.model.Exchange;
import it.marcinmatynia.currencyExchange.service.CurrencyService;
import it.marcinmatynia.currencyExchange.service.ExchangeRateService;
import it.marcinmatynia.currencyExchange.service.ExchangeService;
import it.marcinmatynia.currencyExchange.tools.HasLogger;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/currencies")
class MainController implements HasLogger {
    private final CurrencyService currencyService;
    private final ExchangeRateService exchangeRateService;
    private final ExchangeService exchangeService;

    @GetMapping
    ResponseEntity<List<Currency>> getAvailableCurrencies() {
        var currencies = currencyService.getAvailableCurrencies();
        getLogger().info("Exposing all currencies.");
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @GetMapping(value = "/rate")
    ResponseEntity<Map<Object, ?>> getExchangeRateForAvailableCurrencies() {
        var exchangeRates = exchangeRateService.getExchangeRateForAvailableCurrencies();
        getLogger().info("Exposing all exchange rate.");
        return new ResponseEntity<>(exchangeRates, HttpStatus.OK);
    }

    @PostMapping(value = "/exchange")
    @Transactional
    ResponseEntity<Exchange> currencyExchange(@RequestBody CurrencyExchangeDTO currencyExchangeDTO, BindingResult bindingResult) {
        var exchange = exchangeService.currencyExchange(currencyExchangeDTO, bindingResult);
        getLogger().info("Currency exchange " + currencyExchangeDTO.getAmount() + " " + currencyExchangeDTO.getFromCurrency()
                + " -> " + currencyExchangeDTO.getToCurrency());
        return new ResponseEntity<>(exchange, HttpStatus.CREATED);
    }
}
