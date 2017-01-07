package incremental;

import incremental.damping.DampingEntropyCalculator;
import incremental.damping.DampingGiniCalculator;
import incremental.sliding.SlidingEntropyCalculator;
import incremental.sliding.SlidingGiniCalculator;
import incremental.util.StringClassGenerator;

/**
 * A simple demonstration of how to use the calculators. Recall that the entropy
 * of a 4-valued uniform random variable is log_2 4 = 2.0, and that its Gini
 * index is 1-1/4 = 3/4 = 0.75.
 * 
 * In general, the entropy of a uniform n-valued discrete random variable is
 * log_2 n, and the Gini index of this variable is 1-1/n.
 * 
 * @author Jean Paul Barddal (jpbarddal@gmail.com).
 * @author Blaz Sovdat (blaz.sovdat@gmail.com).
 *
 */
public class SimpleUsageExample {
	public static void main(String[] args) {
		final int numClasses = 4;
		final int numValues = 10000;

		StringClassGenerator generator = new StringClassGenerator(numClasses);

		@SuppressWarnings("unchecked")
		Calculator<String>[] calculators = new Calculator[4];

		calculators[0] = new SlidingEntropyCalculator<String>(50);
		calculators[1] = new DampingEntropyCalculator<String>(0.99999);
		calculators[2] = new SlidingGiniCalculator<String>(50);
		calculators[3] = new DampingGiniCalculator<String>(0.99999);

		for (int i = 0; i < numValues; i++) {
			StreamElement<String> value = generator.nextValue();
			for (Calculator<String> calc : calculators) {
				calc.process(value);
			}
		}

		for (Calculator<String> calc : calculators) {
			System.out.println(calc.getClass().getName() + ": " + calc.getValue());
		}
	}
}
