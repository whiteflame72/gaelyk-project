import com.opensymphony.module.sitemesh.parser.FastPageParser 
    
def parser = new FastPageParser() 
def page = parser.parse( new 
URL('http://www.google.com').text.toCharArray() ) 

// then use the sitemesh Page API - javadoc is at http://tinyurl.com/yrge6l
def someBitOfThePage = page.body
println someBitOfThePage