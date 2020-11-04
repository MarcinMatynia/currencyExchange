package it.marcinmatynia.currencyExchange.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.FieldError;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class InvalidFieldsException extends RuntimeException {
    private final List<FieldError> errors;
}
