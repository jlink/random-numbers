package randomNumbers;

import java.util.*;
import java.util.concurrent.*;

import com.pholser.junit.quickcheck.random.*;

import net.jqwik.api.*;
import net.jqwik.engine.SourceOfRandomness;

public class GeneratorPerformance {

	@Example
	@Label("jqwik biased: -100000 .. 100000")
	void biasedMin100000to100000() {
		Arbitrary<Integer> integers =
				Arbitraries.integers()
						   .between(-100000, 100000)
						   .withDistribution(RandomDistribution.biased());

		RandomGenerator<Integer> generator = integers.generator(10000);
		measure("jqwik biased", generator, SourceOfRandomness.newRandom());
	}

	@Example
	@Label("jqwik uniform: -100000 .. 100000")
	void uniformMin100000to100000() {
		Arbitrary<Integer> integers =
				Arbitraries.integers()
						   .between(-100000, 100000)
						   .withDistribution(RandomDistribution.uniform());

		RandomGenerator<Integer> generator = integers.generator(10000);
		measure("jqwik uniform", generator, SourceOfRandomness.newRandom());
	}

	@Example
	@Label("jqwik gaussian: -100000 .. 100000")
	void gaussianMin100000to100000() {
		Arbitrary<Integer> integers =
				Arbitraries.integers()
						   .between(-100000, 100000)
						   .withDistribution(RandomDistribution.gaussian());

		RandomGenerator<Integer> generator = integers.generator(10000);
		measure("jqwik gaussian", generator, SourceOfRandomness.newRandom());
	}

	// @Example
	@Label("fast gaussian: -100000 .. 100000")
	void fastGaussianMin100000to100000() {
		Arbitrary<Integer> integers =
				Arbitraries.integers()
						   .between(-100000, 100000)
						   .withDistribution(RandomDistribution.gaussian());

		RandomGenerator<Integer> generator = integers.generator(10000);
		measure("fast gaussian", generator, new FastRandom());
	}

	// @Example
	@Label("simple: -100000 .. 100000")
	void simpleMin100000to100000() {
		RandomGenerator<Integer> generator = new SimpleGenerator(-100000, 100000);
		measure("simple", generator, new Random());
	}

	// @Example
	@Label("fast: -100000 .. 100000")
	void fastMin100000to100000() {
		RandomGenerator<Integer> generator = new SimpleGenerator(-100000, 100000);
		measure("fast", generator, new FastRandom());
	}

	// @Example
	@Label("threadLocal: -100000 .. 100000")
	void threadLocalMin100000to100000() {
		RandomGenerator<Integer> generator = new SimpleGenerator(-100000, 100000);
		measure("thread local", generator, ThreadLocalRandom.current());
	}

	// @Example
	@Label("splittable: -100000 .. 100000")
	void splittableMin100000to100000() {
		RandomGenerator<Integer> generator = new SimpleGenerator(-100000, 100000);
		measure("splittable", generator, getSplittableRandom());
	}

	private Random getSplittableRandom() {
		final SplittableRandom splittableRandom = new SplittableRandom(new Random().nextLong());
		return new Random() {
			@Override
			public int nextInt(int bound) {
				return splittableRandom.nextInt(bound);
			}
		};
	}

	// @Example
	@Label("XORShift: -100000 .. 100000")
	void xorshiftMin100000to100000() {
		RandomGenerator<Integer> generator = new SimpleGenerator(-100000, 100000);
		measure("XORShift", generator, SourceOfRandomness.newRandom());
	}

	private void measure(String label, RandomGenerator<Integer> generator, Random random) {
		long before = System.currentTimeMillis();
		int tries = 50000000;
		for (int i = 0; i < tries; i++) {
			generator.next(random);
		}
		long after = System.currentTimeMillis();

		double secs = (after - before) / 1000.0;

		System.out.printf("%s (%s): %s secs%n", label, tries, secs);
	}


}
