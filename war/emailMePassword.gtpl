<% include '/WEB-INF/includes/header.gtpl' %>
<% import com.google.appengine.api.datastore.* %>
<% import static com.google.appengine.api.datastore.FetchOptions.Builder.* %>
<% import com.deluan.shiro.gae.realm.DatastoreRealm %>
<% import org.apache.shiro.crypto.hash.Sha512Hash %>
<% import org.apache.shiro.codec.Hex %>
<!-- client side -->
<% import net.tanesha.recaptcha.ReCaptcha %>
<% import net.tanesha.recaptcha.ReCaptchaFactory %>
<!-- server side -->
<% import net.tanesha.recaptcha.ReCaptchaImpl %>
<% import net.tanesha.recaptcha.ReCaptchaResponse %>
<% import com.techventus.server.voice.Voice %>

<h1>Password Reset</h1>
<%
	def query1 = new Query("SysPrefs")
	PreparedQuery preparedQuery1 = datastore.prepare(query1)
	def entities1 = preparedQuery1.asList( withLimit(1) )
	entities1.each() {	
		your_public_key = it.recaptchapublickey1
		your_private_key = it.recaptchaprivatekey1
		email1 = it.email1
		phone1 = it.phone1
		password1 = it.password1
	}
	
if(!params.submit) {
%>
<form name="resetForm" action="/emailMePassword.gtpl?submit=y" method="post">
	Login ID: &nbsp;&nbsp;&nbsp;<input style="width:300px;" required type="email" name="email" /> (Your Email) <br/><p>
	<%
	ReCaptcha c = ReCaptchaFactory.newReCaptcha(your_public_key, your_private_key, false)
	println c.createRecaptchaHtml(null, null)
	%>
	<p>
  	<input type="submit" value="Submit"/>
	<input type="button" name="cancel" value="Cancel" onclick="window.location='/'" />
</form>
<%
} else {
	def query = new Query(DatastoreRealm.DEFAULT_USER_STORE_KIND)
	//query.addSort("dateCreated", Query.SortDirection.DESCENDING)
	query.addFilter("email", Query.FilterOperator.EQUAL, params.email)
	PreparedQuery preparedQuery = datastore.prepare(query)
	def entities = preparedQuery.asList( withLimit(1) )
	def record = 0
	def storedPassword = ""
	entities.each() {
		record++
		//storedPassword = Hex.decode(it.passwordHash)
		storedPassword = it.password
	}

	try {
		if(record == 0) {
			println 'Sorry the email params.email does not exist in our system, please <a href=/registerMe.gtpl>try again</a>.'
		} else {
			def remoteAddr = request.getRemoteAddr()
	        ReCaptchaImpl reCaptcha = new ReCaptchaImpl()
	        reCaptcha.setPrivateKey(your_private_key)
	
	        def challenge = params.recaptcha_challenge_field
	        def uresponse = params.recaptcha_response_field
	        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse)
	
	        if (reCaptchaResponse.isValid()) {
/*		
*/	        
				def admin = email1
				if(params.email ==~ /[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[A-Za-z]{2,4}/) {
					mail.send from: admin,
					to: params.email,
					subject: "Your Request",
					textBody: "You or someone had requested your password to be resent. Your password is $storedPassword. If it was not you, it is recommended that you change your password immediately by visting the site. The site is purposely left out for the sake of security."
					println 'Your login info has been emailed to you.<br><p>The sender will be from [filtered]@gmail.com with the subject "Your Request".'
				} else {
					println 'Sorry, your need to enter a valid email address which is your login id. Please <a href=/emailMePassword.gtpl>try again</a>.'
				}
	        } else {
				println 'Sorry, your recaptcha entry is incorrect. Please <a href=/emailMePassword.gtpl>try again</a>.'
	        }
/*				
*/	        
		}
	} catch(e) {
		println 'Our system is currently down/having issue, please try again later.<p><p>'
		log.info "Failed to send reset password email"
        log.info String.valueOf(e)
        log.info String.valueOf(e.cause)	
		e.stackTrace.each {
			log.info String.valueOf(it)
		}
	} finally {
		println '<p><a href=/>Back to login page</a>'
	}
}
%>

<% include '/WEB-INF/includes/footer.gtpl' %>