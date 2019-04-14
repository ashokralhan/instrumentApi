package instrumentApi;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
 *  This is main singleton class
 *  You can add instrument to this at present no facility to remove instrument.
 *  You can add rule which will be applied before publishing
 */
public class InstrumentAPI {
	/*
	 * We are not doing lazy initialization so we need not synchronize the get instance
	 */
	private InstrumentStore store = new InstrumentStore();
	private ConcurrentLinkedQueue<MergeRule> rules = new ConcurrentLinkedQueue<MergeRule>();
	public static final InstrumentAPI INSTANCE = new InstrumentAPI();

	/*
	 * Private Constructor
	 */
	private InstrumentAPI() {

	}

	/*
	 * clear all rules and instrument for running different story This is provided
	 * just for testing and may not be required at all Thread Safety if another
	 * thread is still working it may continue to work as it will its own copies of
	 * rules and store or new values but will not throw exception.
	 */
	public void clearAll() {
		store = new InstrumentStore();
		rules = new ConcurrentLinkedQueue<MergeRule>();
	}

	public void AddInstrument(Instrument i) {
		Objects.requireNonNull(i);
		store.AddOrUpdateInstrument(i);
	}

	public void AddInstrument(String source, String instrumentCode, LocalDate lastTradingDate, LocalDate deliveryDate,
			String market, String label, Boolean tradeable) {
		Instrument i = new Instrument(source, instrumentCode, lastTradingDate, deliveryDate, market, label, tradeable);
		store.AddOrUpdateInstrument(i);
	}

	public void AddInstrument(String source, String instrumentCode, LocalDate lastTradingDate, LocalDate deliveryDate,
			String market, String label) {
		Instrument i = new Instrument(source, instrumentCode, lastTradingDate, deliveryDate, market, label,
				Boolean.TRUE);
		store.AddOrUpdateInstrument(i);
	}

	public Instrument GetInstrument(String source, String instrumentCode) {
		return store.GetInstrument(source, instrumentCode);
	}

	public String PublishInstrument(String source, String instrumentCode) {
		Instrument i = GetInstrument(source, instrumentCode);
		if (i != null) {
			for (MergeRule rule : rules)
				i = rule.apply(i);

			return i.toString();
		}
		return null;
	}

	public void RegisterRule(MergeRule rule) {
		Objects.requireNonNull(rule);
		rules.add(rule);
	}

}
