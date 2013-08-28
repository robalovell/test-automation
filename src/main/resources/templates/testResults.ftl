<html>
<head>
 <link rel="stylesheet" type="text/css" href="../report.css">
</head>
<body>
	<#include "testSenarioResults.ftl">
	<div id="testCaseResult">
		<#list testFiles as file>
		<#list file.testCases as testCase>
			<#assign first = (testCase_index == 0)?string("testCaseFirst","")>
			<#assign trCss = (testCase_index % 2 == 0)?string("Even","Odd")>
			<div class="testCase ${first} testCase${trCss}">
				<div class="title">	${testCase.name}</div>		
				<#list testCase.methods as method>
					<#assign methodCss = (method_index % 2 == 0)?string("Even","Odd")>
					<#if method.result?? >
						<#assign passedCss = (method.result.succesfull == true)?string("passed","failed")>
					<#else>
						<#assign passedCss = "">
					</#if>
					<div class="testStep testStep${methodCss} ${passedCss}">${method.command}
						<#if method.result?? && method.result.succesfull == false>
						<div class="error">
							${method.result.stackTrace?replace("\n", "\n<div class='clear'></div>")}
						</div>
						</#if>
					</div>
				</#list>
				<div class="gap"></div>
			</div>
		</#list>
		</#list>
	</div>
</div>

</body></html>