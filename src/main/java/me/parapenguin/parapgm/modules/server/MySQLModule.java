package me.parapenguin.parapgm.modules.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import me.parapenguin.parapgm.ParaPGM;
import me.parapenguin.parapgm.ServerLog;
import me.parapenguin.parapgm.modules.AdvancedModule;

public class MySQLModule implements AdvancedModule {
	
	public static abstract class Database {
		protected boolean connected;
		protected Connection connection;

		protected enum Statements {
			SELECT, INSERT, UPDATE, DELETE, DO, REPLACE, LOAD, HANDLER, CALL,
			CREATE, ALTER, DROP, TRUNCATE, RENAME, START, COMMIT, ROLLBACK, 
			SAVEPOINT, LOCK, UNLOCK, PREPARE, EXECUTE, DEALLOCATE, SET, SHOW, 
			DESCRIBE, EXPLAIN, HELP, USE, ANALYZE, ATTACH, BEGIN, DETACH, 
			END, INDEXED, ON, PRAGMA, REINDEX, RELEASE, VACUUM
		}

		public int lastUpdate;

		public Database() {
			this.connected = false;
			this.connection = null;
		}

		protected Statements getStatement(String query) {
			String trimmedQuery = query.trim();
			if (trimmedQuery.substring(0,6).equalsIgnoreCase("SELECT"))
				return Statements.SELECT;
			else if (trimmedQuery.substring(0,6).equalsIgnoreCase("INSERT"))
				return Statements.INSERT;
			else if (trimmedQuery.substring(0,6).equalsIgnoreCase("UPDATE"))
				return Statements.UPDATE;
			else if (trimmedQuery.substring(0,6).equalsIgnoreCase("DELETE"))
				return Statements.DELETE;
			else if (trimmedQuery.substring(0,6).equalsIgnoreCase("CREATE"))
				return Statements.CREATE;
			else if (trimmedQuery.substring(0,5).equalsIgnoreCase("ALTER"))
				return Statements.ALTER;
			else if (trimmedQuery.substring(0,4).equalsIgnoreCase("DROP"))
				return Statements.DROP;
			else if (trimmedQuery.substring(0,8).equalsIgnoreCase("TRUNCATE"))
				return Statements.TRUNCATE;
			else if (trimmedQuery.substring(0,6).equalsIgnoreCase("RENAME"))
				return Statements.RENAME;
			else if (trimmedQuery.substring(0,2).equalsIgnoreCase("DO"))
				return Statements.DO;
			else if (trimmedQuery.substring(0,7).equalsIgnoreCase("REPLACE"))
				return Statements.REPLACE;
			else if (trimmedQuery.substring(0,4).equalsIgnoreCase("LOAD"))
				return Statements.LOAD;
			else if (trimmedQuery.substring(0,7).equalsIgnoreCase("HANDLER"))
				return Statements.HANDLER;
			else if (trimmedQuery.substring(0,4).equalsIgnoreCase("CALL"))
				return Statements.CALL;
			else
				return Statements.SELECT;
		}
	}
	
	public class MySQL extends Database {
		private String hostname = "";
		private String portnmbr = "";
		private String username = "";
		private String password = "";
		private String database = "";

		public MySQL(String hostname, String port, String db, String username, String password) {
			super();
			this.hostname = hostname;
			this.portnmbr = port;
			this.database = db;
			this.username = username;
			this.password = password;
		}

		public Connection open() {
			String url = "";
			try {
				url = "jdbc:mysql://" + this.hostname + ":" + this.portnmbr + "/" + this.database + "?zeroDateTimeBehavior=convertToNull&failOverReadOnly=false";
				this.connection = DriverManager.getConnection(url, this.username, this.password);
				return this.connection;
			} catch (SQLException e) {
			}
			return null;
		}

		public void close() {
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public Connection getConnection() {
			return this.connection;
		}

		public boolean checkConnection() {
			if (connection != null)
				return true;
			return false;
		}

		public ResultSet query(String query) {
			if (!checkConnection()) open();
			
			Statement statement = null;
			ResultSet result = null;
			try {
				statement = connection.createStatement();
				result = statement.executeQuery(query);
				return result;
			} catch (SQLException e) {
				ServerLog.warning("MySQL query failed: " + query);
				e.printStackTrace();
			}
			
			return result;
		}

		public boolean update(final String query) {
			SchedulerModule run = new SchedulerModule(ParaPGM.getInstance()) {
				public void run() {
					syncUpdate(query);
				}
			};
			
			run.start(true);
			return true;
		}

		public boolean syncUpdate(final String query) {
			if (!checkConnection()) open();
			
			Statement statement = null;
			try {
				statement = connection.createStatement();
				statement.executeUpdate(query);
			} catch (SQLException e) {
				ServerLog.warning("MySQL update failed: " + query);
				e.printStackTrace();
			}
			
			return true;
		}

		public int resultInt(ResultSet result, int column) {
			if (result == null)
				return 0;

			try {
				result.next();
				int integer = result.getInt(column);
				result.close();

				return integer;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return 0;
		}

		public String resultString(ResultSet result, int column) {
			if (result == null)
				return null;

			try {
				result.next();
				String string = result.getString(column);
				result.close();

				return string;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return null;
		}

		public PreparedStatement prepare(String query) {
			PreparedStatement ps = null;
			try {
				ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				return ps;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ps;
		}

		public boolean insert(String table, String[] column, String[] value) {
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			for (String s : column) {
				sb1.append(s + ",");
			}
			for (String s : value) {
				sb2.append(s + ",");
			}
			String columns = sb1.toString().substring(0, sb1.toString().length() - 1);
			String values = sb2.toString().substring(0, sb2.toString().length() - 1);
			
			return update("INSERT INTO " + table + "(" + columns + ") VALUES (" + values + ")");
		}

		public boolean deleteTable(String table) {
			if (table.equals("") || table == null) {
				return true;
			}
			
			return update("DROP TABLE " + table);
		}

		public boolean createTable(String table, String query) {
			Statement statement = null;
			try {
				statement = connection.createStatement();
				statement.executeUpdate("create table if not exists " + table + " (" + query + ");");
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		public String escape(String str) throws Exception {
			if (str == null) {
				return null;
			}

			if (str.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/? ]", "").length() < 1) {
				return str;
			}

			String clean_string = str;
			clean_string = clean_string.replaceAll("\\\\", "\\\\\\\\");
			clean_string = clean_string.replaceAll("\\n", "\\\\n");
			clean_string = clean_string.replaceAll("\\r", "\\\\r");
			clean_string = clean_string.replaceAll("\\t", "\\\\t");
			clean_string = clean_string.replaceAll("\\00", "\\\\0");
			clean_string = clean_string.replaceAll("'", "\\\\'");
			clean_string = clean_string.replaceAll("\\\"", "\\\\\"");

			if (clean_string.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/?\\\\\"' ]", "").length() < 1) {
				return clean_string;
			}

			java.sql.Statement stmt = connection.createStatement();
			String qry = "SELECT QUOTE('" + clean_string + "')";

			stmt.executeQuery(qry);
			java.sql.ResultSet resultSet = stmt.getResultSet();
			resultSet.first();
			String r = resultSet.getString(1);
			return r.substring(1, r.length() - 1);
		}

		public String quote(String str) throws Exception {
			if (str == null) {
				return "NULL";
			}
			return "'" + escape(str) + "'";
		}

		public String nameQuote(String str) throws Exception {
			if (str == null) {
				return "NULL";
			}
			return "`" + escape(str) + "`";
		}
		
		public int getInt(String table, String collect) {
			if (!checkConnection()) open();
			
			if (!checkConnection()) {
				ServerLog.warning("No connection to database... Returning 0.");
				return 0;
			}
			
			String query = "SELECT " + collect + " FROM " + table;
			ResultSet res = query(query);
			
			int r = 0;
			try {
				while (res.next()) {
					r = res.getInt(collect);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return r;
		}
		
		public int getInt(String table, String collect, String where_equals) {
			if (!checkConnection()) open();
			
			if (!checkConnection()) {
				ServerLog.warning("No connection to database... Returning 0.");
				return 0;
			}
			
			String query = "SELECT " + collect + " FROM " + table + " WHERE " + where_equals;
			ResultSet res = query(query);
			
			int r = 0;
			try {
				while (res.next()) {
					r = res.getInt(collect);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return r;
		}
		
		public String getString(String table, String collect) {
			if (!checkConnection()) open();
			
			if (!checkConnection()) {
				ServerLog.warning("No connection to database... Returning 0.");
				return "";
			}
			
			String query = "SELECT " + collect + " FROM " + table;
			ResultSet res = query(query);
			
			String r = "";
			try {
				while (res.next()) {
					r = res.getString(collect);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return r;
		}
		
		public String getString(String table, String collect, String where_equals) {
			if (!checkConnection()) open();
			
			if (!checkConnection()) {
				ServerLog.warning("No connection to database... Returning 0.");
				return "";
			}
			
			String query = "SELECT " + collect + " FROM " + table + " WHERE " + where_equals;
			ResultSet res = query(query);
			
			String r = "";
			try {
				while (res.next()) {
					r = res.getString(collect);
				}
			} catch (SQLException e) {

			}
			
			return r;
		}
	}
	
}
