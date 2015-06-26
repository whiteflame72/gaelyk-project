<% include '/WEB-INF/includes/header.gtpl' %>
<% import com.google.appengine.api.datastore.* %>
<% import static com.google.appengine.api.datastore.FetchOptions.Builder.* %>
<% import com.deluan.shiro.gae.realm.DatastoreRealm %>
<% import org.apache.shiro.crypto.hash.Sha512Hash %>
<% import org.apache.shiro.SecurityUtils %>
<!-- client side -->
<% import net.tanesha.recaptcha.ReCaptcha %>
<% import net.tanesha.recaptcha.ReCaptchaFactory %>
<!-- server side -->
<% import net.tanesha.recaptcha.ReCaptchaImpl %>
<% import net.tanesha.recaptcha.ReCaptchaResponse %>
<% import com.techventus.server.voice.Voice %>

<h1>User Registration</h1>
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
		app1 = it.app1
	}
	
if(params.smsadmin) {
	if(params.page1) { %>
		Please provide us with your cell phone number:<p>
		<form action="/registerMe.gtpl?smsadmin=y&page2=y" method="post">
			Contact Number: <input width="10" size="10" type="phone" name="mycontactnumber" /> (Your US cell number only please) <p>
	<%
	ReCaptcha c1 = ReCaptchaFactory.newReCaptcha(your_public_key, your_private_key, false)
	println c1.createRecaptchaHtml(null, null)
	%>
		  	<input type="submit" value="Submit"/>
			<input type="button" name="cancel" value="Cancel" onclick="window.location='/registerMe.gtpl'" />
		</form>
		<%
	} else {
			def remoteAddr = request.getRemoteAddr()
	        ReCaptchaImpl reCaptcha = new ReCaptchaImpl()
	        reCaptcha.setPrivateKey(your_private_key)
	
	        def challenge = params.recaptcha_challenge_field
	        def uresponse = params.recaptcha_response_field
	        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse)
	
	        if (reCaptchaResponse.isValid()) {
		try {
	    	Voice voice = new Voice(email1, password1)
			voice.sendSMS(phone1, '[' + app1 + ']User registration failure: user\'s cell ' + params.mycontactnumber + ' time:' + new Date())
			println 'Sys Admin texted. Thank you for your feedback.'
		} catch(all) {
			println 'Our SMS gateway is currently down. Please try again later.<p>'
		}
		try {
	    	Voice voice = new Voice(email1, password1)
			voice.sendSMS(params.mycontactnumber, 'You or someone had reported that the user registration service is down on ' + new Date() + '. If this is not you or you suspected a misuse of your number, please report the incident by texting ' + phone1 + '. Thank you.')
		} catch(all) {
			println 'Not able to send a confirmation to your cell at ' + params.mycontactnumber + '. <p>'
		}
		println '<p><a href=/>Back to login page</a>'
	        } else {
				println 'Sorry, your recaptcha entry is incorrect. Please <a href=/registerMe.gtpl?smsadmin=y&page1=y>try again</a>.'
	        }
	}
} else 
if(!params.submit) {
%>
<form name="loginForm" action="/registerMe.gtpl?submit=y" method="post">
	Login ID: &nbsp;&nbsp;&nbsp;<input style="width:300px;" required type="email" name="email" /> (Email only) <br/>
	Password: <input style="width:300px;" required type="password" name="password" /> (can be a <a href=http://www.passworddragon.com/password-vs-passphrase>Passphrase</a>) <br/><br/>
	<%
	ReCaptcha c = ReCaptchaFactory.newReCaptcha(your_public_key, your_private_key, false)
	println c.createRecaptchaHtml(null, null)
	%>
	<p>
  	<input type="submit" value="Submit"/>
	<input type="button" name="cancel" value="Cancel" onclick="window.location='/'" />
</form>
<form action="/registerMe.gtpl?smsadmin=y&page1=y" method="post">
	<input style="float:right;" type="submit" name="smsadmin" value="*** For US User Only *** SMS Sys Admin: This is not working!" />
</form>
<%
} else {
	def query = new Query(DatastoreRealm.DEFAULT_USER_STORE_KIND)
	//query.addSort("dateCreated", Query.SortDirection.DESCENDING)
	query.addFilter("email", Query.FilterOperator.EQUAL, params.email)
	PreparedQuery preparedQuery = datastore.prepare(query)
	def entities = preparedQuery.asList( withLimit(1) )
	def record = 0
	entities.each() {
		record++
	}

	try {
		if(record > 0) {
			println 'Sorry that username/email has been taken, please <a href=/registerMe.gtpl>try again</a>.'
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
				Entity user = new Entity(DatastoreRealm.DEFAULT_USER_STORE_KIND)
				user.username = params.email
				user.passwordHash = new Sha512Hash(params.password).toHex()
				user.date = new Date()
				user.firstName = params.firstName
				user.lastName = params.lastName
				user.email = params.email
				user.password = params.password
				user.app = params.app
				user.save()
			
				def admin = email1
				if(params.email ==~ /[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[A-Za-z]{2,4}/) {
					mail.send from: admin,
					to: params.email,
					subject: "Welcome to Google App Engine Gaelyk App",
					textBody: "Your password is $params.password."
					println 'Thank you for your registration. Your login info has been emailed to you.<br><p>The sender will be from [filtered]@gmail.com with the subject "Welcome to Google App Engine Gaelyk App".'
				} else {
					println 'Thank you for your registration.'
				}
	        } else {
				println 'Sorry, your recaptcha entry is incorrect. Please <a href=/registerMe.gtpl>try again</a>.'
	        }
/*				
*/	        
		}
	} catch(all) {
		println 'Our system is currently down/having issue, please try again later.<p><p>'
		println 'Your submission was:<p>'
		println 'Email/Username: ' + params.email + ' Password: ' + params.password
		//all.stackTrace.each {
		//	println it
		//}
	} finally {
		println '<p><a href=/>Back to login page</a>'
	}
}
%>

<% include '/WEB-INF/includes/footer.gtpl' %>