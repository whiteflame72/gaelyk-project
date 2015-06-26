get  "/musers",                forward:  "/user/manageUsers.groovy"
post  "/musers",                forward:  "/user/manageUsers.groovy"
get  "/users/add",            forward:  "/WEB-INF/pages/user/user.gtpl"
post "/users/insert",         forward:  "/user/insertUser.groovy"
get  "/users/delete/@id",     forward:  "/user/deleteUser.groovy?id=@id"
get  "/users/edit/@id",       forward:  "/user/editUser.groovy?id=@id"
post "/users/update",         forward:  "/user/updateUser.groovy"
post "/users/updatemyaccount",         forward:  "/user/updateMyAccount.groovy"
get  "/users/editmyaccount",       forward:  "/user/editMyAccount.groovy"
get  "/users/myaccountupdated",       forward:  "/WEB-INF/pages/user/myAccountUpdated.gtpl"

// routes for the blobstore service example
get "/upload",  forward: "/upload.gtpl"
get "/success", forward: "/success.gtpl"
get "/failure", forward: "/failure.gtpl"

get "/", forward: "/index.gtpl"
get "/datetime", forward: "/datetime.groovy"
get "/scaffold", forward: "/scaffold.groovy"

get "/favicon.ico", redirect: "/images/gaelyk-small-favicon.png"

//http://globalgateway.wordpress.com/2010/10/21/simple-crud-with-gaelyk/
get  "/goals",                forward:  "/showGoals.groovy"
post  "/goals",                forward:  "/showGoals.groovy"
get  "/goals/add",            forward:  "/WEB-INF/pages/goal.gtpl"
post "/goals/insert",         forward:  "/insertGoal.groovy"
get  "/goals/delete/@id",     forward:  "/deleteGoal.groovy?id=@id"
get  "/goals/edit/@id",       forward:  "/editGoal.groovy?id=@id"
post "/goals/update",         forward:  "/updateGoal.groovy"

get  "/statements",                forward:  "/showStatements.groovy"
post  "/statements",                forward:  "/showStatements.groovy"
get  "/statements/add",            forward:  "/WEB-INF/pages/statement.gtpl"
post "/statements/insert",         forward:  "/insertStatement.groovy"
get  "/statements/delete/@id",     forward:  "/deleteStatement.groovy?id=@id"
get  "/statements/edit/@id",       forward:  "/editStatement.groovy?id=@id"
post "/statements/update",         forward:  "/updateStatement.groovy"

get  "/mvideos",                forward:  "/video/manageVideos.groovy", cache: 0.second
post  "/mvideos",                forward:  "/video/manageVideos.groovy"
get  "/videos/mpreview/@id",            forward:  "/video/mPreviewVideo.groovy?id=@id"
get  "/videos/mgpreview/@id",            forward:  "/video/mgPreviewVideo.groovy?id=@id"
get  "/videos",                forward:  "/video/showVideos.groovy", cache: 0.second
post  "/videos",                forward:  "/video/showVideos.groovy"
get  "/videos/add",            forward:  "/WEB-INF/pages/video/video.gtpl"
post "/videos/insert",         forward:  "/video/insertVideo.groovy"
get  "/videos/delete/@id",     forward:  "/video/deleteVideo.groovy?id=@id"
get  "/videos/edit/@id",       forward:  "/video/editVideo.groovy?id=@id", cache: 0.second
post "/videos/update",         forward:  "/video/updateVideo.groovy"
get  "/videos/preview/@id",            forward:  "/video/previewVideo.groovy?id=@id"

get  "/demo",                redirect:  "/splitview/index-vz.html"

get  "/videos/search/@keyword",            forward:  "/video/searchVideoXML.groovy?s=@keyword"
