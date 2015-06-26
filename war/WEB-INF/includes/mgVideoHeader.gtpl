<html>
    <head>
        <title>GU</title>
        <link rel="shortcut icon" href="/images/gaelyk-small-favicon.png" type="image/png">
        <link rel="icon" href="/images/gaelyk-small-favicon.png" type="image/png">
		<!-- jquerymobile stuff (http://jquerymobile.com/demos/1.0a2/#docs/pages/docs-pages.html) -->
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<style>
  	video { border:2px solid red; width:320px; height:auto; }
</style>
<script>
//https://developer.mozilla.org/en/DOM/Using_full-screen_mode
function enterFullscreen(videoId) {
	var elem = document.getElementById(videoId);
	if (elem.requestFullScreen) {
	  elem.requestFullScreen();
	} else if (elem.mozRequestFullScreen) {
	  elem.mozRequestFullScreen();
	} else if (elem.webkitRequestFullScreen) {
	  elem.webkitRequestFullScreen();
	}
}
</script>
    </head>
    <body>
        <div>
