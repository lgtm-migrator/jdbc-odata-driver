package org.fornever.odata.jdbc.v2.sql;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;

/**
 * Test how to parse sql
 * 
 * @author Theo Sun <theo.sun@outlook.com>
 *
 */
public class DruidSQLParserTests {

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(this.getClass().getName());

	private static String SIMPLE_SELECT_STATEMENT = "select * from hello";

	private static String COMPLEX_SELECT_STATEMENT = "select eventId,eventKey,eventName,flag from event where eventId = 10 and eventKey = '123' ";

	/**
	 * basic test
	 */
	@Test
	public void testSimpleSelectStatement() {

		SQLStatement s = SQLUtils.parseSingleMysqlStatement(SIMPLE_SELECT_STATEMENT);

		assert s instanceof SQLSelectStatement;

		assert ((SQLSelectStatement) s).getSelect().getQueryBlock().getSelectList().get(0).getExpr().toString()
				.equals("*");

	}

	/**
	 * test how to get select fields & table name
	 */
	@Test
	public void testComplexSelectStatement() {

		SQLStatement s = SQLUtils.parseSingleMysqlStatement(COMPLEX_SELECT_STATEMENT);

		assert s instanceof SQLSelectStatement;

		List<SQLSelectItem> selects = ((SQLSelectStatement) s).getSelect().getQueryBlock().getSelectList();

		SQLTableSource t = ((SQLSelectStatement) s).getSelect().getQueryBlock().getFrom();

		assert t instanceof SQLExprTableSource;

		assert ((SQLExprTableSource) t).getExpr().toString().equals("event");

		assert selects.get(0).getExpr().toString().equals("eventId");

		assert selects.get(1).getExpr().toString().equals("eventKey");

		assert selects.get(2).getExpr().toString().equals("eventName");

		assert selects.get(3).getExpr().toString().equals("flag");

	}

}
