<% include '/WEB-INF/includes/header.gtpl' %>

<h1>Welcome</h1>

<p>
    Congratulations, you've just created your first 
    <a href="http://gaelyk.appspot.com">Gaelyk</a> application.
</p>

<h1>
    Domain Objects
</h1>
<%

DomainEntity.entityList.each { %>
    <a href="${it.name.toLowerCase()}/list">${it.displayName}</a><br/>
<% } %>
<% include '/WEB-INF/includes/footer.gtpl' %>

