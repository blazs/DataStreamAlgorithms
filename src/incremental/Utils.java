package incremental;

public class Utils {
	/**
	 * Computes base-2 logarithm of a non-negative double, with the property
	 * that log2(0) := 0.
	 * 
	 * @param x
	 *            Input argument (a non-negative <code>double</code>)
	 * @return Base-2 logarithm of <code>x</code>
	 */
	public static double log2(double x) {
		if (x < 0.0) {
			throw new IllegalArgumentException("Expected x>=0.0");
		}
		return x > 0.0 ? Math.log(x) / Math.log(2.0) : 0.0;
	}
}
