<#include "../layouts/body.ftl"/>
<@body title="Успешная регистрация">
<div class="narrow">
    <div id="right">
        <div class="both"></div>
        <h1>Письмо с подтверждением отправлено на ${user.getEmail()}</h1>
        <p></p>
        <br>
        <p>Нет письма? <a href="/registration?resend_token">Отправить заново</a> </p>
    </div>
</div>
</@body>