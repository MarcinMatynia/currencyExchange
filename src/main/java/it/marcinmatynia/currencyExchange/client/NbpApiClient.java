package it.marcinmatynia.currencyExchange.client;

import it.marcinmatynia.currencyExchange.Currency;
import it.marcinmatynia.currencyExchange.dto.NbpApiResponseDTO;
import it.marcinmatynia.currencyExchange.exception.RestTemplateException;
import it.marcinmatynia.currencyExchange.model.CurrencyDetails;
import it.marcinmatynia.currencyExchange.repository.CurrencyDetailsRepository;
import it.marcinmatynia.currencyExchange.tools.HasLogger;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class NbpApiClient implements HasLogger {
    private final RestTemplate restTemplate;
    private final CurrencyDetailsRepository currencyDetailsRepository;

    public NbpApiResponseDTO.Rate getExchangeRate(final Currency currency) {
        ResponseEntity<NbpApiResponseDTO> response;
        Integer statusCode = null;
        final String nbpEndPoint = "http://api.nbp.pl/api/exchangerates/rates/c/" + currency + "/today?format=json";

        try {
            response = restTemplate.getForEntity(nbpEndPoint, NbpApiResponseDTO.class);
            statusCode = response.getStatusCodeValue();

            var rates = getExchange(response.getBody());
            var currencyDetails = new CurrencyDetails(currency.toString(), rates.getBid(), rates.getAsk(), LocalDateTime.now());
            currencyDetailsRepository.save(currencyDetails);

            return rates;

        } catch (HttpClientErrorException ex) {
            throw new RestTemplateException(ex.getStatusCode(), ex.getMessage());

        } finally {
            getLogger().info(nbpEndPoint + "\nStatus code was: " + statusCode);
        }
    }

    private NbpApiResponseDTO.Rate getExchange(NbpApiResponseDTO nbpApiResponseDTO) {
        try {
            return nbpApiResponseDTO.getRates().stream()
                    .findFirst()
                    .orElseThrow();

        } catch (NullPointerException ex) {
            throw new RestTemplateException(HttpStatus.NOT_FOUND, "Average exchange rate not found!");
        }
    }
}
