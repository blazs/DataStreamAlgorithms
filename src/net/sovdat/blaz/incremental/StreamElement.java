package net.sovdat.blaz.incremental;

/**
 * Class intended for storing elements of a data stream.
 * 
 * An example would be data stream learning scenario where elements represent
 * values of a discrete attribute (e.g. <code>male</code> is a value of the
 * attribute <code>sex</code>) and the value represents the number of such
 * elements (e.g. <code>1.0</code>); in other words, the stream element
 * <code>(male,1.0)</code> would indicate that one example whose value of the
 * attribute <code>sex</code> equals <code>male</code> entered the stream.
 * 
 * The class <code>StreamElemenet</code> is more general in that the
 * <code>value</code> of the <code>label</code> represents the change to the
 * (not necessarily integer) "count" associated with the label.
 * 
 * The calculators working with <code>StreamElemenet</code>s compute the entropy
 * and the Gini index of the distribution induced by these counts (the
 * calculators work with normalized counts).
 *
 * @author Jean Paul Barddal (jpbarddal@gmail.com)
 * @author Blaz Sovdat (blaz.sovdat@gmail.com)
 *
 */
public class StreamElement<T> {
	private final T label;
	private final double value;

	public StreamElement(T label, double value) {
		this.label = label;
		this.value = value;
	}

	public StreamElement(T label) {
		this(label, 1.0);
	}

	public T getLabel() {
		return label;
	}

	public double getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return Double.valueOf(value).hashCode() + prime * label.hashCode();
	}

	@Override
	public boolean equals(Object oth) {
		if (oth != null && oth instanceof StreamElement) {
			@SuppressWarnings("unchecked")
			StreamElement<T> el = (StreamElement<T>) oth;
			return value == el.value && label.equals(el.label);
		}
		return false;
	}

	@Override
	public String toString() {
		return label + ":" + value;
	}
}
