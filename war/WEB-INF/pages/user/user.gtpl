<% include '/WEB-INF/includes/mUserHeader.gtpl' %>

<%
  def user = request.getAttribute("user")
  boolean existingKey = user?.key
  String action = !existingKey ? 'Add' : 'Update'
%>

<h2>${action} User</h2>

<form action="/users/${!existingKey ? 'insert' : 'update'}" method="POST">
   <table border="0">
      <tbody>
         <tr>
            <td>First Name:</td>
            <td><input type="text" name="firstName" required size="50" value="${user?.firstName ? user.firstName : ''}"></td>
         </tr>
         <tr>
            <td>Last Name:</td>
            <td><input type="text" name="lastName" size="50" value="${user?.lastName ? user.lastName : ''}"></td>
         </tr>
         <tr>
            <td>Email/Username:</td>
            <td><input type="text" name="email" required size="50" value="${user?.email ? user.email : ''}"></td>
         </tr>
         <tr>
            <td>Password:</td>
            <td><input type="password" name="password" required size="50" value="${user?.password ? user.password : ''}"></td>
         </tr>
         <tr>
            <td>App:</td>
            <td><input type="text" name="app" required size="50" value="${user?.app ? user.app : ''}"></td>
         </tr>
         <tr>
            <td>Date:</td>
            <td><label for="date">${user?.date ? user.date : ''}</label></td>
         </tr>
      </tbody>
      <% 
      if(existingKey) { %>
         <input type="hidden" name="id" value="${user.key.id}">
      <% } %>
   </table>
   <br>
   <input type="submit" value="${action}">
   <input type="button" value="Cancel" onclick="javascript:document.location.href = '/musers';">
</form>

<% include '/WEB-INF/includes/footer.gtpl' %>
