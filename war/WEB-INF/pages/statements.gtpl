<% include '/WEB-INF/includes/statementHeader.gtpl' %>

<h2>Statements</h2>

<a href="/statements/add">Add Statement</a>
<br><br>
<table>
  <thead>
     <tr>
        <th>Branch No</th>
        <th>Last Updated By</th>
        <th>Created Date</th>
        <th>Updated Date</th>
        <th>Points</th>
        <th>&nbsp;</th>
     </tr>
  </thead>
  <tbody>
     <% request.statements.each { statement -> %>
        <tr>
           <td>${statement.branchNo}</td>
           <td>${statement.lastUpdatedBy}</td>
           <td>${statement.createdDate}</td>
           <td>${statement.updatedDate}</td>
           <td>${statement.points}</td>
           <td><a href="/statements/delete/${statement.key.id}" onclick="if (!confirm('Are you sure?')) return false;">Delete</a> | <a href="/statements/edit/${statement.key.id}">Edit</a></td>
        </tr>
     <% } %>
  </tbody>
</table>

<% include '/WEB-INF/includes/footer.gtpl' %>
