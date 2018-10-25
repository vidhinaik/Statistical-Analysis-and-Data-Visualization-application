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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>IDS517-Spring 2018-s18t23</title>
<link rel="stylesheet" href="CSS/style.css" />
</head>
<body>
<div  align="center" style="background-color:#F0F0F0; Font-family:calibri"  >
		<br/>
		<h2 align="center">Statistical Analysis and Data Visualization application</h2>
		<h4>IDS517 Spring-2018</h4>
		<h4>Submitted by Group s18t23</h4>
		<br/></div>

	<f:view>
		<center>
			<div class="operationsMain">
				<a href="dbOptions.jsp">Main Menu</a> &#160;&#160; <a
					href="confirm.jsp">Logout</a><br />
				<hr />
				<h:form>
							<h:commandButton value="Show Access Log"
								action="#{databaseOperations.accessLog}" styleClass="button" />
					<br />
					<br />
					<pre>
						<h:outputText value="#{databaseOperations.message}"
								rendered="#{databaseOperations.renderMessage}" style="color:red" />
					</pre>
					<br />
					<br />
						<div
							style="background-attachment: scroll; overflow: auto; height: auto; background-repeat: repeat"
							align="center">
							<t:dataTable value="#{databaseOperations.result}" var="row"
								rendered="#{databaseOperations.renderTabledata}" border="1"
								cellspacing="0" cellpadding="1"
								columnClasses="columnClass border" headerClass="headerClass"
								footerClass="footerClass" rowClasses="rowClass2"
								styleClass="dataTableEx" width="900px">
								<t:columns var="col"
									value="#{databaseOperations.columnSelected}">
									<f:facet name="header">
										<t:outputText styleClass="outputHeader" value="#{col}" />
									</f:facet>
									<t:outputText styleClass="outputText" value="#{row[col]}" />
								</t:columns>
							</t:dataTable>
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