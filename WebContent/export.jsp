<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:t="http://myfaces.apache.org.tomahawk" version="2.0">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<jsp:text>
		<![CDATA[ <?xml version="1.0" encoding="UTF-8" ?> ]]>
	</jsp:text>
	<jsp:text>
		<![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
	</jsp:text>
	<html xmlns="http://www.w3.org/1999/xhtml"
		xmlns:f="http://java.sun.com/jsf/core"
		xmlns:h="http://java.sun.com/jsf/html"
		xmlns:t="http://myfaces.apache.org/tomahawk">
		<style>
.button {
    background-color: #3f51b5;
    border: none;
    color: white;
    padding: 5px 10px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    margin: 2px 1px;
    cursor: pointer;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>IDS517-Spring2018-s18t23</title>
<link rel="stylesheet" href="CSS/style.css" />
</head>
<body><div  align="center" style="background-color:#F0F0F0; Font-family:calibri"  >
		<br/>
		<h2 align="center">Statistical Analysis and Data Visualization application</h2>
		<h4>IDS517 Spring-2018</h4>
		<h4>Submitted by Group s18t23</h4>
		<br/></div>
	<f:view>
		<center>
			
			<p>
				This functionality facilitates export of the database tables in CSV, Tab Delimited and XML format.
			</p>
			<hr />
			<div class="operationsMain">
				<a href="dbOptions.jsp">Main Menu</a> &#160;&#160; 
				<a href="confirm.jsp">Logout</a><br />
				<hr />
				<h:form>
					<br />
					<br />
					<div class="left-div">
						<h:panelGrid columns="1">
							<h:commandButton value="Show Table's list"
								action="#{databaseOperations.fetchTables}" styleClass="button" />
							<h:commandButton value="Export CSV file"
								action="#{databaseOperations.exportCSV}" disabled="#{databaseOperations.disableDisplayTable})" styleClass="button" />
							<h:commandButton value="Export Tab Delimited file"
								action="#{databaseOperations.exportTabDelimited}" disabled="#{databaseOperations.disableDisplayTable})" styleClass="button" />
							<h:commandButton value="Export XML file"
								action="#{databaseOperations.exportXML}" disabled="#{databaseOperations.disableDisplayTable})" styleClass="button" />
							
						</h:panelGrid>
					</div>
					<div class="right-div">
						<pre>
						<h:outputText value="#{databaseOperations.message}"
								rendered="#{databaseOperations.renderMessage}" style="color:red" />
					</pre>
				 
						<h:selectOneListbox
							id="selectOneCb" style="width:150px; height:100px"
							value="#{databaseOperations.tableSelected}"
							rendered="#{databaseOperations.renderTablename}" size="5">
							<f:selectItems value="#{databaseOperations.tableList}" />
						</h:selectOneListbox> 
					<br /> <br />
						
					</div>
					
					
				</h:form>
				<div class="footer">
					
				</div>
			</div>
		</center>
	</f:view>
</body>
	</html>
</jsp:root>