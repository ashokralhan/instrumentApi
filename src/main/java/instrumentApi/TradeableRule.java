package instrumentApi;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * <li>Tradeable Rule for implementing the requirement that <b>However we
 * enforce the TRADABLE flag from PRIME in all cases.</b>
 * <li>Ideally we should be able to build rule from configuration which can be
 * applied to certain targets
 * <li>Rule can be of target and update property from source
 */
public final class TradeableRule implements MergeRule {
	// for future use for which sources this rule should be applied for for time
	// being it is always for LME
	private final HashSet<String> targetSources = new HashSet<String>();
	/**
	 * 
	 * @param targetSources
	 *                      <li>Please provide either empty list or list of strings
	 *                      where each item is source for which rule to apply.
	 */
	public TradeableRule(List<String> targetSources) {
		Objects.requireNonNull(targetSources);
		for (String source : targetSources) {
			this.targetSources.add(source);
		}

	}

	/**
	 * Apply will always return result either the same instance or updates instance
	 */
	@Override
	public Instrument apply(Instrument instrument) {
		Objects.requireNonNull(instrument);
		if (instrument.getSource() == "PRIME") // no need to apply for PRIME
			return instrument;
		// Empty targetSources means apply for all
		else if (targetSources.isEmpty() || targetSources.contains(instrument.getSource())) {
			InstrumentAPI api = InstrumentAPI.INSTANCE;
			Instrument prime = api.GetInstrument("PRIME", instrument.getInstrumentCode());
			if (prime != null) {
				return new Instrument(instrument.getSource(), instrument.getInstrumentCode(),
						instrument.getLastTradingDate(), instrument.getDeliveryDate(), instrument.getMarket(),
						instrument.getLabel(), prime.getTradeable());
			}
		}
		return instrument;
	}

}
