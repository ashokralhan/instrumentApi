package instrumentApi;

import java.util.function.UnaryOperator;

/*
 * Merge Rule is Unary Operator which given an instrument will update the instrument according to given rules.
 * we have done this way so that it can be used in future in stream and parallel streams
 * as Instrument is immutable it will return a new Instrument always and is thread safe
 */

public interface MergeRule extends UnaryOperator<Instrument> {
}
