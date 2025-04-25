package org.ramacox.boundary;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ramacox.entity.ProductPanache;

@Path("/v1/products")
public class ProductResource {

     @GET
     @Produces(MediaType.APPLICATION_JSON)
     public Uni<Response> getProducts() {
         return ProductPanache.getAllProducts()
                 .onItem().transform(products -> Response.ok(products))
                 .onItem().transform(Response.ResponseBuilder::build);
     }
}
