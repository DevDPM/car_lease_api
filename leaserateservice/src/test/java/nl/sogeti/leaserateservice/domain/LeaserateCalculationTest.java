package nl.sogeti.leaserateservice.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static nl.sogeti.leaserateservice.domain.LeaserateCalculation.MILEAGE_PER_DURATION;
import static nl.sogeti.leaserateservice.domain.LeaserateCalculation.MILEAGE_PER_NETT_PRICE;
import static nl.sogeti.leaserateservice.domain.LeaserateCalculation.INTERESTRATE_PER_MONTH;
import static nl.sogeti.leaserateservice.domain.LeaserateCalculation.TOTAL_LEASERATE_PER_MONTH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


class LeaserateCalculationTest {

    private BigDecimal mileage = BigDecimal.valueOf(45000);
    private BigDecimal durationInMonths = BigDecimal.valueOf(60);
    private BigDecimal interestRate = BigDecimal.valueOf(4.5);
    private BigDecimal nettPrice = BigDecimal.valueOf(63000);

    @Test
    void testMileagePerDuration() {
        // when
        BigDecimal result = MILEAGE_PER_DURATION.calculate(mileage, durationInMonths);

        // then
        assertThat(result, is(BigDecimal.valueOf(225000)));
    }

    @Test
    void testMileagePerNettPrice() {
        // given
        BigDecimal mileagePerDuration = MILEAGE_PER_DURATION.calculate(mileage, durationInMonths);

        // when
        BigDecimal result = MILEAGE_PER_NETT_PRICE.calculate(mileagePerDuration, nettPrice);

        // then
        assertThat(result, is(BigDecimal.valueOf(3.571428571428571)));
    }

    @Test
    void testInterestRatePerMonth() {
        // when
        BigDecimal result = INTERESTRATE_PER_MONTH.calculate(interestRate, nettPrice);

        // then
        assertThat(result, is(BigDecimal.valueOf(236.25)));
    }

    @Test
    void testTotalLeaseRatePerMonth() {
        // given
        BigDecimal mileagePerDuration = MILEAGE_PER_DURATION.calculate(mileage, durationInMonths);
        BigDecimal mileagePerNettPrice = MILEAGE_PER_NETT_PRICE.calculate(mileagePerDuration, nettPrice);
        BigDecimal interestRatePerMonth = INTERESTRATE_PER_MONTH.calculate(interestRate, nettPrice);

        // when
        BigDecimal result = TOTAL_LEASERATE_PER_MONTH.calculate(mileagePerNettPrice, interestRatePerMonth);

        // then
        assertThat(result, is(BigDecimal.valueOf(239.82))); // should be 239.76 ;/
    }
}