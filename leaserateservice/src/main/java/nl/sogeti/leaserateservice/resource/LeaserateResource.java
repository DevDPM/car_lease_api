package nl.sogeti.leaserateservice.resource;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class LeaserateResource implements CarsApi {


    @Override
    public Response get(Integer id, Integer mileage, Integer duration, Integer interestrate) {
        return Response.ok().build();
    }
}