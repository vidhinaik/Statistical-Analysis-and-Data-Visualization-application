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
			
			<hr />
			<div class="operationsMain">
				<a href="dbOptions.jsp">Main Menu</a> &#160;&#160; <a
					href="confirm.jsp">Logout</a><br />
				<hr />
				<h:form enctype="multipart/form-data">
					<pre>
						<h:outputText value="#{dbAccess.message}"
							rendered="#{dbAccess.renderMessage}"
							style="color:red; font-weight:bold; text-decoration:blink;" />
						</pre>
					<h:messages
						style="position:relative;align:center;color:red;word-wrap:break-word;"></h:messages>
					<br />
					<h:panelGrid columns="2" columnClasses="firstColumn,secondColumn">

						<h:outputText value="Type:" style="color: #3f51b5; Font-family:calibri"/>
						<h:selectOneMenu id="fileType" value="#{dbBean.schema}"
    							style="width:100%;border-color:#757de8">
	        					<f:selectItem itemValue="CSV" itemLabel="CSV" />
    	    					<f:selectItem itemValue="Tab" itemLabel="Tab" />
    						</h:selectOneMenu>

						<h:outputLabel value="*File :" style="color: #3f51b5; Font-family:calibri"/>
						<t:inputFileUpload id="fileUpload" label="File to upload"
							storage="default" value="#{upload.uploadedFile}" size="60"
							required="true" requiredMessage="File cannot be empty" />
						
						
							
						<h:outputLabel value="*Label :" style="color: #3f51b5; Font-family:calibri"/>
						<h:inputText id="Label" value="#{upload.fName}"
							style="width:100%; border-color:#757de8" required="true"
							requiredMessage="&#160;&#160;Label is required" />
					

						<br />
						<h:outputLabel value=" " />
					</h:panelGrid>
					<h:commandButton styleClass="button" id="upload"
						type="submit" value="Upload File" action="#{upload.processFile}"></h:commandButton>
					</h:form>

				<h:form>
					<h:panelGrid columns="2" rendered="#{upload.fileImport}">
						<h:outputLabel value="Number of records:"
							style="font-weight: bold;" />
						<h:outputText value="#{upload.totalNumberOfRows }" />
						<h:outputLabel value="fileLabel:" style="font-weight: bold;" />
						<h:outputText value="#{upload.fileLabel }" />
						<h:outputLabel value="fileName:" style="font-weight: bold;" />
						<h:outputText value="#{upload.fileName }" />
						<h:outputLabel value="fileSize:" style="font-weight: bold;" />
						<h:outputText value="#{upload.fileSize }" />
						<h:outputLabel value="fileContentType:" style="font-weight: bold;" />
						<h:outputText value="#{upload.fileContentType }" />
						<h:outputLabel value="tempFilePath:" style="font-weight: bold;" />
						<h:outputText value="#{upload.filePath }" />
						<h:outputLabel value="tempFileName:" style="font-weight: bold;" />
						<h:outputText value="#{upload.tempFileName }" />
						<h:outputLabel value="facesContext:" style="font-weight: bold;" />
						<h:outputText value="#{upload.facesContext }" />
					</h:panelGrid>
					<br />
					<h:outputText rendered="#{upload.fileImportError }"
						value="#{messageBean.errorMessage }" />
				</h:form>
				<div class="footer">
					
				</div>
			</div>
		</center>
	</f:view>
</body>
	</html>
</jsp:root>