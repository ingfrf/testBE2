package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Output {
    private @Getter String description;
    private @Getter int quantity;
    private @Getter double salesTax;
    private @Getter double amount;
}
