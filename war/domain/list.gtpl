<% include '/WEB-INF/includes/header.gtpl' %>

<h1>Recipes</h1>
<p>
<table>
    <tr>
        <th>ID</th>
        <% def domain = request.getAttribute("domainEntity") 
            domain.attributes.each {attr -> %>
            <th>${attr.displayName}</th>
        <% } %>
    </tr>
    <% request.getAttribute('list').each { %>
        <tr>
            <td><a href="edit?id=${it.key.id}">${it.key.id}</a></td>
            <% domain.attributes.each {attr -> %>
                <td>${attr.transformToString(it[attr.name])}</td>
            <% } %>
            <td><a href="delete?id=${it.key.id}">Delete</a></td>
        </tr>
    <% } %>
</table>
</p>
<a href="create">Add New</a>
<% include '/WEB-INF/includes/footer.gtpl' %>