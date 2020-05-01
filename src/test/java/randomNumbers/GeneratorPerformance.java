package randomNumbers;

import java.util.Random;

import net.jqwik.api.*;

public class GeneratorPerformance {

	@Example
	@Label("jqwik: -100000 .. 100000")
	void jqwikMin100000to100000() {
		Arbitrary<Integer> integers = Arbitraries.integers().between(-100000, 100000);

		RandomGenerator<Integer> generator = integers.generator(10000);
		measure("jqwik", generator);
	}

	@Example
	@Label("gaussioan: -100000 .. 100000")
	void gaussianMin100000to100000() {
		RandomGenerator<Integer> generator = new GaussianGenerator(new Random(), -100000, 100000);
		measure("gaussian", generator);
	}

	private void measure(String label, RandomGenerator<Integer> generator) {
		Random random = new Random();
		long before = System.currentTimeMillis();
		int tries = 10000000;
		for (int i = 0; i < tries; i++) {
			generator.next(random);
		}
		long after = System.currentTimeMillis();

		double secs = (after - before) / 1000.0;

		System.out.printf("%s secs (%s): %s%n", label, tries, secs);
	}


}
