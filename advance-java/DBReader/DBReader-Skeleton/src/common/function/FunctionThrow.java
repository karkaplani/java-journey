package common.function;

import java.sql.SQLException;
import java.util.function.Function;

/**
 * not used in the assignment by default.
 * 
 * This is used specifically for cases when lambda needs to throw a checked exception. Default java.util.funtion which
 * are <Code>@FunctionalInterface</Code>s don't support throwing checked exceptions.
 * 
 * @see https://stackoverflow.com/a/27252163/764951
 * 
 * @author Shariar (Shawn) Emami
 *
 * @param <T> type of argument the lambda will take
 * @param <R> type of argument the lambda will return
 */
@FunctionalInterface
public interface FunctionThrow< T, R> extends Function< T, R> {

	@Override
	default R apply( T t) {
		try {
			return applyThrow( t);
		} catch ( SQLException e) {
			throw new IllegalStateException( e);
		}
	}

	R applyThrow( T t) throws SQLException;
}
