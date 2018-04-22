package net.sovdat.blaz.incremental.util;

import java.util.stream.IntStream;

import net.sovdat.blaz.incremental.StreamElement;

/**
 * This class is used to generate numeric classes represented by integer
 * (discrete) values, each of which with the same probability (uniform
 * distribution).
 *
 * @author Jean Paul Barddal (jpbarddal@gmail.com).
 * @author Blaz Sovdat (blaz.sovdat@gmail.com).
 *
 */
public class NumericClassGenerator extends ClassGenerator<Integer> {
	/**
	 * String class generator constructor. Instantiates a generator that will
	 * generate 'numClasses' different values given an uniform distribution.
	 * 
	 * @param numClasses,
	 *            the amount of values to be generated.
	 */
	public NumericClassGenerator(int numClasses) {
		super(numClasses);
	}

	@Override
	public StreamElement<Integer> nextValue() {
		return new StreamElement<>(Integer.valueOf(randomizer.nextInt() % numClasses), 1.0);
	}

	@Override
	public Integer[] getPossibleValues(){
		return IntStream.range(0, numClasses).boxed()
				.toArray(Integer[]::new);
	}
}
