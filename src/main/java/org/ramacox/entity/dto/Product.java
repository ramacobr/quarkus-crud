package org.ramacox.entity.dto;

import lombok.*;
import org.ramacox.entity.ProductPanache;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Product {
    public String name;
    public Long id;
    public String description;
    public Double price;

    public static Product fromProductEntity(ProductPanache productPanache) {
        return Product.builder()
                .name(productPanache.name)
                .id(productPanache.id)
                .description(productPanache.description)
                .price(productPanache.price).build();
    }

    public ProductPanache toProductEntity() {
        return ProductPanache.builder()
                .name(this.name)
                .description(this.description)
                .price(this.price).build();
    }
}
