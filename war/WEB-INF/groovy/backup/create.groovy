def domain = DomainEntity.findByName(params.domain)
request.setAttribute("domainEntity", domain)

forward getRoute(domain, 'create')