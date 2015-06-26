import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Key
import com.deluan.shiro.gae.realm.DatastoreRealm
import org.apache.shiro.SecurityUtils

log.info "Editing My Account"
def o = System.out
def userId = SecurityUtils.getSubject().getPrincipal()
//o << 'userId ' + userId 
def query = new Query(DatastoreRealm.DEFAULT_USER_STORE_KIND)
query.addFilter("email", Query.FilterOperator.EQUAL, userId)
PreparedQuery preparedQuery = datastore.prepare(query)
def entities = preparedQuery.asList( withLimit(1) )
Key id
entities.each() {
	id = it.key
}
def user = datastore.get(id)

request.user = user
forward '/WEB-INF/pages/user/editMyAccount.gtpl'
