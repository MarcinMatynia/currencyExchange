package it.marcinmatynia.currencyExchange.exception;

import it.marcinmatynia.currencyExchange.dto.ErrorDTO;
import it.marcinmatynia.currencyExchange.dto.NbpApiErrorDTO;
import it.marcinmatynia.currencyExchange.tools.HasLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorExceptionHandler extends ResponseEntityExceptionHandler implements HasLogger {

    @ExceptionHandler(value = InvalidFieldsException.class)
    ResponseEntity<ErrorDTO> handleInvalidFieldsException(InvalidFieldsException ex) {
        getLogger().error("An error happened while calling {}", ex.toString());

        ErrorDTO errorDTO = new ErrorDTO();
        Map<String, String> invalidFields = new HashMap<>();
        ex.getErrors()
                .forEach(e -> invalidFields.put(e.getField(), e.getDefaultMessage()));

        errorDTO.setStatus(HttpStatus.BAD_REQUEST);
        errorDTO.setInvalidFields(invalidFields);

        return new ResponseEntity<>(errorDTO, errorDTO.getStatus());
    }

    @ExceptionHandler(value = RestTemplateException.class)
    ResponseEntity<NbpApiErrorDTO> test(RestTemplateException ex) {
        var statusCode = ex.getStatusCode();
        NbpApiErrorDTO nbpApiErrorDTO = new NbpApiErrorDTO(statusCode, ex.getError());
        return new ResponseEntity<>(nbpApiErrorDTO, statusCode);
    }
}
