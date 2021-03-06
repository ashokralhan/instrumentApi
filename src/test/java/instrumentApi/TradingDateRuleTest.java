package instrumentApi;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TradingDateRuleTest {

	@Before
	public void setUp() throws Exception {
		InstrumentAPI api = InstrumentAPI.INSTANCE;
		api.clearAll();
	}

	@Test
	public void test() {
		MergeRule rule = new TradingDateRule(new ArrayList<String>());
		Instrument instrument = new Instrument("PRIME", "PB_03_2018", LocalDate.of(2018, 3, 14), LocalDate.of(2018, 3, 18),
				"LME_PB", "Lead 13 March 2018", Boolean.FALSE);
		Instrument result = rule.apply(instrument);
		assertEquals(instrument, result);
	}

	@Test
	public void testApply() {
		MergeRule rule = new TradingDateRule(new ArrayList<String>());
		Instrument instrument = new Instrument("PRIME", "PB_03_2018", LocalDate.of(2018, 3, 14), LocalDate.of(2018, 3, 18),
				"LME_PB", "Lead 13 March 2018", Boolean.FALSE);
		InstrumentAPI api = InstrumentAPI.INSTANCE;
		api.AddInstrument(new Instrument("LME", "PB_03_2018", LocalDate.of(2018, 3, 15), LocalDate.of(2018, 3, 17),
				"LME_PB", "Lead 13 March 2018"));
		Instrument result = rule.apply(instrument);
		System.out.println(result.getLastTradingDate());
		assertEquals(LocalDate.of(2018, 3, 15), result.getLastTradingDate());
		assertEquals(LocalDate.of(2018, 3, 17), result.getDeliveryDate());
	}

}
