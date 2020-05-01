package randomNumbers;

import java.util.*;

public class Histogram {

	public static Histogram between(int min, int max, int step) {
		Histogram histogram = new Histogram();
		for (int upper = min + step; upper < max + step; upper = upper + step) {
			histogram.addBasket(upper);
		}
		return histogram;
	}

	private final List<Basket> baskets = new ArrayList<>();
	private int countAll = 0;

	public void addBasket(int upper) {
		baskets.add(new Basket(upper, "<" + upper));
	}

	public void collect(int value) {
		countAll++;
		for (Basket basket : baskets) {
			if (value <= basket.upper) {
				basket.count++;
				return;
			}
		}
		throw new RuntimeException("No basket found for value: " + value);
	}

	public void printHistogram() {
		System.out.println("Count: " + countAll);
		OptionalInt maxCount = baskets.stream().mapToInt(b -> b.count).max();
		double scale = Math.max(1.0, maxCount.getAsInt() / 100.0);
		for (int i = 0; i < baskets.size(); i++) {
			Basket basket = baskets.get(i);
			String line = String.format("%s (%s) \t: %s", basket.name, basket.count, convertToStars(basket.count, scale));
			System.out.println(line);
		}
	}

	private String convertToStars(int num, double scale) {
		StringBuilder builder = new StringBuilder();
		int weight = (int) (num / scale);
		for (int j = 0; j < weight; j++) {
			builder.append('*');
		}
		return builder.toString();
	}
}
