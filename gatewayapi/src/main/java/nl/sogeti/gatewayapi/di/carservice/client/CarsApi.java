/**
 * Car service
 * REST Entity service about car details
 *
 * The version of the OpenAPI document: 0.1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package nl.sogeti.gatewayapi.di.carservice.client;

import jakarta.ws.rs.core.Response;

import jakarta.ws.rs.*;


import nl.sogeti.gatewayapi.di.carservice.dto.CarDetails;
import nl.sogeti.gatewayapi.di.carservice.dto.CarsDetails;
import nl.sogeti.gatewayapi.di.carservice.exception.ApiException;
import nl.sogeti.gatewayapi.di.carservice.exception.ApiExceptionMapper;
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
public interface CarsApi  {

    /**
     * Retrieve all cars details
     *
     */
    @POST
    
    @Consumes({ "application/json" })
    Response create(CarDetails carDetails) throws ApiException, ProcessingException;

    /**
     * Retrieve car details
     *
     */
    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") Integer id) throws ApiException, ProcessingException;

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
    CarsDetails getAll() throws ApiException, ProcessingException;

    /**
     * Retrieve car details
     *
     */
    @PUT
    @Path("/{id}")
    @Consumes({ "application/json" })
    Response update(@PathParam("id") Integer id, CarDetails carDetails) throws ApiException, ProcessingException;
}
