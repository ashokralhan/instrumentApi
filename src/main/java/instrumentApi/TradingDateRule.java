package instrumentApi;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/*
 *  ideally we should be able to build rule from configuration which can be applied to certain targets
 *  rule can be of target and update property from source
 */
public final class TradingDateRule implements MergeRule {

	// for future use for which sources this rule should be applied for for time
	// being it is always for PRIME
	private HashSet<String> targetSources = new HashSet<String>();

	/*
	 * Please provide either empty list or list of string where each item is source
	 * for which rule to apply.
	 */
	public TradingDateRule(List<String> targetSources) {
		Objects.requireNonNull(targetSources);
		for (String source : targetSources) {
			this.targetSources.add(source);
		}

	}

	/*
	 * Apply will always return result either the same instance or updates instance
	 */
	@Override
	public Instrument apply(Instrument t) {
		Objects.requireNonNull(t);
		// Empty targetSources means apply for all
		if (t.getSource() == "LME") // no need to apply for LME
			return t;
		else if (targetSources.isEmpty() || targetSources.contains(t.getSource())) {
			InstrumentAPI api = InstrumentAPI.INSTANCE;
			Instrument i = api.GetInstrument("LME", t.getInstrumentCode());
			if (i != null) {
				return new Instrument(t.getSource(), t.getInstrumentCode(), i.getLastTradingDate(), i.getDeliveryDate(),
						t.getMarket(), t.getLabel(), t.getTradeable());
			}
		}
		return t;
	}
}
