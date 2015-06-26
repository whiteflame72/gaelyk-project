import com.google.appengine.api.datastore.*
import static  com.google.appengine.api.datastore.FetchOptions.Builder.*

binding {
    getRoute = {domain, action-> 
        if (new File("${domain.name.toLowerCase()}").exists()) { 
            "/${domain.name.toLowerCase()}/${action}.gtpl"
        } else {
            "/domain/${action}.gtpl"
        }
    }
    
    getPropertyMap = {domain ->
        def props = [:]
        domain.attributes.each { attr ->
            props << ["${attr.name}":attr.transformFromString(params[attr.name])]
        }
        props
    }
    
    findByKey = {kind, id ->  
        def key = KeyFactory.createKey(kind.name, id as Long)
        DatastoreServiceFactory.getDatastoreService().get(key)
    }
    
    list = {domain ->
        def query = DatastoreServiceFactory.getDatastoreService() .prepare(new Query(domain.name))
        query.asList(withDefaults())    
    }
    
    createEntity = {name, params ->
        def entity = new Entity(name)
        entity << params
        entity.save()
        entity
    }
    
    propertyValues = {domain, params ->
        def props = [:]
        domain.attributes.each { attr ->
            props << ["${attr.name}":attr.transformFromString(params[attr.name])]
        }
        props
    }
    
    
}

routes {
    get "/@domain/@action", forward: "/@domain/@action.groovy?domain=@domain" , validate: {DomainEntity.findByName(domain) && new File("WEB-INF/groovy/$domain").exists() && ['create', 'edit', 'list','save','update','delete'].contains(action)}
    get "/@domain/@action", forward: "/domain/@action.groovy?domain=@domain" , validate: {DomainEntity.findByName(domain) && !new File("WEB-INF/groovy/$domain").exists() && ['create', 'edit', 'list','save','update','delete'].contains(action)}
}