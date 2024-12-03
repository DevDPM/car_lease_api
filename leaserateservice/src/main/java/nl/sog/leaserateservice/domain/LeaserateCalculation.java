package nl.sog.leaserateservice.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.BinaryOperator;

public enum LeaserateCalculation {
    /**
     * Calculate mileage per duration
     * calculate args:
     * - mileage per year
     * - duration in months
     *
     * returns mileage per duration as BigDecimal
     * */
    MILEAGE_PER_DURATION((mileagePerYear, durationInMonths) ->
            mileagePerYear.divide(BigDecimal.valueOf(12), new MathContext(10, RoundingMode.HALF_EVEN))
                          .multiply(durationInMonths, new MathContext(10, RoundingMode.HALF_EVEN))),

    /**
     * Calculate mileage nett price
     * calculate args:
     * - mileage per duration in months
     * - duration in months
     *
     * returns mileage per nett price as BigDecimal
     * */
    MILEAGE_PER_NETT_PRICE((mileagePerDuration, nettPrice) ->
        mileagePerDuration.divide(nettPrice, new MathContext(16, RoundingMode.HALF_EVEN))),

    /**
     * Calculate interestrate per month
     * calculate args:
     * - interestrate
     * - nett price
     *
     * returns interestrate per month BigDecimal
     * */
    INTERESTRATE_PER_MONTH((interestRate, nettPrice) ->
            interestRate.divide(BigDecimal.valueOf(100), new MathContext(10, RoundingMode.HALF_EVEN))
                        .multiply(nettPrice, new MathContext(10, RoundingMode.HALF_EVEN))
                        .divide(BigDecimal.valueOf(12), new MathContext(5, RoundingMode.HALF_EVEN))),

    /**
     * Calculate total leaserate per month
     * calculate args:
     * - mileage per nett price
     * - interestrate per month
     *
     * returns total leaserate per month with 2 decimals as BigDecimal
     * */
    TOTAL_LEASERATE_PER_MONTH((mileagePerNettPrice, interestRatePerMonth) ->
            mileagePerNettPrice.add(interestRatePerMonth, new MathContext(10, RoundingMode.HALF_EVEN))
                               .setScale(2, RoundingMode.HALF_EVEN));

    private final BinaryOperator<BigDecimal> calculation;

    LeaserateCalculation(BinaryOperator<BigDecimal> calculation) {
        this.calculation = calculation;
    }

    public BigDecimal calculate(BigDecimal a, BigDecimal b) {
        return calculation.apply(a, b);
    }
}
