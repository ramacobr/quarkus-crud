package org.ramacox.entity;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity(name="product")
public class ProductPanache extends PanacheEntity {
    public String name;
    public String description;
    public Double price;

    public static Uni<ProductPanache> findProductById(Long id) {
        return findById(id);
    }

    @WithTransaction
    public static Uni<ProductPanache> updateProduct(Long id, ProductPanache product) {
        return Panache.withTransaction(() -> findProductById(id)
                .onItem().ifNotNull()
                .transform(entity -> {
                    entity.description = product.description;
                    entity.name = product.name;
                    entity.price = product.price;
                    return entity;
                })
                .onItem().ifNull().fail()
                .onFailure().recoverWithNull());
    }

    @WithTransaction
    public static Uni<Boolean> deleteProduct(Long id) {
        return ProductPanache.deleteById(id);
    }

}
