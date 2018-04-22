package net.sovdat.blaz.incremental.sliding;

import org.junit.Test;

import net.sovdat.blaz.incremental.Calculator;
import net.sovdat.blaz.incremental.sliding.SlidingGiniCalculator;
import net.sovdat.blaz.incremental.util.StringClassGenerator;

public class TestSlidingGiniCalculator {
	@Test
	public void testSlidingGiniUniformDistributionNoDrift() {
		final int windowSize = 500;
		final int numberOfClasses = 5;
		final int numberOfSamples = 10000;

		StringClassGenerator elementGenerator = new StringClassGenerator(numberOfClasses);
		Calculator<String> slidingGiniCalc = new SlidingGiniCalculator<>(windowSize);
		for (int i = 0; i < numberOfSamples; i++) {
			slidingGiniCalc.process(elementGenerator.nextValue());
		}

		final double exactGini = 1 - 1.0 / numberOfClasses;

		org.junit.Assert.assertEquals(exactGini, slidingGiniCalc.getValue(), 1e-2);
	}
}
