package net.sovdat.blaz.incremental.damping;

import org.junit.Test;

import net.sovdat.blaz.incremental.Calculator;
import net.sovdat.blaz.incremental.util.StringClassGenerator;

public class TestDampingGiniCalculator {
	@Test
	public void testDampingGiniUniformDistributionNoDrift() {
		final double dampingFactor = 1.0;
		final int numberOfClasses = 5;
		final int numberOfSamples = 10000;

		StringClassGenerator elementGenerator = new StringClassGenerator(numberOfClasses);
		Calculator<String> dampingGiniCalc = new DampingGiniCalculator<>(dampingFactor);
		for (int i = 0; i < numberOfSamples; i++) {
			dampingGiniCalc.process(elementGenerator.nextValue());
		}

		final double exactGini = 1 - 1.0 / numberOfClasses;

		org.junit.Assert.assertEquals(exactGini, dampingGiniCalc.getValue(), 1e-3);
	}
}
