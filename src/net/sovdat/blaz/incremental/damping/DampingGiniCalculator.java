package net.sovdat.blaz.incremental.damping;

import java.util.HashMap;
import java.util.Map;

import net.sovdat.blaz.incremental.StreamElement;

/**
 * Class implementing damping version of algorithms for computing Gini index
 * from (time-changing) data streams [1].
 *
 * [1] https://arxiv.org/abs/1403.6348
 *
 * @author Jean Paul Barddal (jpbarddal@gmail.com).
 * @author Blaz Sovdat (blaz.sovdat@gmail.com).
 * 
 * @param <T>
 *            Type of the stream element label.
 */
public class DampingGiniCalculator<T> implements DampingCalculator<T> {
	private final double dampingFactor;

	private double sum;
	private double gini;

	private Map<T, Double> counters;

	/**
	 * The choice of the damping factor reflects what subset of stream elements
	 * we consider recent.
	 * 
	 * @param dampingFactor
	 *            The damping factor used by the algorithm.
	 */
	public DampingGiniCalculator(double dampingFactor) {
		if (!(dampingFactor > 0.0 && dampingFactor <= 1.0)) {
			throw new IllegalArgumentException("Expected dampingFactor from (0,1]");
		}

		this.dampingFactor = dampingFactor;
		this.sum = 0.0;
		this.gini = 0.0;
		this.counters = new HashMap<>();
	}

	@Override
	public void process(StreamElement<T> element) {
		initializeCounterIfNecessary(element);
		// NOTE: Important that we update counters *after* the entropy (see [1]
		// for details).
		updateGini(element);
		updateCounters(element);
	}

	@Override
	public double getValue() {
		return gini;
	}

	@Override
	public double getDampingFactor() {
		return dampingFactor;
	}

	private void initializeCounterIfNecessary(StreamElement<T> element) {
		final T label = element.getLabel();
		if (!counters.containsKey(label)) {
			counters.put(label, 0.0);
		}
	}

	private void updateGini(StreamElement<T> element) {
		final double ni = counters.get(element.getLabel());
		final double n = sum;

		final double nIncSq = ((n + 1) * (n + 1));
		final double t = (n * n) * (1.0 - dampingFactor * gini) + (2.0 * ni) + 1.0;

		gini = 1 - t / nIncSq;
	}

	private void updateCounters(StreamElement<T> element) {
		final T label = element.getLabel();
		final double value = element.getValue();

		counters.put(label, (counters.get(label) + value));
		sum += value;
	}
}
