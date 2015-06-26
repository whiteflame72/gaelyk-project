//=== Has to be before jquerymobile!!!
//=== http://jquerymobile.com/test/docs/api/globalconfig.html
$(document).bind("mobileinit", function(){
	//alert('mobileinit');
	$.mobile.page.prototype.options.addBackBtn=true;
	$.mobile.page.prototype.options.backBtnText = "b";
	$.mobile.page.prototype.options.headerTheme = "a";
	$.mobile.page.prototype.options.backBtnText = "previous";
});

//http://stackoverflow.com/questions/6482620/setting-async-false-with-jquery
$.ajaxSetup({
	   async: false
	});

//https://developer.mozilla.org/en/DOM/Using_full-screen_mode
function toggleFullScreen() {
	  if ((document.fullScreenElement && document.fullScreenElement !== null) ||    // alternative standard method
	      (!document.mozFullScreen && !document.webkitIsFullScreen)) {               // current working methods
	    if (document.documentElement.requestFullScreen) {
	      document.documentElement.requestFullScreen();
	    } else if (document.documentElement.mozRequestFullScreen) {
	      document.documentElement.mozRequestFullScreen();
	    } else if (document.documentElement.webkitRequestFullScreen) {
	      document.documentElement.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);
	    }
	  } else {
	    if (document.cancelFullScreen) {
	      document.cancelFullScreen();
	    } else if (document.mozCancelFullScreen) {
	      document.mozCancelFullScreen();
	    } else if (document.webkitCancelFullScreen) {
	      document.webkitCancelFullScreen();
	    }
	  }
	}

//document.addEventListener("keydown", function(e) {
//	  if (e.keyCode == 13) {
//	    toggleFullScreen();
//	  }
//	}, false);

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