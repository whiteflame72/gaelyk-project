<?xml version="1.0" encoding="UTF-8"?>
<%
import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4

if(params.s) {
	def target = "http://" + "http://cloudserviceapi.appspot.com/go/searchhost1".toURL().text
	def vce = new XmlParser().parse(target+params.s)
	def num = '0' //${vce.list.attribute('num')}.text()
	def count = 1
%>
<videos totalResults="${num}">
<%
	vce.list.document.each {
	  def name = escapeHtml4(it.content.find { it.@name.equals("speaker_name") }.text().replaceAll("<(.|\n)*?>", ''))
	  def summary = it.content.find { it.@name.equals("snippet") }.text().replaceAll("<(.|\n)*?>", '')
	  def desc = ''	//it.media.thumbnail.text().replace('.jpg','-poster.jpg').replaceAll("<(.|\n)*?>", '')
	  def topic = it.content.find { it.@name.equals("snippet") }.text().replaceAll("<(.|\n)*?>", '')
	  def category = it.content.find { it.@name.equals("theme_id") }.text().replaceAll("<(.|\n)*?>", '')
	  def about = it.content.find { it.@name.equals("snippet") }.text().replaceAll("<(.|\n)*?>", '')
	  def affiliation = ''	//it.name.text().replaceAll("<(.|\n)*?>", '')
	  def hometown = ''	//it.name.text().replaceAll("<(.|\n)*?>", '')
	  def occupation = ''	//it.name.text().replaceAll("<(.|\n)*?>", '')
	  def region = it.content.find { it.@name.equals("speaker_region_id") }.text().replaceAll("<(.|\n)*?>", '')
	  def transcript = ''	//it.name.text().replaceAll("<(.|\n)*?>", '')
	  def mediaName = ''//it.content.find { it.@name.equals("clip_filename") }.text().replaceAll("<(.|\n)*?>", '')
%>
	<video uuid="v$count">
		<name>$name</name>
		<summary>$summary</summary>
		<desc>$desc</desc>
        <metadata>
            <attribute name="topic">$topic</attribute>
            <attribute name="category">$category</attribute>
            <attribute name="about">$about</attribute>
            <attribute name="affiliation">$affiliation</attribute>
            <attribute name="hometown">$hometown</attribute>
            <attribute name="occupation">$occupation</attribute>
            <attribute name="region">$region</attribute>
            <attribute name="transcript">$transcript</attribute>
        </metadata>
		<media>
            <name>$mediaName</name>
            <url>http://aaa.nih.nlm.gov/</url>
            <thumbnail>http://aaa.nih.nlm.gov/NA0055_Tex_Hall.jpg</thumbnail>
            <qrcode>http://aaa.nih.nlm.gov/nlmipad/v1.0/qr/nlm_sample_qr.png</qrcode>
		</media>
	</video>
<%
		count++
	}
}
%>
</videos>