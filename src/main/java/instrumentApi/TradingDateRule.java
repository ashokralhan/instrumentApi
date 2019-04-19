package instrumentApi;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * <li>TradeingDate Rule for implementing the requirement that <b>We trust/use
 * the last trading date and delivery date from the LME exchange over that of
 * PRIME</b>
 * <li>Ideally we should be able to build rule from configuration which can be
 * applied to certain targets
 * <li>Rule can be of target and update property from source
 */
public final class TradingDateRule implements MergeRule {

	// for future use for which sources this rule should be applied for for time
	// being it is always for PRIME
	private final HashSet<String> targetSources = new HashSet<String>();

	/**
	 * 
	 * @param targetSources
	 *                      <li>Please provide either empty list or list of strings
	 *                      where each item is source for which rule to apply.
	 */
	public TradingDateRule(List<String> targetSources) {
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
		if (instrument.getSource() == "LME") // no need to apply for LME
			return instrument;
		// Empty targetSources means apply for all
		else if (targetSources.isEmpty() || targetSources.contains(instrument.getSource())) {
			InstrumentAPI api = InstrumentAPI.INSTANCE;
			Instrument lme = api.GetInstrument("LME", instrument.getInstrumentCode());
			if (lme != null) {
				return instrument.withLastTradingDateAndDeliveryDate(lme.getLastTradingDate(), lme.getDeliveryDate());
			}
		}
		return instrument;
	}
}
