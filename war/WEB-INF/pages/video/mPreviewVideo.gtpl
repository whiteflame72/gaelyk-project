<!--
<% include '/WEB-INF/includes/header.gtpl' %>
-->
    <style type="text/css">
        #table1 {
            width: 100%;
        }
        thead {
            background-color: black;
            color: white;
            text-indent: 14px;
            text-align: left;
        }
        tbody tr:nth-child(odd) {
            background-color: rgba(0, 200, 0, 0.1); /* green, 10% alpha */
        }
        tbody tr:nth-child(even) {
            background-color: rgba(10, 0, 0, 0.1); /* red, 30% alpha */
        }
    </style>
<%
  def video = request.getAttribute("video")
  boolean existingKey = video?.key
  String action = !existingKey ? 'Add' : 'Preview'
%>
<!--
//=== http://developer.apple.com/library/safari/#documentation/AudioVideo/Conceptual/Using_HTML5_Audio_Video/AudioandVideoTagBasics/AudioandVideoTagBasics.html
<script src="AC_QuickTime.js" type="text/javascript">
</script>
-->

<h2>${action} Video</h2>

<form action="/videos" method="POST">
   <table id="table1" border="0">
      <thead>
         <tr>
            <td>Name: <input type="text" name="name" required size="50" value="${video?.name ? video.name : ''}">
            URL: <input type="url" name="url" required size="155" value="${video?.url ? video.url : ''}"></td>
         </tr>
         <tr>
            <td>Valid (applicable only if the XSD is supplied): <input type="text" name="valid" value="${video?.valid ? video.valid : ''}">
            Details:
			<textarea name="details" rows="4" cols="90" maxlength="800">${video?.details ? video.details : ''}</textarea>
			</td>
         </tr>
         <tr>
            <td>Preview (HTTP Live Streaming works only on Mac OS Desktop/iOS Safari browser):
            <!--<input type="text" name="preview" size="150" placeholder="This will be the preview of all videos" value="${video?.preview ? video.preview : ''}">-->
         </tr>
      </thead>
      <tbody></tbody>
   </table>
   <table id="table2" border="0">      
<%
//http://camendesign.com/code/video_for_everybody/test.html
if(video && video.url) {
	def videos = new XmlParser().parse(video.url)
	println "total videos = ${videos.attribute("totalResults")}<p>"
	def count = 1
	videos.video.each {
%>
         <tr>
<style type="text/css" media="screen">
	canvas, img { display:block; margin:1em auto; border:1px solid black; }
</style>
<style>
  	video { border:2px solid red; width:288px; height:auto; }
</style>
<script>
function getDimensions${count}() {
	video = document.getElementsByTagName('video')[0];
	span1 = document.getElementById('dimensions');
	span1.innerHTML = video.videoWidth + ' x ' + video.videoHeight;
}
</script>
<%
	  def realUrl = it.media.url.text()+it.media.name.text()
	  def mp4Url = it.media.url.text()+it.media.name.text().replace('.m3u8','.MP4')
	  def posterUrl = it.media.thumbnail.text().replace('.jpg','-poster.jpg')
	  def thumbnail = it.media.thumbnail.text()
	  def category = it.metadata.attribute.find { it.@name.equals("category") }.text()
	  def name = it.name.text()
      println "<td style='width:20%;'>"
	  println "<span style='font-weight: bold;text-decoration: underline;color: rgba(10, 10, 10, 1);'>"
	  println "Name: $name"
	  println "</span><p>" 
	  println "Category: $category</span> <p>Thumbnail: <img src=$thumbnail alt=thumbnail height=42 width=62 />"
      println "</td>"
	  println "<td width=300>"
//	  println "<video onmouseover=getDimensions${count} preload=none poster=$posterUrl width=30% height=auto id=html5videoplayer controls=controls><source src=$realUrl><source src=$mp4Url>Your browser does not support the video element.</video>"
	  //the following video tag was generated by http://sandbox.thewikies.com/vfe-generator/
%>
<!-- "Video For Everybody" http://camendesign.com/code/video_for_everybody -->
<video preload=none controls="controls" poster="$posterUrl" width="640" height="360">
	<source src="$mp4Url" type="video/mp4" />
	<source src=$realUrl>
	<object type="application/x-shockwave-flash" data="http://player.longtailvideo.com/player.swf" width="640" height="360">
		<param name="movie" value="http://player.longtailvideo.com/player.swf" />
		<param name="allowFullScreen" value="true" />
		<param name="wmode" value="transparent" />
		<param name="flashVars" value="controlbar=over&amp;image=${URLEncoder.encode(posterUrl)}&amp;file=${URLEncoder.encode(mp4Url)}" />
		<img alt="Streaming Video Preview" src="$posterUrl" width="640" height="360" title="No video playback capabilities, please download the video directly" />
	</object>
</video>
<%
	  println "<td><canvas width=288 height=168></canvas></td>"
      println "<td width=50>"
	  println "*** Video Preview is 30% of the native resolution (1280x720p), M3U8 is 960x540p (75% of the 720p) ***"
	  println "<span style='font-size: 8'>"
	  println "<p>Video Dimensions: <span id='dimensions'></span><p>"
	  println "M3U8: ${realUrl.toURL().text}<p>"
	  println "<strong>Stream video:</strong><p><a href=$realUrl>$realUrl</a><p>"
%>	  
	  <strong>Download video:</strong><p><a href="http://streaming.radiantexp.com/encoding/exhibit_native_voices/section_tradition/TR0042_Thomas_Begay/TR0042_Thomas_Begay.MP4">MP4 format</a>
</p>
<%
	  println "</span><p>" 
      println "</td>"
%>
         </tr>
<%
		count++
	}
}
%>
      </tbody>
      <% 
      if(existingKey) { %>
         <input type="hidden" name="id" value="${video.key.id}">
      <% } %>
   </table>
   <br>
   <input type="button" value="Cancel" onclick="javascript:document.location.href = '/mvideos';">
</form>

<% include '/WEB-INF/includes/footer.gtpl' %>
