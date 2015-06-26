
get "/", forward: "/WEB-INF/pages/index.gtpl"
get "/datetime", forward: "/datetime.groovy"
get "/scaffold", forward: "/scaffold.groovy"

get "/favicon.ico", redirect: "/images/gaelyk-small-favicon.png"

//http://globalgateway.wordpress.com/2010/10/21/simple-crud-with-gaelyk/
get  "/goals",                forward:  "/showGoals.groovy"
get  "/goals/add",            forward:  "/WEB-INF/pages/goal.gtpl"
post "/goals/insert",         forward:  "/insertGoal.groovy"
get  "/goals/delete/@id",     forward:  "/deleteGoal.groovy?id=@id"
get  "/goals/edit/@id",       forward:  "/editGoal.groovy?id=@id"
post "/goals/update",         forward:  "/updateGoal.groovy"
