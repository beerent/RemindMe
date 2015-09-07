package database;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {
	private static final String DB_URL = "jdbc:mysql://192.168.1.111/remindme";
	private static final String username = "root";
	private static final String password = "root";

	private Connection conn; // holds a connection to the database

	public DatabaseHandler() {
		this.conn = getConnection();
	}

	/*
	 * connects to the database; if successful, returns a Connection obj
	 * connected to database, else returns null
	 */
	private Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(DB_URL, DatabaseHandler.username, DatabaseHandler.password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Accepts: a sql query
	 *
	 * Logic: creates a 2D ArrayList of the results found from the query.
	 * Examples (each <> is an ArrayList, anything inside it separated by commas
	 * are elements): "select release_name from releases limit 1" would return:
	 * <<"Release name 1">>
	 *
	 * "select release_name, date from releases limit 1" would return: <<
	 * "Release name 1", "date">>
	 *
	 * "select release_name from releases limit 2" would return: <<
	 * "Release name 1">, <"Release Name 2">>
	 *
	 * "select release_name, date from releases limit 2" would return: <<
	 * "Release name 1", "date">, <"Release Name 2", "date">>
	 */
	public QueryResult executeQuery(String sql) {
		ArrayList<ArrayList<String>> container = new ArrayList<ArrayList<String>>();

		try {
			Statement statement = this.conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			ResultSetMetaData meta = resultSet.getMetaData();
			final int columnCount = meta.getColumnCount();

			while (resultSet.next()) {
				ArrayList<String> columnList = new ArrayList<String>();
				container.add(columnList);

				for (int column = 1; column <= columnCount; column++) {
					Object value = resultSet.getObject(column);
					if (value != null) {
						columnList.add(value.toString());
					} else {
						columnList.add("null"); // you need this to keep your
												// columns in sync.
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new QueryResult(container);
	}

	// accepts an insert statement and commits it to the database
	public void executeInsert(String sql) {
		try {
			Statement stmt = this.conn.createStatement();
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect(){
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}