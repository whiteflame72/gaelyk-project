<% import org.apache.shiro.SecurityUtils %>
<% import org.apache.shiro.subject.Subject %>
<%
//http://www.javacodegeeks.com/2011/10/apache-shiro-application-security-made.html

Subject usr = SecurityUtils.getSubject();

if (usr.isPermitted("user:demo")) {
log.info(usr.getPrincipal() + " has permission user:demo");
%>
<!DOCTYPE html> 
<html> 
Hellon world!
</body>
</html>
<%
} else {
%>
    Sorry, you need to register and login to access this demo app.
<%
}
%>