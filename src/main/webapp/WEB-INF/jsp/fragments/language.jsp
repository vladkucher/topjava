<%@page contentType="text/html" pageEncoding="UTF-8" %>

<li class="dropdown">
    <a id="drop1" href="#" class="dropdown-toggle" data-toggle="dropdown">
        ${pageContext.response.locale}
        <span class="caret"></span>
    </a>
    <ul class="dropdown-menu">
        <li><a onclick="show('en')">English</a></li>
        <li><a onclick="show('ru')">Русский</a></li>
    </ul>
</li>
<script type="text/javascript">
    var localeCode="${pageContext.response.locale}";
    function show(lang) {
        window.location.href = window.location.href.split('?')[0] + '?lang=' + lang;
    }
</script>
