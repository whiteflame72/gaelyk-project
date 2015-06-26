import java.util.logging.Logger
import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*

def Logger log = Logger.getLogger("controller");

log.info("URI:" + request.uri + " params:" + params)

log.severe("URI:" + request.getRequestURI())

if (params.activity == "list") {
  list(log, params)
} else if (params.activity == "listAll") {
  listAll(log, params)
} else if (params.activity == "edit") {
  edit(log, params)
} else if (params.activity == "save") {
  save(log, params); list(log, params)
} else if (params.activity == "addProp") {
  addprop(log, params); edit(log, params)
} else if (params.activity == "delProp") {
  delprop(log, params); edit(log, params)
} else if (params.activity == "delete") {
  delete(log, params); list(log, params)
} else if (params.activity == "new") {
  newEntity(log, params); list(log, params)
} else if (params.activity == "newXml") {
  newEntityXml(log, params);
} else if (params.activity == "new") {
  newEntity(log, params); list(log, params)
} else if (params.activity == "clone") {
  clone(log, params); list(log, params)
} else { response.sendError(404, "Activity $params.activity not found ") }

// utils

def clone(log, params) {
  def entity = getEntityById(params.id)
  Entity newEntity = new Entity(params.entity); newEntity.setPropertiesFrom(entity)
  newEntity.save()
}

def newEntity(log, params) {
  def entity = new Entity(params.entity); entity.lastChange = new Date(); entity.name = "new"; entity.save()
  log.info "Entity $entity.kind($entity.key.id) created"
}

def newEntityXml(log, params) {
//  def entity = new Entity(params.entity);
  def xml = params.xml

  def records = new XmlParser().parseText(xml)
  records.entity.each {row ->
    def entity = new Entity(row.'@table')

    row.column.each {col ->
      def prop = col.'@name'
      def value = col.'@value'
      def valueT
      try {
        valueT = Class.forName(col.'@type').newInstance(value)
      } catch (Exception e) {
        log.error("Unable to use class " + col.'@type' + e.message)
      }
      entity[(prop)] = valueT
    }
    entity.save()
  }
}


def delete(log, params) {
  if (params.id == "all") {
    Query q = new Query(params.entity);
    PreparedQuery preparedQuery = datastore.prepare(q)
    def entities = preparedQuery.asList(withLimit(100))
    entities.each {it.delete()}

  } else {
    def id = Long.valueOf(params.id).longValue()
    def key = KeyFactory.createKey(params.entity, id)
    datastore.delete(key)
  }

}

def delprop(log, params) {
  def entity = getEntityById(params.id)
  def prop = entity.removeProperty(params.extId)
  entity.save()
}

def addprop(log, params) {
  try {
    def v = Class.forName(params.type).newInstance(params.newPropValue)
    def entity = getEntityById(params.id)
    entity[(params.newPropName)] = v
    entity.save()
  } catch (Exception e) {
    println "Unable to create property " + params.newPropName + " Error: $e"
  }
}

def getEntityById(myid) {
  def id = Long.valueOf(myid).longValue()
  def key = KeyFactory.createKey(params.entity, id)
  return datastore.get(key)

}

/*
def save(log, params) {
  def id = Long.valueOf(params.id).longValue()
  def key = KeyFactory.createKey(params.entity, id)
  def query = new Query(params.entity, key)
  def entity = datastore.get(key)
  params.each {k, v ->
    if (k != "activity" && k != "entity" && k != "id")
      entity[(k)] = v
  }
  entity.save()
  println "Saved"
}
*/

def save(log, params) {
  def id = Long.valueOf(params.id).longValue()
  def key = KeyFactory.createKey(params.entity, id)
  def query = new Query(params.entity, key)
  def entity = datastore.get(key)
  def value
  if (params.type == "com.google.appengine.api.datastore.Text") value = new Text(params.value); else value = params.value
  log.info "Would save property $params.property with type $params.type value: $params.value"
  entity[(params.property)] = value; entity.save()
}

def listAll(log, params) {

  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  Query query = new Query("__Stat_Kind__");
//  query.addFilter("kind_name", FilterOperator.EQUAL, "Page");
  def globalStat = datastore.prepare(query).asIterable()

  html.html {
    if (globalStat.iterator().size() != 0) {

      body {
        p("Number of entity kinds:" + globalStat.iterator().size())
        globalStat.each {
          def kind = it["kind_name"];
          def count = it["count"];
          def bytes = it["bytes"];
          def timestamp = it["timestamp"]
          if (!kind.startsWith("_")) {
            p {
              a(href: "/entity/$kind", "$kind, Count:$count, Size: $bytes (bytes), Timestamp:$timestamp")
            }
          }
        }

      }
    } else {
      body {
        p("Dataengine stat is null. There are either no entities or it runs on dev app engine. Try on remote app engine again")
      }

    }

    hr()
    form(action: "/entity/new", method: "POST") {
      p("Create new entity kind:")
      input(name: "entity")
      input(type: "submit", value: "Create new Entity")
      hr()
      p("""Please note, this list uses app engine statistics that are calculated once a day. This is why newly created
        entities does not appear immediatelly""")
    }
  }
}

def list(log, params) {

  def paging = 100
  def query = new Query(params.entity)
  PreparedQuery preparedQuery = datastore.prepare(query)
  query.addSort("lastChange", Query.SortDirection.DESCENDING)
  def entities = preparedQuery.asList(withLimit(paging))

//  def cursor = preparedQuery.asQueryResultList(withLimit(10)).getCursor()
//  log.info "Cursor $cursor"

  html.html {
    body {
      p("Entities of kind $params.entity:" + entities.size()); hr()
      p { a(href: "/entity/" + params.entity + "/new", "create new entity of this kind") }; hr()
      entities.each {
        Entity e = it
        p {
          a(href: "/entity/" + params.entity + "/edit/" + e.key.id, e.name + " (" + e.key.id + ")")
          a(href: "/entity/" + params.entity + "/delete/" + e.key.id, "[X]")
          a(href: "/entity/" + params.entity + "/clone/" + e.key.id, "[CC]")
        }
      }
      hr()
      if (!localMode) a(href: users.createLogoutURL('/'), "logout")
      a(href: "/entity/listAll", "List all entity kinds")
    }
  }

}

def edit(log, params) {
  def id = Long.valueOf(params.id).longValue()
  def key = KeyFactory.createKey(params.entity, id)
//  def query = new Query(params.entity, key)
  def entity = datastore.get(key)

  html.html {
    body {
      p("Entity:" + entity.kind + " id:" + entity.key.id)
//      form(action: "/entity/" + params.entity + "/save", method: "POST") {
//        input(type: "hidden", name: "id", value: entity.key.id)
      table {
        entity.properties.each {k, v ->
          def vv
          if (v.class.name.contains("Text")) vv = v.getValue()
          else if (v.class.name.contains("Date")) vv = v.getDateTimeString()
          else vv = "" + v
          tr {
            form(action: "/entity/" + params.entity + "/save", method: "POST") {
              td(k)
              if (vv.length() > 100) { td { textarea(name: "value", vv, cols: 100, rows: vv.length() / 100 + 1) } } else
                td { input(type: "input", name: "value", value: vv, size: vv.length()) }
              td {
                input(name: "type", value: v.class.name)
                input(type: "hidden", name: "property", value: k); input(type: "hidden", name: "id", value: entity.key.id);
                input(type: "submit", value: "Save")
              }
              td { a(href: "/entity/" + params.entity + "/delProp/" + entity.key.id + "/$k", method: "POST", "delete property")    }
            }
          }
        }
      }
      hr()
      p("Add new property:")
      form(action: "/entity/" + params.entity + "/addProp", method: "POST") {
        input(type: "hidden", name: "id", value: entity.key.id)
        table {
          tr {
            td { p("Property name:")}; td { input(type: "input", name: "newPropName")}
            td { p("Initial value:")}; td { input(type: "input", name: "newPropValue")}
            td {
              select(name: "type") {
                DataTypeUtils.getSupportedTypes().each { option(value: it.name, it.name)  }
              }
            }
          }
        }
        input(type: "submit", value: "Save")
      }
      a(href: "/entity/" + params.entity, "<<< back to entity list")
    }
  }
}

