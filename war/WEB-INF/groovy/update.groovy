def domain = DomainEntity.findByName(params.domain)
request.setAttribute("domainEntity", domain)

def props = propertyValues(domain, params)

if (domain.validate(params)) {
    def item = findByKey(domain, params.id)
    item << props
    item.save()
    forward '/domain/list.groovy'
} else {
    props.each {key, value ->
        request[key] = value
    }
    request.id = params.id
    request.setAttribute("error","Failed Validation")
    forward getRoute(domain, 'edit')
}