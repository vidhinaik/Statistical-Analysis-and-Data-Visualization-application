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
		<![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
	</jsp:text>
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>IDS517-Spring2018-s18t23</title>
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
				<h:form>
					 Hit the below button to log out <br />
					<h:commandButton value="Logout" action="#{methods.logout}"
						styleClass="button" />
					<br />
					<br />
					<a href="dbOptions.jsp">To go back to main menu click
						here</a>
					<br />
				</h:form>
				<div class="footer">
					
				</div>
			</div>
		</center>
	</f:view>
</body>
	</html>
</jsp:root>