package instrumentApi;

import java.time.LocalDate;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <li>This is main singleton class to be used as API gateway.
 * <li>You can add instrument through this API.
 * <li>At present there is no facility to remove instrument but can be added at
 * request.
 * <li>If same instrument ( same source and instrument code ) is added it will
 * replace the existing instrument.
 * <li>You can add rule which will be applied before publishing. Presently no
 * facility to remove rules.
 * <li>For now rules are applied only while publishing in future we can have add
 * two types of rules while saving the instrument or while publishing
 * <li>Rules can be applied to selected source or all source
 */
public final class InstrumentAPI {
	/*
	 * We are not doing lazy initialization so we need not synchronize the get
	 * instance
	 */
	private InstrumentStore store = new InstrumentStore();
	private ConcurrentLinkedQueue<MergeRule> rules = new ConcurrentLinkedQueue<MergeRule>();
	public static final InstrumentAPI INSTANCE = new InstrumentAPI();

	/*
	 * Private Constructor to implement Singleton design pattern
	 */
	private InstrumentAPI() {

	}

	/**
	 * <li>Clear all rules and instrument for running different story.
	 * <li>This is provided just for testing and may not be required at all
	 * <li>Thread Safety if another thread is still working it may continue to work
	 * as it will have have new or old copies of rules and instrument
	 * <li>in that case result may not be correct but will not throw exception or
	 * deadlock
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

	/**
	 * Async version of PublishInstrument assuming if publish instrument takes a lot
	 * of time, we can provide similar functions for others.
	 * 
	 * @param source
	 * @param instrumentCode
	 * @return a future supplier calling get on that you can get value
	 */
	public CompletableFuture<String> PublishInstrumentAsync(final String source, final String instrumentCode) {
		return CompletableFuture.supplyAsync(() -> PublishInstrument(source, instrumentCode));
	}

	public void RegisterRule(MergeRule rule) {
		Objects.requireNonNull(rule);
		rules.add(rule);
	}

}
