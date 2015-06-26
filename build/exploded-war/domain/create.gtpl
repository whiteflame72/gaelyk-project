<% include '/WEB-INF/includes/header.gtpl' %>

<h1>Recipes</h1>

<p>
<div style="color:red">
${request.error?:""}
</div>
<form action="save">
<table>
<% def domain = request.getAttribute("domainEntity") 
domain.attributes.each {attr ->
%>
<tr>
    <td>${attr.displayName}</td>
    <td><input type="text" name="${attr.name}" value="${params[attr.name]?:""}"/></td>
</tr>
<% } %>
<tr>
<td><input type="submit" value="submit"/></td>
</tr>

</table>
</form>
</p>

<% include '/WEB-INF/includes/footer.gtpl' %>