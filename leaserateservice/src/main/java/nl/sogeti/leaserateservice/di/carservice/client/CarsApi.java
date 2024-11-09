package nl.sogeti.leaserateservice.di.carservice.client;

import jakarta.ws.rs.*;
import nl.sogeti.leaserateservice.di.carservice.dto.CarDetails;
import nl.sogeti.leaserateservice.di.carservice.exception.ApiException;
import nl.sogeti.leaserateservice.di.carservice.exception.mapper.ApiExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@RegisterProvider(ApiExceptionMapper.class)
@Path("/cars")
public interface CarsApi {

    @POST
    @Consumes({ "application/json" })
    void create(CarDetails carDetails) throws ApiException, ProcessingException;

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Integer id) throws ApiException, ProcessingException;

    @GET
    @Path("/{id}")
    @Produces({ "application/json" })
    CarDetails get(@PathParam("id") Integer id) throws ApiException, ProcessingException;

    @GET
    @Produces({ "application/json" })
    CarDetails getAll() throws ApiException, ProcessingException;

    @PUT
    @Path("/{id}")
    @Consumes({ "application/json" })
    void update(@PathParam("id") Integer id, CarDetails carDetails) throws ApiException, ProcessingException;
}
