package incremental;

import incremental.util.StringClassGenerator;
import incremental.damping.*;
import incremental.sliding.*;

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
			for (int index = 0; index < calculators.length; index++) {
				calculators[index].process(value);
			}
		}

		for (int index = 0; index < calculators.length; index++) {
			System.out.println(calculators[index].getClass().getName() + ": " + calculators[index].getValue());
		}
	}
}
