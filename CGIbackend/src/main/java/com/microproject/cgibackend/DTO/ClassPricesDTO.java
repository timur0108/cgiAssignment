package com.microproject.cgibackend.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
public class ClassPricesDTO {

    private BigDecimal touristClassPrice;
    private BigDecimal businessClassPrice;
    private BigDecimal firstClassPrice;

    public ClassPricesDTO(BigDecimal touristClassPrice, BigDecimal businessClassPrice, BigDecimal firstClassPrice) {
        this.touristClassPrice = touristClassPrice;
        this.businessClassPrice = businessClassPrice;
        this.firstClassPrice = firstClassPrice;
    }
}
