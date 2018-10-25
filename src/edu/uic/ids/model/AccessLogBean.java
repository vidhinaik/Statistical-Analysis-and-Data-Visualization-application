//Part of model package
package edu.uic.ids.model;

//Imports
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import edu.uic.ids.action.DbAccess;
@ManagedBean(name="accessLogBean")
public class AccessLogBean 
{
	private DbAccess dbAccess;
	private DatabaseAccessInfoBean dbBean;
	private int id;
	private String userName;
	private String dataSetName;
	private String fileType;
	private String fileName;
	private String action;
	

	//Getters and setters
	public DbAccess getDbAccess() {
		return dbAccess;
	}
	public void setDbAccess(DbAccess dbAccess) {
		this.dbAccess = dbAccess;
	}
	public DatabaseAccessInfoBean getDbBean() {
		return dbBean;
	}
	public void setDbBean(DatabaseAccessInfoBean dbBean) {
		this.dbBean = dbBean;
	}	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDataSetName() {
		return dataSetName;
	}
	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	

    //Log entry 
	public String logEntry( String action)
	{
						
		 HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //Proxy
		 String ipAddress = request.getHeader("X-FORWARDED-FOR");
		
        
		FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		//Get session info
        String sessionID = session.getId(); 
		
		//Get IP address from remote user
		if (ipAddress == null)
		{
        	ipAddress = request.getRemoteAddr();
        	System.out.println("ip x: " + ipAddress);
		}
		

			

		//Do no delete, for testing purposes
		System.out.println("Access log called Entry called");
		String schema = "f17x321";
		//Query to generate log entry
		String sqlQuery = "INSERT INTO f17x321.s18t23_Access_Logs(User, tableSchema, ipAddress, SessionID, Action, Date) VALUES(\"" +
					dbAccess.getDbBean().getUserName() + "\",\"" + schema +  "\",\"" + ipAddress + "\",\"" + sessionID + "\",\"" + action + "\", now());";
		System.out.println("SQL Query: "+sqlQuery);
		int count = dbAccess.crudQueryProcessing(sqlQuery);
		if(count>0)
		{
			System.out.println("SUCCESS");
			return "SUCCESS";
		}
		else
		{
			System.out.println("FAIL");
			return "FAIL";
		}
	}
	
	
	
	     
	
	}
