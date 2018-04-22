package net.sovdat.blaz.incremental.sliding;

import org.junit.Test;

import net.sovdat.blaz.incremental.Calculator;
import net.sovdat.blaz.incremental.Utils;
import net.sovdat.blaz.incremental.sliding.SlidingEntropyCalculator;
import net.sovdat.blaz.incremental.util.StringClassGenerator;

public class TestSlidingEntropyCalculator {
	@Test
	public void testSlidingEntropyUniformDistributionNoDrift() {
		final int windowSize = 500;
		final int numberOfClasses = 5;
		final int numberOfSamples = 10000;

		StringClassGenerator elementGenerator = new StringClassGenerator(numberOfClasses);
		Calculator<String> slidingEntropyCalc = new SlidingEntropyCalculator<String>(windowSize);
		for (int i = 0; i < numberOfSamples; i++) {
			slidingEntropyCalc.process(elementGenerator.nextValue());
		}

		final double exactEntropy = Utils.log2(numberOfClasses);

		org.junit.Assert.assertEquals(exactEntropy, slidingEntropyCalc.getValue(), 1e-2);
	}
}
