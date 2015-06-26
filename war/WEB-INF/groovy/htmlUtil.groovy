final String HEADER_PRAGMA = "Pragma";
final String HEADER_EXPIRES = "Expires";
final String HEADER_CACHE_CONTROL = "Cache-Control";

protected preventCache (response) {
	response.setHeader(HEADER_PRAGMA, "no-cache");
	response.setDateHeader(HEADER_EXPIRES, 1L);
	response.setHeader(HEADER_CACHE_CONTROL, "no-cache");
	response.addHeader(HEADER_CACHE_CONTROL, "no-store");
}