package instrumentApi;

import java.util.concurrent.ConcurrentHashMap;
/*
 * Instrument Store is store of all instrument added If same instrument( same source and instrument code) then it replaces the previous value.
 */
public class InstrumentStore {

	// Two step map first step by source and then by instrument code
	ConcurrentHashMap<String, ConcurrentHashMap<String, Instrument>> map = new ConcurrentHashMap<>();

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
