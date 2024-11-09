package nl.sogeti.leaserateservice.di.carservice.client;

import jakarta.ws.rs.*;
import nl.sogeti.leaserateservice.di.carservice.dto.CarDetails;
import nl.sogeti.leaserateservice.di.carservice.exception.ApiException;
import nl.sogeti.leaserateservice.di.carservice.exception.mapper.ApiExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * Car service
 *
 * <p>REST Entity service about car details
 *
 */

@RegisterRestClient
@RegisterProvider(ApiExceptionMapper.class)
@Path("/cars")
public interface CarsApi {

    /**
     * Retrieve all cars details
     *
     */
    @POST

    @Consumes({ "application/json" })
    void create(CarDetails carDetails) throws ApiException, ProcessingException;

    /**
     * Retrieve car details
     *
     */
    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Integer id) throws ApiException, ProcessingException;

    /**
     * Retrieve car details
     *
     */
    @GET
    @Path("/{id}")
    @Produces({ "application/json" })
    CarDetails get(@PathParam("id") Integer id) throws ApiException, ProcessingException;

    /**
     * Retrieve all cars details
     *
     */
    @GET

    @Produces({ "application/json" })
    CarDetails getAll() throws ApiException, ProcessingException;

    /**
     * Retrieve car details
     *
     */
    @PUT
    @Path("/{id}")
    @Consumes({ "application/json" })
    void update(@PathParam("id") Integer id, CarDetails carDetails) throws ApiException, ProcessingException;
}
