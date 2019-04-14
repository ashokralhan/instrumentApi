package instrumentApi;

import java.time.LocalDate;
import java.util.Collections;

/*
 * Given the “LME” instrument “PB_03_2018” with these details:
    |  LAST_TRADING_DATE             | DELIVERY_DATE |  MARKET                 | LABEL                        |
    |  15-03-2018                    | 17-03-2018    |  PB                     | Lead 13 March 2018           |

    When “LME” publishes instrument “PB_03_2018”

    Then the application publishes the following instrument internally
    |  LAST_TRADING_DATE | DELIVERY_DATE  |  MARKET                 | LABEL                             | TRADABLE           |
    |  15-03-2018        | 17-03-2018     |  PB                     | Lead 13 March 2018                |  TRUE              |
    
 Given the “LME” instrument “PB_03_2018” with these details:
    |  LAST_TRADING_DATE             | DELIVERY_DATE |  MARKET                 | LABEL                  |
    |  15-03-2018                    | 17-03-2018    |  LME_PB                 | Lead 13 March 2018     |

    And a  “PRIME” instrument “PB_03_2018” with these details:
    |  LAST_TRADING_DATE | DELIVERY_DATE |  MARKET                 | LABEL                  | EXCHANGE_CODE | TRADABLE  |
    |  14-03-2018        | 18-03-2018    |  LME_PB                 | Lead 13 March 2018     | PB_03_2018    | FALSE     |    
 */
public class Driver {

	public static Instrument getInstrumentData1() {
		return new Instrument("LME", "PB_03_2018", LocalDate.of(2018, 3, 15), LocalDate.of(2018, 3, 17), "PB",
				"Lead 13 March 2018");
	}

	public static Instrument getInstrumentData2() {
		return new Instrument("LME", "PB_03_2018", LocalDate.of(2018, 3, 15), LocalDate.of(2018, 3, 17), "LME_PB",
				"Lead 13 March 2018");
	}

	public static Instrument getInstrumentData3() {
		return new Instrument("PRIME", "PB_03_2018", LocalDate.of(2018, 3, 14), LocalDate.of(2018, 3, 18), "LME_PB",
				"Lead 13 March 2018", Boolean.FALSE);
	}

	public static MergeRule getRule1() {
		TradingDateRule rule = new TradingDateRule(Collections.emptyList());
		return rule;
	}

	public static MergeRule getRule2() {
		TradeableRule rule = new TradeableRule(Collections.emptyList());
		return rule;
	}

	public static InstrumentAPI getAPI() {
		InstrumentAPI api = InstrumentAPI.INSTANCE;
		api.clearAll();
		api.RegisterRule(getRule1());
		api.RegisterRule(getRule2());
		return api;
	}

	public static String runStory1() {
		InstrumentAPI api = getAPI();
		api.AddInstrument(getInstrumentData1());
		return api.PublishInstrument("LME", "PB_03_2018");
	}

	public static String runStory21() {
		InstrumentAPI api = getAPI();
		api.AddInstrument(getInstrumentData2());
		api.AddInstrument(getInstrumentData3());
		return api.PublishInstrument("LME", "PB_03_2018");
	}

	public static String runStory22() {
		InstrumentAPI api = getAPI();
		api.AddInstrument(getInstrumentData2());
		api.AddInstrument(getInstrumentData3());
		return api.PublishInstrument("PRIME", "PB_03_2018");
	}

	public static void main(String[] args) {
		System.out.println("Making an assumption that in second story when prime tradeable is FALSE them LME should have published FALSE");
		System.out.println(runStory1());
		System.out.println(runStory21());
		System.out.println(runStory22());
	}

}
