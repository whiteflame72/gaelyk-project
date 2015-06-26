import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Key

log.info "Editing Statement"

def id = Long.parseLong(params.id)
Key key = KeyFactory.createKey("Statement", id)
def statement = datastore.get(key)

request.statement = statement
forward '/WEB-INF/pages/statement.gtpl'
