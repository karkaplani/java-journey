package jdbc;

import java.sql.PreparedStatement;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.sql.ResultSet;

public class JDBCModel {

	private List<String> columnNames;
	private List<String> tableNames;
	private Connection connection;
	private String user;
	private String pass;

	protected JDBCModel() {
		columnNames = new LinkedList<String>();
		tableNames = new LinkedList<String>();
	}

	public void setCredentials(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}
	
	private void checkConnectionIsValid() throws SQLException {
		
        if (this.connection == null || this.connection.isClosed()) {
            throw new SQLException("Connection is invalid");
        }
    }

	private void checkTableNameAndColumnsAreValid(String table) throws SQLException {

		Objects.requireNonNull(table, "table name cannot be null");
		table = table.trim();

		if (this.tableNames.isEmpty()) {
			this.getAndInitializeTableNames();
		}
		if (this.columnNames.isEmpty()) {
			this.getAndInitializeColumnNames(table);
		}
		if (table.isEmpty() || !(this.tableNames.contains(table))) {
			throw new IllegalArgumentException("table name=\"" + table + "\" is not valid");
		}
	}

	public void connectTo(String url) throws SQLException {

		if(isConnected()) {
			connection.close();
		}

		connection = DriverManager.getConnection(url, user, pass); 
	}

	public boolean isConnected() throws SQLException {
		return connection != null && connection.isValid(0);
	} 

	public List<String> getAndInitializeColumnNames(String table) throws SQLException {

		this.checkConnectionIsValid();
		this.columnNames.clear();

		DatabaseMetaData dbMeta = connection.getMetaData();

		try (ResultSet rs = dbMeta.getColumns(connection.getCatalog(), null, table, null)) {
			while (rs.next()) {
				this.columnNames.add(rs.getNString("COLUMN_NAME"));
			}
		}
		List<String> list = Collections.unmodifiableList(this.columnNames);
		return list;
	}

	public List<String> getAndInitializeTableNames() throws SQLException {

		this.checkConnectionIsValid();
		this.tableNames.clear();

		DatabaseMetaData dbMeta = connection.getMetaData();
		String[] s = { "TABLE" };

		try (ResultSet rs = dbMeta.getTables(connection.getCatalog(), null, null, s)) {
			while (rs.next()) {
				this.tableNames.add(rs.getString("TABLE_NAME"));
			}
		}
		
		List<String> list = Collections.unmodifiableList(this.tableNames);
		return list;
	}

	public List<List<Object>> getAll(String table) throws SQLException {

		return search(table, null);
	}

	public List<List<Object>> search(String table, String searchTerm) throws SQLException {

		this.checkConnectionIsValid();
		this.checkTableNameAndColumnsAreValid(table);

		List<List<Object>> list = new LinkedList<List<Object>>(); 
		boolean result = (searchTerm == null || searchTerm.isEmpty());
		String results = this.buildSQLSearchQuery(table, result);

		try (PreparedStatement statement = connection.prepareStatement(results)) {

			if (searchTerm != null) {
				searchTerm = String.format("%%%s%%", searchTerm);
				for (int i = 1; i <= columnNames.size(); i++) {
					statement.setObject(i, searchTerm);
				}
			}
			this.extractRowsFromResultSet(statement, list);
		} catch (SQLException ex) { throw new IllegalStateException(ex); }
		return list;
	}

	private String buildSQLSearchQuery(String table, boolean withParameters) { 

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM ");
		builder.append(table);

		if (!withParameters) {
			return builder.toString();
		}
		else { 
			builder.append(" WHERE ");
			String delimeter = "";
			for (String columnName : columnNames) {
				builder.append(delimeter).append(columnName).append(" LIKE ?"); 
				delimeter = " OR ";
			}
			return builder.toString();
		}
	}

	private void extractRowsFromResultSet(PreparedStatement ps, List<List<Object>> list) throws SQLException {

		try (ResultSet result = ps.executeQuery()) {

			while (result.next()) {
				
				List<Object> row = new LinkedList<>(); 
				for(int j = 1; j <= this.columnNames.size(); j++) {
					row.add(result.getObject(j));
				} 
				list.add(row);
			}
		} 
	}

	public void close() throws SQLException {
		
		if (!(this.connection.equals(null))) {
			this.connection.close();
		}
	}
}