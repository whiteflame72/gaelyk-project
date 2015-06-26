<!--
<% include '/WEB-INF/includes/videoHeader.gtpl' %>
-->

<%
  def video = request.getAttribute("video")
  boolean existingKey = video?.key
  String action = !existingKey ? 'Add' : 'Update'
%>

<h2>${action} Video</h2>

<form action="/videos/${!existingKey ? 'insert' : 'update'}" method="POST">
   <table border="0">
      <tbody>
         <tr>
            <td>Name:</td>
            <td><input type="text" name="name" required size="50" value="${video?.name ? video.name : ''}"></td>
         </tr>
         <tr>
            <td>URL:</td>
            <td><input type="url" name="url" required size="150" value="${video?.url ? video.url : ''}"></td>
         </tr>
         <tr>
            <td>XSD (URL only for now):</td>
            <td><input type="url" name="xsd" size="150" value="${video?.xsd ? video.xsd : ''}"></td>
         </tr>
         <tr>
            <td>Valid (applicable only if the XSD is supplied):</td>
            <td><input type="text" name="valid" value="${video?.valid ? video.valid : ''}"></td>
         </tr>
         <tr>
            <td>Details:</td>
            <td>
			<textarea name="details" rows="5" cols="100" maxlength="500">${video?.details ? video.details : ''}</textarea>
         </tr>
         <tr>
            <td>Date:</td>
            <td><label for="date">${video?.date ? video.date : ''}</label></td>
         </tr>
      </tbody>
      <% 
      if(existingKey) { %>
         <input type="hidden" name="id" value="${video.key.id}">
      <% } %>
   </table>
   <br>
   <input type="submit" value="${action}">
   <input type="button" value="Cancel" onclick="javascript:document.location.href = '/mvideos';">
</form>

<% include '/WEB-INF/includes/footer.gtpl' %>
