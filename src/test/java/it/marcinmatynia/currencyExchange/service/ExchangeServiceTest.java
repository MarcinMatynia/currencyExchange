package it.marcinmatynia.currencyExchange.service;

import it.marcinmatynia.currencyExchange.Currency;
import it.marcinmatynia.currencyExchange.client.NbpApiClient;
import it.marcinmatynia.currencyExchange.dto.ExchangeDTO;
import it.marcinmatynia.currencyExchange.dto.NbpApiResponseDTO;
import it.marcinmatynia.currencyExchange.repository.ExchangeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExchangeServiceTest {
    @Test
    void shouldCorrectlyCurrencyExchangeWhenCurrenciesAreDifferentThanPLN() {
        //given
        var exchangeDTO = mockExchangeDTO();
        exchangeDTO.setFromCurrency(Currency.USD);
        exchangeDTO.setToCurrency(Currency.EUR);
        var usdRates = usdRates();
        var eurRates = eurRates();
        var mockNbpApiClient = mock(NbpApiClient.class);
        var serviceToTest = new ExchangeService(mockNbpApiClient, mock(SmartValidator.class), mock(ExchangeRepository.class));

        //when
        when(mockNbpApiClient.getExchangeRate(Currency.USD)).thenReturn(usdRates);
        when(mockNbpApiClient.getExchangeRate(Currency.EUR)).thenReturn(eurRates);
        var result = serviceToTest.currencyExchange(exchangeDTO, mock(BindingResult.class));

        //then
        assertEquals(new BigDecimal("83.70"), result.getAmountAfterExchange());
    }

    @Test
    void shouldCorrectlyCurrencyExchangeWhenFromCurrencyIsPLN() {
        //given
        var exchangeDTO = mockExchangeDTO();
        exchangeDTO.setFromCurrency(Currency.PLN);
        exchangeDTO.setToCurrency(Currency.USD);
        var usdRates = usdRates();
        var mockNbpApiClient = mock(NbpApiClient.class);
        var serviceToTest = new ExchangeService(mockNbpApiClient, mock(SmartValidator.class), mock(ExchangeRepository.class));

        //when
        when(mockNbpApiClient.getExchangeRate(Currency.USD)).thenReturn(usdRates);
        var result = serviceToTest.currencyExchange(exchangeDTO, mock(BindingResult.class));

        //then
        assertEquals(new BigDecimal("25.43"), result.getAmountAfterExchange());
    }

    @Test
    void shouldCorrectlyCurrencyExchangeWhenToCurrencyIsPLN(){
        //given
        var exchangeDTO = mockExchangeDTO();
        exchangeDTO.setFromCurrency(Currency.USD);
        exchangeDTO.setToCurrency(Currency.PLN);
        var usdRates = usdRates();
        var mockNbpApiClient = mock(NbpApiClient.class);
        var serviceToTest = new ExchangeService(mockNbpApiClient, mock(SmartValidator.class), mock(ExchangeRepository.class));

        //when
        when(mockNbpApiClient.getExchangeRate(Currency.USD)).thenReturn(usdRates);
        var result = serviceToTest.currencyExchange(exchangeDTO, mock(BindingResult.class));

        //then
        assertEquals(new BigDecimal("385.49"), result.getAmountAfterExchange());
    }

    private ExchangeDTO mockExchangeDTO() {
        var exchangeDTO = new ExchangeDTO();
        exchangeDTO.setAmount(new BigDecimal(100));
        return exchangeDTO;
    }

    private NbpApiResponseDTO.Rate usdRates() {
        var usdRates = new NbpApiResponseDTO.Rate();
        usdRates.setBid(new BigDecimal("3.8549"));
        usdRates.setAsk(new BigDecimal("3.9327"));
        return usdRates;
    }

    private NbpApiResponseDTO.Rate eurRates() {
        var eurRates = new NbpApiResponseDTO.Rate();
        eurRates.setBid(new BigDecimal("4.5146"));
        eurRates.setAsk(new BigDecimal("4.6058"));
        return eurRates;
    }
}