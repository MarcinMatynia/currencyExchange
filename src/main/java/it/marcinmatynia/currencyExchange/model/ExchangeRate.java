package it.marcinmatynia.currencyExchange.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_rates")
@Getter
@Setter
@NoArgsConstructor
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String currency;
    private BigDecimal rate;
    private LocalDateTime dateTime;

    public ExchangeRate(final String currency, final BigDecimal rate, final LocalDateTime dateTime) {
        this.currency = currency;
        this.rate = rate;
        this.dateTime = dateTime;
    }
}
