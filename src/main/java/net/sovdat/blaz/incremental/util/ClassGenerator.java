package net.sovdat.blaz.incremental.util;

import java.util.Random;

import net.sovdat.blaz.incremental.StreamElement;

/**
 * Abstract class that defines the behavior for stream generators.
 *
 * @author	Jean Paul Barddal (jpbarddal@gmail.com).
 * @author	Blaz Sovdat (blaz.sovdat@gmail.com).
 */
public abstract class ClassGenerator<T> {
	protected int numClasses;
	protected Random randomizer;

	/**
	 * Constructor.
	 * 
	 * @param numClasses Number of different class labels
	 */
	public ClassGenerator(int numClasses) {
		if (numClasses < 1) {
			throw new IllegalArgumentException("Expected number of classes to be at least 1");
		}

		this.numClasses = numClasses;
		this.randomizer = new Random();
	}

	/**
	 * Provides a new value amongst the <code>numClasses</code> 
	 * different values with the same probability.
	 * 
	 * @return A stream element
	 */
	public abstract StreamElement<T> nextValue();

	/**
	 * 
	 * @return All possible values of the generator.
	 */
	public abstract T[] getPossibleValues();	
}