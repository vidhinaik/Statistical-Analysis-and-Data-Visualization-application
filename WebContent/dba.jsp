<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html" version="2.0">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<jsp:text>
		<![CDATA[ <?xml version="1.0" encoding="UTF-8" ?> ]]>
	</jsp:text>
	<jsp:text>
		<![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
	</jsp:text>
	<html xmlns="http://www.w3.org/1999/xhtml"
		xmlns:f="http://java.sun.com/jsf/core"
		xmlns:h="http://java.sun.com/jsf/html"
		xmlns:t="http://myfaces.apache.org/tomahawk">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>IDS517 - Spring 2018 - s18t23</title>
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
			<div align="center" style="Font-family:calibri">
			<h3>
			<i style="color: #3f51b5; font-size: 90%; Font-family:calibri">*Required
					field</i> <br />
			</h3>
			<f:view>
				<h:form>
					<h:panelGrid columns="3">
						<h:outputText value="Username*:" style="color: #3f51b5; Font-family:calibri"/>
						<h:inputText id="username" value="#{dbBean.userName}"
							style="width:100%; border-color:#757de8" required="true"
							requiredMessage="&#160;&#160;UserName is required" />
						<h:message for="username"
							style="color:red; font-size:90% ;text-decoration:blink;" />
						<h:outputText value="Password*:" style="color: #3f51b5; Font-family:calibri"/>
						<h:inputSecret id="password" value="#{dbBean.password}"
							style="width:100%;border-color:#757de8" required="true"
							requiredMessage="&#160;&#160;Password is required" />
						<h:message for="password"
							style="color:red; font-size:90% ;text-decoration:blink;" />
						<h:outputText value="Host*:" style="color: #3f51b5; Font-family:calibri"/>
						
						
						<h:selectOneMenu id="host" value="#{dbBean.host}"
    							style="width:100%;border-color:#757de8" required="true"
    							requiredMessage="&#160;&#160;Please select a host">
	        					<f:selectItem itemValue="localhost" itemLabel="localhost" />
    	    					<f:selectItem itemValue="131.193.209.68" itemLabel="131.193.209.68" />
   	    						<f:selectItem itemValue="131.193.209.68" itemLabel="131.193.209.69" />
    						</h:selectOneMenu>
    						
    					<h:message for="password"
							style="color:red; font-size:90% ;text-decoration:blink;" />
						<h:outputText value="Schema*:" style="color: #3f51b5; Font-family:calibri"/>	
    						<h:selectOneMenu id="schema" value="#{dbBean.schema}"
    							style="width:100%;border-color:#757de8" required="true"
    							requiredMessage="&#160;&#160;Please select a schema">
    	    					<f:selectItem itemValue="f17x321" itemLabel="f17x321" />
   	    						<f:selectItem itemValue="world" itemLabel="world" />
    						</h:selectOneMenu>

						<h:message for="host"
							style="color:red; font-size:90%; text-decoration:blink;" />
						<h:outputText value="Database*:" style="color: #3f51b5; Font-family:calibri"/>
						<h:selectOneRadio id="database" value="#{dbBean.dbms}"
							style="width:100%;border-color:#757de8" required="true"
							requiredMessage="&#160;&#160;Please click on a database">
							<f:selectItem itemValue="mysql" itemLabel="My SQL" />
							<f:selectItem itemValue="db2" itemLabel="DB 2" />
							<f:selectItem itemValue="oracle" itemLabel="Oracle" />
						</h:selectOneRadio>
						
						
						

						<h:message for="database"
							style="color:red; font-size:90%; text-decoration:blink;" />
						
						<h:outputText value="" />
						<h:commandButton value="Login" action="#{methods.login}"
							styleClass="button" />
						<h:outputText value="" />
					</h:panelGrid>
					<br />
					<pre>
						<h:outputText value="#{dbAccess.message}"
							rendered="#{dbAccess.renderMessage}"
							style="color:red; font-weight:bold; text-decoration:blink;" />
						</pre>
				</h:form>
			</f:view>
		</div>
		<div align="center">
		<a href="index.html" >Go back to the home-page</a> <br /> <br /></div>
		<div class="footer">
			
		</div>
	

</body>
	</html>
</jsp:root>