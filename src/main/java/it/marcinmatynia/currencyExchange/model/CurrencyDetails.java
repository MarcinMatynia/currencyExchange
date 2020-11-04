package it.marcinmatynia.currencyExchange.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "currency_details")
@Getter
@Setter
@NoArgsConstructor
public class CurrencyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String currency;
    private BigDecimal bidRate;
    private BigDecimal askRate;
    private LocalDateTime dateTime;

    public CurrencyDetails(final String currency, final BigDecimal bidRate, final BigDecimal askRate, final LocalDateTime dateTime) {
        this.currency = currency;
        this.bidRate = bidRate;
        this.askRate = askRate;
        this.dateTime = dateTime;
    }
}
