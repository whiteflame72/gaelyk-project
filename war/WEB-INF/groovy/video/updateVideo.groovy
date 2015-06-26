import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Key
import static XMLUtil.validateVideoXML

final String HEADER_PRAGMA = "Pragma";
final String HEADER_EXPIRES = "Expires";
final String HEADER_CACHE_CONTROL = "Cache-Control";

protected preventCache (response) {
	response.setHeader(HEADER_PRAGMA, "no-cache");
	response.setDateHeader(HEADER_EXPIRES, 1L);
	response.setHeader(HEADER_CACHE_CONTROL, "no-cache");
	response.addHeader(HEADER_CACHE_CONTROL, "no-store");
}

log.info "Updating Video"

def id = Long.parseLong(params.id)
Key key = KeyFactory.createKey("Video", id)
def video = datastore.get(key)
video.date = new Date()
video.name = params.name
video.valid = params.valid
video.details = params.details
video.url = params.url
video.xsd = params.xsd
video.preview = params.preview

if(video && video.xsd) {
	validateVideoXML video
//	new XMLUtil().validateVideoXML video
} else {
	video.details = ''
}

video.save()

redirect '/mvideos'
