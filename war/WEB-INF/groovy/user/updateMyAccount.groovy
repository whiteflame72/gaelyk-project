import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Key
import com.deluan.shiro.gae.realm.DatastoreRealm
import org.apache.shiro.crypto.hash.Sha512Hash

log.info "Updating My Account"

def id = Long.parseLong(params.id)
Key key = KeyFactory.createKey(DatastoreRealm.DEFAULT_USER_STORE_KIND, id)
def user = datastore.get(key)

user.username = params.email
user.passwordHash = new Sha512Hash(params.password).toHex()

user.date = new Date()
user.firstName = params.firstName
user.lastName = params.lastName
user.email = params.email
user.password = params.password
user.app = params.app

user.save()

redirect '/users/myaccountupdated'
