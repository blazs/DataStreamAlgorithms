package incremental.util;

import java.lang.Math;
import java.util.stream.IntStream;

import incremental.StreamElement;

/**
 * This class is used to generate string classes, each of which with the same
 * probability (uniform distribution).
 *
 * @author Jean Paul Barddal (jpbarddal@gmail.com).
 * @author Blaz Sovdat (blaz.sovdat@gmail.com).
 *
 */
public class StringClassGenerator extends ClassGenerator<String> {
	
	/**
	 * String class generator constructor. Instantiates a generator that will
	 * generate <code>numClasses</code> different values given an uniform
	 * distribution.
	 * 
	 * @param numClasses,
	 *            the amount of values to be generated.
	 */
	public StringClassGenerator(int numClasses) {
		super(numClasses);
	}

	@Override
	public StreamElement<String> nextValue() {
		final String label = "class" + (Math.abs(randomizer.nextLong()) % numClasses);
		return new StreamElement<>(label, 1.0);
	}

	@Override
	public String[] getPossibleValues(){
		return IntStream.range(0, numClasses).boxed()
				.map(i -> "class" + i)
				.toArray(String[]::new);
	}
}