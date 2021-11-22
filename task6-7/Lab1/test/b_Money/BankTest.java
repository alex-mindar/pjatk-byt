package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test(expected=AccountExistsException.class)
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("Oleksii");
		SweBank.openAccount("Ulrika");
	}

	@Test(expected=AccountDoesNotExistException.class)
	public void testDeposit() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("Oleskii");
		SweBank.deposit("Oleksii", new Money(5000, SEK));
		Nordea.openAccount("Glob");
		Nordea.deposit("Blob", new Money(20, SEK));
	}

	@Test(expected=AccountDoesNotExistException.class)
	public void testWithdraw() throws AccountExistsException, AccountDoesNotExistException {
		Nordea.withdraw("Bob", new Money(5000, SEK));
		assertEquals(-5000, Nordea.getBalance("Bob").intValue());
		SweBank.withdraw("Babax", new Money(6000, SEK));
	}
	
	@Test(expected=AccountDoesNotExistException.class)
	public void testGetBalance() throws AccountExistsException, AccountDoesNotExistException {
		assertEquals(0, Nordea.getBalance("Bob").intValue());
		Nordea.deposit("Bob", new Money(5000, SEK));
		assertEquals(5000, Nordea.getBalance("Bob").intValue());
		SweBank.openAccount("Oleksii");
		assertEquals(0, SweBank.getBalance("Oleksii").intValue());
		SweBank.deposit("Oleksii", new Money(6000, SEK));
		assertEquals(6000, SweBank.getBalance("Oleksii").intValue());
		SweBank.withdraw("Oleksii", new Money(10000, SEK));
		assertEquals(-4000, SweBank.getBalance("Oleksii").intValue());
		DanskeBank.getBalance("Barbara");
	}
	
	@Test(expected=AccountDoesNotExistException.class)
	public void testTransfer() throws AccountDoesNotExistException, AccountExistsException {
		assertEquals(0, SweBank.getBalance("Bob").intValue());
		assertEquals(0, Nordea.getBalance("Bob").intValue());
		DanskeBank.openAccount("Oleksii");
		assertEquals(0, DanskeBank.getBalance("Oleksii").intValue());
		SweBank.deposit("Bob", new Money(1000, SEK));
		SweBank.transfer("Bob", Nordea, "Bob", new Money(500, SEK));
		SweBank.transfer("Bob", DanskeBank, "Oleksii", new Money(1500, SEK));
		assertEquals(-1000, SweBank.getBalance("Bob").intValue());
		assertEquals(500, Nordea.getBalance("Bob").intValue());
		assertEquals(1125, DanskeBank.getBalance("Oleksii").intValue());
		DanskeBank.transfer("Oleksii", SweBank, "Glob", new Money(10000, SEK));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(20000, SEK));
		SweBank.addTimedPayment("Ulrika", "1884", 2, 1, new Money(5000, SEK), Nordea, "Bob");
		assertEquals(20000, SweBank.getBalance("Ulrika").intValue());
		assertEquals(0, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals(20000, SweBank.getBalance("Ulrika").intValue());
		assertEquals(0, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals(15000, SweBank.getBalance("Ulrika").intValue());
		assertEquals(5000, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals(15000, SweBank.getBalance("Ulrika").intValue());
		assertEquals(5000, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals(15000, SweBank.getBalance("Ulrika").intValue());
		assertEquals(5000, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals(10000, SweBank.getBalance("Ulrika").intValue());
		assertEquals(10000, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals(10000, SweBank.getBalance("Ulrika").intValue());
		assertEquals(10000, Nordea.getBalance("Bob").intValue());
		SweBank.removeTimedPayment("Ulrika", "1884");
		SweBank.tick();
		assertEquals(10000, SweBank.getBalance("Ulrika").intValue());
		assertEquals(10000, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals(10000, SweBank.getBalance("Ulrika").intValue());
		assertEquals(10000, Nordea.getBalance("Bob").intValue());
		SweBank.addTimedPayment("Ulrika", "1885", 2, 1, new Money(5000, SEK), Nordea, "Glob");
		assertEquals(10000, SweBank.getBalance("Ulrika").intValue());
		SweBank.tick();
		assertEquals(10000, SweBank.getBalance("Ulrika").intValue());
		SweBank.tick();
		assertEquals(10000, SweBank.getBalance("Ulrika").intValue());
		SweBank.removeTimedPayment("Ulrika", "1885");
	}
}
