<% include '/WEB-INF/includes/header.gtpl' %>

<%
  def statement = request.getAttribute("statement")
  boolean existingKey = statement?.key
  String action = !existingKey ? 'Add' : 'Update'
%>

<h2>${action} Statement</h2>

<form action="/statements/${!existingKey ? 'insert' : 'update'}" method="POST">
   <table border="0">
      <tbody>
         <tr>
            <td>Branch No:</td>
            <td><input type="text" name="branchNo" required value="${statement?.branchNo ? statement.branchNo : ''}"></td>
         </tr>
         <tr>
            <td>Last Updated By:</td>
            <td><input type="text" name="lastUpdatedBy" required value="${statement?.lastUpdatedBy ? statement.lastUpdatedBy : ''}"></td>
         </tr>
         <tr>
            <td>Created Date:</td>
            <td><input type="text" name="createdDate" value="${statement?.createdDate ? statement.createdDate : ''}"></td>
         </tr>
         <tr>
            <td>Updated Date:</td>
            <td><input type="text" name="updatedDate" value="${statement?.updatedDate ? statement.updatedDate : ''}"></td>
         </tr>
         <tr>
            <td>Points:</td>
            <td><g:select name="statement.points" from="${[800, 100, 50]}" valueMessagePrefix="statement.points" /></td>
         </tr>
      </tbody>
      <% if(existingKey) { %>
         <input type="hidden" name="id" value="${statement.key.id}">
      <% } %>
   </table>
   <br>
   <input type="submit" value="${action}">
   <input type="button" value="Cancel" onclick="javascript:document.location.href = '/statements';">
</form>

<% include '/WEB-INF/includes/footer.gtpl' %>
