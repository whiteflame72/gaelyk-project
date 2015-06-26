<% include '/WEB-INF/includes/mVideoHeader.gtpl' %>

<h2>Videos</h2>

<a href="/videos/add">Add Video</a>
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
        <th>&nbsp;</th>
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
           <td><a href=${video.xsd}>${abbreviate(video?.xsd)}</a></td>
           <td>${video.valid} 
           <% if(video.valid) { %>
           (<a href="/videos/mpreview/${video.key.id}">Show All</a><br> <a href="/videos/mgpreview/${video.key.id}">Show Grid</a>)
		   <%} %>
           </td>
           <td style="max-width:150px;word-wrap: break-word;">${video.details}</td>
           <td>${video.date}</td>
           <td><a href="/videos/delete/${video.key.id}" onclick="if (!confirm('Are you sure?')) return false;">Delete</a> <br><br><a href="/videos/edit/${video.key.id}">Edit</a></td>
        </tr>
     <% } %>
  </tbody>
</table>

<% include '/WEB-INF/includes/footer.gtpl' %>