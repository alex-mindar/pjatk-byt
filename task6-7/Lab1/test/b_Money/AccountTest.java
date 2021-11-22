package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		assertFalse(testAccount.timedPaymentExists("1886"));
		testAccount.addTimedPayment("1886", 1, 1, new Money(600, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("1886"));
		testAccount.removeTimedPayment("1886");
		assertFalse(testAccount.timedPaymentExists("1886"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("1887", 1, 1, new Money(5000, SEK), SweBank, "Alice");
		assertEquals(10000000, testAccount.getBalance().getAmount().intValue());
		assertEquals(1000000, SweBank.getBalance("Alice").intValue());
		testAccount.tick();
		assertEquals(10000000, testAccount.getBalance().getAmount().intValue());
		assertEquals(1000000, SweBank.getBalance("Alice").intValue());
		testAccount.tick();
		assertEquals(9995000, testAccount.getBalance().getAmount().intValue());
		assertEquals(1005000, SweBank.getBalance("Alice").intValue());
		testAccount.tick();
		assertEquals(9995000, testAccount.getBalance().getAmount().intValue());
		assertEquals(1005000, SweBank.getBalance("Alice").intValue());
		testAccount.tick();
		assertEquals(9990000, testAccount.getBalance().getAmount().intValue());
		assertEquals(1010000, SweBank.getBalance("Alice").intValue());
		testAccount.removeTimedPayment("1887");
		testAccount.tick();
		assertEquals(9990000, testAccount.getBalance().getAmount().intValue());
		assertEquals(1010000, SweBank.getBalance("Alice").intValue());
		testAccount.tick();
		assertEquals(9990000, testAccount.getBalance().getAmount().intValue());
		assertEquals(1010000, SweBank.getBalance("Alice").intValue());
		testAccount.addTimedPayment("1888", 1, 1, new Money(5000, SEK), SweBank, "Glob");
		testAccount.tick();
		assertEquals(9990000, testAccount.getBalance().getAmount().intValue());
		testAccount.tick();
		assertEquals(9990000, testAccount.getBalance().getAmount().intValue());
		testAccount.removeTimedPayment("1888");
		testAccount.tick();
		assertEquals(9990000, testAccount.getBalance().getAmount().intValue());
	}

	@Test
	public void testAddWithdraw() {
		assertEquals(10000000, testAccount.getBalance().getAmount().intValue());
		testAccount.withdraw(new Money(10000, SEK));
		assertEquals(9990000, testAccount.getBalance().getAmount().intValue());
		testAccount.withdraw(new Money(-10000, SEK));
		assertEquals(10000000, testAccount.getBalance().getAmount().intValue());
		testAccount.withdraw(new Money(0, SEK));
		assertEquals(10000000, testAccount.getBalance().getAmount().intValue());
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(10000000, testAccount.getBalance().getAmount().intValue());
		testAccount.deposit(new Money(10000, SEK));
		assertEquals(10010000, testAccount.getBalance().getAmount().intValue());
		testAccount.withdraw(new Money(10010000, SEK));
		assertEquals(0, testAccount.getBalance().getAmount().intValue());
		testAccount.withdraw(new Money(10010000, SEK));
		assertEquals(-10010000, testAccount.getBalance().getAmount().intValue());
	}
}
