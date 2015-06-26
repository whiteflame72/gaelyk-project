import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.apache.shiro.session.Session

log.info "Setting attribute datetime"

request.setAttribute 'datetime', new Date().toString()

//http://shiro.apache.org/10-minute-tutorial.html
//Subject currentUser = SecurityUtils.getSubject();
//Session session = currentUser.getSession();
//session.setAttribute( "someKey", "aValue" );

log.info "Forwarding to the template"

forward '/datetime.gtpl'