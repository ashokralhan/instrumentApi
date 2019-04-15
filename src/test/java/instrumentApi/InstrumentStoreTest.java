package instrumentApi;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class InstrumentStoreTest {

	@Test
	public void testGetAndSetInstrument() {
		InstrumentStore store = new InstrumentStore();
		Instrument i = new Instrument("LME", "PB_03_2018", LocalDate.of(2018, 3, 15), LocalDate.of(2018, 3, 17), "PB",
				"Lead 13 March 2018");
		store.AddOrUpdateInstrument(i);
		Instrument r = store.GetInstrument("LME", "PB_03_2018");
		assertEquals(i, r);

	}

}
