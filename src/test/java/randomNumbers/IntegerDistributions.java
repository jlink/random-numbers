package randomNumbers;

import java.util.Random;

import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import net.jqwik.api.*;
import org.quicktheories.generators.SourceDSL;

import static org.quicktheories.QuickTheory.*;

class IntegerDistributions {

	@Example
	@Label("jqwik: -100 .. 100")
	void jqwikMin100to100() {
		Histogram histogram = Histogram.between(-100, 100, 10);

		Arbitrary<Integer> integers = Arbitraries.integers().between(-100, 100);
		integers.sampleStream().limit(1000).forEach(i -> histogram.collect(i));
		histogram.printHistogram();
	}

	@Example
	@Label("jqwik: -100000 .. 100000")
	void jqwikMin100000to100000() {
		Histogram histogram = Histogram.between(-100000, 100000, 5000);

		Arbitrary<Integer> integers = Arbitraries.integers().between(-100000, 100000);
		integers.sampleStream().limit(10000).forEach(i -> histogram.collect(i));
		histogram.printHistogram();
	}

	@Example
	@Label("random gaussian: -100000 .. 100000")
	void randomGaussian() {
		Histogram histogram = Histogram.between(-100000, 100000, 5000);

		Random random = new Random();
		GaussianGenerator generator = new GaussianGenerator(-100000, 100000);
		for (int i = 0; i < 10000; i++) {
			int value = generator.next(random).value();
			histogram.collect(value);
		}
		histogram.printHistogram();
	}

	@Example
	@Label("fast gaussian: -100000 .. 100000")
	void fastGaussian() {
		Histogram histogram = Histogram.between(-100000, 100000, 5000);

		Random random = new FastRandom();
		GaussianGenerator generator = new GaussianGenerator(-100000, 100000);
		for (int i = 0; i < 10000; i++) {
			int value = generator.next(random).value();
			histogram.collect(value);
		}
		histogram.printHistogram();
	}

	@Example
	@Label("fast triangle: -100000 .. 100000")
	void fastTriangle() {
		Histogram histogram = Histogram.between(-100000, 100000, 5000);

		FastRandom random = new FastRandom();
		for (int i = 0; i < 10000; i++) {
			int value = (int) random.triangular(0, 100000, 0);
			histogram.collect(value);
		}
		histogram.printHistogram();
	}

	@Example
	@Label("junit-quickcheck: -100000 .. 100000")
	void junitquickcheckMin100000to100000() {
		Histogram histogram = Histogram.between(-100000, 100000, 5000);

		SourceOfRandomness randomness = new SourceOfRandomness(new Random());
		for (int i = 0; i < 10000; i++) {
			int value = randomness.nextInt(-100000, 100000);
			histogram.collect(value);
		}
		histogram.printHistogram();
	}

	@Example
	@Label("quick-theories: -100000 .. 100000")
	void quicktheoriesMin100000to100000() {
		Histogram histogram = Histogram.between(-100000, 100000, 5000);

		qt().withGenerateAttempts(10000)
				.forAll(SourceDSL.integers().between(-100000, 100000))
				.checkAssert(value -> histogram.collect(value));
		histogram.printHistogram();
	}
}
