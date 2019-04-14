package instrumentApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
/*
 *  This class in immutable and hence thread safe can be used in stream of parallel stream without any issues.
 *  By default if you don't provide Tradeable then Tradeable is assumed true like in story 1 irrespective of instrument
 *  Used Local date as they are immutable if you use date then while getting the value get clone of date (Date implements IClonable )
 */
public final class Instrument {
	private final String source;
	private final String instrumentCode; // // also known as exchangeCode
	private final LocalDate lastTradingDate; // Local dates are immutable
	private final LocalDate deliveryDate; // Local dates are immutable
	private final String market;
	private final String label;
	private final Boolean tradeable;
	private static final String FORAMT_STRING = "| %s | %s | %s | %s | %s |";
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");

	public Instrument(String source, String instrumentCode, LocalDate lastTradingDate, LocalDate deliveryDate,
			String market, String label, Boolean tradeable) {
		Objects.requireNonNull(source);
		Objects.requireNonNull(instrumentCode);
		Objects.requireNonNull(lastTradingDate);
		Objects.requireNonNull(deliveryDate);
		Objects.requireNonNull(market);
		Objects.requireNonNull(label);
		Objects.requireNonNull(tradeable);
		this.source = source;
		this.instrumentCode = instrumentCode;
		this.lastTradingDate = lastTradingDate;
		this.deliveryDate = deliveryDate;
		this.market = market;
		this.label = label;
		this.tradeable = tradeable;
	}

	public Instrument(String source, String instrumentCode, LocalDate localDate, LocalDate localDate2, String market,
			String label) {
		this(source, instrumentCode, localDate, localDate2, market, label, Boolean.TRUE);
	}

	public String getSource() {
		return source;
	}

	public String getMarket() {
		return market;
	}

	public String getLabel() {
		return label;
	}

	public LocalDate getLastTradingDate() {
		return lastTradingDate;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public String getInstrumentCode() {
		return instrumentCode;
	}

	public Boolean getTradeable() {
		return tradeable;
	}

	@Override
	public String toString() {
		return String.format(FORAMT_STRING, lastTradingDate.format(dateFormatter), deliveryDate.format(dateFormatter),
				market, label, (tradeable ? "TRUE" : "FALSE"));
	}

}
