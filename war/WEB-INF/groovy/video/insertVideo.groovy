import java.util.Date;

import com.google.appengine.api.datastore.Entity
import static XMLUtil.validateVideoXML

log.info "Inserting Video"

def goal = new Entity("Video")
goal.date = new Date()
goal.name = params.name
goal.valid = params.valid
goal.details = params.details
goal.url = params.url
goal.xsd = params.xsd
goal.preview = params.preview

if(goal.xsd) {
	validateVideoXML goal
} else {
	goal.details = ''
}
goal.save()

redirect '/mvideos'
