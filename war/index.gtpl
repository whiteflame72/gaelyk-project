<% include '/WEB-INF/includes/header.gtpl' %>
<% import com.google.appengine.api.datastore.* %>
<% import static com.google.appengine.api.datastore.FetchOptions.Builder.* %>
<% import org.apache.shiro.SecurityUtils %>
<%
	def query1 = new Query("SysPrefs")
	PreparedQuery preparedQuery1 = datastore.prepare(query1)
	def entities1 = preparedQuery1.asList( withLimit(1) )
	entities1.each() {
		title1 = !it.title1?'Gaelyk':it.title1
		appuri1 = !it.appuri1?'':it.appuri1
	}
%>
<h1>Welcome</h1>

<p>
	<div style="float:right;"><a href="/users/editmyaccount">Edit My Account</a></div>
    Congratulations, you've just visited a web application hosted on Google App Engine for Java.<p>
    Proceed to your application by clicking <a href="$appuri1">here</a>.
    <p>
    <% 
    //SecurityUtils.getSubject().getSession().getAttribute( "someKey")
    log.info( "User [" + SecurityUtils.getSubject().getPrincipal() + "] logged in successfully." );
    %>
</p>

<p>
    Or just click <a href="datetime.groovy">here</a> to view the current date/time.
</p>

<% include '/WEB-INF/includes/footer.gtpl' %>

