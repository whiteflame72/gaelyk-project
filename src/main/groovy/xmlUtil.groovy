//package com.appspot.cloudserviceapi

import org.dom4j.io.SAXReader

class XMLUtil {
//	static setupSaxReader(reader, stream) {
//		reader.setValidation(true)
//		reader.setFeature("http://apache.org/xml/features/validation/schema", true)
//		reader.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true)
//		reader.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema")
//		reader.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", stream)
//	}
	 
	static validateVideoXML(videoInstance) {
		try {
//			memcache.clearCacheForUri(videoInstance.url)
//			memcache.clearCacheForUri(videoInstance.xsd)
			def schema = videoInstance.xsd.toURL()
			def document = videoInstance.url.toURL()
			def schemaStream = schema.newInputStream()
			def documentReader = document.newReader()
			SAXReader reader = new SAXReader()
//			setupSaxReader(reader, schemaStream)	//cause strange error: No signature of method: static com.appspot.cloudserviceapi.XMLUtil.setupSaxReader() is applicable for argument types: (org.dom4j.io.SAXReader, java.io.BufferedInputStream) values: [org.dom4j.io.SAXReader@7691c0, java.io.BufferedInputStream@5b02a6]
//Possible solutions: setupSaxReader(java.lang.Object, java.lang.Object)
			reader.setValidation(true)
			reader.setFeature("http://apache.org/xml/features/validation/schema", true)
			reader.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true)
			reader.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema")
			reader.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", schemaStream)
			
			reader.read(documentReader)
			videoInstance.valid = true
			videoInstance.details = ""
			println "Document valid.\n"
		} catch(all) {
			videoInstance.valid = false
			videoInstance.details = all.getMessage()
		}
	}
}