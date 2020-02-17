package com.example.service.impl;

import com.example.model.Input;
import com.example.model.Output;
import com.example.service.PrintService;
import com.example.service.TaxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

@Component
public class PrintServiceImpl implements PrintService {
    private static Logger LOG = LoggerFactory.getLogger(PrintServiceImpl.class);
    private final DecimalFormat df;

    @Autowired
    private TaxService taxService;

    public PrintServiceImpl() {
        df = new DecimalFormat("#0.00");
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
    }

    @Override
    public void print(List<Input> inputList) {
        double totalTaxes = 0.0d;
        double totalAmount = 0.0d;
        for (Input input : inputList) {
            Output output = taxService.applyTaxes(input);
            totalTaxes += output.getSalesTax();
            totalAmount += output.getAmount();
            LOG.info("--> " + output.getQuantity() + " " + output.getDescription() + " at " + df.format(output.getAmount()));
        }
        LOG.info("Sales Taxes: " + df.format(totalTaxes));
        LOG.info("Total: " + df.format(totalAmount));
        LOG.info("=============================================");
    }
}
