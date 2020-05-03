package randomNumbers;

import java.util.*;

/**
 * https://www.javamex.com/tutorials/random_numbers/xorshift.shtml
 */
public class XORShiftRandom extends Random {
	private long seed;

	public XORShiftRandom() {
		this(System.nanoTime());
	}

	public XORShiftRandom(long seed) {
		this.seed = seed;
	}

	@Override
	protected int next(int nbits) {
		// N.B. Not thread-safe!
		long x = nextLong();
		x &= ((1L << nbits) - 1);
		return (int) x;
	}

	@Override
	public long nextLong() {
		long x = this.seed;
		x ^= (x << 21);
		x ^= (x >>> 35);
		x ^= (x << 4);
		this.seed = x;
		return x;
	}
}