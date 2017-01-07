# DataStreamAlgorithms
A Java package implementing algorithms for computing entropy and Gini index from time-changing data streams.

## What is this?
Incremental machine learning algorithms, such as the [Hoeffding tree learner](homes.cs.washington.edu/~pedrod/papers/kdd00.pdf), work by processing learning examples one-by-one, updating the model as the examples arrive. Some of these algorithms compute various statistics to help them guide the search in the hypothesis space; two such popular statistics are [information gain](https://en.wikipedia.org/wiki/Information_gain_in_decision_trees), defined in terms of [information-theoretic entropy](https://en.wikipedia.org/wiki/Entropy_(information_theory)), and Gini index.

Most of the aforementioned learning algorithms work by recomputing such statistics periodically (e.g. recompute statistics every 100 examples). This package implements algorithms that can maintain (information-theoretic) entropy and Gini index of a data stream by using computationally-cheap update formulas derived in [1].

We offer four variants of these algorithms:
- Computing entropy of a sliding window of elements. The size of the window reflects what part of the stream we consider recent.
- Computing entropy with a damping factor which renders "older" stream elements less important: the older the element, the less it contributes to the current entropy. The damping factor choice reflects what part of the stream we consider recent.
- Computing Gini index of a sliding window of elements.
- Computing Gini index with a damping factor.

## How do I use it?
The algorithms are very easy to use. For example:
```java
final int windowSize = 100;
Calculator<String> entropyCalculator = new SlidingEntropyCalculator<>(windowSize);
for (StreamElement<String> element : elements) {
  entropyCalculator.process(element);
}
```

At any point, `entropyCalculator.getValue()` returns the entropy of the distribution induced by the labels of the last 100 elements.

See `test/incremental.SimpleUsageExample` for how to use the calculators.

## References
[1] Blaz Sovdat. [Updating Formulas and Algorithms for Computing Entropy and Gini Index from Time-Changing Data Streams](https://arxiv.org/abs/1403.6348), arXiv:1403.6348.

## Contact
For any questions regarding the code or the paper, contact [Blaz Sovdat](https://github.com/blazs) (blaz.sovdat@gmail.com) or Jean Paul Barddal (jpbarddal@gmail.com).
