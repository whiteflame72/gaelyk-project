<% import com.google.appengine.api.datastore.* %>
<% import static com.google.appengine.api.datastore.FetchOptions.Builder.* %>
<% import org.apache.shiro.SecurityUtils %>
<%
	def	title1 = "", favicon1 = "", googleanalytics1 = "", headerimg1 = "", headerimg2 = ""
	def query1 = new Query("SysPrefs")
	PreparedQuery preparedQuery1 = datastore.prepare(query1)
	def entities1 = preparedQuery1.asList( withLimit(1) )
	try {
	entities1.each() {
		title1 = !it.title1?'Gaelyk':it.title1
		favicon1 = !it.favicon1?'/images/gaelyk-small-favicon.png':it.favicon1
		headerimg1 = !it.headerimg1?'/images/gaelyk.png':it.headerimg1
		headerimg2 = !it.headerimg2?'/images/google-app-engine-groovy.png':it.headerimg2
		googleanalytics1 = !it.googleanalytics1?'':it.googleanalytics1
	}
	} catch(Exception e) {
		println e.getMessage()
	}
%>
<html>
    <head>
        <title>$title1</title>
        
        <link rel="shortcut icon" href="$favicon1" type="image/png">
        <link rel="icon" href="$favicon1" type="image/png">
        
        <link rel="stylesheet" type="text/css" href="/css/main.css"/>
		<script type="text/javascript">
		
		  var _gaq = _gaq || [];
		  _gaq.push(['_setAccount', '$googleanalytics1']);
		  _gaq.push(['_trackPageview']);
		
		  (function() {
		    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
		    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
		    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
		  })();
		
		</script>
    </head>
    <body>
        <div>
            <img src="$headerimg1">
            <img src="$headerimg2" align="right">
        </div>
        <% def user = SecurityUtils.subject %>
        <% if (user?.isAuthenticated()) { %>
          <div>
              Logged in as <strong>${user.getPrincipal()}</strong> -
              <a href="logout.groovy">logout</a>
          </div>
        <% } %>
        <div>
