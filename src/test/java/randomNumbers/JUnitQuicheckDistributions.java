package randomNumbers;

import com.pholser.junit.quickcheck.*;
import com.pholser.junit.quickcheck.generator.*;
import com.pholser.junit.quickcheck.runner.*;
import org.junit.*;
import org.junit.runner.*;

@RunWith(JUnitQuickcheck.class)
public class JUnitQuicheckDistributions {

	private static Histogram histogram;

	@BeforeClass
	public static void initHistogram() {
		histogram = Histogram.between(-100000, 100000, 5000);
	}

	@AfterClass
	public static void printHistogram() {
		histogram.printHistogram();
	}

	@Property(trials = 10000)
	public void integers(@InRange(minInt = -100000, maxInt = 100000) int anInt) {
		histogram.collect(anInt);
	}
}
