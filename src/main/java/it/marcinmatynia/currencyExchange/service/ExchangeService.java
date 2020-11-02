package it.marcinmatynia.currencyExchange.service;

import it.marcinmatynia.currencyExchange.Currency;
import it.marcinmatynia.currencyExchange.client.NbpApiClient;
import it.marcinmatynia.currencyExchange.dto.CurrencyExchangeDTO;
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

    //TODO: Refactor?!
    public Exchange currencyExchange(final CurrencyExchangeDTO currencyExchangeDTO, BindingResult bindingResult) {
        validate(currencyExchangeDTO, bindingResult);

        var amount = currencyExchangeDTO.getAmount();
        var fromCurrency = currencyExchangeDTO.getFromCurrency();
        var toCurrency = currencyExchangeDTO.getToCurrency();
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
        }

        amountAfterExchange = amountAfterExchange.setScale(2, RoundingMode.HALF_UP);

        var exchange = new Exchange(amount, fromCurrency.toString(), toCurrency.toString(), amountAfterExchange, LocalDateTime.now());
        exchangeRepository.save(exchange);
        return exchange;
    }

    private BigDecimal getExchangeRate(final Currency currency, final boolean isBid) {
        return nbpApiClient.getExchangeRate(currency, isBid);
    }

    private void validate(final CurrencyExchangeDTO currencyExchangeDTO, BindingResult bindingResult) {
        smartValidator.validate(currencyExchangeDTO, bindingResult);
        if (currencyExchangeDTO.getFromCurrency() == currencyExchangeDTO.getToCurrency()) {
            var fieldError = new FieldError("currency", "currency", "Currencies cannot be the same.");
            bindingResult.addError(fieldError);
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidFieldsException(bindingResult.getFieldErrors());
        }
    }
}
