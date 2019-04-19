package instrumentApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * <li>This class in immutable and hence thread safe and can be used in parallel
 * streams without any issues.
 * <li>By default if you don't provide Tradeable then Tradeable is assumed TRUE
 * like in story 1 irrespective of instrument
 * <li>Used Local date as they are immutable if you want to use date then while
 * getting the value get clone of date (Date implements IClonable )
 * <li>Have not implemented hashcode and equals as this class should not to be
 * used as key
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

	/**
	 * 
	 * @param source          source of the instrument like LME/PRIME
	 * @param instrumentCode  instrument code like PB_03_2018
	 * @param lastTradingDate Last trading Trade
	 * @param deliveryDate    Delivery Date
	 * @param market          Market
	 * @param label           Label
	 * @param tradeable       Tradeable
	 */
	public Instrument(String source, String instrumentCode, LocalDate lastTradingDate, LocalDate deliveryDate,
			String market, String label, Boolean tradeable) {
		this.source = Objects.requireNonNull(source);
		this.instrumentCode = Objects.requireNonNull(instrumentCode);
		this.lastTradingDate = Objects.requireNonNull(lastTradingDate);
		this.deliveryDate = Objects.requireNonNull(deliveryDate);
		this.market = Objects.requireNonNull(market);
		this.label = Objects.requireNonNull(label);
		this.tradeable = Objects.requireNonNull(tradeable);
	}

	/**
	 * 
	 * @param source          source of the instrument like LME/PRIME
	 * @param instrumentCode  instrument code like PB_03_2018
	 * @param lastTradingDate Last trading Trade
	 * @param deliveryDate    Delivery Date
	 * @param market          Market
	 * @param label           Label
	 */
	public Instrument(String source, String instrumentCode, LocalDate lastTradingDate, LocalDate deliveryDate,
			String market, String label) {
		this(source, instrumentCode, lastTradingDate, deliveryDate, market, label, Boolean.TRUE);
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

	public Instrument withTradeable(Boolean tradeable) {
		return new Instrument(this.getSource(), this.getInstrumentCode(), this.getLastTradingDate(),
				this.getDeliveryDate(), this.getMarket(), this.getLabel(), tradeable);
	}

	public Instrument withLastTradingDateAndDeliveryDate(LocalDate lastTradingDate, LocalDate deliveryDate) {
		return new Instrument(this.getSource(), this.getInstrumentCode(), lastTradingDate, deliveryDate,
				this.getMarket(), this.getLabel(), this.getTradeable());
	}

	@Override
	public String toString() {
		return String.format(FORAMT_STRING, lastTradingDate.format(dateFormatter), deliveryDate.format(dateFormatter),
				market, label, (tradeable ? "TRUE" : "FALSE"));
	}

}
