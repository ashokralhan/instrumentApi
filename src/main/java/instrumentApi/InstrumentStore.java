package instrumentApi;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <li>Instrument Store is store of all instruments added
 * <li>If same instrument( same source and instrument code) then it replaces the
 * previous value.
 * <li>The assumption here is that later message is more updated message if not
 * we should implement either compare method for instrument or should write a
 * rule for merging the instrument.
 * <li>we have deliberately decided to not use market as key to keep the design
 * simple
 */
public final class InstrumentStore {

	// Two step map first step by source and then by instrument code
	private ConcurrentHashMap<String, ConcurrentHashMap<String, Instrument>> map = new ConcurrentHashMap<>();

	public void AddOrUpdateInstrument(Instrument i) {
		ConcurrentHashMap<String, Instrument> instrumentMap = map.get(i.getSource());
		if (instrumentMap == null) {
			map.putIfAbsent(i.getSource(), new ConcurrentHashMap<String, Instrument>());
			instrumentMap = map.get(i.getSource());
		}
		instrumentMap.put(i.getInstrumentCode(), i);
	}

	public Instrument GetInstrument(String source, String instrumentCode) {
		ConcurrentHashMap<String, Instrument> instrumentMap = map.get(source);
		if (instrumentMap != null)
			return instrumentMap.get(instrumentCode);
		return null;
	}

}
