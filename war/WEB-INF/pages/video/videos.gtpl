<% include '/WEB-INF/includes/videoHeader.gtpl' %>

<h2>Videos</h2>

<br><br>
<table>
  <thead>
     <tr>
        <th>Name</th>
        <th>URL (Abbreviated)</th>
        <th>XSD (Abbreviated)</th>
        <th>Valid (Preview)</th>
        <th>Processing Details</th>
        <th>Date</th>
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
     	request.videos.each { video -> %>
        <tr>
           <td>${video.name}</td>
           <td><a href=${video.url}>${abbreviate(video.url)}</a></td>
           <td><a href=${video.xsd}>${abbreviate(video.xsd)}</a></td>
           <td>${video.valid} 
           <% if(video.valid) { %>
           (<a href="/videos/preview/${video.key.id}">Show</a>)
		   <%} %>
           </td>
           <td style="max-width:150px;word-wrap: break-word;">${video.details}</td>
           <td>${video.date}</td>
        </tr>
     <% } %>
  </tbody>
</table>

<% include '/WEB-INF/includes/footer.gtpl' %>