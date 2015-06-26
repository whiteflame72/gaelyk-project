def domain = DomainEntity.findByName(params.domain)
request.setAttribute("domainEntity", domain)

def item = findByKey(domain, params.id)
domain.attributes.each { attr ->
    request[attr.name] = attr.transformToString(item[attr.name])
}
request.setAttribute "id", item.key.id
forward getRoute(domain, 'edit')