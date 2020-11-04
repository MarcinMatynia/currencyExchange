package it.marcinmatynia.currencyExchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CurrencyDetailsDTO {
    private final String name;
    private final BigDecimal bid;
    private final BigDecimal ask;
}
