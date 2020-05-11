package randomNumbers;

import java.math.*;
import java.util.*;

import com.pholser.junit.quickcheck.random.*;
import org.quicktheories.generators.*;

import net.jqwik.api.*;
import net.jqwik.api.arbitraries.*;
import net.jqwik.engine.properties.*;
import net.jqwik.engine.properties.arbitraries.randomized.*;

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
	@Label("jqwik gaussian: -100000 .. 100000")
	void jqwikGaussian100000to100000() {
		Histogram histogram = Histogram.between(-100000, 100000, 5000);

		Arbitrary<Integer> integers = Arbitraries.integers()
												 .between(-100000, 100000)
												 .withDistribution(RandomDistribution.gaussian());
		integers.sampleStream().limit(10000).forEach(i -> histogram.collect(i));
		histogram.printHistogram();
	}

	@Example
	@Label("jqwik BigInteger: -100000000000 .. 100000000000")
	void jqwikBigInteger100000to100000() {
		BigInteger min = BigInteger.valueOf(-100_000_000_000L);
		BigInteger max = BigInteger.valueOf(100_000_000_000L);
		Histogram histogram = Histogram.between(min, max, BigInteger.valueOf(5_000_000_000L));

		BigIntegerArbitrary integers = Arbitraries.bigIntegers().between(min, max);
		integers.sampleStream().limit(10000).forEach(i -> histogram.collect(i));
		histogram.printHistogram();
	}

	@Example
	@Label("jqwik BigInteger: -100000000000 .. 100000000000")
	void jqwikBigInteger() {
		BigInteger min = BigInteger.valueOf(-100_000_000_000L);
		BigInteger max = BigInteger.valueOf(100_000_000_000L);
		Histogram histogram = Histogram.between(min, max, BigInteger.valueOf(5_000_000_000L));

		BigDecimalArbitrary integers = Arbitraries.bigDecimals().between(new BigDecimal(min), new BigDecimal(max));
		integers.sampleStream().limit(10000).forEach(i -> histogram.collect(i.toBigInteger()));
		histogram.printHistogram();
	}

	@Example
	@Label("jqwik uniform BigInteger: -100000000000 .. 100000000000")
	void jqwikBigIntegerUniform() {
		BigInteger min = BigInteger.valueOf(-100_000_000_000L);
		BigInteger max = BigInteger.valueOf(100_000_000_000L);
		Histogram histogram = Histogram.between(min, max, BigInteger.valueOf(5_000_000_000L));

		Arbitrary<BigInteger> integers = Arbitraries.bigIntegers()
												 .between(min, max)
												 .withDistribution(RandomDistribution.uniform());
		integers.sampleStream().limit(10000).forEach(i -> histogram.collect(i));

		histogram.printHistogram();
	}

	@Example
	@Label("jqwik uniform BigDecimal: -100000000000 .. 100000000000")
	void jqwikBigDecimalUniform() {
		BigDecimal min = BigDecimal.valueOf(-100_000_000_000L);
		BigDecimal max = BigDecimal.valueOf(100_000_000_000L);
		Histogram histogram = Histogram.between(min.toBigInteger(), max.toBigInteger(), BigInteger.valueOf(5_000_000_000L));

		Arbitrary<BigDecimal> integers = Arbitraries.bigDecimals()
													.between(min, max)
													.withDistribution(RandomDistribution.uniform());
		integers.sampleStream().limit(10000).forEach(i -> histogram.collect(i.toBigInteger()));

		histogram.printHistogram();
	}


	@Example
	@Label("jqwik BigDecimal decimal places: -100000000000 .. 100000000000")
	void jqwikBigDecimalDecimalPlaces() {
		BigDecimal min = BigDecimal.valueOf(-100_000_000_000L);
		BigDecimal max = BigDecimal.valueOf(100_000_000_000L);
		Histogram histogram = Histogram.between(0, 99, 1);

		Range<BigDecimal> range = Range.of(min, max);
		RandomGenerator<BigDecimal> randomGenerator =
				RandomGenerators.bigDecimals(range, 2, BigDecimal.ZERO, RandomDistribution.biased());

		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			BigDecimal value = randomGenerator.next(random).value();
			BigInteger integerPart = value.toBigInteger().multiply(BigInteger.valueOf(100));
			BigInteger decimals = value.unscaledValue().subtract(integerPart).abs();
			histogram.collect(decimals);
		}
		histogram.printHistogram();
	}

	@Example
	@Label("jqwik BigDecimal gaussian decimal places: -100000000000 .. 100000000000")
	void jqwikBigDecimalGaussianDecimalPlaces() {
		BigDecimal min = BigDecimal.valueOf(-100_000_000_000L);
		BigDecimal max = BigDecimal.valueOf(100_000_000_000L);
		Histogram histogram = Histogram.between(0, 99, 1);

		Range<BigDecimal> range = Range.of(min, max);
		RandomGenerator<BigDecimal> randomGenerator =
				RandomGenerators.bigDecimals(range, 2, BigDecimal.ZERO, RandomDistribution.gaussian(4.0));

		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			BigDecimal value = randomGenerator.next(random).value();
			BigInteger integerPart = value.toBigInteger().multiply(BigInteger.valueOf(100));
			BigInteger decimals = value.unscaledValue().subtract(integerPart).abs();
			histogram.collect(decimals);
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

		qt().withExamples(10000)
			.forAll(SourceDSL.integers().between(-100000, 100000))
			.checkAssert(value -> histogram.collect(value));
		histogram.printHistogram();
	}
}
