package com.example;

import com.example.enums.Type;
import com.example.model.Input;
import com.example.service.PrintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SalesTaxesApplication implements CommandLineRunner {
    private static Logger LOG = LoggerFactory.getLogger(SalesTaxesApplication.class);

    @Autowired
    private PrintService printService;

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(SalesTaxesApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        LOG.info("EXECUTING : command line runner");

        List<Input> inputList1 = new ArrayList<>();
        List<Input> inputList2 = new ArrayList<>();
        List<Input> inputList3 = new ArrayList<>();

        inputList1.add(new Input("book", 12.49, 1, Type.BOOK, false));
        inputList1.add(new Input( "music CD", 14.99, 1, Type.OTHER, false));
        inputList1.add(new Input("chocolate bar", 0.85, 1, Type.FOOD, false));

        inputList2.add(new Input( "imported box of chocolates", 10.00, 1, Type.FOOD, true));
        inputList2.add(new Input("imported bottle of perfume", 47.50, 1, Type.OTHER, true));

        inputList3.add(new Input("imported bottle of perfume", 27.99, 1, Type.OTHER, true));
        inputList3.add(new Input("bottle of perfume", 18.99, 1, Type.OTHER, false));
        inputList3.add(new Input( "package of headache pills", 9.75, 1, Type.MEDICAL, false));
        inputList3.add(new Input( "box of imported chocolates", 11.25, 1, Type.FOOD, true));

        printService.print(inputList1);
        printService.print(inputList2);
        printService.print(inputList3);
    }
}
