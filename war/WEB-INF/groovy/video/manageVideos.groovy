import com.google.appengine.api.datastore.Query
import com.google.appengine.api.datastore.PreparedQuery
import static com.google.appengine.api.datastore.FetchOptions.Builder.*

log.info "Manage all Videos"

def query = new Query("Video")
PreparedQuery preparedQuery = datastore.prepare(query)
query.addSort("date", Query.SortDirection.DESCENDING)
//if(params.search) {
//	query.addFilter("name", Query.FilterOperator.EQUAL, params.search)
//}
def videos = preparedQuery.asList(withDefaults())
def newVideos = []
if(params.search) {
	videos.each() {
		//System.out.println(it.name)
		if(it.name.toLowerCase().contains(params.search.toLowerCase())) {
			//System.out.println('found ' + it.name)
			newVideos.add(it)
		}
	}
	videos = newVideos
}
request.videos = videos

forward '/WEB-INF/pages/video/manageVideos.gtpl'
