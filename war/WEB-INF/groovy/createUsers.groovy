import com.deluan.shiro.gae.realm.DatastoreRealm
import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.Query
import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import org.apache.shiro.crypto.hash.Sha512Hash

def saveUser(username, password) {
    Entity entity = new Entity(DatastoreRealm.DEFAULT_USER_STORE_KIND)

    entity.username = username
    entity.passwordHash = new Sha512Hash(password).toHex()

    entity.save()
    return entity
}

def saveSysPrefs() {
	def query1 = new Query("SysPrefs")
	PreparedQuery preparedQuery1 = datastore.prepare(query1)
	def entities1 = preparedQuery1.asList( withLimit(1) )
    Entity entity = new Entity("SysPrefs")
	def count
	try {
		entities1.each() {
			//captcha and admin stuff
		    entity.recaptchapublickey1 = !it.recaptchapublickey1?"replace_me":it.recaptchapublickey1
		    entity.recaptchaprivatekey1 = !it.recaptchaprivatekey1?"replace_me":it.recaptchaprivatekey1
		    entity.email1 = !it.email1?"replace_me":it.email1
		    entity.phone1 = !it.phone1?"replace_me":it.phone1
		    entity.password1 = !it.password1?"replace_me":it.password1
			//header stuff
			entity.title1 = !it.title1?"replace_me":it.title1
			entity.favicon1 = !it.favicon1?"replace_me":it.favicon1
			entity.headerimg1 = !it.headerimg1?"replace_me":it.headerimg1
			entity.headerimg2 = !it.headerimg2?"replace_me":it.headerimg2
			entity.app1 = !it.app1?"replace_me":it.app1
			entity.appuri1 = !it.appuri1?"replace_me":it.appuri1
			entity.googleanalytics1 = !it.googleanalytics1?"replace_me":it.googleanalytics1
			count++
		}
	} catch(all) {
	}
	if(count == 0) {
		//captcha and admin stuff
	    entity.recaptchapublickey1 = "replace_me"
	    entity.recaptchaprivatekey1 = "replace_me"
	    entity.email1 = "replace_me"
	    entity.phone1 = "replace_me"
	    entity.password1 = "replace_me"
		//header stuff
		entity.title1 = "replace_me"
		entity.favicon1 = "replace_me"
		entity.headerimg1 = "replace_me"
		entity.headerimg2 = "replace_me"
		entity.app1 = "replace_me"
		entity.appuri1 = "replace_me"
		entity.googleanalytics1 = "replace_me"
	}
    entity.save()
    return entity
}

def hasUsers() {
    def query = new Query(DatastoreRealm.DEFAULT_USER_STORE_KIND)
	query.addFilter("username", Query.FilterOperator.EQUAL, "admin")
    def result = datastore.prepare(query).countEntities() > 0
}


if (!hasUsers()) {
    saveUser("admin", "admin")
    saveUser("user1", "pass1")
	saveSysPrefs()
	println "Users admin and user1 created, with default system preferences.<br/>"
} else {
    println "Users were already created. Nothing is done!<br/>"
}

//println "admin / admin<br/>"
//println "user1 / pass1<br/>"

println "<br/><a href=\"/\">back</a>"
