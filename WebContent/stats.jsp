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
						<p>
			This functionality facilitates to perform data analysis 
			</p>
		
			<div class="operationsMain">
				<a href="dbOptions.jsp">Main Menu</a> &#160;&#160; <a
					href="confirm.jsp">Logout</a><br />
				<hr />
				<h:form>
					<br />
					<br />
					<div class="left-div">
						<h:panelGrid columns="1">
							<h:commandButton value="Table list" action="#{statsBean.getTables}" styleClass="button" />
							<h:commandButton value="Column list" action="#{statsBean.getColumnNames}" styleClass="button" disabled="#{statsBean.renderColumnListbutton}" />
							<h:commandButton value="Get Statistics Report" action="#{statsBean.generateReport}" styleClass="button" disabled="#{statsBean.renderReport}" />
							<h:commandButton value="Get Regression Analysis" action="#{statsBean.displayColumnsforRegression}" styleClass="button" />
							<h:commandButton value="Export Business Report" action="#{statsBean.export}"  disabled="#{statsBean.disableTabledata}" styleClass="button" />					
							<h:commandButton value="Generate Regression Report" action="#{statsBean.generateRegressionReport}" styleClass="button" disabled="#{statsBean.renderRegressionButton}" />
							<h:commandButton value="Reset" action="#{statsBean.resetButton}" styleClass="button" />
							<h:commandButton value="GenerateGraph" action="#{statsBean.generateChart}"  disabled="#{statsBean.disableRegressionResult}"  styleClass="button" />					
							
					</h:panelGrid>
					</div>

						<div class="right-div">
					
						<p>Tables, columns and variables selection</p>
						<br />
						
						<h:outputText value="#{statsBean.message}"
								rendered="#{statsBean.renderMessage}" style="color:red" />
					
						<panelGrid columns="4">
							<h:selectOneListbox id="selectOneCb"
								style="width:150px; height:100px"
								value="#{statsBean.tableSelected}"
								rendered="#{statsBean.renderTablename}" size="5">
								<f:selectItems value="#{statsBean.tableList}" />
							</h:selectOneListbox>
							<h:selectManyListbox id="selectcolumns"
								style="width:150px; height:100px"
								value="#{statsBean.columnSelected}"
								rendered="#{statsBean.columnRender}" size="5">
								<f:selectItems value="#{statsBean.columnsList}" />
							</h:selectManyListbox>
							<h:selectOneListbox id="predictor"
								value="#{statsBean.predictorValue}"
								rendered="#{statsBean.renderRegressionColumn}" size="5">
								<f:selectItem itemValue="0" itemLabel="Select Predictor Value" />
								<f:selectItems value="#{statsBean.numericData}" />
							</h:selectOneListbox>
							<h:selectOneListbox id="response"
								value="#{statsBean.responseValue}"
								rendered="#{statsBean.renderRegressionColumn}" size="5">
								<f:selectItem itemValue="0" itemLabel="Select Response Value" />
								<f:selectItems value="#{statsBean.numericData}" />
							</h:selectOneListbox>

						</panelGrid>
					</div>
					<div class="bottom">
						<div
							style="background-attachment: scroll; overflow: auto; background-repeat: repeat"
							align="center">
							<t:dataTable value="#{statsBean.statisticList}"
								var="rowNumber" rendered="#{statsBean.renderTabledata}"
								border="1" cellspacing="0" cellpadding="1"
								headerClass="headerWidth">
								<h:column>
									<f:facet name="header">
										<h:outputText value="Column Selected" />
									</f:facet>
									<h:outputText value="#{rowNumber.columnSelected}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Minimum Value" />
									</f:facet>
									<h:outputText value="#{rowNumber.minimumValue}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Maximum Value" />
									</f:facet>
									<h:outputText value="#{rowNumber.maximumValue}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Mean" />
									</f:facet>
									<h:outputText value="#{rowNumber.mean}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Variance" />
									</f:facet>
									<h:outputText value="#{rowNumber.variance}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Standard Deviation" />
									</f:facet>
									<h:outputText value="#{rowNumber.standardDeviation}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="quartileOne" />
									</f:facet>
									<h:outputText value="#{rowNumber.quartileOne}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="quartileThree" />
									</f:facet>
									<h:outputText value="#{rowNumber.quartileThree}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Range" />
									</f:facet>
									<h:outputText value="#{rowNumber.range}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="interquartileRange" />
									</f:facet>
									<h:outputText value="#{rowNumber.interquartileRange}" />
								</h:column>
							</t:dataTable>
						</div>
						<br />
					</div>
					<div class="bottom">
						<h:outputText value="Regression Statement: "
							rendered="#{statsBean.renderRegressionResult}">
						</h:outputText>
						&#160;
						<h:outputText
							value="#{mathManagedBean.regEquation}"
							rendered="#{statsBean.renderRegressionResult}">
						</h:outputText>
						<br /> <br />
						<h:outputText value="Regression Model"
							rendered="#{statsBean.renderRegressionResult}"></h:outputText>
						<h:panelGrid columns="5"
							rendered="#{statsBean.renderRegressionResult}" border="1">
							<h:outputText value="Predictor" />
							<h:outputText value="Co-efficient" />
							<h:outputText value="Standard Error Co-efficient" />
							<h:outputText value="T-Statistic" />
							<h:outputText value="P-Value" />
							<h:outputText value="Constant" />
							<h:outputText value="#{mathManagedBean.intercept}" />
							<h:outputText
								value="#{mathManagedBean.interceptStdErr}" />
							<h:outputText
								value="#{mathManagedBean.tStatistic }" />
							<h:outputText
								value="#{mathManagedBean.interceptPValue }" />
							<h:outputText value="#{statsBean.predictorValue}" />
							<h:outputText value="#{mathManagedBean.slope}" />
							<h:outputText
								value="#{mathManagedBean.slopeStdErr}" />
							<h:outputText
								value="#{mathManagedBean.tStatPred }" />
							<h:outputText
								value="#{mathManagedBean.pValuePred }" />
						</h:panelGrid>
						<br /> <br />
						<h:panelGrid columns="2"
							rendered="#{statsBean.renderRegressionResult}" border="1">
							<h:outputText value="Model of Standard Error" />
							<h:outputText
								value="#{mathManagedBean.stdErrorM}" />
							<h:outputText value="R Square" />
							<h:outputText value="#{mathManagedBean.rSquare}" />
							<h:outputText
								value="R Square Adjusted" />
							<h:outputText
								value="#{mathManagedBean.rSquareAdj}" />
						</h:panelGrid>
						<br /> <br />
						<h:outputText value="Analysis of Variance"
							rendered="#{statsBean.renderRegressionResult}" />
						<br />
						<h:panelGrid columns="6"
							rendered="#{statsBean.renderRegressionResult}" border="1">
							<h:outputText value="Source" />
							<h:outputText value="Degrees of Freedom" />
							<h:outputText value="Sum of Squares" />
							<h:outputText value="Mean of Squares" />
							<h:outputText value="F-Statistic" />
							<h:outputText value="P-Value" />
							<h:outputText value="Regression" />
							<h:outputText
								value="#{mathManagedBean.predictorDegreesFreedom}" />
							<h:outputText
								value="#{mathManagedBean.regSumSquares}" />
							<h:outputText
								value="#{mathManagedBean.meanSquare }" />
							<h:outputText value="#{mathManagedBean.fValue }" />
							<h:outputText value="#{mathManagedBean.pValue}" />
							<h:outputText value="Residual Error" />
							<h:outputText
								value="#{mathManagedBean.residualErrorDegreesFreedom}" />
							<h:outputText
								value="#{mathManagedBean.sumSquaredErr }" />
							<h:outputText
								value="#{mathManagedBean.meanSquareErr }" />
							<h:outputText value="" />
							<h:outputText value="" />
							<h:outputText value="Total" />
							<h:outputText value="#{mathManagedBean.totalDegreesFreedom}" />
							<h:outputText value="#{mathManagedBean.totalSumSquares}" />
							
						</h:panelGrid>
						
					</div>
					<div class="right-div2">
						
						<h:graphicImage value="#{statsBean.seriesPath}" width="600"
							height="600" rendered="#{statsBean.seriesChart}" />
						
						<br />
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