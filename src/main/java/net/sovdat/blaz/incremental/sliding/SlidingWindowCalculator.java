package net.sovdat.blaz.incremental.sliding;

import net.sovdat.blaz.incremental.Calculator;

/**
 * Interface for classes that implement the sliding-window version of the
 * updating formulas.
 *
 * @author	Jean Paul Barddal (jpbarddal@gmail.com)
 * @author	Blaz Sovdat (blaz.sovdat@gmail.com)
 *
 */
public interface SlidingWindowCalculator<T> extends Calculator<T> {
	/**
	 * Returns the size of the sliding window.
	 * 
	 * @return	The size of the sliding window.
	 */
	int getWindowSize();
}
