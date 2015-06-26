def domain = DomainEntity.findByName(params.domain)
request.setAttribute("domainEntity", domain)

def item = findByKey(domain, params.id)
item.delete()
forward "/domain/list.groovy"