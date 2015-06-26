import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Key

log.info "Deleting Video"

def id = Long.parseLong(params.id)
Key key = KeyFactory.createKey("Video", id)
key.delete()

redirect '/mvideos'
