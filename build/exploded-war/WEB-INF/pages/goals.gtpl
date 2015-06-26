<% include '/WEB-INF/includes/header.gtpl' %>

<h2>Goals</h2>

<a href="/goals/add">Add Goal</a>
<br><br>
<table border="1">
  <thead>
     <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Created</th>
        <th>&nbsp;</th>
     </tr>
  </thead>
  <tbody>
     <% request.goals.each { goal -> %>
        <tr>
           <td>${goal.name}</td>
           <td>${goal.description}</td>
           <td>${goal.created}</td>
           <td><a href="/goals/delete/${goal.key.id}">Delete</a> | <a href="/goals/edit/${goal.key.id}">Edit</a></td>
        </tr>
     <% } %>
  </tbody>
</table>

<% include '/WEB-INF/includes/footer.gtpl' %>
