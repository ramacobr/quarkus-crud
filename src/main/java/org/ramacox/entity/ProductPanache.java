package org.ramacox.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name="product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPanache extends PanacheEntity {
    public String name;
    public String description;
    public Double price;

    public static ProductPanache findById(Long id) {
        return findById(id);
    }

    public static Uni<List<ProductPanache>> getAllProducts() {
        return findAll().list();
    }
}
