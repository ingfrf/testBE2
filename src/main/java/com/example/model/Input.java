package com.example.model;

import com.example.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Input {
    private @Getter String description;
    private @Getter double price;
    private @Getter int quantity;
    private @Getter Type type;
    private @Getter boolean isImported;
}
