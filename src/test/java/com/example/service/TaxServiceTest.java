package com.example.service;

import com.example.SalesTaxesApplication;
import com.example.enums.Type;
import com.example.model.Input;
import com.example.model.Output;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SalesTaxesApplication.class})
@SpringBootTest
public class TaxServiceTest {
    private static final double DELTA = 1e-10;

    @Autowired
    TaxService taxService;

    @Test
    public void testDependency() {
        assertEquals("TaxServiceImpl", taxService.getClass().getSimpleName());
    }

    @Test
    public void applyTaxesTaxFreeItem() {
        Input input = new Input( "book", 12.49, 1, Type.BOOK, false);
        Output output = taxService.applyTaxes(input);
        assertEquals(0.0d, output.getSalesTax(), DELTA);
        assertEquals(12.49, output.getAmount(), DELTA);
    }

    @Test
    public void applyTaxesBasicTaxItem() {
        Input input = new Input( "box of imported chocolates", 11.25, 1, Type.FOOD, true);
        Output output = taxService.applyTaxes(input);
        assertEquals(0.60, output.getSalesTax(), DELTA);
        assertEquals(11.85, output.getAmount(), DELTA);
    }

    @Test
    public void applyTaxesImportedTaxFreeItem() {
        Input input = new Input( "box of imported chocolates", 11.25, 1, Type.FOOD, true);
        Output output = taxService.applyTaxes(input);
        assertEquals(0.60, output.getSalesTax(), DELTA);
        assertEquals(11.85, output.getAmount(), DELTA);
    }

    @Test
    public void applyTaxesImportedBasicTaxItem() {
        Input input = new Input( "imported bottle of perfume", 47.50, 1, Type.OTHER, true);
        Output output = taxService.applyTaxes(input);
        assertEquals(7.15, output.getSalesTax(), DELTA);
        assertEquals(54.65, output.getAmount(), DELTA);
    }
}