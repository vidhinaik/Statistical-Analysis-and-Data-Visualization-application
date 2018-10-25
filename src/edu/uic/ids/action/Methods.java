package edu.uic.ids.action;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import edu.uic.ids.model.AccessLogBean;
import edu.uic.ids.model.DatabaseAccessInfoBean;
import edu.uic.ids.model.MathManagedBean;
import edu.uic.ids.model.StatsBean;
import edu.uic.ids.model.StatsManagedBean;

@ManagedBean(name="methods")
@SessionScoped
public class Methods {
private DbAccess dbAccess;
private DatabaseAccessInfoBean dbBean; 
private DatabaseOperations databaseOperations;
private StatsBean statsBean;
private StatsManagedBean statsManagedBean;
private Reports reports;
private Upload upload;
private MathManagedBean mathManagedBean;
private Export export;
private String message;
private List<String> schemaList;
private AccessLogBean accessLogBean;

public Methods()
{
	schemaList=new ArrayList<String>();
}
public String login()
{
	String status = dbAccess.dbConnect();
	databaseOperations = new DatabaseOperations();
	upload = new Upload();
	statsBean = new StatsBean();
	statsManagedBean = new StatsManagedBean();
	reports = new Reports();
	mathManagedBean = new MathManagedBean();
	export = new Export();
	
	databaseOperations.setDbAccess(dbAccess);
	databaseOperations.setDbBean(dbBean);
	upload.setDbBean(dbBean);
	upload.setDbAccess(dbAccess);
	
	
	statsBean.setDbBean(dbBean);
	statsBean.setDbAccess(dbAccess);
	
	statsManagedBean.setDbBean(dbBean);
	statsManagedBean.setDbAccess(dbAccess);
	
	reports.setDbBean(dbBean);
	reports.setDbAccess(dbAccess);
	
	mathManagedBean.setDbBean(dbBean);
	mathManagedBean.setDbAccess(dbAccess);
	
	export.setDbBean(dbBean);
	export.setDbAccess(dbAccess);
	
	if(status.equals("SUCCESS"))
	{
		accessLogBean.logEntry("Logged in");
		dbAccess.setMessage("");
		dbAccess.setRenderMessage(false);
		return "SUCCESS";
	}
	else
		return "FAIL";
}

public String logout() 
{
	accessLogBean.logEntry("Logged out");
	try {
		System.out.println();
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.invalidateSession();
		return "LOGOUT";
	} catch (Exception e) {
		message = e.getMessage();
		databaseOperations.setMessage(message);
		databaseOperations.setRenderMessage(true);
		databaseOperations.setMessage(message);
		return "FAIL";
	}
}

public DbAccess getDbAccess() {
	return dbAccess;
}

public void setDbAccess(DbAccess dbAccess) {
	this.dbAccess = dbAccess;
}


public DatabaseOperations getDatabaseOperations() {
	return databaseOperations;
}


public String getMessage() {
	return message;
}


public void setDatabaseOperations(DatabaseOperations databaseOperations) {
	this.databaseOperations = databaseOperations;
}


public void setMessage(String message) {
	this.message = message;
}

public List<String> getSchemaList() {
	return schemaList;
}


public void setSchemaList(List<String> schemaList) {
	this.schemaList = schemaList;
}
public DatabaseAccessInfoBean getDbBean() {
	return dbBean;
}
public void setDbBean(DatabaseAccessInfoBean dbBean) {
	this.dbBean = dbBean;
}
public StatsBean getStatsBean() {
	return statsBean;
}
public void setStatsBean(StatsBean statsBean) {
	this.statsBean = statsBean;
}

public StatsManagedBean getStatsManagedBean() {
	return statsManagedBean;
}
public void setStatsManagedBean(StatsManagedBean statsManagedBean) {
	this.statsManagedBean = statsManagedBean;
}
public Reports getReports() {
	return reports;
}
public void setReports(Reports reports) {
	this.reports = reports;
}
public Upload getUpload() {
	return upload;
}
public void setUpload(Upload upload) {
	this.upload = upload;
}
public MathManagedBean getMathManagedBean() {
	return mathManagedBean;
}
public void setMathManagedBean(MathManagedBean mathManagedBean) {
	this.mathManagedBean = mathManagedBean;
}
public AccessLogBean getAccessLogBean() {
	return accessLogBean;
}

public void setAccessLogBean(AccessLogBean accessLogBean) {
	this.accessLogBean = accessLogBean;
}


}
