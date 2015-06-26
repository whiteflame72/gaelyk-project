// this is simple scaffolding plugin descriptor for Gaelyk
// author: Ladislav Skokan (skokys@gmail.com)


binding {

}

routes {
  post "/entity/new", forward: "/WEB-INF/groovy/scaffolding.groovy?activity=new"
  get "/entity/listAll", forward: "/WEB-INF/groovy/scaffolding.groovy?activity=listAll"
  get "/entity/@entity/@activity/@id/@extId", forward: "/WEB-INF/groovy/scaffolding.groovy?entity=@entity&activity=@activity&extId=@extId&id=@id"
  get "/entity/@entity/@activity/@id", forward: "/WEB-INF/groovy/scaffolding.groovy?entity=@entity&activity=@activity&id=@id"
  get "/entity/@entity/@activity", forward: "/WEB-INF/groovy/scaffolding.groovy?entity=@entity&activity=@activity"
  get "/entity/@entity", forward: "/WEB-INF/groovy/scaffolding.groovy?entity=@entity&activity=list"
  post "/entity/@entity/@activity", forward: "/WEB-INF/groovy/scaffolding.groovy?entity=@entity&activity=@activity"

}

