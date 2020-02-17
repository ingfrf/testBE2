package com.example;

import com.example.SalesTaxesApplication;
import com.example.enums.Type;
import com.example.model.Input;
import com.example.model.Output;
import com.example.service.PrintService;
import com.example.service.TaxService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SalesTaxesApplication.class})
@SpringBootTest
public class SalesTaxesApplicationTests {
    private static final double DELTA = 1e-10;

    @Autowired
    private PrintService printService;

    @Autowired
    private TaxService taxService;

    @Test
    public void testPrintServiceDependency() {
        assertEquals("PrintServiceImpl", printService.getClass().getSimpleName());
    }

    @Test
    public void testTaxServiceDependency() {
        assertEquals("TaxServiceImpl", taxService.getClass().getSimpleName());
    }

    @Test
    public void testInput1() {
        List<Input> inputList = new ArrayList<>();
        inputList.add(new Input("book", 12.49, 1, Type.BOOK, false));
        inputList.add(new Input( "music CD", 14.99, 1, Type.OTHER, false));
        inputList.add(new Input( "chocolate bar", 0.85, 1, Type.FOOD, false));
        double totalTaxes = 0.0d;
        double totalAmount = 0.0d;
        List<Output> outputList = new ArrayList<>();
        for (Input input : inputList) {
            Output output = taxService.applyTaxes(input);
            totalTaxes += output.getSalesTax();
            totalAmount += output.getAmount();
            outputList.add(output);
        }

        assertThat(outputList, hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(0.0d, DELTA))),
                        hasProperty("amount", is(closeTo(12.49, DELTA)))
                )

        ));
        assertThat(outputList, hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(1.50, DELTA))),
                        hasProperty("amount", is(closeTo(16.49, DELTA)))
                )

        ));
        assertThat(outputList, hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(0.0d, DELTA))),
                        hasProperty("amount", is(closeTo(0.85, DELTA)))
                )

        ));

        assertEquals(1.50, totalTaxes, DELTA);
        assertEquals(29.83, totalAmount, DELTA);
    }

    @Test
    public void testInput2() {
        List<Input> inputList = new ArrayList<>();

        inputList.add(new Input( "imported box of chocolates", 10.00, 1, Type.FOOD, true));
        inputList.add(new Input( "imported bottle of perfume", 47.50, 1, Type.OTHER, true));

        double totalTaxes = 0.0d;
        double totalAmount = 0.0d;
        List<Output> outputList = new ArrayList<>();
        for (Input input : inputList) {
            Output output = taxService.applyTaxes(input);
            totalTaxes += output.getSalesTax();
            totalAmount += output.getAmount();
            outputList.add(output);
        }

        assertThat(outputList, hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(0.50, DELTA))),
                        hasProperty("amount", is(closeTo(10.50, DELTA)))
                )

        ));
        assertThat(outputList, hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(7.15, DELTA))),
                        hasProperty("amount", is(closeTo(54.65, DELTA)))
                )

        ));

        assertEquals(7.65, totalTaxes, DELTA);
        assertEquals(65.15, totalAmount, DELTA);
    }

    @Test
    public void testInput3() {
        List<Input> inputList = new ArrayList<>();

        inputList.add(new Input( "imported bottle of perfume", 27.99, 1, Type.OTHER, true));
        inputList.add(new Input( "bottle of perfume", 18.99, 1, Type.OTHER, false));
        inputList.add(new Input( "package of headache pills", 9.75, 1, Type.MEDICAL, false));
        inputList.add(new Input( "box of imported chocolates", 11.25, 1, Type.FOOD, true));

        double totalTaxes = 0.0d;
        double totalAmount = 0.0d;
        List<Output> outputList = new ArrayList<>();
        for (Input input : inputList) {
            Output output = taxService.applyTaxes(input);
            totalTaxes += output.getSalesTax();
            totalAmount += output.getAmount();
            outputList.add(output);
        }

        assertThat(outputList, hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(4.20, DELTA))),
                        hasProperty("amount", is(closeTo(32.19, DELTA)))
                )

        ));
        assertThat(outputList, hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(1.9, DELTA))),
                        hasProperty("amount", is(closeTo(20.89, DELTA)))
                )

        ));
        assertThat(outputList, hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(0.0d, DELTA))),
                        hasProperty("amount", is(closeTo(9.75, DELTA)))
                )

        ));
        assertThat(outputList, hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(0.60, DELTA))),
                        hasProperty("amount", is(closeTo(11.85, DELTA)))
                )

        ));

        assertEquals(6.70, totalTaxes, DELTA);
        assertEquals(74.68, totalAmount, DELTA);
    }
}