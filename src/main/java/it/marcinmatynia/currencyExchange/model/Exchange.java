package it.marcinmatynia.currencyExchange.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "exchanges")
@Getter
@NoArgsConstructor
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BigDecimal amount;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amountAfterExchange;
    private LocalDateTime dateTime;

    public Exchange(final BigDecimal amount, final String fromCurrency, final String toCurrency, final BigDecimal amountAfterExchange, final LocalDateTime dateTime) {
        this.amount = amount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amountAfterExchange = amountAfterExchange;
        this.dateTime = dateTime;
    }
}
