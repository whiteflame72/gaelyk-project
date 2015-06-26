import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Key
import com.deluan.shiro.gae.realm.DatastoreRealm

log.info "Editing User"

def id = Long.parseLong(params.id)
Key key = KeyFactory.createKey(DatastoreRealm.DEFAULT_USER_STORE_KIND, id)
def user = datastore.get(key)

request.user = user
forward '/WEB-INF/pages/user/user.gtpl'
