package randomNumbers;

import java.math.*;

public class Basket {

	public final BigInteger upper;
	public final String name;

	public int count = 0;

	public Basket(BigInteger upper, String name) {
		this.upper = upper;
		this.name = name;
	}
}
