package it.marcinmatynia.currencyExchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDTO {
    private HttpStatus status;
    private String message;
    private Map<String, String> invalidFields;
}
