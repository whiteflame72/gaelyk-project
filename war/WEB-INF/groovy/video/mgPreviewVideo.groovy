import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Key

final String HEADER_PRAGMA = "Pragma";
final String HEADER_EXPIRES = "Expires";
final String HEADER_CACHE_CONTROL = "Cache-Control";

protected preventCache (response) {
	response.setHeader(HEADER_PRAGMA, "no-cache");
	response.setDateHeader(HEADER_EXPIRES, 1L);
	response.setHeader(HEADER_CACHE_CONTROL, "no-cache");
	response.addHeader(HEADER_CACHE_CONTROL, "no-store");
}

log.info "Previewing Video In Grid"

def id = Long.parseLong(params.id)
Key key = KeyFactory.createKey("Video", id)
def video = datastore.get(key)

request.video = video
forward '/WEB-INF/pages/video/mgPreviewVideo.gtpl'
