package org.ramacox.entity.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Product {
    public String name;
    public Long id;
    public String description;
    public Double price;
}
