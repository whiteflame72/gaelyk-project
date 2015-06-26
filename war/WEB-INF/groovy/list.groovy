def domain = DomainEntity.findByName(params.domain)
request.setAttribute("domainEntity", domain)

request.setAttribute('list',list(domain))
forward getRoute(domain, 'list')