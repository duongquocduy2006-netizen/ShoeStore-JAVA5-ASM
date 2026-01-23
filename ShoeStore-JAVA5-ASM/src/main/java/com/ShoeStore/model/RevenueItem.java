package com.ShoeStore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               
@AllArgsConstructor  
@NoArgsConstructor   
public class RevenueItem {
    private String month;
    private int percent;
    private String value;
}