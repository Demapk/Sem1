<#include "../layouts/body.ftl"/>
<@body title="Сбросить пароль">
<div class="narrow">
    <div id="right">
        <ul class="breadcrumb-navigation">
            <li><a href="/" title=""><img src="/resources/images/home.png" width="12" height="12"></a></li>
            <li><span>&nbsp;/&nbsp;</span></li>
            <li>Сбросить пароль</li>
        </ul>
        <div class="both"></div>
        <h1>Сбросить пароль</h1>
        <#if message??>
            <p><span class="errortext">${message}<br></span></p>
        </#if>
        <div id="pop-form" class="static">
            <div class="container_form">
                <div style="margin-top: 16px;">
                    <@form.form method="post" action="/login?reset_password">
                        <div class="registration-block">
                            <span class="input-text">Введите email</span>
                            <input class="text" required="required" autocomplete="on" type="email" maxlength="255" name="email">
                        </div>
                        <div class="block_button" style="overflow: auto">
                            <input style="float: right" type="submit" name="Login" value="Отправить" class="button_red_big">
                        </div>
                    </@form.form>
                </div>
            </div>
        </div>
    </div>
</div>
</@body>