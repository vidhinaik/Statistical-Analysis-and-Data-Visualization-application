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
<title>IDS517-Spring2018-s</title>
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
				This functionality facilitates access of the database and query processing
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
							<h:commandButton value="View table"
								action="#{databaseOperations.fetchTableData}" disabled="#{databaseOperations.disableDisplayTable})" styleClass="button" />
							<h:commandButton value="Show Colummn's list"
								action="#{databaseOperations.fetchColumnNames}" disabled="#{databaseOperations.disableColumnList}" styleClass="button" />
							<h:commandButton value="View selected columns"
								action="#{databaseOperations.fetchColumnData}" disabled="#{databaseOperations.disableColumnData}" styleClass="button" />
							<h:commandButton value="Process SQL query"
								action="#{databaseOperations.queryProcessing}" styleClass="button" />
							<h:commandButton value="Drop tables"
								action="#{databaseOperations.dropTable}" styleClass="button" />							
							<h:commandButton value="Create AccessLog Table"
								action="#{databaseOperations.createAccessLogTable}" styleClass="button" />
						</h:panelGrid>
					</div>
					<div class="right-div">
						<pre>
						<h:outputText value="#{databaseOperations.message}"
								rendered="#{databaseOperations.renderMessage}" style="color:red" />
					</pre>
						<panelGrid columns="4"> <h:selectOneListbox
							id="selectOneCb" style="width:150px; height:100px"
							value="#{databaseOperations.tableSelected}"
							rendered="#{databaseOperations.renderTablename}" size="5">
							<f:selectItems value="#{databaseOperations.tableList}" />
						</h:selectOneListbox> <h:outputText value=" " /> <h:outputText value=" " /> <h:outputText
							value=" " /> <h:selectManyListbox id="selectcolumns"
							style="width:150px; height:100px"
							value="#{databaseOperations.columnSelected}"
							rendered="#{databaseOperations.columnRender}" size="5">
							<f:selectItems value="#{databaseOperations.columnsList}" />
						</h:selectManyListbox> <h:outputText value=" " /> <h:outputText value=" " /> <h:outputText
							value=" " /> <h:inputTextarea rows="6" cols="40"
							style="height:100px" value="#{databaseOperations.userQuery}" /> </panelGrid>
						<br /> <br />
						<h:outputText value="Rows: " />
						<h:outputText value="#{databaseOperations.rowsAffected}" />
						&#160;&#160;
						<h:outputText value="Columns: " />
						<h:outputText value="#{databaseOperations.columnCount}" />
						<hr />
					</div>
					<div class="bottom">
						<div
							style="background-attachment: scroll; overflow: auto; height: auto; background-repeat: repeat"
							align="center">
							<t:dataTable value="#{databaseOperations.result}" var="row"
								rendered="#{databaseOperations.renderTabledata}" border="1"
								cellspacing="0" cellpadding="1"
								columnClasses="columnClass border" headerClass="headerClass"
								footerClass="footerClass" rowClasses="rowClass2"
								styleClass="dataTableEx" width="900px">
								<t:columns var="col" value="#{databaseOperations.columnSelected}">
									<f:facet name="header">
										<t:outputText styleClass="outputHeader" value="#{col}" />
									</f:facet>
									<t:outputText styleClass="outputText" value="#{row[col]}" />
								</t:columns>
							</t:dataTable>
						</div>
					</div>
					<hr />
				</h:form>
				<div class="footer">
					
				</div>
			</div>
		</center>
	</f:view>
</body>
	</html>
</jsp:root>