<% include '/WEB-INF/includes/mUserHeader.gtpl' %>

<h2>Users</h2>

<a href="/users/add">Add User</a>
<br><br>
<table>
  <thead>
     <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>App</th>
        <th>Date</th>
        <th>&nbsp;</th>
     </tr>
  </thead>
  <tbody>
     <% 
def abbreviate(String s) {
	retVal = ""
	def limit = 30
	def begin = s.length()-20
	def end = s.length()-1
	if(s.length() > limit) {
	    retVal = s[0..10]+"..."+s[begin..end]
    }
    retVal?retVal:""
}     
     	request.users.each { user -> %>
        <tr>
           <td>${user.firstName}</td>
           <td>${user.lastName}</td>
           <td>${user.email}</td>
           <td>${user.app}</td>
           <td>${user.date}</td>
           <td><a href="/users/delete/${user.key.id}" onclick="if (!confirm('Are you sure?')) return false;">Delete</a> <br><br><a href="/users/edit/${user.key.id}">Edit</a></td>
        </tr>
     <% } %>
  </tbody>
</table>

<% include '/WEB-INF/includes/footer.gtpl' %>