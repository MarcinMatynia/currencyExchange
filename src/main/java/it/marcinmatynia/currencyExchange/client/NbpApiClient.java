package it.marcinmatynia.currencyExchange.client;

import it.marcinmatynia.currencyExchange.Currency;
import it.marcinmatynia.currencyExchange.dto.NbpApiResponseDTO;
import it.marcinmatynia.currencyExchange.exception.RestTemplateException;
import it.marcinmatynia.currencyExchange.model.ExchangeRate;
import it.marcinmatynia.currencyExchange.repository.ExchangeRateRepository;
import it.marcinmatynia.currencyExchange.tools.HasLogger;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class NbpApiClient implements HasLogger {
    private final RestTemplate restTemplate;
    private final ExchangeRateRepository exchangeRateRepository;

    public BigDecimal getExchangeRate(final Currency currency, boolean isBid) {
        ResponseEntity<NbpApiResponseDTO> response;
        Integer statusCode = null;
        final String nbpEndPoint = "http://api.nbp.pl/api/exchangerates/rates/c/" + currency + "/today?format=json";

        try {
            response = restTemplate.getForEntity(nbpEndPoint, NbpApiResponseDTO.class);
            statusCode = response.getStatusCodeValue();
            var rate = getExchange(response.getBody(), isBid);

            var exchangeRate = new ExchangeRate(currency.toString(), rate, LocalDateTime.now());
            exchangeRateRepository.save(exchangeRate);

            return rate;

        } catch (HttpClientErrorException ex) {
            throw new RestTemplateException(ex.getStatusCode(), ex.getMessage());

        } finally {
            getLogger().info(nbpEndPoint + "\n Status code was: " + statusCode);
        }
    }

    //TODO: refactor ?!
    private BigDecimal getExchange(NbpApiResponseDTO nbpApiResponseDTO, boolean isBid) {
        try {
            var rate = nbpApiResponseDTO.getRates().stream()
                    .findFirst()
                    .orElseThrow();
            if (isBid) {
                return rate.getBid();
            } else {
                return rate.getAsk();
            }

        } catch (NullPointerException ex) {
            throw new RestTemplateException(HttpStatus.NOT_FOUND, "Average exchange rate not found!");
        }
    }
}
