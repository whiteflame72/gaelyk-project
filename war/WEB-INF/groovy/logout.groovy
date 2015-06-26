import org.apache.shiro.SecurityUtils

def user = SecurityUtils.subject

if (user.authenticated) {
    user.logout()
}

redirect '/'