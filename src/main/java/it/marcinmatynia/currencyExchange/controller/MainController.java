package it.marcinmatynia.currencyExchange.controller;

import it.marcinmatynia.currencyExchange.Currency;
import it.marcinmatynia.currencyExchange.service.CurrencyService;
import it.marcinmatynia.currencyExchange.tools.HasLogger;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
class MainController implements HasLogger {
    private final CurrencyService currencyService;

    @GetMapping(value = "/currencies")
    ResponseEntity<List<Currency>> getAvailableCurrencies(){
        var currencies = currencyService.getAvailableCurrencies();
        getLogger().info("Exposing all currencies.");
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }
}
