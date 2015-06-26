
log.info "Setting attribute of scaffolding"

def domain = DomainEntity.findByName(params.domain)
request.setAttribute("domainEntity", domain)

def props = propertyValues(domain, params)
if (domain.validate(params)) {
    createEntity(domain.name, props)
    forward '/domain/list.groovy'
} else {
    request.setAttribute("error","Failed Validation")
    forward getRoute(domain, 'create')
}

//request.setAttribute 'employee', new Employee().toString()

log.info "Forwarding to the template"

forward '/WEB-INF/pages/scaffold.gtpl'