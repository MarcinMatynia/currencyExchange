package it.marcinmatynia.currencyExchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class NbpApiErrorDTO {
    private final String info = "There was a problem with the NBP API!";
    private HttpStatus httpStatus;
    private String error;
}
