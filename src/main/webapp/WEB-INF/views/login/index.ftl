<#include "../layouts/body.ftl"/>
<@body title="Вход">
<div class="narrow">
    <div id="right">
        <ul class="breadcrumb-navigation">
            <li><a href="/" title=""><img src="/resources/images/home.png" width="12" height="12"></a></li>
            <li><span>&nbsp;/&nbsp;</span></li>
            <li>Войти</li>
        </ul>
        <div class="both"></div>
        <h1>Войти</h1>
        <#if RequestParameters.error??>
            <p><span class="errortext">Неверный логин или пароль.<br></span></p>
        </#if>
        <div id="pop-form" class="static">
            <div class="container_form">
                <div style="margin-top: 16px;">
                    <@form.form method="post" action="/login_processing">
                        <div class="registration-block">
                            <span class="input-text">Логин</span>
                            <input class="text" required="required" autocomplete="on" type="text" maxlength="255"  name="name">
                        </div>
                        <div class="registration-block">
                            <span class="input-text">Пароль</span>
                            <input class="text" required="required" title="UpperCase, LowerCase, Number/SpecialChar and min 8 Chars" pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" autocomplete="on" type="password" maxlength="255" name="password">
                        </div>
                        <input style="margin-left: 111px;" type="checkbox" id="USER_REMEMBER" name="remember-me"
                               value="Y"><label for="USER_REMEMBER">&nbsp;Запомнить меня на этом компьютере</label>
                        <p>
                            <a href="/login?reset_password"> Забыли пароль?</a>
                        </p>
                        <div class="block_button">
                            <a href="/registration" class="button_gray_big" rel="nofollow"><b>Регистрация</b></a>
                            <input type="submit" name="Login" value="Войти" class="button_red_big"
                                   style="left: 213px; top: -31px">
                        </div>
                    </@form.form>
                </div>
            </div>
        </div>
    </div>
</div>
</@body>