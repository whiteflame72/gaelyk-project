import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Key

log.info "Deleting Statement"

def id = Long.parseLong(params.id)
Key key = KeyFactory.createKey("Statement", id)
key.delete()

redirect '/statements'
