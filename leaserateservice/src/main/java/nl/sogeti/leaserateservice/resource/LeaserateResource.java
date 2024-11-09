package nl.sogeti.leaserateservice.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import nl.sogeti.leaserateservice.dto.LeaseRateDetails;
import nl.sogeti.leaserateservice.service.LeaserateService;

import java.math.BigDecimal;

@Provider
public class LeaserateResource implements CarsApi {

    private final LeaserateService leaserateService;

    @Inject
    public LeaserateResource(LeaserateService leaserateService) {
        this.leaserateService = leaserateService;
    }

    @Override
    public Response get(Integer carId, Integer mileage, Integer duration, BigDecimal interestrate) {
        LeaseRateDetails leaseRateDetails = leaserateService.calculateLeaserate(carId, mileage, duration, interestrate);
        return Response.ok(leaseRateDetails).build();
    }
}