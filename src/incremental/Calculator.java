package incremental;

/**
 * This interface defines a set of operations that we expect each type of class
 * for computing entropy or Gini index to implement.
 *
 * @author Jean Paul Barddal (jpbarddal@gmail.com).
 * @author Blaz Sovdat (blaz.sovdat@gmail.com).
 * 
 * @param <T>
 *            Type of the label. Typical choices are `T=Integer` (e.g. an
 *            identifier) and `T=String` (e.g. an attribute name).
 */
public interface Calculator<T> {
	/**
	 * Processes a stream element and updates the statistic to reflect changes
	 * made by the element to the underlying discrete distribution.
	 * 
	 * @param element	Stream element.
	 */
	void process(StreamElement<T> element);

	/**
	 * Returns the value of the statistic of the underlying distribution.
	 * 
	 * @return	The current value of the statistic.
	 */
	double getValue();
}
