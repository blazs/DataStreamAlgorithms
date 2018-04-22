package net.sovdat.blaz.incremental.damping;

import java.util.HashMap;
import java.util.Map;

import net.sovdat.blaz.incremental.StreamElement;
import net.sovdat.blaz.incremental.Utils;

/**
 * Implements the damping-factor version of the algorithm for computing entropy
 * from a (time-changing) data stream [1].
 * 
 * [1] https://arxiv.org/abs/1403.6348
 *
 * @author	Jean Paul Barddal (jpbarddal@gmail.com)
 * @author	Blaz Sovdat (blaz.sovdat@gmail.com)
 */
public class DampingEntropyCalculator<T> implements DampingCalculator<T> {
	private final double dampingFactor;

	private double sum;
	private double entropy;

	private Map<T, Double> counters;

	/**
	 * The choice of the damping factor reflects what subset of stream elements
	 * we consider recent.
	 * 
	 * @param dampingFactor
	 *            The damping factor used by the algorithm.
	 */
	public DampingEntropyCalculator(double dampingFactor) {
		if (!(dampingFactor > 0.0 && dampingFactor <= 1.0)) {
			throw new IllegalArgumentException("dampingFactor should be from (0,1]");
		}

		this.dampingFactor = dampingFactor;
		this.sum = 0.0;
		this.entropy = 0.0;
		this.counters = new HashMap<>();
	}

	@Override
	public void process(StreamElement<T> element) {
		initializeCounterIfNecessary(element);
		// NOTE: Important that we update counters *after* the entropy (see [1]
		// for details).
		updateEntropy(element);
		updateCounters(element);
	}

	@Override
	public double getValue() {
		return entropy;
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

	private void updateCounters(StreamElement<T> element) {
		final double val = element.getValue();
		final T label = element.getLabel();

		counters.put(label, counters.get(label) + val);
		sum += val;
	}

	private void updateEntropy(StreamElement<T> element) {
		final double val = element.getValue();

		final double frac = sum / (sum + val);
		final double ni = counters.get(element.getLabel());
		final double ratio = (ni + val) / (sum + val);
		final double prevRatio = ni / (sum + val);

		entropy = frac * (dampingFactor * entropy - Utils.log2(frac)) - ratio * Utils.log2(ratio)
				+ prevRatio * Utils.log2(prevRatio);
	}
}
