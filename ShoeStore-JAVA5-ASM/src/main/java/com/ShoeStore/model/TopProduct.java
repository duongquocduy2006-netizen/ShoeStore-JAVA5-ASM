package com.ShoeStore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopProduct {
    private String name;
    private String category;
    private int sales;
    private String revenue;
    private String image;
}