package instrumentApi;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

public class InstrumentAPITest {
	
	@Before
	public void setUp() throws Exception {
		InstrumentAPI api = InstrumentAPI.INSTANCE;
		api.clearAll();
	}

	@Test
	public void testStory1() {
		InstrumentAPI api = InstrumentAPI.INSTANCE;
		api.RegisterRule(new TradingDateRule(Collections.emptyList()));
		api.RegisterRule(new TradeableRule(Collections.emptyList()));
		api.AddInstrument(new Instrument("LME", "PB_03_2018", LocalDate.of(2018, 3, 15), LocalDate.of(2018, 3, 17),
				"PB", "Lead 13 March 2018"));
		String result = api.PublishInstrument("LME", "PB_03_2018");
		System.out.println(result);
		assertEquals("| 15-03-2018 | 17-03-2018 | PB | Lead 13 March 2018 | TRUE |", result);
	}

	@Test
	public void testStory21() {
		InstrumentAPI api = InstrumentAPI.INSTANCE;
		api.RegisterRule(new TradingDateRule(Collections.emptyList()));
		api.RegisterRule(new TradeableRule(Collections.emptyList()));
		api.AddInstrument(new Instrument("LME", "PB_03_2018", LocalDate.of(2018, 3, 15), LocalDate.of(2018, 3, 17),
				"LME_PB", "Lead 13 March 2018"));
		api.AddInstrument(new Instrument("PRIME", "PB_03_2018", LocalDate.of(2018, 3, 14), LocalDate.of(2018, 3, 18),
				"LME_PB", "Lead 13 March 2018", Boolean.FALSE));
		String result = api.PublishInstrument("LME", "PB_03_2018");
		System.out.println(result);
		assertEquals("| 15-03-2018 | 17-03-2018 | LME_PB | Lead 13 March 2018 | FALSE |", result);
	}

	@Test
	public void testStory22() {
		InstrumentAPI api = InstrumentAPI.INSTANCE;
		api.RegisterRule(new TradingDateRule(Collections.emptyList()));
		api.RegisterRule(new TradeableRule(Collections.emptyList()));
		api.AddInstrument(new Instrument("LME", "PB_03_2018", LocalDate.of(2018, 3, 15), LocalDate.of(2018, 3, 17),
				"LME_PB", "Lead 13 March 2018"));
		api.AddInstrument(new Instrument("PRIME", "PB_03_2018", LocalDate.of(2018, 3, 14), LocalDate.of(2018, 3, 18),
				"LME_PB", "Lead 13 March 2018", Boolean.FALSE));
		String result = api.PublishInstrument("PRIME", "PB_03_2018");
		System.out.println(result);
		assertEquals("| 15-03-2018 | 17-03-2018 | LME_PB | Lead 13 March 2018 | FALSE |", result);
	}

	@Test
	public void testInstrumentLME() {
		InstrumentAPI api = InstrumentAPI.INSTANCE;
		api.AddInstrument("LME", "PB_03_2018", LocalDate.of(2018, 3, 15), LocalDate.of(2018, 3, 17), "LME_PB",
				"Lead 13 March 2018");
		Instrument i = api.GetInstrument("LME", "PB_03_2018");
		System.out.println(i.toString());
		assertEquals("| 15-03-2018 | 17-03-2018 | LME_PB | Lead 13 March 2018 | TRUE |", i.toString());
	}

	@Test
	public void testInstrumentPRIME() {
		InstrumentAPI api = InstrumentAPI.INSTANCE;
		api.AddInstrument("PRIME", "PB_03_2018", LocalDate.of(2018, 3, 14), LocalDate.of(2018, 3, 18), "LME_PB",
				"Lead 13 March 2018", Boolean.FALSE);
		Instrument i = api.GetInstrument("PRIME", "PB_03_2018");
		System.out.println(i.toString());
		assertEquals("| 14-03-2018 | 18-03-2018 | LME_PB | Lead 13 March 2018 | FALSE |", i.toString());
	}

}
