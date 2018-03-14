<!DOCTYPE html>
<html lang="en-US">
 <header></header>
 <body>
	 <#if users?exists>
		 <table>
	        <#list users as key> 
	           <tr>
	               <td>${key.id}</td>
	               <td>${key.name}</td>
	           </tr>
	        </#list>
		 </table>
    </#if>
 
 
 </body>
</html>