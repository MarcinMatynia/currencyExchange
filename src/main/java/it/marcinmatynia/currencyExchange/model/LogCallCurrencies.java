package it.marcinmatynia.currencyExchange.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_call_currencies")
@Getter
@NoArgsConstructor
public class LogCallCurrencies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime dateTime;

    public LogCallCurrencies(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
