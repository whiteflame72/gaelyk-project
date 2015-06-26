import java.util.Date;
import com.google.appengine.api.datastore.Entity
import com.deluan.shiro.gae.realm.DatastoreRealm
import org.apache.shiro.crypto.hash.Sha512Hash

log.info "Inserting User"

Entity user = new Entity(DatastoreRealm.DEFAULT_USER_STORE_KIND)

user.username = params.email
user.passwordHash = new Sha512Hash(params.password).toHex()

user.date = new Date()
user.firstName = params.firstName
user.lastName = params.lastName
user.email = params.email
user.password = params.password
user.app = params.app

user.save()

redirect '/musers'
