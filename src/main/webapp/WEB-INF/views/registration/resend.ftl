<#include "../layouts/body.ftl"/>
<@body title="Успешная регистрация">
<div class="narrow">
    <div id="right">
        <div class="both"></div>
        <#if tooManyResend??>
            <h1>Слишком много запросов</h1>
            <p>Повтори 5 минут позже.</p><br>
        <#else>
            <h1>Письмо выслано повторно.</h1>
        </#if>
        <br>
        <p>Нет письма? <a href="/registration?resend_token">Отправить заново</a> </p>
    </div>
</div>
</@body>