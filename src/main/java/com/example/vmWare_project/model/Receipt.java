package com.example.vmWare_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Receipt {
    private String itemId;
    private String itemName;
    private String itemDescription;
    private Double chargedAmount;
    private String customerName;
    private String customerCreditCard;
}
