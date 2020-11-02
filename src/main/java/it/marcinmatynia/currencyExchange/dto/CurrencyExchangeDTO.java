package it.marcinmatynia.currencyExchange.dto;

import it.marcinmatynia.currencyExchange.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyExchangeDTO {
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    private Currency fromCurrency;

    @NotNull
    private Currency toCurrency;
}
