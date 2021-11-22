package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals("0.15", 0.15, SEK.getRate(), 0.1);
		assertEquals("0.20", 0.20, DKK.getRate(), 0.1);
		assertEquals("1.5", 1.5, EUR.getRate(), 0.1);
	}
	
	@Test
	public void testSetRate() {
		SEK.setRate(0.23);
		DKK.setRate(0.5);
		EUR.setRate(2.0);
		
		assertEquals("0.23", 0.23, SEK.getRate(), 0.1);
		assertEquals("0.5", 0.5, DKK.getRate(), 0.1);
		assertEquals("2.0", 2.0, EUR.getRate(), 0.1);
	}
	
	@Test
	public void testGlobalValue() {
		assertEquals("75", 75, SEK.universalValue(500).intValue());
		assertEquals("100", 100, DKK.universalValue(500).intValue());
		assertEquals("750", 750, EUR.universalValue(500).intValue());
	}
	
	@Test
	public void testValueInThisCurrency() {
		assertEquals("SEK to EUR: 5000", 5000, SEK.valueInThisCurrency(500, EUR).intValue());
		assertEquals("DKK to SEK: 375", 375, DKK.valueInThisCurrency(500, SEK).intValue());
		assertEquals("EUR to DKK: 66", 66, EUR.valueInThisCurrency(500, DKK).intValue());
	}

}
