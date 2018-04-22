package net.sovdat.blaz.incremental.sliding;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import net.sovdat.blaz.incremental.StreamElement;
import net.sovdat.blaz.incremental.Utils;

/**
 * A class implementing damping version of the algorithm for computing entropy
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
public class SlidingEntropyCalculator<T> implements SlidingWindowCalculator<T> {
	private final int windowSize;

	private Map<T, Double> counters;
	private Queue<StreamElement<T>> window;

	private double sum;
	private double entropy;

	/**
	 * The sliding window size determines what portion of stream elements we
	 * consider recent. The calculator maintains the entropy of the distribution
	 * induced by the elements in the window.
	 * 
	 * @param windowSize
	 *            Size of the sliding window.
	 */
	public SlidingEntropyCalculator(int windowSize) {
		if (windowSize <= 0) {
			throw new IllegalArgumentException("Expected windowSize>0");
		}

		this.windowSize = windowSize;
		this.counters = new HashMap<>();
		this.window = new LinkedList<>();
		this.sum = 0.0;
		this.entropy = 0.0;
	}

	@Override
	public void process(StreamElement<T> element) {
		initializeCounterIfNecessary(element);
		// NOTE: It is important that we update the counters *before* updating
		// the entropy, and update entropy before updating window content (see
		// [1] for details).
		updateCounters(element);
		updateEntropy(element);
		updateWindowContent(element);
	}

	private void initializeCounterIfNecessary(StreamElement<T> element) {
		final T label = element.getLabel();
		if (!counters.containsKey(label)) {
			counters.put(label, 0.0);
		}
	}

	private void updateWindowContent(StreamElement<T> element) {
		window.add(element);
		if (window.size() > windowSize) {
			removeLastElement();
		}
	}

	private void removeLastElement() {
		final StreamElement<T> lastElement = window.remove();
		updateEntropyWithoutElement(lastElement);
	}

	private void updateEntropy(StreamElement<T> element) {
		final T label = element.getLabel();
		final double pi = counters.get(label) / sum;
		final double value = element.getValue();
		final double piPrev = (counters.get(label) - value) / sum;
		final double ratio = (sum - value) / sum;

		entropy = ratio * (entropy - Utils.log2(ratio)) - pi * Utils.log2(pi) + piPrev * Utils.log2(piPrev);
	}

	private void updateCounters(StreamElement<T> element) {
		final double value = element.getValue();
		final T label = element.getLabel();

		sum += value;
		counters.put(label, counters.get(label) + value);
	}

	// TODO: Clean up the code
	private void updateEntropyWithoutElement(StreamElement<T> element) {
		final double value = element.getValue();
		sum -= value;
		final T label = element.getLabel();
		counters.put(label, counters.get(label) - value);

		if (sum == 0.0) {
			entropy = 0.0;
		} else {
			final double pi = counters.get(label) / (sum + value);
			final double piPrev = (counters.get(label) + value) / (sum + value);
			final double ratio = sum / (sum + value);
			final double ratioInv = (sum + value) / sum;

			entropy = ratioInv * (entropy + piPrev * Utils.log2(piPrev) - pi * Utils.log2(pi)) + Utils.log2(ratio);
		}	
	}

	@Override
	public double getValue() {
		return entropy;
	}

	@Override
	public int getWindowSize() {
		return windowSize;
	}
}
