package com.everyonewaiter.common;

import java.util.Objects;

import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.internal.Formatter;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class P6SpyFormatter implements MessageFormattingStrategy {

	private static final String CONNECTION_ID_FORMAT = "Connection ID: %d";
	private static final String EXECUTION_TIME_FORMAT = "Execution Time: %d ms";
	private static final String SEPARATOR = "-------------------------------------------------------------------------";

	@Override
	public String formatMessage(
		int connectionId,
		String now,
		long elapsed,
		String category,
		String prepared,
		String sql,
		String url
	) {
		if (isEmptySql(sql)) {
			return category;
		}
		return buildFormattedMessage(connectionId, elapsed, category, sql);
	}

	private boolean isEmptySql(String sql) {
		return sql.trim().isEmpty();
	}

	private String buildFormattedMessage(int connectionId, long elapsed, String category, String sql) {
		return System.lineSeparator()
			+ highlight(formatSql(sql, category))
			+ System.lineSeparator()
			+ System.lineSeparator()
			+ CONNECTION_ID_FORMAT.formatted(connectionId)
			+ EXECUTION_TIME_FORMAT.formatted(elapsed)
			+ SEPARATOR;
	}

	private String highlight(String sql) {
		return FormatStyle.HIGHLIGHT.getFormatter().format(sql);
	}

	private String formatSql(String sql, String category) {
		Formatter formatter = isDataDefinitionLanguageStatement(sql, category)
			? FormatStyle.DDL.getFormatter()
			: FormatStyle.BASIC.getFormatter();
		return formatter.format(sql);
	}

	private boolean isDataDefinitionLanguageStatement(String sql, String category) {
		return Objects.equals(Category.STATEMENT.getName(), category)
			&& isDataDefinitionLanguage(sql.trim().toLowerCase());
	}

	private boolean isDataDefinitionLanguage(String lowerSql) {
		return lowerSql.startsWith("create")
			|| lowerSql.startsWith("alter")
			|| lowerSql.startsWith("drop")
			|| lowerSql.startsWith("comment");
	}
}
