package instrumentApi;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class InstrumentTest {

	@Test
	public void testLMEInstrument() {
		Instrument i = new Instrument("LME", "PB_03_2018", LocalDate.of(2018, 3, 15), LocalDate.of(2018, 3, 17), "PB",
				"Lead 13 March 2018");
		assertEquals(Boolean.TRUE, i.getTradeable());
		assertEquals("| 15-03-2018 | 17-03-2018 | PB | Lead 13 March 2018 | TRUE |", i.toString());
	}

	@Test
	public void testLWithTradeable() {
		Instrument i = new Instrument("LME", "PB_03_2018", LocalDate.of(2018, 3, 15), LocalDate.of(2018, 3, 17), "PB",
				"Lead 13 March 2018");
		assertEquals(Boolean.TRUE, i.getTradeable());
		Instrument r = i.withTradeable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, r.getTradeable());
	}

	@Test
	public void testLWithLastTradingDate() {
		Instrument i = new Instrument("LME", "PB_03_2018", LocalDate.of(2018, 3, 15), LocalDate.of(2018, 3, 17), "PB",
				"Lead 13 March 2018");
		assertEquals(Boolean.TRUE, i.getTradeable());
		Instrument r = i.withLastTradingDateAndDeliveryDate(LocalDate.of(2018, 3, 20), LocalDate.of(2018, 3, 20));
		assertEquals(LocalDate.of(2018, 3, 20), r.getLastTradingDate());
		assertEquals(LocalDate.of(2018, 3, 20), r.getDeliveryDate());
	}

	@Test(expected = NullPointerException.class)
	public void testFailedInstrument() {
		Instrument i = new Instrument(null, null, null, null, null, null);
		assertEquals(Boolean.TRUE, i.getTradeable());
		assertEquals("| 15-03-2018 | 17-03-2018 | PB | Lead 13 March 2018 | TRUE |", i.toString());
	}
}
