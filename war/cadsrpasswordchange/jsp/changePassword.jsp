<%@ page isELIgnored="false"%>
<%@ taglib uri="/WEB-INF/tld/cadsrpasswordchange.tld"
 	prefix="cadsrpasswordchangetags"%>
<%-- <%@ taglib uri="/WEB-INF/tld/Owasp.CsrfGuard.tld" prefix="csrf"%> --%>
<%@ page import="gov.nih.nci.cadsr.cadsrpasswordchange.core.Constants"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <title><%=Constants.CHANGE_PASSWORD_TITLE %></title>

<div style="position: absolute;">
	<a href="#skip"> <img src="/cadsrpasswordchange/images/skipnav.gif"
		border="0" height="1" width="1" alt="Skip Navigation"
		title="Skip Navigation" />
	</a>
</div>

<html:base />
<meta http-equiv="Content-Language" content="en-us">
<meta HTTP-EQUIV="Content-Type"
	CONTENT="text/html; charset=WINDOWS-1252">
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK href="/cadsrpasswordchange/css/cadsrpasswordchange.css"
	rel="stylesheet" type="text/css">

<script language="JavaScript" type="text/JavaScript">
		function callLogout(){
		     document.LogoutForm.submit();
		}
		</script>

</head>

<body>

	<%
		/*		
		 if (session.getAttribute("username") == null) {
		 // this shouldn't happen, make the user start over
		 //response.sendRedirect("./jsp/loggedOut.jsp");
		 response.sendRedirect(Constants.LOGGEDOUT_URL);
		 return;
		 }
		 */
		if (session.getAttribute(Constants.USERNAME) != null && request.getParameter("donotclear") != null) {
			request.setAttribute("LoginID", (String)session.getAttribute(Constants.USERNAME)!=null?(String)session.getAttribute(Constants.USERNAME):"");
		} else {
			request.setAttribute("LoginID", (String)request.getParameter("LoginID")!=null?(String)request.getParameter("LoginID"):"");
		}

		String errorMessage = (String) session.getAttribute("ErrorMessage");
		if (errorMessage == null)
			errorMessage = "";
		session.setAttribute("ErrorMessage", "");

		String userMessage = (String) session.getAttribute("UserMessage");
		if (userMessage == null)
			userMessage = "";
		session.setAttribute("UserMessage", "");
	%>

	<form name="LogoutForm" method="post"
		action="../../cadsrpasswordchange/logout"></form>

	<table class="secttable">
		<colgroup></colgroup>
		<tbody class="secttbody" />
		<tr>
			<td><cadsrpasswordchangetags:header
					showlogout="false" /> <a name="skip" id="skip"></a>
					
		<table><tr><td><p class=\"ttl18\"><h3 style="margin-left:300px;"><%=Constants.CHANGE_PASSWORD_TITLE %></h3></p></td></tr></table>
					
					<table>
					<!--
						<td><%=Constants.PWD_RESTRICTIONS%></td>
						-->
<h3>Password Restrictions:</h3>
			<td class=\face\ style=\WIDTH: 617px\ colspan=\2\> 
    <ul> 
Your new password may not re-use your last 24 passwords.<br> 
You may not change your password more than once within 24 hours.<br> 
Your new password must be at least 8 and no more than 30 characters long.<br> 
Your new password must start with a letter.<br> 
Your new password may only use characters from the following categories and must include characters from at least three of these categories: 
<br><br>
<div style="margin-left:40px;">
<li>Uppercase Letters (A-Z)</li> 
<li>Lowercase Letters (a-z)</li> 
<li>Numerals (0-9)</li> 
<li>Special Characters ( _  #  $)
</div>
    </ul><tt> 
    </tt></td> 
					</table>
				<form name="PasswordChangeForm"
					action="../../cadsrpasswordchange/changePassword" method="POST"
					focus="userid" title="Use this screen to change your password">
					<input type="hidden" name="<csrf:token-name/>"
						value="<csrf:token-value/>" />

					<%
						if (errorMessage.equals("")) {
							if (userMessage.equals("")) {
					%>
					<p class=std>Please provide your login credentials and a new password (repeated). You may login with an expired password.</p>
					<%
						} else {
					%>
					<p class=std><%=userMessage%></p>
					<%
						}
					%>
					<%
						} else {
					%>
					<strong class="accessible-error-text" align="center"><%=errorMessage%></strong>
					<%
						}
					%>
				</td>
				
			<table
				summary="Login credentials and new password to change password.">
				<tr>
					<td valign="middle"><label for="LoginID" class=bstd>Login
							ID:
							</p></td>
					<td valign="middle"><input id="LoginID" type="text"
						name="userid" value="<%= request.getAttribute("LoginID") %>" style="width: 3.75in"
						class="std"></td>
				</tr>
				<tr>
				<tr>
					<td valign="middle"><label for="OldPassword" class=bstd>Current
							Password:
							</p></td>
					<td valign="middle"><input id="OldPassword" type="password"
						name="pswd" value="" style="width: 3.75in" class="std"
						autocomplete="off"></td>
				</tr>
				<tr>
				<tr>
					<td valign="middle"><label for="NewPassword" class=bstd>New
							Password:
							</p></td>
					<td valign="middle"><input id="NewPassword" type="password"
						name="newpswd1" value="" style="width: 3.75in" class="std"
						autocomplete="off"></td>
				</tr>
				<tr>
				<tr>
					<td valign="middle"><label for="NewPasswordRepeat" class=bstd>New
							Password (repeated):
							</p></td>
					<td valign="middle"><input id="NewPasswordRepeat"
						type="password" name="newpswd2" value="" style="width: 3.75in"
						class="std" autocomplete="off"></td>
				</tr>
				<tr>
					<td colspan="2" valign="middle"><p class="bstd"
							style="text-align: center; margin-top: 8pt; margin-bottom: 8pt"
							id="msg"></p></td>
				</tr>
				<tr>
					<td valign="bottom"><input type="submit" name="changePassword"
						value="Change" style="text-align: center" class="but2"> <input
						type="submit" name="cancel" value="Cancel"
						style="text-align: center" class="but2">
				</tr>
				<tr>
					<!--
                <td colspan="2" valign="middle"><a target="_blank" href="https://wiki.nci.nih.gov/x/3AJQB">Please see the NCI Wiki for information on caDSR passwords including restrictions on choice of passwords</a></td>
                -->
                
				</tr>
			</table>
			</form>

			<cadsrpasswordchangetags:footer />

			</td>
		</tr>
	</table>

</body>

</html>
