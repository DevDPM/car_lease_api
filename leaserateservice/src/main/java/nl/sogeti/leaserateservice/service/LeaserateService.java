package nl.sogeti.leaserateservice.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import nl.sogeti.leaserateservice.di.carservice.dto.CarDetails;
import nl.sogeti.leaserateservice.di.carservice.client.CarsApiClient;
import nl.sogeti.leaserateservice.dto.LeaseRateDetails;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Dependent
public class LeaserateService {

    private final CarsApiClient carsApiClient;

    @Inject
    public LeaserateService(CarsApiClient carsApiClient) {
        this.carsApiClient = carsApiClient;
    }

    public LeaseRateDetails calculateLeaserate(Integer carId, Integer mileage, Integer duration, BigDecimal interestrate) {
        CarDetails carDetails = carsApiClient.getCarDetails(carId);
        BigDecimal leaserate = calculateLeaserate(mileage, duration, carDetails.getNett(), interestrate);

        LeaseRateDetails leaseRateDetails = new LeaseRateDetails();
        leaseRateDetails.setLeaserate(leaserate);
        return leaseRateDetails;
    }

    private BigDecimal calculateLeaserate(Integer mileage, Integer durationInMonths, BigDecimal nett, BigDecimal interestrate) {
        BigDecimal mileageBigDecimal = new BigDecimal(mileage);
        BigDecimal durationBigDecimal = new BigDecimal(durationInMonths);
        MathContext mathConfigs = new MathContext(10, RoundingMode.HALF_UP);

        BigDecimal leaserate = calculateLeaseratePerMonth(nett, interestrate, mileageBigDecimal, durationBigDecimal, mathConfigs);
        return leaserate.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateLeaseratePerMonth(BigDecimal nett, BigDecimal interestrate, BigDecimal mileageBigDecimal, BigDecimal durationBigDecimal, MathContext mathConfigs) {
        BigDecimal numOfmonthsPerYear = BigDecimal.valueOf(12);

        BigDecimal totalMileageInDurationPerNett = calculateMileageInDurationPerNett(nett, mileageBigDecimal, durationBigDecimal, numOfmonthsPerYear, mathConfigs);
        BigDecimal totalNettWithInterestPerMonth = calculateNettWithInterestPerMonth(nett, interestrate, mathConfigs, numOfmonthsPerYear);

        return totalMileageInDurationPerNett.add(totalNettWithInterestPerMonth, mathConfigs);
    }

    private static BigDecimal calculateNettWithInterestPerMonth(BigDecimal nett, BigDecimal interestrate, MathContext mathConfigs, BigDecimal numOfmonthsPerYear) {
        BigDecimal normalizedInterestrate = interestrate.divide(BigDecimal.valueOf(100), mathConfigs);
        BigDecimal totalNettWithInterest = normalizedInterestrate.multiply(nett, mathConfigs);
        return totalNettWithInterest.divide(numOfmonthsPerYear, mathConfigs);
    }

    private BigDecimal calculateMileageInDurationPerNett(BigDecimal nett, BigDecimal mileageBigDecimal, BigDecimal durationBigDecimal, BigDecimal numOfmonthsPerYear, MathContext mathConfigs) {
        BigDecimal mileagePerMonth = mileageBigDecimal.divide(numOfmonthsPerYear, mathConfigs);
        BigDecimal totalMileageInDuration = mileagePerMonth.multiply(durationBigDecimal, mathConfigs);
        return totalMileageInDuration.divide(nett, mathConfigs);
    }
}
