import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Key

log.info "Updating Statement"

def id = Long.parseLong(params.id)
Key key = KeyFactory.createKey("Statement", id)
def goal = datastore.get(key)
goal.branchNo = params.branchNo
goal.createdDate = new Date()
goal.updatedDate = new Date()
goal.lastUpdatedBy = params.lastUpdatedBy
goal.points = params.points
goal.save()

redirect '/statements'
