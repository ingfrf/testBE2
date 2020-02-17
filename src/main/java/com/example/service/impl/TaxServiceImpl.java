package com.example.service.impl;

import com.example.model.Input;
import com.example.model.Output;
import com.example.service.TaxCalculatorService;
import com.example.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaxServiceImpl implements TaxService {
    private static final double BASIC_TAX = 0.1d;
    private static final double IMPORTED_TAX = 0.05d;

    @Autowired
    TaxCalculatorService taxCalculatorService;

    @Override
    public Output applyTaxes(Input input) {
        double tax = 0.00d;
        switch (input.getType()) {
            case OTHER:
                tax = BASIC_TAX;
                break;
            case BOOK:
            case FOOD:
            case MEDICAL:
            default:
                break;
        }
        tax += input.isImported() ? IMPORTED_TAX : 0;
        double amount = taxCalculatorService.calculateAmountWithTax(input.getPrice(), input.getQuantity(), tax);
        double salesTax = amount - input.getPrice();
        return new Output(input.getDescription(), input.getQuantity(), salesTax, amount);
    }
}
