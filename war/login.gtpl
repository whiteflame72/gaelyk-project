<% include '/WEB-INF/includes/header.gtpl' %>

<h1>Please identify yourself</h1>
<form name="loginForm" action="/login.gtpl" method="post">
	Login ID: &nbsp;&nbsp;&nbsp;<input style="width:300px;"required type="text" name="username" /> (Your Email) <br/>
	Password: <input style="width:300px;" required type="password" name="password" /> (or <a href=http://www.passworddragon.com/password-vs-passphrase>Passphrase</a>)<br/><br/>
  	<input type="submit" value="Login"/>
</form>
<!--
<br/>
<br/>
	<p>Use:</p>
	<p>admin/admin or user1/pass1</p>
<br/>
<br/>
<br/>
<a href="createUsers.groovy">Create some users</a>, if you haven't done it yet
-->
<a href="registerMe.groovy">Create a login</a>, if you do not have one. <a href="emailMePassword.groovy">Reset your password</a>, if you have forgotten it.

<% include '/WEB-INF/includes/footer.gtpl' %>