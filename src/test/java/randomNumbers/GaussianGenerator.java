package randomNumbers;

import java.util.Random;

public class GaussianGenerator {

	private final int min;
	private final int max;
	private final Random random;

	public GaussianGenerator(Random random, int min, int max) {
		this.min = min;
		this.max = max;
		this.random = random;
	}

	int next() {
		double gaussian = random.nextGaussian();
		int value = Math.toIntExact((long) (gaussian * max / 3));
		if (value < min || value > max) {
			return next();
		}
		return value;
	}
}
