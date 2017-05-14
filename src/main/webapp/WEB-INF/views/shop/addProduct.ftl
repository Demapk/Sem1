<#include "../layouts/body.ftl"/>
<@body title="Добавление товара">
<div class="narrow">
    <div id="right">
        <ul class="breadcrumb-navigation">
            <li><a href="/" title=""><img src="/resources/images/home.png" width="12" height="12"></a></li>
            <li><span>&nbsp;/&nbsp;</span></li>
            <li>Добавить товар</li>
        </ul>
        <div class="both"></div>
        <h1>Добавить товар</h1>
        <#if RequestParameters.error??>
            <p><span class="errortext">Уже существует.<br></span></p>
        </#if>
        <#if RequestParameters.success??>
            <p style="color: green"><span>Добавлено<br></span></p>
        </#if>
        <div id="pop-form" class="static">
            <div class="container_form">
                <div style="margin-top: 16px;">
                    <@form.form name="form_auth" method="post" target="_top" action="/admin/addProduct">
                        <div class="form-group">
                            <span class="input-text">Название: </span>
                            <input class="text" autocomplete="on" required="required" type="text" maxlength="255" value="" name="name">
                        </div>
                        <div class="form-group">
                            <span class="input-text">Категория: </span>
                            <input class="text" autocomplete="on" required="required" type="text" maxlength="255" value="" name="category">
                        </div>
                        <div class="form-group">
                            <span class="input-text">Цена: &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp </span>
                            <input required="required" class="text" autocomplete="on" type="number" min="0" value="" name="price">
                        </div>
                        <div class="form-group">
                            <span class="input-text">Описание: </span>
                            <textarea required="required" class="text" name="description"></textarea>
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