package randomNumbers;

import java.util.Random;

import net.jqwik.api.*;

public class GaussianGenerator implements RandomGenerator<Integer> {

	private final int min;
	private final int max;

	public GaussianGenerator(int min, int max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public Shrinkable<Integer> next(Random random) {
		double gaussian = random.nextGaussian();
		Integer value = Math.toIntExact((long) (gaussian * max / 3));
		if (value < min || value > max) {
			return next(random);
		}
		return new Shrinkable<Integer>() {
			@Override
			public int compareTo(Shrinkable<Integer> other) {
				return 0;
			}

			@Override
			public Integer value() {
				return value;
			}

			@Override
			public ShrinkingSequence<Integer> shrink(Falsifier falsifier) {
				return ShrinkingSequence.dontShrink(this);
			}

			@Override
			public ShrinkingDistance distance() {
				return ShrinkingDistance.of(0);
			}
		};
	}
}
