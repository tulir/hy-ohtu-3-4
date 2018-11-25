
package ohtu;

import ohtu.verkkokauppa.*;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class TestClass {
	Viitegeneraattori viite;
	Pankki pankki;
	Varasto varasto;
	Kauppa kauppa;

	@Before
	public void setUp() {
		viite = mock(Viitegeneraattori.class);
		pankki = mock(Pankki.class);
		varasto = mock(Varasto.class);
		kauppa = new Kauppa(varasto, pankki, viite);
	}

	@Test
	public void test1() {
		when(viite.uusi()).thenReturn(42);

		when(varasto.saldo(1)).thenReturn(10);
		when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

		kauppa.aloitaAsiointi();
		kauppa.lisaaKoriin(1);
		kauppa.tilimaksu("pekka", "12345");

		verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));
	}

	@Test
	public void test2() {
		when(viite.uusi()).thenReturn(43);

		when(varasto.saldo(1)).thenReturn(10);
		when(varasto.saldo(2)).thenReturn(1);
		when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
		when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "leipä", 6));

		kauppa.aloitaAsiointi();
		kauppa.lisaaKoriin(1);
		kauppa.lisaaKoriin(2);
		kauppa.tilimaksu("pekka", "12345");

		verify(pankki).tilisiirto(eq("pekka"), eq(43), eq("12345"), anyString(), eq(11));
	}

	@Test
	public void test3() {
		when(viite.uusi()).thenReturn(44);

		when(varasto.saldo(1)).thenReturn(10);
		when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

		kauppa.aloitaAsiointi();
		kauppa.lisaaKoriin(1);
		kauppa.lisaaKoriin(1);
		kauppa.tilimaksu("pekka", "12345");

		verify(pankki).tilisiirto(eq("pekka"), eq(44), eq("12345"), anyString(), eq(10));
	}

	@Test
	public void test4() {
		when(viite.uusi()).thenReturn(45);

		when(varasto.saldo(1)).thenReturn(0);
		when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

		kauppa.aloitaAsiointi();
		kauppa.lisaaKoriin(1);
		kauppa.tilimaksu("pekka", "12345");

		verify(pankki).tilisiirto(eq("pekka"), eq(45), eq("12345"), anyString(), eq(0));
	}

}
