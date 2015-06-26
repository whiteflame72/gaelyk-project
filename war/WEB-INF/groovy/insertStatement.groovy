import java.util.Date;

import com.google.appengine.api.datastore.Entity

log.info "Inserting Statement"

def goal = new Entity("Statement")
goal.branchNo = params.branchNo
goal.createdDate = new Date()
goal.updatedDate = new Date()
goal.lastUpdatedBy = params.lastUpdatedBy
goal.points = params.points
goal.save()

redirect '/statements'
