package edu.uic.ids.action;

import java.lang.Object;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.mysql.jdbc.DatabaseMetaData;

import edu.uic.ids.model.DatabaseAccessInfoBean;
import edu.uic.ids.model.StatsBean;

@ManagedBean(name = "databaseOperations")
@SessionScoped
public class DatabaseOperations {

	// declaring all the variables required
	private StatsBean statsBean;
	private boolean columnRender;
	private boolean columnDataRender;
	private ResultSet resultSet;
	private boolean renderTablename;
	private Result result;
	private boolean renderButton;
	private boolean renderTabledata;
	private String userQuery;
	private int rowsAffected;
	private int columnCount;
	private boolean renderSchema;
	private String message;
	private String tableSelected;
	private boolean renderMessage;
	private DbAccess dbAccess;
	private DatabaseMetaData metaData;
	private ResultSetMetaData resultSetMetaData;
	private DatabaseAccessInfoBean dbBean;
	private List<String> schemaList;
	private List<String> tableList;
	private List<String> columnsList;
	private List<String> columnSelected;
	private List<String> columns = new ArrayList<String>();
	public  List<String> worldTables = new ArrayList<String>();
	// Disabling / Enabling various options
	private boolean disableTableList = false;
	private boolean disableColumnList;
	private boolean disableDisplayTable;
	private boolean disableColumnData;

	// ActivityLogBean

	// constructor
	public DatabaseOperations() {
		columnRender = false;
		rowsAffected = 0;
		columnDataRender = false;
		renderTablename = false;
		renderTabledata = false;
		renderMessage = false;
		renderSchema = false;
		tableList = new ArrayList<String>();
		columnsList = new ArrayList<String>();
		columnSelected = new ArrayList<String>();
		schemaList = new ArrayList<String>();

		disableColumnList = false;
		disableDisplayTable = false;
		disableColumnData = false;

	}

	private String sourceColumn;

	public String getSourceColumn() {
		return sourceColumn;
	}

	public void setSourceColumn(String sourceColumn) {
		this.sourceColumn = sourceColumn;
	}

	public String getDestinationColumn() {
		return destinationColumn;
	}

	public void setDestinationColumn(String destinationColumn) {
		this.destinationColumn = destinationColumn;
	}

	private String destinationColumn;

	
	public void activityLog() {
		clear();
		String sqlQuery = "Select * from f17x321.s18t23_Activity_Logs"
				+ " order by Date desc;";
		System.out.println("SQL Query: " + sqlQuery);
		resultSet = dbAccess.selectQueryProcessing(sqlQuery);
		try {
			if (resultSet != null) {
				columnSelected = new ArrayList<String>();
				resultSetMetaData = resultSet.getMetaData();
				result = ResultSupport.toResult(resultSet);
				columnCount = resultSetMetaData.getColumnCount();
				System.out.println("columnCount: " + columnCount);
				rowsAffected = result.getRowCount();
				System.out.println("rowsAffected: " + rowsAffected);
				String columnNameList[] = result.getColumnNames();
				for (int i = 0; i < columnCount; i++) {
					columnSelected.add(columnNameList[i]);
				}
				renderTabledata = true;
			} else {
				message = dbAccess.getMessage();
				renderMessage = true;
			}
		} catch (Exception err) {
			err.printStackTrace();
			message = err.getMessage();
			renderMessage = true;
		}
		renderTabledata = true;
	}

	public void accessLog() {
		clear();
		String sqlQuery = "Select * from f17x321.s18t23_Access_Logs"
				+ " order by Date desc;";
		System.out.println("SQL Query: " + sqlQuery);
		resultSet = dbAccess.selectQueryProcessing(sqlQuery);
		try {
			if (resultSet != null) {
				columnSelected = new ArrayList<String>();
				resultSetMetaData = resultSet.getMetaData();
				result = ResultSupport.toResult(resultSet);
				columnCount = resultSetMetaData.getColumnCount();
				System.out.println("columnCount: " + columnCount);
				rowsAffected = result.getRowCount();
				System.out.println("rowsAffected: " + rowsAffected);
				String columnNameList[] = result.getColumnNames();
				for (int i = 0; i < columnCount; i++) {
					columnSelected.add(columnNameList[i]);
				}
				renderTabledata = true;
			} else {
				message = dbAccess.getMessage();
				renderMessage = true;
			}
		} catch (Exception err) {
			err.printStackTrace();
			message = err.getMessage();
			renderMessage = true;
		}
		renderTabledata = true;
	}

	public void renderTableList() {
		renderMessage = false;
		if (tableList.isEmpty()) {
			message = "The selected schema has no tables to be dispayed";
			renderMessage = true;
			renderTabledata = false;
			renderMessage = true;
			renderTablename = false;
			userQuery = "";
		} else
			renderTablename = true;
		disableDisplayTable = false;
		disableColumnList = false;
	}

	public String fetchTables() {
		try {
			clear();
			tableList.clear();
			String tableNames;
			ResultSet[] rs  = dbAccess.fetchTables();
			if (rs != null) {
				while (rs[0].next()) {
					tableNames = rs[0].getString("TABLE_NAME");
					tableList.add(tableNames);
				}
				while (rs[1].next()) {
					tableNames = rs[1].getString("TABLE_NAME");
					tableList.add(tableNames);
					worldTables.add(tableNames);
				}
				renderTableList();
				return "SUCCESS";
			} else {
				message = dbAccess.getMessage();
				renderMessage = true;
				return "FAIL";
			}
		} catch (Exception err) {
			err.printStackTrace();
			message = "An exception has occured. The details of the error are given below." + "\n" + err.getMessage();
			renderMessage = true;
			return "FAIL";
		}
	}

	public String fetchTableData() {
		try {
			clear();
			if (tableList.isEmpty()) {
				message = "No tables are available to display at the moment";
				renderMessage = true;
				return "FAIL";
			}
			if (tableSelected.isEmpty()) {
				message = "Please select a table and try again";
				renderMessage = true;
				return "FAIL";
			} else {
				String sqlQuery ="";
				if(worldTables.contains(tableSelected))
					sqlQuery = "select * from world." + tableSelected;
				else
					sqlQuery = "select * from f17x321." + tableSelected;
				resultSet = dbAccess.selectQueryProcessing(sqlQuery);
				userQuery = sqlQuery;
				if (resultSet != null) {
					buildMetaData();
					renderTabledata = true;
					/*
					if(resultSet.next())
					{
						System.out.println("not empty");
					}
					else
						System.out.println("empty");
					*/
					
					//export();
					return "SUCCESS";
				} else {
					message = dbAccess.getMessage();
					renderMessage = true;
					return "FAIL";
				}
			}
		} catch (Exception err) {
			message = "An exception has occured. The details of the error are given below." + "\n" + err.getMessage();
			renderMessage = true;
			return "FAIL";
		}
	}
	
	public String exportTabDelimited()
	{
		
		try {
			
			if(tableList.isEmpty())
			{
				message = "No tables found in the schema.";
				renderMessage = true;
				return "FAIL";
			}
			if(tableSelected.isEmpty())
			{
				message = "Please select table to export data.";
				renderMessage=true;
				return "FAIL";
			}
			else
			{
				FacesContext fc = FacesContext.getCurrentInstance();
				ExternalContext ec = fc.getExternalContext();
				FileOutputStream fos = null;
				String path = fc.getExternalContext().getRealPath("/temp");
				File dir = new File(path);
				if(!dir.exists())
					new File(path).mkdirs();
				ec.setResponseCharacterEncoding("UTF-8");
				Date date= new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS") ;
				String fileNameBase = dbAccess.getUserName()+"_"+tableSelected+"_"+dateFormat.format(date)+".txt";
				String fileName = path + "/" + dbAccess.getUserName() + "_" + fileNameBase;
				File f = new File(fileName);
				resultSet = null;
				String sqlQuery ="";
				if(worldTables.contains(tableSelected))
					sqlQuery = "select * from world." + tableSelected;
				else
					sqlQuery = "select * from f17x321." + tableSelected;
				resultSet = dbAccess.selectQueryProcessing(sqlQuery);
				if(resultSet!=null)
				{
					result = ResultSupport.toResult(resultSet);
					Object [][] sData = result.getRowsByIndex();
					String columnNames [] = result.getColumnNames();
					StringBuffer sb = new StringBuffer();
					try {
						fos = new FileOutputStream(fileName);
						for(int i=0; i<columnNames.length; i++) 
						{
							sb.append(columnNames[i].toString() + "\t");
						}
						sb.append("\n");
						fos.write(sb.toString().getBytes());
						for(int i = 0; i < sData.length; i++) {
							sb = new StringBuffer();
							for(int j=0; j<sData[0].length; j++) {
								if(sData[i][j]==null)
								{
									String value2="0";
									value2=value2.replaceAll("[^A-Za-z0-9.]", " . ");
									if(value2.isEmpty())
									{
										value2="0";
									}
									sb.append(value2 + ",");
								}
								else
								{
									String value =sData[i][j].toString();
									if(value.contains(","))
									{
										int index=value.indexOf(",");
										String newValue=value.substring(0, index-1);
										value=newValue+value.substring(index+1,value.length());
									}
									value=value.replaceAll("[^A-Za-z0-9,.]", " ");
									if(value.isEmpty())
									{
										value="0";
									}
									sb.append(value + "\t");
									
								}
							}
							sb.append("\n");
							fos.write(sb.toString().getBytes());
						}
						fos.flush();
						fos.close();
					} catch (FileNotFoundException e) {
						message = e.getMessage();
						renderMessage = true; 
					} catch (IOException io) {
						message = io.getMessage();
						renderMessage = true;
					}
					String mimeType = ec.getMimeType(fileName);
					FileInputStream in = null;
					byte b;
					ec.responseReset();
					ec.setResponseContentType(mimeType);
					ec.setResponseContentLength((int) f.length());
					ec.setResponseHeader("Content-Disposition",
							"attachment; filename=\"" + fileNameBase + "\"");
					try {
						in = new FileInputStream(f);
						OutputStream output = ec.getResponseOutputStream();
						while(true) {
							b = (byte) in.read();
							if(b < 0)
								break;
							output.write(b);
						}
					} catch (IOException e) {
						message=e.getMessage();
						renderMessage=true;
					}
					finally
					{
						try { 
							in.close(); 
						} catch (IOException e) {
							message=e.getMessage();
							renderMessage=true;
						}
					}
					fc.responseComplete();
				} 
				else
				{
					message=dbAccess.getMessage();
					renderMessage=true;
				}
			}
			return "SUCCESS";
		} catch (Exception e) {
			message = e.getMessage();
			renderMessage = true;
			return "FAIL";
		}
	}
	public String exportCSV()
	{
		
		try {
			
			if(tableList.isEmpty())
			{
				message = "No tables found in the schema.";
				renderMessage = true;
				return "FAIL";
			}
			if(tableSelected.isEmpty())
			{
				message = "Please select table to export data.";
				renderMessage=true;
				return "FAIL";
			}
			else
			{
				FacesContext fc = FacesContext.getCurrentInstance();
				ExternalContext ec = fc.getExternalContext();
				FileOutputStream fos = null;
				String path = fc.getExternalContext().getRealPath("/temp");
				File dir = new File(path);
				if(!dir.exists())
					new File(path).mkdirs();
				ec.setResponseCharacterEncoding("UTF-8");
				Date date= new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS") ;
				String fileNameBase = dbAccess.getUserName()+"_"+tableSelected+"_"+dateFormat.format(date)+".csv";
				String fileName = path + "/" + dbAccess.getUserName() + "_" + fileNameBase;
				File f = new File(fileName);
				resultSet = null;
				String sqlQuery ="";
				if(worldTables.contains(tableSelected))
					sqlQuery = "select * from world." + tableSelected;
				else
					sqlQuery = "select * from f17x321." + tableSelected;
				resultSet = dbAccess.selectQueryProcessing(sqlQuery);
				if(resultSet!=null)
				{
					result = ResultSupport.toResult(resultSet);
					Object [][] sData = result.getRowsByIndex();
					String columnNames [] = result.getColumnNames();
					StringBuffer sb = new StringBuffer();
					try {
						fos = new FileOutputStream(fileName);
						for(int i=0; i<columnNames.length; i++) 
						{
							sb.append(columnNames[i].toString() + ",");
						}
						sb.append("\n");
						fos.write(sb.toString().getBytes());
						for(int i = 0; i < sData.length; i++) {
							sb = new StringBuffer();
							for(int j=0; j<sData[0].length; j++) {
								if(sData[i][j]==null)
								{
									String value2="0";
									value2=value2.replaceAll("[^A-Za-z0-9.]", " . ");
									if(value2.isEmpty())
									{
										value2="0";
									}
									sb.append(value2 + ",");
								}
								else
								{
									String value =sData[i][j].toString();
									if(value.contains(","))
									{
										int index=value.indexOf(",");
										String newValue=value.substring(0, index-1);
										value=newValue+value.substring(index+1,value.length());
									}
									value=value.replaceAll("[^A-Za-z0-9,.]", " ");
									if(value.isEmpty())
									{
										value="0";
									}
									sb.append(value + ",");
								}
							}
							sb.append("\n");
							fos.write(sb.toString().getBytes());
						}
						fos.flush();
						fos.close();
					} catch (FileNotFoundException e) {
						message = e.getMessage();
						renderMessage = true; 
					} catch (IOException io) {
						message = io.getMessage();
						renderMessage = true;
					}
					String mimeType = ec.getMimeType(fileName);
					FileInputStream in = null;
					byte b;
					ec.responseReset();
					ec.setResponseContentType(mimeType);
					ec.setResponseContentLength((int) f.length());
					ec.setResponseHeader("Content-Disposition",
							"attachment; filename=\"" + fileNameBase + "\"");
					try {
						in = new FileInputStream(f);
						OutputStream output = ec.getResponseOutputStream();
						while(true) {
							b = (byte) in.read();
							if(b < 0)
								break;
							output.write(b);
						}
					} catch (IOException e) {
						message=e.getMessage();
						renderMessage=true;
					}
					finally
					{
						try { 
							in.close(); 
						} catch (IOException e) {
							message=e.getMessage();
							renderMessage=true;
						}
					}
					fc.responseComplete();
				} 
				else
				{
					message=dbAccess.getMessage();
					renderMessage=true;
				}
			}
			return "SUCCESS";
		} catch (Exception e) {
			message = e.getMessage();
			renderMessage = true;
			return "FAIL";
		}
	}
	public String exportXML()
	{
		
		try {
		
			if(tableList.isEmpty())
			{
				message = "No tables found in the schema.";
				renderMessage = true;
				return "FAIL";
			}
			if(tableSelected.isEmpty())
			{
				message = "Please select table to export data.";
				renderMessage = true;
				return "FAIL";
			}
			else
			{
				try {
					FacesContext fc = FacesContext.getCurrentInstance();
					ExternalContext ec = fc.getExternalContext();
					String path = fc.getExternalContext().getRealPath("/temp");ec.setResponseCharacterEncoding("UTF-8");
					String fileNameBase = dbAccess.getUserName()+"_"+tableSelected+".xml";
					String fileName = path + "/" + dbAccess.getUserName() + "_" + fileNameBase;
					
					
					
					String sqlQuery ="";
					if(worldTables.contains(tableSelected))
						sqlQuery = "select * from world." + tableSelected;
					else
						sqlQuery = "select * from f17x321." + tableSelected;
					resultSet = dbAccess.selectQueryProcessing(sqlQuery);
					
					
					if(resultSet!=null)
					{
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
						DocumentBuilder builder = factory.newDocumentBuilder();
						Document doc = builder.newDocument();
						Element results = doc.createElement(tableSelected);
						doc.appendChild(results);
						ResultSetMetaData rsmd = resultSet.getMetaData();
						int colCount = rsmd.getColumnCount();
						while (resultSet.next())
						{
							Element row = doc.createElement("Row");
							results.appendChild(row);
							int column=0;
							String stringColumn=null;
							int floatColumn=0;
							int smallInt=0;
							double doubleColumn=0;
							for (int i = 1; i <= colCount; i++)
							{
								String columnName = rsmd.getColumnName(i);
								String dataType=rsmd.getColumnTypeName(i);
								Element node = doc.createElement(columnName);
								switch(dataType.toLowerCase())
								{
								case("int") :
									column=resultSet.getInt(i);
								node.appendChild(doc.createTextNode(String.valueOf(column)));
								row.appendChild(node);     
								break;
								case("char"):
									stringColumn=resultSet.getString(i);
								node.appendChild(doc.createTextNode(String.valueOf(stringColumn)));
								row.appendChild(node);    
								break;
								case("smallint"):
									smallInt=resultSet.getInt(i);
								node.appendChild(doc.createTextNode(String.valueOf(smallInt)));
								row.appendChild(node);
								break;
								case("double") :
									doubleColumn=resultSet.getInt(i);
								node.appendChild(doc.createTextNode(String.valueOf(doubleColumn)));
								row.appendChild(node);
								break;
								case("float") :
									floatColumn=resultSet.getInt(i);
								node.appendChild(doc.createTextNode(String.valueOf(floatColumn)));
								row.appendChild(node);
								break;
								}
							}
						}
						TransformerFactory transformerFactory = TransformerFactory.newInstance();
						Transformer transformer = transformerFactory.newTransformer();
						transformer.setOutputProperty(OutputKeys.INDENT, "yes");
						DOMSource source = new DOMSource(doc);
						StreamResult file = new StreamResult(new File(fileName));
						transformer.transform(source, file);
						HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
						response.setHeader("Content-Disposition", "attachment;filename=\"" + fileNameBase + "\"");
						response.setContentLength((int) fileName.length());
						FileInputStream input= null;
						try {
							int i= 0;
							input = new FileInputStream(fileName);  
							byte[] buffer = new byte[1024];
							while ((i = input.read(buffer)) != -1) {  
								response.getOutputStream().write(buffer,0,i);  
								response.getOutputStream().flush();  
							}               
							fc.responseComplete();
							fc.renderResponse();
							return "SUCCESS";
						} catch (IOException e) {
							message=e.getMessage();
							renderMessage=true;
							return "FAIL";
						} finally {
							try {
								if(input != null) {
									input.close();
								}
							} catch(IOException e) {
								message=e.getMessage();
								renderMessage=true;
								return "FAIL";
							}
						}
					}
					else
					{
						message=dbAccess.getMessage();
						renderMessage=true;
						return "FAIL";
					}
				} catch(ParserConfigurationException pe) {
					message=pe.getMessage();
					renderMessage=true;
					return "FAIL";
				} catch(SQLException se) {
					message=se.getMessage();
					renderMessage=true;
					return "FAIL";
				} catch(Exception e) {
					message=e.getMessage();
					renderMessage=true;
					return "FAIL";
				}
			}
		} catch (Exception e) {
			message = e.getMessage();
			renderMessage = true;
			return "FAIL";
		}
	}
	private void buildMetaData() {
		try {
			if (resultSet != null) {
				columnSelected = new ArrayList<String>();
				resultSetMetaData = resultSet.getMetaData();
				result = ResultSupport.toResult(resultSet);
				columnCount = resultSetMetaData.getColumnCount();
				rowsAffected = result.getRowCount();
				String columnNameList[] = result.getColumnNames();
				for (int i = 0; i < columnCount; i++) {
					columnSelected.add(columnNameList[i]);
				}
				renderTabledata = true;
			} else {
				message = dbAccess.getMessage();
				renderMessage = true;
			}
		} catch (Exception err) {
			err.printStackTrace();
			message = err.getMessage();
			renderMessage = true;
		}
	}

	public String fetchColumnNames() {
		try {
			clear();
			if (tableList.isEmpty()) {
				message = "No tables available to display at the moment";
				renderMessage = true;
				return "FAIL";
			}
			if (tableSelected.isEmpty()) {
				message = "Please select a table and try again";
				renderMessage = true;
			} else {
				String sqlQuery = "";
				if(worldTables.contains(tableSelected))
					 sqlQuery = "select * from world." + tableSelected;
				else
				 sqlQuery = "select * from f17x321." + tableSelected;
				ResultSet resultSet = dbAccess.fetchColumnNames(sqlQuery);
				userQuery = "";
				if (resultSet != null) {
					columnsList.clear();
					ResultSetMetaData resultSetmd = (ResultSetMetaData) resultSet.getMetaData();
					int columnCount = resultSetmd.getColumnCount();
					for (int i = 1; i <= columnCount; i++) {
						String name = resultSetmd.getColumnName(i);
						String datatype = resultSetmd.getColumnTypeName(i);
						columns.add(name);
						columnsList.add(name + " " + datatype);
					}
					columnRender = true;
					disableColumnData = false;
				} else {
					message = dbAccess.getMessage();
					renderMessage = true;
				}
			}
			return "SUCCESS";
		} catch (Exception err) {
			err.printStackTrace();
			message = "An exception has occured. The details of the error are given below." + "\n" + err.getMessage();
			renderMessage = true;
			return "FAIL";
		}
	}

	public String fetchColumnData() {
		clear();
		if (tableList.isEmpty()) {
			message = "No tables available to display at the moment";
			renderMessage = true;
			return "FAIL";
		}
		if (tableSelected.isEmpty()) {
			message = "A table and a column in the table should be selected to proceed";
			renderMessage = true;
			return "FAIL";
		}
		if (columnSelected.isEmpty()) {
			message = "Please select a column and try again";
			renderMessage = true;
			return "FAIL";
		}
		String data = columnSelected.get(0);
		int index = data.indexOf(" ");
		if (index < 0) {
			message = "Please select a column and try again";
			renderMessage = true;
			return "FAIL";
		} else {
			splitColumns();
			return "SUCCESS";
		}
	}

	public void splitColumns() {
		try {
			if (tableSelected != null && columnSelected != null) {
				List<String> columnSeperated = new ArrayList<String>();
				for (int i = 0; i < columnSelected.size(); i++) {
					String data = columnSelected.get(i);
					int index = data.indexOf(" ");
					data = data.substring(0, index);
					columnSeperated.add(data);
				}
				columnSelected = new ArrayList<String>();
				columnSelected = columnSeperated;
				columnSeperated = null;
				StringBuilder rString = new StringBuilder();
				for (String each : columnSelected) {
					rString.append(",").append(each);
				}
				String sqlQuery = rString.toString();
				int index = sqlQuery.indexOf(",");
				sqlQuery = sqlQuery.substring(index + 1, sqlQuery.length());
				if(worldTables.contains(tableSelected))
				sqlQuery = "select " + sqlQuery + " from world." + tableSelected;
				else sqlQuery = "select " + sqlQuery + " from f17x321." + tableSelected;

				resultSet = dbAccess.selectQueryProcessing(sqlQuery);
				userQuery = sqlQuery;
				if (resultSet != null) {
					buildMetaData();
				} else {
					message = dbAccess.getMessage();
					renderMessage = true;
				}
			} else {
				message = "A table and a column in the table should be selected to proceed further";
			}
		} catch (Exception err) {
			message = "An exception has occured. The details of the error are given below." + "\n" + err.getMessage();
			renderMessage = true;
		}
	}

	public String queryProcessing() {
		try {
			clear();
			if (userQuery.isEmpty()) {
				message = "Please enter a query to proceed further";
				renderMessage = true;
				return "FAIL";
			} else {
				userQuery = userQuery.toLowerCase();
				int index = userQuery.indexOf(" ");
				if (index < 0) {
					message = "Please enter a valid query to proceed further";
					renderMessage = true;
					return "FAIL";
				}
				String subString = userQuery.substring(0, index);
				switch (subString) {
				case "select":
					clear();
					resultSet = dbAccess.selectQueryProcessing(userQuery);
					if (resultSet != null) {
						buildMetaData();
					} else {
						message = dbAccess.getMessage();
						renderMessage = true;
					}
					break;
				case "update":
					clear();
					rowsAffected = dbAccess.crudQueryProcessing(userQuery);
					if (rowsAffected < 0) {
						message = dbAccess.getMessage();
						renderMessage = true;
					} else {
						message = "Updated" + rowsAffected + " rows successfully";
						renderMessage = true;
					}
					break;
				case "drop":
					clear();
					rowsAffected = dbAccess.crudQueryProcessing(userQuery);
					if (rowsAffected < 0) {
						message = dbAccess.getMessage();
						renderMessage = true;
					} else {
						message = "Dropped the table successfully";
						renderMessage = true;
						fetchTables();
					}
					break;
				case "create":
					clear();
					rowsAffected = dbAccess.crudQueryProcessing(userQuery);
					if (rowsAffected < 0) {
						message = dbAccess.getMessage();
						renderMessage = true;
					} else {
						message = "The table was created successfully";
						renderMessage = true;
					}
					break;
				case "delete":
					clear();
					rowsAffected = dbAccess.crudQueryProcessing(userQuery);
					if (rowsAffected < 0) {
						message = dbAccess.getMessage();
						renderMessage = true;
					} else {
						message = "Deleted" + rowsAffected + " rows successfully";
						renderMessage = true;
					}
					break;
				case "insert":
					clear();
					rowsAffected = dbAccess.crudQueryProcessing(userQuery);
					if (rowsAffected < 0) {
						message = dbAccess.getMessage();
						renderMessage = true;
					} else {
						message = "Inserted" + rowsAffected + " rows successfully";
						renderMessage = true;
					}
					break;
				default:
					message = "Please enter a valid query to proceed further";
					renderMessage = true;
					break;
				}
			}
		} catch (Exception err) {
			message = "An exception has occured. The details of the error are given below." + "\n" + err.getMessage();
			renderMessage = true;
		}
		return "SUCCESS";
	}

	public String dropTable() {
		try {
			clear();
			tableSelected = getTableSelected();
			if (tableList.isEmpty()) {
				message = "No tables are available to drop at the moment";
				renderMessage = true;
				return "FAIL";
			}
			if (tableSelected.isEmpty()) {
				message = "Select a table to be dropped";
				renderMessage = true;
				return "FAIL";
			}
			if (!worldTables.contains(tableSelected)) {
				System.out.println("Table Selected:" + tableSelected);
				String sqlQuery = "drop table f17x321." + tableSelected;
				rowsAffected = dbAccess.crudQueryProcessing(sqlQuery);
				if (rowsAffected < 0) {
					message = dbAccess.getMessage();
					renderMessage = true;
				} else{
					fetchTables();
				message = tableSelected + " has been dropped";
				renderMessage = true;}
				
			} else {
				message = "Tables cannot be dropped at the moment as World Database has only read permissions";
				renderMessage = true;
			}

			// Display message that table has been dropped
			
			// create log entry
			return "SUCCESS";
		} catch (Exception err) {
			err.printStackTrace();
			message = "An exception has occured. The details of the error are given below." + "\n" + err.getMessage();
			renderMessage = true;
			return "FAIL";
		}
	}


	public String createAccessLogTable() {
		try {
			String sqlQuery = "CREATE TABLE f17x321.s18t23_Access_Logs(User Varchar(25), tableSchema Varchar(25), ipAddress Varchar(25), SessionID Varchar(100), Action Varchar(25),Date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP);";
			System.out.println("Create Query: " + sqlQuery);
			rowsAffected = dbAccess.crudQueryProcessing(sqlQuery);
			if (rowsAffected < 0) {
				message = dbAccess.getMessage();
				renderMessage = true;
				return "FAIL";
			} else {
				message = "The Access log table was created successfully";
				renderMessage = true;
				return "SUCCESS";
			}
		} catch (Exception err) {
			err.printStackTrace();
			message = "An exception has occured. The details of the error are given below." + "\n" + err.getMessage();
			renderMessage = true;
			return "FAIL";
		}
	}

	public void clear() {
		message = "";
		// clearing message and table details
		renderMessage = false;
		renderTabledata = false;
		// resetting rows and columns
		rowsAffected = 0;
		columnCount = 0;
	}

	public List<String> getTableList() {
		return tableList;
	}

	public void setTableList(List<String> tableList) {
		this.tableList = tableList;
	}

	public String getTableSelected() {
		return tableSelected;
	}

	public void setTableSelected(String tableSelected) {
		this.tableSelected = tableSelected;
	}

	public List<String> getColumnSelected() {
		return columnSelected;
	}

	public void setColumnSelected(List<String> columnSelected) {
		this.columnSelected = columnSelected;
	}

	public boolean isColumnRender() {
		return columnRender;
	}

	public void setColumnRender(boolean columnRender) {
		this.columnRender = columnRender;
	}

	public boolean isColumnDataRender() {
		return columnDataRender;
	}

	public void setColumnDataRender(boolean columnDataRender) {
		this.columnDataRender = columnDataRender;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public boolean isRenderTablename() {
		return renderTablename;
	}

	public void setRenderTablename(boolean renderTablename) {
		this.renderTablename = renderTablename;
	}

	public ResultSetMetaData getResultSetMetaData() {
		return resultSetMetaData;
	}

	public void setResultSetMetaData(ResultSetMetaData resultSetMetaData) {
		this.resultSetMetaData = resultSetMetaData;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public boolean isRenderTable() {
		return renderTabledata;
	}

	public void setRenderTable(boolean renderTable) {
		this.renderTabledata = renderTable;
	}

	public List<String> getColumnsList() {
		return columnsList;
	}

	public void setColumnsList(List<String> columnsList) {
		this.columnsList = columnsList;
	}

	public String getUserQuery() {
		return userQuery;
	}

	public void setUserQuery(String userQuery) {
		this.userQuery = userQuery;
	}

	public int getRowsAffected() {
		return rowsAffected;
	}

	public void setRowsAffected(int rowsAffected) {
		this.rowsAffected = rowsAffected;
	}

	public DbAccess getDbAccess() {
		return dbAccess;
	}

	public void setDbAccess(DbAccess dbAccess) {
		this.dbAccess = dbAccess;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public List<String> getSchemaList() {
		return schemaList;
	}

	public void setSchemaList(List<String> schemaList) {
		this.schemaList = schemaList;
	}

	public DatabaseMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(DatabaseMetaData metaData) {
		this.metaData = metaData;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public boolean isRenderSchema() {
		return renderSchema;
	}

	public void setRenderSchema(boolean renderSchema) {
		this.renderSchema = renderSchema;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRenderTabledata() {
		return renderTabledata;
	}

	public void setRenderTabledata(boolean renderTabledata) {
		this.renderTabledata = renderTabledata;
	}

	public boolean isRenderMessage() {
		return renderMessage;
	}

	public void setRenderMessage(boolean renderMessage) {
		this.renderMessage = renderMessage;
	}

	public boolean isRenderButton() {
		return renderButton;
	}

	public void setRenderButton(boolean renderButton) {
		this.renderButton = renderButton;
	}

	public boolean isDisableTableList() {
		return disableTableList;
	}

	public void setDisableTableList(boolean disableTableList) {
		this.disableTableList = disableTableList;
	}

	public boolean isDisableColumnList() {
		return disableColumnList;
	}

	public void setDisableColumnList(boolean disableColumnList) {
		this.disableColumnList = disableColumnList;
	}

	public boolean isDisableDisplayTable() {
		return disableDisplayTable;
	}

	public void setDisableDisplayTable(boolean disableDisplayTable) {
		this.disableDisplayTable = disableDisplayTable;
	}

	public boolean isDisableColumnData() {
		return disableColumnData;
	}

	public void setDisableColumnData(boolean disableColumnData) {
		this.disableColumnData = disableColumnData;
	}

	public DatabaseAccessInfoBean getDbBean() {
		return dbBean;
	}

	public void setDbBean(DatabaseAccessInfoBean dbBean) {
		this.dbBean = dbBean;
	}

	

}