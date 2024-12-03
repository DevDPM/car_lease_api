package nl.sog.leaserateservice.domain;

import nl.sog.leaserateservice.exception.MissingLeaserateInputArgument;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static nl.sog.leaserateservice.domain.LeaserateCalculation.*;

public class LeaserateCalculator {
    private BigDecimal mileage;
    private BigDecimal durationInMonths;
    private BigDecimal interestRate;
    private BigDecimal nettPrice;

    private LeaserateCalculator() {}

    public static LeaserateCalculator getInstance() {
        return new LeaserateCalculator();
    }

    public LeaserateCalculator mileage(BigDecimal mileage) {
        if (mileage == null) {
            throw new IllegalArgumentException("mileage cannot be null");
        }
        this.mileage = mileage;
        return this;
    }

    public LeaserateCalculator duration(BigDecimal months) {
        if (months == null) {
            throw new IllegalArgumentException("months cannot be null");
        }
        this.durationInMonths = months;
        return this;
    }

    public LeaserateCalculator interestRate(BigDecimal interestRate) {
        if (interestRate == null) {
            throw new IllegalArgumentException("interestRate cannot be null");
        }
        this.interestRate = interestRate;
        return this;
    }

    public LeaserateCalculator nettPrice(BigDecimal nettPrice) {
        if (nettPrice == null) {
            throw new IllegalArgumentException("nettPrice cannot be null");
        }
        this.nettPrice = nettPrice;
        return this;
    }

    public BigDecimal calculateLeaseRate() {
        validateLeaserateInputArgs();

        BigDecimal mileagePerDuration = MILEAGE_PER_DURATION.calculate(mileage, durationInMonths);
        BigDecimal mileagePerNettPrice = MILEAGE_PER_NETT_PRICE.calculate(mileagePerDuration, nettPrice);
        BigDecimal interestRatePerMonth = INTERESTRATE_PER_MONTH.calculate(interestRate, nettPrice);
        BigDecimal totalLeaseRatePerMonth = TOTAL_LEASERATE_PER_MONTH.calculate(mileagePerNettPrice, interestRatePerMonth);

        return totalLeaseRatePerMonth.setScale(2, RoundingMode.HALF_UP);
    }

    private void validateLeaserateInputArgs() {
        if (mileage == null || durationInMonths == null ||
            interestRate == null || nettPrice == null) {
            throw new MissingLeaserateInputArgument();
        }
    }
}
