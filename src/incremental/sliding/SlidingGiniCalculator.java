package incremental.sliding;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import incremental.StreamElement;

/**
 * Class implementing sliding version of the algorithm for computing Gini index
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
public class SlidingGiniCalculator<T> implements SlidingWindowCalculator<T> {
	private final int windowSize;

	private Map<T, Double> counters;
	private Queue<StreamElement<T>> window;

	private double sum;
	private double gini;

	/**
	 * The sliding window size determines what portion of stream elements we
	 * consider recent. The calculator maintains the Gini index of the
	 * distribution induced by the elements in the window.
	 * 
	 * @param windowSize
	 *            The size of the sliding window.
	 */
	public SlidingGiniCalculator(int windowSize) {
		if (windowSize <= 0) {
			throw new IllegalArgumentException("Expected windowSize>0");
		}

		this.windowSize = windowSize;

		this.counters = new HashMap<>();
		this.window = new LinkedList<>();

		this.sum = 0.0;
		this.gini = 0.0;
	}

	@Override
	public void process(StreamElement<T> element) {
		initializeCounterIfNecessary(element);
		// NOTE: The order of the three calls below is critical; see [1] for
		// details.
		updateWindowContent(element);
		updateCounters(element);
		updateGini(element);
	}

	private void updateWindowContent(StreamElement<T> element) {
		window.add(element);
		if (window.size() > windowSize) {
			removeLastElement();
		}
	}

	private void removeLastElement() {
		final StreamElement<T> lastElement = window.remove();
		updateGiniWithoutElement(lastElement);
	}

	private void updateGiniWithoutElement(StreamElement<T> lastElement) {
		final T label = lastElement.getLabel();
		final double value = lastElement.getValue();

		sum -= value;
		counters.put(label, counters.get(label) - value);

		gini = 1 - (Math.pow(sum + 1, 2) * (1 - gini) - 2 * counters.get(label) - 1) / (sum * sum);
	}

	private void updateCounters(StreamElement<T> element) {
		final double value = element.getValue();
		final T label = element.getLabel();

		sum += value;
		counters.put(label, counters.get(label) + value);
	}

	private void updateGini(StreamElement<T> element) {
		final double ni = counters.get(element.getLabel());

		gini = 1.0 - (1.0 / (sum * sum)) * (((sum - 1) * (sum - 1)) * (1 - gini) + 2 * ni - 1);
	}

	private void initializeCounterIfNecessary(StreamElement<T> element) {
		final T label = element.getLabel();
		if (!counters.containsKey(label)) {
			counters.put(label, 0.0);
		}
	}

	@Override
	public double getValue() {
		return gini;
	}

	@Override
	public int getWindowSize() {
		return windowSize;
	}
}
