package net.sovdat.blaz.incremental;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import net.sovdat.blaz.incremental.damping.TestDampingEntropyCalculator;
import net.sovdat.blaz.incremental.damping.TestDampingGiniCalculator;
import net.sovdat.blaz.incremental.sliding.TestSlidingEntropyCalculator;
import net.sovdat.blaz.incremental.sliding.TestSlidingGiniCalculator;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestDampingEntropyCalculator.class,
	TestDampingGiniCalculator.class,
	TestSlidingEntropyCalculator.class,
	TestSlidingGiniCalculator.class
})

public class AllTests {
}
