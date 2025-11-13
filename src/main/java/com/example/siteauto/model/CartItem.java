package com.example.siteauto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private Product product;
    private int quantity;

    public BigDecimal getTotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
