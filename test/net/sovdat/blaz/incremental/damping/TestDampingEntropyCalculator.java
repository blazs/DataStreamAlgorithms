package net.sovdat.blaz.incremental.damping;

import org.junit.Test;

import net.sovdat.blaz.incremental.Calculator;
import net.sovdat.blaz.incremental.Utils;
import net.sovdat.blaz.incremental.util.StringClassGenerator;

public class TestDampingEntropyCalculator {
	@Test
	public void testDampingEntropyUniformDistributionNoDrift() {
		final double dampingFactor = 1.0;
		final int numberOfClasses = 5;
		final int numberOfSamples = 10000;

		StringClassGenerator elementGenerator = new StringClassGenerator(numberOfClasses);
		Calculator<String> dampingEntropyCalc = new DampingEntropyCalculator<>(dampingFactor);
		for (int i = 0; i < numberOfSamples; i++) {
			dampingEntropyCalc.process(elementGenerator.nextValue());
		}

		final double exactEntropy = Utils.log2(numberOfClasses);

		org.junit.Assert.assertEquals(exactEntropy, dampingEntropyCalc.getValue(), 1e-3);
	}
}
