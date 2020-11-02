package it.marcinmatynia.currencyExchange.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@AllArgsConstructor
public class RestTemplateException extends RuntimeException {
    private final HttpStatus statusCode;
    private final String error;
}
