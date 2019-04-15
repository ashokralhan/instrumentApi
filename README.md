# InstrumentAPI

InstrumentAPI by Ashok Ralhan

### Design Notes

## Instrument
 * This class in immutable and hence thread safe and can be used in parallel streams without any issues.
 * By default if you don't provide Tradeable then Tradeable is assumed TRUE like in story 1 irrespective of instrument
 * Used Local date as they are immutable if you want to use date then while getting the value get clone of date (Date implements IClonable )
 * Have not implemented hashcode and equals as this class should not to be used as key
 
 ## InstrumentStore
 * Instrument Store is store of all instruments added
 * If same instrument( same source and instrument code) then it replaces the previous value.
 * The assumption here is that later message is more updated message if not we should implement either compare method for instrument or should write a
 * rule for merging the instrument.
 * We have deliberately decided to not use market as key to keep the design simple
 
 ## InstrumentAPI
 * This is main singleton class to be used as API gateway.
 * You can add instrument through this API.
 * At present there is no facility to remove instrument but can be added at request.
 * If same instrument ( same source and instrument code ) is added it will
 * replace the existing instrument.
 * You can add rule which will be applied before publishing. Presently no facility to remove rules.
 * For now rules are applied only while publishing in future we can have add two types of rules while saving the instrument or while publishing
 * Rules can be applied to selected source or all source

## MergeRule
 * Merge Rule is Unary Operator which given an instrument will update the instrument according to given rules.
 * We have done this way so that it can be used in future in stream and parallel streams as Instrument is immutable it will return a new Instrument always and is thread safe
 
## TradeableRule
 * Tradeable Rule for implementing the requirement that <b>However we enforce the TRADABLE flag from PRIME in all cases.</b>
 * Ideally we should be able to build rule from configuration which can be applied to certain targets
 * Rule can be of target and update property from source
 
 ##TradingDateRule
 * Tradeing Date Rule for implementing the requirement that <b>We trust/use the last trading date and delivery date from the LME exchange over that of PRIME</b>
 * Ideally we should be able to build rule from configuration which can be applied to certain targets
 * Rule can be of target and update property from source