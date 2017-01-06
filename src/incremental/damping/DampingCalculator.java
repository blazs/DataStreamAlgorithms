package incremental.damping;

import incremental.Calculator;

/**
 * Interface for classes that implement the damping version of the updating
 * formulas for entropy and Gini index.
 *
 * @author	Jean Paul Barddal (jpbarddal@gmail.com)
 * @author	Blaz Sovdat (blaz.sovdat@gmail.com)
 **/
public interface DampingCalculator<T> extends Calculator<T> {
	/**
	 * Returns the damping factor, also know as fading factor, used.
	 * 
	 * @return	The damping factor
	 */
	double getDampingFactor();
}
