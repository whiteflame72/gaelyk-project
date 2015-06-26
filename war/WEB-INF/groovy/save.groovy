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