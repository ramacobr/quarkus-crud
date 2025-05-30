package org.ramacox.boundary;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ramacox.entity.ProductPanache;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/products")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {


     @GET
     @RolesAllowed("user")
     public Uni<Response> getProducts() {
         return ProductPanache.listAll()
                 .onItem().transform(products -> Response.ok(products).build());
     }

    @GET
    @Path("{id}")
    @RolesAllowed("user")
    public Uni<Response> getSingleProduct(@PathParam("id") Long id) {
        return ProductPanache.findById(id)
                .onItem().ifNotNull().transform(product -> Response.ok(product).build())
                .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND)::build);
    }

    @POST
    @RolesAllowed("user")
    public Uni<Response> addProduct(ProductPanache product) {
         return Panache
                 .withTransaction(product::persist)
                 .replaceWith(Response.ok(product).status(CREATED).build());
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("user")
    public Uni<Response> delete(@PathParam("id") Long id) {
         return ProductPanache
                 .deleteProduct(id)
                 .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                 .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND)::build);
    }

    @PUT
    @RolesAllowed("user")
    public Uni<Response> update(ProductPanache product) {
        if (product == null || product.description == null) {
            throw new WebApplicationException("Product description was not set on request.", 422);
        }
        return ProductPanache.updateProduct(product.id, product)
                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND)::build);
    }
}
