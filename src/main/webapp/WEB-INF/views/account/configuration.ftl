<#include "../layouts/body.ftl"/>
<@body title="Настройки аккаунта">
<div class="narrow">
    <div id="left">
        <div id="left-menu">
            <ul class="left-menu-list">
                <li>
                    <div><a href="/account/basket">Моя корзина</a></div>
                </li>
                <li>
                    <div><a href="/account/orders/">Мои заказы</a></div>
                </li>
                <li>
                    <div><a href="/account/configuration">Мои настройки</a></div>
                </li>
            </ul>
        </div>
        <p></p>
    </div>
    <div id="right">
        <ul class="breadcrumb-navigation">
            <li><a href="/" title=""><img src="/resources/images/home.png" width="12" height="12"></a></li>
            <li><span>&nbsp;/&nbsp;</span></li>
            <li><a href="/account" title="Личный кабинет">Личный кабинет</a></li>
            <li><span>&nbsp;/&nbsp;</span></li>
            <li>Мои настройки</li>
        </ul>
        <div class="both"></div>
        <h1>Мои настройки</h1>
        <div class="bx-auth-profile">
            <#if RequestParameters.success??>
                <p style="color: green"><span class="notetext">Изменения сохранены</span></p>
            </#if>
            <p hidden id="error_naq" class="errortext">
                <br>
            </p>
            <#if RequestParameters.error??>
                <p style="color: red"><span class="errortext">Либо пароли не совпадают, либо старый введен неверно<br><br></span></p>
            </#if>
            <@form.form method="post" name="form1" action="/account/configuration">
                <div class="my_settings">
                    <div class="tab-section-container">
                        <div class="tab-boby-container products">
                            <div class="container">
                                <div id="tab-about-body" class="tab_body active">
                                    <div class="line_block"><@security.authentication property="principal.user.email"/>
                                        <span class="input_text">Эл. почта:</span>
                                    </div>
                                    <div class="line_block"><@security.authentication property="principal.user.username"/>
                                        <span class="input_text">Юзернейм:</span>
                                    </div>
                                    <div class="line_block">
                                        <span class="input_text">Старый пароль</span>
                                        <input id="OLD_PASSWORD" type="password" name="old" maxlength="50"
                                               value=""
                                               autocomplete="off" class="bx-auth-input">
                                    </div>
                                    <div class="line_block">
                                        <span class="input_text">Новый пароль:</span>
                                        <input id="NEW_PASSWORD" type="password" name="new" maxlength="50"
                                               value=""
                                               autocomplete="off" class="bx-auth-input">
                                    </div>
                                    <div class="line_block">
                                        <span class="input_text">Подтверждение пароля:</span>
                                        <input id="NEW_PASSWORD_CONFIRM" type="password" name="newMatch"
                                               maxlength="50" value=""
                                               autocomplete="off" class="text">
                                    </div>
                                </div>
                                <div class="bottom_container">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div style="margin-right: 40%" class="block_button">
                        <input type="submit" name="save" class="button_red_big" value="Сохранить изменения">
                    </div>
                </div>
            </@form.form>
        </div>
    </div>
</div>
</@body>