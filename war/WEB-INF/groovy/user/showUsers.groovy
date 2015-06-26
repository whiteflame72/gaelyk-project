import com.google.appengine.api.datastore.Query
import com.google.appengine.api.datastore.PreparedQuery
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import com.deluan.shiro.gae.realm.DatastoreRealm

log.info "Getting all Users"

def query = new Query(DatastoreRealm.DEFAULT_USER_STORE_KIND)
PreparedQuery preparedQuery = datastore.prepare(query)
query.addSort("date", Query.SortDirection.DESCENDING)
//if(params.search) {
//	query.addFilter("name", Query.FilterOperator.EQUAL, params.search)
//}
def users = preparedQuery.asList(withDefaults())
def newUsers = []
if(params.search) {
	users.each() {
		//System.out.println(it.name)
		if(it.name.toLowerCase().contains(params.search.toLowerCase())) {
			//System.out.println('found ' + it.name)
			newUsers.add(it)
		}
	}
	users = newUsers
}
request.users = users

forward '/WEB-INF/pages/user/users.gtpl'
