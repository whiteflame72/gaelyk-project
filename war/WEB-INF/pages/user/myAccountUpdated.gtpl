<!--
<% include '/WEB-INF/includes/mUserHeader.gtpl' %>
-->

<%
  def user = request.getAttribute("user")
  boolean existingKey = user?.key
%>

<h2>My Account Updated</h2>

Your account has been successfully updated. It will be reflected once you log off and login in again.

<p><a href=/>Back to login page</a>

<% include '/WEB-INF/includes/footer.gtpl' %>
