package edu.uic.ids.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import edu.uic.ids.model.DatabaseAccessInfoBean;

@ManagedBean(name = "upload")
@SessionScoped
public class Upload {
	private DatabaseAccessInfoBean dbBean;
	private UploadedFile uploadedFile;
	private String fileLabel;
	private String dataSetName;
	private String fileName;
	private long fileSize;
	private String fileContentType;
	private int noOfRows;
	private int noOfColumns;
	private String contentsUploadedFile;
	private boolean fileImport;
	private boolean fileImportError;
	private String filePath;
	private String tmpFileName;
	private String header;
	private String fileType;
	private String fileFormat;
	private String fName;
	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	/*private boolean renderDrop;
	private String dropFile;*/
	private FacesContext facesContext;
	private DbAccess dbAccess;

	@PostConstruct
	public void init() {
		Map<String, Object> m = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		dbAccess = (DbAccess) m.get("dbAccess");
	}

	public String processFile() {
		String status = "FAIL";
		contentsUploadedFile = null;
		facesContext = FacesContext.getCurrentInstance();
		filePath = facesContext.getExternalContext().getRealPath("/temp");
		File tempFile = null;
		FileOutputStream fos = null;
		int n = 0;
		fileImport = false;
		fileImportError = true;
		 String[] variableArray;
		 String[] wordsArray;
		try {

			fileName = uploadedFile.getName();
			String baseName = FilenameUtils.getBaseName(fName);
			fileSize = uploadedFile.getSize();
			fileContentType = uploadedFile.getContentType();
			contentsUploadedFile = new String(uploadedFile.getBytes());
			tmpFileName = filePath + "/" + baseName;
			tempFile = new File(tmpFileName);
			fos = new FileOutputStream(tempFile);
			fos.write(uploadedFile.getBytes());
			fos.close();
			Scanner scan = new Scanner(tempFile);
			String eachRow = "";
			List<Integer> errList = new ArrayList<Integer>();
			int counter = 0;

			while (scan.hasNext()) {
				int insertCount = 0;

				eachRow = scan.nextLine();
				
				if(eachRow.contains("\t"))
					variableArray = eachRow.split("\t");
					else
						variableArray = eachRow.split(",");
				
				 int iLen = variableArray.length;
				System.out.println(eachRow);
				String query ="";
				if(counter==0)
				
				{
	           
	             query = "create table "+baseName+" ( ";
	            for(int i = 0; i< iLen; i++) {
	            	query = query + variableArray[i] +" "+ "DECIMAL(18,2), ";
	            }
	            query=query.substring(0, query.length()-2);
	            query = query + " );";
	            System.out.println(query);
				
				}
				
				else
				{
					if(eachRow.contains("\t"))
					     wordsArray = eachRow.split("\t");
						else
							wordsArray = eachRow.split(",");
					
	                    query = "insert into "+baseName+" values(";
	                    for(int i = 0; i< iLen; i++)
	                    {
	                    	query = query + Double.parseDouble(wordsArray[i]);
	                    	if(i<iLen - 1)
	                    	{
	                    		query = query + ",";
	                    	}
	                    }
	                    query = query + ");";
	                    wordsArray = null;
	                    //executeUpdate(insertQueryString);
	                    System.out.println(query);
				}
	            
	            
				if(query.toLowerCase().contains("insert")){
						n++;			
						insertCount = dbAccess.crudQueryProcessing(query);

				}
				else if(eachRow.length()>2)
					 dbAccess.crudQueryProcessing(query);


				if (insertCount == -1) 
					errList.add(counter);	
				
				counter++;
			}
			try {
				//	int insertCount = dbAccess.crudQueryProcessing(insertDataQuery);
					if (!errList.isEmpty()) {
						System.out.println("EERLIST SIZE: "+errList.size());
						String ms ="";
						for(int k : errList)
							{
							ms+=k+",";
							}
//						dropFile = dataSetName;
						FacesMessage message1 = new FacesMessage("Following are the rows where insert failed :\n" +ms);
//						renderDrop = true;
						FacesContext.getCurrentInstance().addMessage(null, message1);

					}
					else
					{
					//Successfully uploaded file message
					FacesMessage message1 = new FacesMessage("Data has been succesfully uploaded.");
					FacesContext.getCurrentInstance().addMessage(null, message1);
					}
				} catch (Exception e) {
					FacesMessage message1 = new FacesMessage("Feeding data to database encounteed a problem");
					FacesContext.getCurrentInstance().addMessage(null, message1);
					return "FAIL";
				}
			noOfRows = n;
			fileImport = true;
			scan.close();
		} catch (IOException inpopEx) {
			FacesMessage message1 = new FacesMessage(inpopEx.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message1);
			return "FAIL";
		} catch (Exception e) {
//			FacesMessage message1 = new FacesMessage(e.getMessage());
//			FacesContext.getCurrentInstance().addMessage(null, message1);
			System.out.println("ERROR OCCURED");
			FacesMessage message1 = new FacesMessage("ERROR OCCURED");
			FacesContext.getCurrentInstance().addMessage(null, message1);

			return "FAIL";
		}
		return status;
	}

	public int startRowValue() {
		if (header.equalsIgnoreCase("no")) {
			return 0;
		} else if (header.equalsIgnoreCase("yes")) {
			return 1;
		} else
			return -1;
	}


	//Adding table data
	public String dataFeeding(ArrayList<String[]> tableData, String fileName) {
//		renderDrop=false;
		int count = 0;
		int startValue = startRowValue();
		StringBuilder values = new StringBuilder();
		List<Integer> errList = new ArrayList<Integer>();
		if (startValue != -1) {
			for (int i = startValue; i < tableData.size(); i++) {
				values=new StringBuilder();
				values.append("(");
				String[] columnElements = tableData.get(i);
				int columnsWithNonEmptyData = 0;
				for (String columnValue : columnElements) {
					if (columnValue != null) {
						columnsWithNonEmptyData++;
					}
				}

				String[] columnElementsResized = new String[columnsWithNonEmptyData];
				int columnNumber = 0;
				for (String columnValue : columnElements) {
					if (columnValue != null) {
						columnElementsResized[columnNumber++] = columnValue;
					}
				}
				for (int j = 0; j < columnElementsResized.length; j++) {
					values.append(columnElementsResized[j]);
					if (j < columnElementsResized.length - 1) {
						values.append(",");
					}
				}
				try {
					String selectQuery = " select * from f17x321.f17g214_" + dataSetName;
//					System.out.println("Select Query: " + selectQuery);
					ResultSet rsDes = dbAccess.selectQueryProcessing(selectQuery);
					ResultSetMetaData metaData = rsDes.getMetaData();
					count = metaData.getColumnCount(); // number of column
//					System.out.println("Count :" + count);
				} catch (SQLException e1) {
					/*FacesMessage message1 = new FacesMessage("ERROR OCCURED IN DESC");
					FacesContext.getCurrentInstance().addMessage(null, message1);*/
					e1.printStackTrace();
				} catch (Exception e) {
					/*FacesMessage message1 = new FacesMessage("ERROR OCCURED IN DESC2");
					FacesContext.getCurrentInstance().addMessage(null, message1);*/
					e.printStackTrace();
				}
				int appendNull = count - columnsWithNonEmptyData;
//				System.out.println("Append Null" + appendNull);
				for (int passNull = 1; passNull <= appendNull; passNull++) {
					values.append(",null");
				}
				values.append(")");
				String insertDataQuery = "INSERT INTO " + "s18t23_" + dataSetName + " VALUES " + values.toString();
				int insertCount = dbAccess.crudQueryProcessing(insertDataQuery);

				if (insertCount == -1) 
					errList.add(i);		
	
				
			}
		} else {
			FacesMessage message1 = new FacesMessage("Invalid header selection");
			FacesContext.getCurrentInstance().addMessage(null, message1);
			return "FAIL";
		}

		String insertDataQuery = "INSERT INTO " + "f17g214_" + dataSetName + " VALUES " + values.toString();
		System.out.println("Insert Data Query: " + insertDataQuery);
		try {
		//	int insertCount = dbAccess.crudQueryProcessing(insertDataQuery);
			if (!errList.isEmpty()) {
				System.out.println("EERLIST SIZE: "+errList.size());
				String ms ="";
				for(int k : errList)
					{
					ms+=k+",";
					}
//				dropFile = dataSetName;
				FacesMessage message1 = new FacesMessage("Following are the rows where insert failed :\n" +ms);
//				renderDrop = true;
				FacesContext.getCurrentInstance().addMessage(null, message1);

			}
			else
			{
			//Successfully uploaded file message
			FacesMessage message1 = new FacesMessage("Data has been succesfully uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message1);
			}
		} catch (Exception e) {
			FacesMessage message1 = new FacesMessage("Feeding data to database encounteed a problem");
			FacesContext.getCurrentInstance().addMessage(null, message1);
			return "FAIL";
		}	
		return "SUCCESS";
	}
	
	/*public String dropInserted()
	{
		System.out.println("DROPFILE :"+dropFile);
		String dropInserted ="drop table " + dbBean.getDatabaseSchema() + ".f17g214_" + dropFile+";"; 
		int drop = dbAccess.crudQueryProcessing(dropInserted);

		if (drop == -1) 
			{
			FacesMessage message1 = new FacesMessage("Delete Failed");
			FacesContext.getCurrentInstance().addMessage(null, message1);
			renderDrop=true;
			return "FAIL";
			}
		else
			{
			FacesMessage message1 = new FacesMessage("Table successfully deleted");
			FacesContext.getCurrentInstance().addMessage(null, message1);
			renderDrop=false;
			return "SUCCESS";
			}
	}*/
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getFileLabel() {
		return fileLabel;
	}

	public void setFileLabel(String fileLabel) {
		this.fileLabel = fileLabel;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public int getTotalNumberOfRows() {
		return noOfRows;
	}

	public void setTotalNumberOfRows(int totalNumberOfRows) {
		this.noOfRows = totalNumberOfRows;
	}

	public int getNumberColumns() {
		return noOfColumns;
	}

	public void setNumberColumns(int numberColumns) {
		this.noOfColumns = numberColumns;
	}

	public String getUploadedFileContents() {
		return contentsUploadedFile;
	}

	public void setUploadedFileContents(String uploadedFileContents) {
		this.contentsUploadedFile = uploadedFileContents;
	}

	public boolean isFileImport() {
		return fileImport;
	}

	public void setFileImport(boolean fileImport) {
		this.fileImport = fileImport;
	}

	public boolean isFileImportError() {
		return fileImportError;
	}

	public void setFileImportError(boolean fileImportError) {
		this.fileImportError = fileImportError;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTempFileName() {
		return tmpFileName;
	}

	public void setTempFileName(String tempFileName) {
		this.tmpFileName = tempFileName;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public String getDataSetName() {
		return dataSetName;
	}

	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}

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

/*	public boolean isRenderDrop() {
		return renderDrop;
	}

	public void setRenderDrop(boolean renderDrop) {
		this.renderDrop = renderDrop;
	}*/

}