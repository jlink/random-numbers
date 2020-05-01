package randomNumbers;

import java.util.Random;

import net.jqwik.api.*;

public class SimpleGenerator implements RandomGenerator<Integer> {

	private final int min;
	private final int max;

	public SimpleGenerator(int min, int max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public Shrinkable<Integer> next(Random random) {
		int range = max - min + 1;
		Integer value = random.nextInt(range) - min;
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
