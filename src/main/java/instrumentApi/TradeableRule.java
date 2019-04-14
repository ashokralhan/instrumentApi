package instrumentApi;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class TradeableRule implements MergeRule {
	// for future use for which sources this rule should be applied for for time
	// being it is always for LME
	private HashSet<String> targetSources = new HashSet<String>();

	/*
	 * Please provide either empty list or list of string where each item is source
	 * for which rule to apply.
	 */
	public TradeableRule(List<String> targetSources) {
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
		if (t.getSource() == "PRIME") // no need to apply for PRIME
			return t;
		else if (targetSources.isEmpty() || targetSources.contains(t.getSource())) {
			InstrumentAPI api = InstrumentAPI.INSTANCE;
			Instrument i = api.GetInstrument("PRIME", t.getInstrumentCode());
			if (i != null) {
				return new Instrument(t.getSource(), t.getInstrumentCode(), t.getLastTradingDate(), t.getDeliveryDate(),
						t.getMarket(), t.getLabel(), i.getTradeable());
			}
		}
		return t;
	}

}
