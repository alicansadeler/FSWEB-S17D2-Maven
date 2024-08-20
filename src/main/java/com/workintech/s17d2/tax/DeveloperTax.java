package com.workintech.s17d2.tax;

import org.springframework.stereotype.Component;

@Component

public class DeveloperTax implements Taxable{
    @Override
    public Double getSimpleTaxRate() {
        return 15.0;
    }

    @Override
    public Double getMiddleTaxRate() {
        return 25.0;
    }

    @Override
    public Double getUpperTaxRate() {
        return 35.0;
    }
}
