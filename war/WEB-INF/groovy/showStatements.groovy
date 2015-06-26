import com.google.appengine.api.datastore.Query
import com.google.appengine.api.datastore.PreparedQuery
import static com.google.appengine.api.datastore.FetchOptions.Builder.*

log.info "Getting all Statements"

def query = new Query("Statement")
PreparedQuery preparedQuery = datastore.prepare(query)
query.addSort("updatedDate", Query.SortDirection.DESCENDING)
if(params.search) {
query.addFilter("branchNo", Query.FilterOperator.EQUAL, params.search)
}
def statements = preparedQuery.asList(withDefaults())

request.statements = statements

forward '/WEB-INF/pages/statements.gtpl'
