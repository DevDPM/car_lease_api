package nl.sogeti.leaserateservice.resource;

import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import nl.sogeti.leaserateservice.dto.LeaseRateDetails;
import nl.sogeti.leaserateservice.service.LeaserateService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Provider
@RolesAllowed({"Fun-User"})
public class LeaserateResource implements CarsApi {

    private final LeaserateService leaserateService;

    // TEMPORARY CODE FOR GENERATING JWT FOR TESTING PURPOSE
//    {
//        String token =
//                Jwt.issuer("http://localhost:9999/")
//                        .upn("daniel@example.com")
//                        .groups(new HashSet<>(List.of("Fun-User")))
//                        .sign();
//        System.out.println(token);
//    }

    @Inject
    public LeaserateResource(LeaserateService leaserateService) {
        this.leaserateService = leaserateService;
    }

    @Override
    public Response get(Integer carId, Integer mileage, Integer duration, BigDecimal interestrate) {
        LeaseRateDetails leaseRateDetails = leaserateService.getLeaserateDetails(carId, mileage, duration, interestrate);
        return Response.ok(leaseRateDetails).build();
    }
}