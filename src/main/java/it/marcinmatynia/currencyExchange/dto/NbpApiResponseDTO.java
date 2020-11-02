package it.marcinmatynia.currencyExchange.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class NbpApiResponseDTO {
    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static final class Rate {
        private String no;
        private LocalDate effectiveDate;
        private BigDecimal bid;
        private BigDecimal ask;
    }
}
