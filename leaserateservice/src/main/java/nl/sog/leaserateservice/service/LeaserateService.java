package nl.sog.leaserateservice.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import nl.sog.leaserateservice.di.carservice.client.CarsApiClient;
import nl.sog.leaserateservice.di.carservice.dto.CarDetails;
import nl.sog.leaserateservice.domain.LeaserateCalculator;
import nl.sog.leaserateservice.dto.LeaseRateDetails;

import java.math.BigDecimal;

@Dependent
public class LeaserateService {

    private final CarsApiClient carsApiClient;

    @Inject
    public LeaserateService(CarsApiClient carsApiClient) {
        this.carsApiClient = carsApiClient;
    }

    public LeaseRateDetails getLeaserateDetails(Integer carId, Integer mileage, Integer duration, BigDecimal interestrate) {
        CarDetails carDetails = carsApiClient.getCarDetails(carId);
        BigDecimal leaseRatePerMonth = getLeaseRatePerMonth(mileage, duration, carDetails.getNett(), interestrate);

        LeaseRateDetails leaseRateDetails = new LeaseRateDetails();
        leaseRateDetails.setLeaseRate(leaseRatePerMonth);
        return leaseRateDetails;
    }

    private BigDecimal getLeaseRatePerMonth(Integer mileage, Integer durationInMonths, BigDecimal nettPrice, BigDecimal interestrate) {
        return LeaserateCalculator.getInstance()
                                  .mileage(BigDecimal.valueOf(mileage))
                                  .duration(BigDecimal.valueOf(durationInMonths))
                                  .nettPrice(nettPrice)
                                  .interestRate(interestrate)
                                  .calculateLeaseRate();
    }
}