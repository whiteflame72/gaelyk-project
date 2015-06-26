import java.util.Date;
import com.google.appengine.api.datastore.Entity
import com.deluan.shiro.gae.realm.DatastoreRealm
import org.apache.shiro.crypto.hash.Sha512Hash

log.info "Resetting User Password"

redirect '/registerMe.gtpl'