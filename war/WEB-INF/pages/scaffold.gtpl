<% include '/WEB-INF/includes/header.gtpl' %>

<h1>Scaffolding</h1>

<p>
    <%
        log.info "outputing the scaffolded domain classes"
    %>
    The current employee: <%= request.getAttribute('employee') %>
</p>

<% include '/WEB-INF/includes/footer.gtpl' %>

