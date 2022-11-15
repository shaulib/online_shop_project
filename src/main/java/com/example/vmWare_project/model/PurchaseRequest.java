package com.example.vmWare_project.model;

import lombok.Data;

@Data
public class PurchaseRequest {
    private String CustomerName;
    private String CustomerPhone;
    private String CustomerEmail;
    private String ItemId;
    private String creditCardNumber;
    private Double AmountToCharge;
}
