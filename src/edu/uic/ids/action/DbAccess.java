package edu.uic.ids.action;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.jsp.jstl.sql.Result;

import edu.uic.ids.model.DatabaseAccessInfoBean;
@ManagedBean(name="dbAccess")
@SessionScoped

public class DbAccess {
	private DatabaseAccessInfoBean dbBean;
	private Connection connection;
	private Connection worldConnection;
	private DatabaseMetaData databaseMetaData;
	private Statement statement;
	private Statement worldStatement;
	private ResultSet resultSet, rs;
	private ResultSetMetaData resultSetMetaData;
	private Result result;
	private String userName;
	

	public String getUserName() {
		return userName;
	}

	

	private ResultSet worldResultSet, worldRs;
	private ResultSetMetaData worldResultSetMetaData;
	private Result worldResult;
	private DatabaseMetaData worldDatabaseMetaData;

	
	private String jdbcDriver;
	private String url;
	private String urlWorld;

	private String message;
	private Boolean renderMessage = false;

	// SQLState constants for SQLException
	private static final String ACCESS_DENIED = "28000";
	private static final String TIMEOUT = "08S01";
	private static final String INVALID_DB_SCHEMA = "42000";

	public DbAccess(){
		dbClose();
	}
	
	//establishing a connection to a database
	public String dbConnect() {
		this.userName = dbBean.getUserName();
		switch (dbBean.getDbms()) {
		case "mysql":

			jdbcDriver = "com.mysql.jdbc.Driver";
			url = "jdbc:mysql://" + dbBean.getHost() + ":3306/f17x321" ;
			urlWorld = "jdbc:mysql://" + dbBean.getHost() + ":3306/world";
			break;
		case "db2":
			jdbcDriver = "com.ibm.db2.jcc.DB2Driver";
			url = "jdbc:db2://" + dbBean.getHost() + ":50000/f17x321" ;
			urlWorld = "jdbc:db2://" + dbBean.getHost() + ":3306/world";
			break;
		case "oracle":
			jdbcDriver = "oracle.jdbc.driver.OracleDriver";
			url = "jdbc:oracle:thin:@" + dbBean.getHost() + ":1521:/f17x321" ;
			urlWorld = "jdbc:oracle:thin:@" + dbBean.getHost() + ":3306/world";
			break;
		default:
			jdbcDriver = "com.mysql.jdbc.Driver";
			url = "jdbc:mysql://" + dbBean.getHost() + ":3306/f17x321" ;
			urlWorld = "jdbc:mysql://" + dbBean.getHost() + ":3306/world";
			break;
		}
		
		try {
			Class.forName(jdbcDriver); // registers driver
			connection = DriverManager.getConnection(url, dbBean.getUserName(), dbBean.getPassword());
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			databaseMetaData = connection.getMetaData();
			
			worldConnection = DriverManager.getConnection(urlWorld, dbBean.getUserName(), dbBean.getPassword());
			worldStatement = worldConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			worldDatabaseMetaData = worldConnection.getMetaData();
			return "SUCCESS";
		} catch (ClassNotFoundException e) {
			message = dbBean.getDbms() + " is not found or supported. Please make a selection again";
			renderMessage = true;
			return "FAIL";
		} // end catch
		
		//catching SQL exceptions
		catch (SQLException se) {
			if (se.getSQLState().equals(ACCESS_DENIED)) {
				System.out.println ("Access has been denied");
				message = "SQL State: " + se.getSQLState() + "\n" +  "SQL Error Code: " + se.getErrorCode() + "\n" +
						 "Message :" + se.getMessage() + "\n" + "Access has been denied. The entered credentials didn't get authenticated. Please try again";
			} else if (se.getSQLState().equals(INVALID_DB_SCHEMA)) {
				System.out.println ("Invalid schema entered");
				message = "SQL State: " + se.getSQLState() + "\n" +  "SQL Error Code: " + se.getErrorCode() + "\n" 
						+ "Message :" + se.getMessage() + "\n" + "Invalid schema enterted. Please enter a schema which is available in the database";
			} else if (se.getSQLState().equals(TIMEOUT)) {
				System.out.println ("Session has timed out");
				message = "SQL State: " + se.getSQLState() + "\n" +  "SQL Error Code: " + se.getErrorCode() + "\n" 
						+ "Message :" + se.getMessage() + "\n" + "The session timed out. Verify whether you have entered the correct host and port";
			}  else {
				System.out.println ("Oops, an error occured.");
				message = "SQL State: " + se.getSQLState() + "\n" +  "SQL Error Code: " + se.getErrorCode() + "\n" 
						+ "Message :" + se.getMessage() + "\n" + "Unknown SQL Exception has occurred, please try after sometime!";
			}
			renderMessage = true;
			return "FAIL";
		} // end catch
		
		//catching other exceptions
		catch (Exception e) {
			message = "The application encountered an exception: " + e.getMessage();
			e.printStackTrace();
			if (connection != null) {
				try {
					connection.close();
				} 
				
				catch (SQLException se) 
				{
					message = "Sorry, application encountered an SQL Exception at this moment while closing the connection. Please find the error details below "+ "\n" + " SQL State: " + se.getSQLState() + "\n" + "SQL Error Code: " + se.getErrorCode() + "\n"  + "Message :" + 
		            se.getMessage();
				}
			}
			renderMessage = true;
			return "FAIL";
		} // end catch
	}
	
	/* Method used to close the created resources */
	public void dbClose()
	{
		try {
			if(resultSet!=null) resultSet.close();
			if(statement!=null) statement.close();
			if(connection!=null) connection.close();
		} catch (SQLException se) {
			message = "Sorry, application encountered an SQL Exception at this moment while closing the connection. Please find the error details below"+ "\n" + "SQL State: " + se.getSQLState() + "\n" +  "SQL Error Code: " + se.getErrorCode() + "\n" +
					"Message :" + se.getMessage();
		}
		catch (Exception e)
		{
			message = "The application encountered an exception: " + e.getMessage() + "occured";
		}
	}

	/* getters and setters for the dataMembers */
	public DatabaseAccessInfoBean getDbBean() {
		return dbBean;
	}

	public void setDbBean(DatabaseAccessInfoBean dbBean) {
		this.dbBean = dbBean;
	}

	public Connection getConnection() {
		return connection;
	}

	public DatabaseMetaData getDatabaseMetaData() {
		return databaseMetaData;
	}

	public Statement getStatement() {
		return statement;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public ResultSet getRs() {
		return rs;
	}

	public ResultSetMetaData getResultSetMetaData() {
		return resultSetMetaData;
	}

	public Result getResult() {
		return result;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public String getUrl() {
		return url;
	}

	public String getMessage() {
		return message;
	}

	public Boolean getRenderMessage() {
		return renderMessage;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void setDatabaseMetaData(DatabaseMetaData databaseMetaData) {
		this.databaseMetaData = databaseMetaData;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public void setResultSetMetaData(ResultSetMetaData resultSetMetaData) {
		this.resultSetMetaData = resultSetMetaData;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setRenderMessage(Boolean renderMessage) {
		this.renderMessage = renderMessage;
	}
	
	/* Methods related to Database operations */
	
	/* Method used to fetch columns in the selected table*/
	public ResultSet fetchColumnNames(String sqlQuery)
	{
		try
		{
			ResultSet resultSet;
			if(sqlQuery.contains("world"))
			resultSet= worldStatement.executeQuery(sqlQuery);
			else 			resultSet= statement.executeQuery(sqlQuery);

			return resultSet;
		} catch (SQLException se) {
			message = "Sorry, application encountered an SQL Exception at this moment while fetching the column names. Please find the error details below" + "\n" + "SQL State: " + se.getSQLState() + "\n" + "SQL Error Code: " + se.getErrorCode() + "\n" +
					"Message :" + se.getMessage() + "\n";
			return resultSet = null;
		} 
		catch (Exception e) {
			e.printStackTrace();
			message = "Ooops, the application encountered an exception: " + e.getMessage();
			return resultSet = null;
		}
	}
	
    /* Method used to fetch columns' data in the selected table*/
	public ResultSet fetchColumnData(String query)
	{
		try {
			ResultSet resultSet ;
			if(query.contains("world"))
				 resultSet = worldStatement.executeQuery(query);
			else
			 resultSet = statement.executeQuery(query);
			return resultSet;
		} catch (SQLException se) {
			message = "Sorry, application encountered an SQL Exception at this moment while fetching the column data requested. Please find the error details below" + "\n" + "SQL Error Code: " + se.getErrorCode() + "\n"+ "SQL State: " + se.getSQLState() + "\n" +
					"Message :" + se.getMessage() + "\n";
			return resultSet = null;
		} catch (Exception e) {e.printStackTrace();
			message = "Ooops, the application encountered an exception:  " + e.getMessage();
			return resultSet = null;
		}
	}
	
	/* Method used to process select query and returns ResultSet as output*/
	public ResultSet selectQueryProcessing(String query)
	{
		try {
			resultSet = statement.executeQuery(query);
			return resultSet;
		} catch (SQLException se) {
			try {
				
				resultSet = worldStatement.executeQuery(query);
			} catch (SQLException e) {
				message = "Sorry, application encountered an SQL Exception at this moment, find the error details below and forward it to the support team(support.s18t23@uic.edu) for further assistance" +  "\n" + "SQL Error Code: " + se.getErrorCode() + "\n"+  "SQL State: " + se.getSQLState() + "\n" +
						"Message :" + se.getMessage() + "\n";
				se.printStackTrace();
				return resultSet;
			}
			return resultSet;

			
		} 
		catch (Exception e) {
			e.printStackTrace();
			message = "Ooops, the application encountered an exception: " + e.getMessage();
			e.printStackTrace();
			return resultSet;
		}
	}
	
	/* Method used to process queries other than select and returns the number of rows affected as output for a successful event */
	public int crudQueryProcessing(String sqlQuery)
	{
		try {       
			if(!sqlQuery.contains("world"))
			{
				int count = statement.executeUpdate(sqlQuery);
			System.out.println("attempting: " + sqlQuery);
			return count;
			}
			else {
				message = "Sorry, The Schema that you are trying to manipulate has only read Access ";
				return -1;
			}
		} 
		catch (SQLException se) {
			message = se.getMessage();
			return -1;
		} 
		catch (Exception e) {
			e.printStackTrace();
			message = "Ooops, the application encountered an exception: " + e.getMessage();
			return -1;
		   }
	}
	
	public int crudComputeQueryProcessing(String sqlQuery)
	{
		try {   
			Statement stmt =  connection.createStatement();
			int count = stmt.executeUpdate(sqlQuery);
			System.out.println(count);
			return count;
		} 
		catch (SQLException se) {
			message = "SQL State: " + se.getSQLState() + "\n" +
					"Message :" + se.getMessage() + "\n";
			se.printStackTrace();
			return -1;
		} 
		catch (Exception e) {
			e.printStackTrace();
			message = "Encountered an exception, " + e.getMessage();
			return -1;
		   }
	}
	/* Method used to fetch the table names */
	public ResultSet[] fetchTables()
	{
		ResultSet[] rs = new ResultSet[2];
		try {
			DatabaseMetaData meta = (DatabaseMetaData) connection.getMetaData();
			ResultSet rSet = meta.getTables(null, null, "%", null);
			rs[0]=rSet;
			DatabaseMetaData meta1 = (DatabaseMetaData) worldConnection.getMetaData();
			ResultSet rSet1 = meta1.getTables(null, null, "%", null);
			rs[1]= rSet1;

			return rs;
		} 
		catch (SQLException se) 
		{
			se.printStackTrace();
			message = "SQL Exception has occured while fetching the tables, find the error details below and forward it to the support team(support.s18t23@uic.edu) for further assistance" + "\n" + "SQL State: " + se.getSQLState() + "\n" +  "SQL Error Code: " + se.getErrorCode() + "\n" +
					"Message :" + se.getMessage() + "\n";
			
			return rs;
		} 
		catch (Exception e) {
			e.printStackTrace();
			message = "Ooops, the application encountered an exception: " + e.getMessage();
			return rs;
		}
	}
	
	/* Method used to fetch the table data */
	public ResultSet fetchTableData(String sqlQuery)
	{
		try {
			resultSet = statement.executeQuery(sqlQuery);
			return resultSet;
		} catch (SQLException se) {
			message = "SQL Exception has occured while fetching data from the tables, find the error details below and forward it to the support team(support.s18t23@uic.edu) for further assistance"+ "\n" + "Error Code: " + se.getErrorCode() + "\n" +
					"SQL State: " + se.getSQLState() + "\n" +
					"Message :" + se.getMessage();
			return resultSet = null;
		} catch (Exception e) {e.printStackTrace();
			message = "Ooops, the application encountered an exception: " + e.getMessage();
			return resultSet = null;
		}
	}
	
	/* Method used to create a specified table in the Database */
	
}