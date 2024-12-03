/**
 * Customer service
 * REST Entity service about customer details
 *
 * The version of the OpenAPI document: 0.1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package nl.sog.gatewayapi.di.customerservice;

import jakarta.ws.rs.core.Response;
import nl.sog.gatewayapi.di.customerservice.dto.CustomerDetails;

import jakarta.ws.rs.*;


import nl.sog.gatewayapi.di.customerservice.dto.CustomersDetails;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * Customer service
 *
 * <p>REST Entity service about customer details
 *
 */

@RegisterRestClient
@RegisterProvider(ApiExceptionMapper.class)
@Path("/customers")
public interface CustomerApi  {

    /**
     * Retrieve all customers details
     *
     */
    @POST
    
    @Consumes({ "application/json" })
    Response create(CustomerDetails customerDetails) throws ApiException, ProcessingException;

    /**
     * Retrieve customer details
     *
     */
    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") Integer id) throws ApiException, ProcessingException;

    /**
     * Retrieve customer details
     *
     */
    @GET
    @Path("/{id}")
    @Produces({ "application/json" })
    CustomerDetails get(@PathParam("id") Integer id) throws ApiException, ProcessingException;

    /**
     * Retrieve all customers details
     *
     */
    @GET
    
    @Produces({ "application/json" })
    CustomersDetails getAll() throws ApiException, ProcessingException;

    /**
     * Retrieve customer details
     *
     */
    @PUT
    @Path("/{id}")
    @Consumes({ "application/json" })
    Response update(@PathParam("id") Integer id, CustomerDetails customerDetails) throws ApiException, ProcessingException;
}