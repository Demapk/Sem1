<#include "../layouts/body.ftl"/>
<@body title="Добавление склада">
<div class="narrow">
    <div id="right">
        <ul class="breadcrumb-navigation">
            <li><a href="/" title=""><img src="/resources/images/home.png" width="12" height="12"></a></li>
            <li><span>&nbsp;/&nbsp;</span></li>
            <li>Добавить склад</li>
        </ul>
        <div class="both"></div>
        <h1>Добавить склад</h1>
        <#if RequestParameters.error??>
            <p><span class="errortext">Уже существует.<br></span></p>
        </#if>
        <#if RequestParameters.success??>
            <p style="color: green"><span>Добавлено<br></span></p>
        </#if>
        <div id="pop-form" class="static">
            <div class="container_form">
                <div style="margin-top: 16px;">
                    <@form.form name="form_auth" method="post" target="_top" action="/admin/addWarehouse">
                        <div class="form-group">
                            <span class="input-text">Город: </span>
                            <input class="text" autocomplete="on" type="text" maxlength="255" value="" name="city">
                        </div>
                        <div class="form-group">
                            <span class="input-text">Адрес: </span>
                            <input class="text" autocomplete="on" type="text" maxlength="255" value="" name="address">
                        </div>

                        <br><br>
                        <p></p>
                        <input align="center" type="submit" name="Login" value="Добавить" class="button_red_big"
                               style="left: 110px; top: -31px">
                    </@form.form>
                </div>
            </div>
        </div>

    </div>
</div>
</@body>