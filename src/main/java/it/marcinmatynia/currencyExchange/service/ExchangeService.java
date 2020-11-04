package it.marcinmatynia.currencyExchange.service;

import it.marcinmatynia.currencyExchange.Currency;
import it.marcinmatynia.currencyExchange.client.NbpApiClient;
import it.marcinmatynia.currencyExchange.dto.ExchangeDTO;
import it.marcinmatynia.currencyExchange.exception.InvalidFieldsException;
import it.marcinmatynia.currencyExchange.model.Exchange;
import it.marcinmatynia.currencyExchange.repository.ExchangeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ExchangeService {
    private final NbpApiClient nbpApiClient;
    private final SmartValidator smartValidator;
    private final ExchangeRepository exchangeRepository;

    public Exchange currencyExchange(final ExchangeDTO exchangeDTO, BindingResult bindingResult) {
        validate(exchangeDTO, bindingResult);

        var amount = exchangeDTO.getAmount();
        var fromCurrency = exchangeDTO.getFromCurrency();
        var toCurrency = exchangeDTO.getToCurrency();
        BigDecimal amountAfterExchange;

        if (fromCurrency != Currency.PLN && toCurrency != Currency.PLN) {
            var fromCurrencyExchangeRate = getExchangeRate(fromCurrency, true);
            var toCurrencyExchangeRate = getExchangeRate(toCurrency, false);
            var toPLN = amount.multiply(fromCurrencyExchangeRate);
            amountAfterExchange = toPLN.divide(toCurrencyExchangeRate, 2, RoundingMode.HALF_UP);
        } else if (fromCurrency == Currency.PLN) {
            var toCurrencyExchangeRate = getExchangeRate(toCurrency, false);
            amountAfterExchange = amount.divide(toCurrencyExchangeRate, 2, RoundingMode.HALF_UP);
        } else {
            var fromCurrencyExchangeRate = getExchangeRate(fromCurrency, true);
            amountAfterExchange = amount.multiply(fromCurrencyExchangeRate);
            amountAfterExchange = amountAfterExchange.setScale(2, RoundingMode.HALF_UP);
        }

        var exchange = new Exchange(amount, fromCurrency.toString(), toCurrency.toString(), amountAfterExchange, LocalDateTime.now());
        exchangeRepository.save(exchange);
        return exchange;
    }

    private BigDecimal getExchangeRate(final Currency currency, final boolean isBid) {
        var rate = nbpApiClient.getExchangeRate(currency);
        if (isBid) {
            return rate.getBid();
        }
        return rate.getAsk();
    }

    private void validate(final ExchangeDTO exchangeDTO, BindingResult bindingResult) {
        smartValidator.validate(exchangeDTO, bindingResult);
        if (exchangeDTO.getFromCurrency() == exchangeDTO.getToCurrency()) {
            var fieldError = new FieldError("currency", "currency", "Currencies cannot be the same.");
            bindingResult.addError(fieldError);
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidFieldsException(bindingResult.getFieldErrors());
        }
    }
}
