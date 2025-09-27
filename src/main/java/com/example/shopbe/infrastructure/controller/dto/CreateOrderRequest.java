package com.example.shopbe.infrastructure.controller.dto;

import java.util.List;

public class CreateOrderRequest {
    private String customerName;
    private String customerEmail;
    private List<CreateOrderItemRequest> items;

    public CreateOrderRequest() {}

    public CreateOrderRequest(String customerName, String customerEmail, List<CreateOrderItemRequest> items) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<CreateOrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<CreateOrderItemRequest> items) {
        this.items = items;
    }
}