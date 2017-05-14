<#include "../layouts/body.ftl"/>
<@body title="Добавление товара на склад">
<div class="narrow">
    <div id="right">
        <ul class="breadcrumb-navigation">
            <li><a href="/" title=""><img src="/resources/images/home.png" width="12" height="12"></a></li>
            <li><span>&nbsp;/&nbsp;</span></li>
            <li>Добавить товар на склад</li>
        </ul>
        <div class="both"></div>
        <h1>Добавить товар на склад</h1>
        <#if RequestParameters.error??>
            <p><span class="errortext">Что-то пошло не так?? Склад или товар не создан<br></span></p>
        </#if>
        <#if RequestParameters.success??>
            <p style="color: green"><span>Добавлено<br></span></p>
        </#if>
        <div id="pop-form" class="static">
            <div class="container_form">
                <div style="margin-top: 16px;">
                    <@form.form name="form_auth" method="post" target="_top" action="/admin/addProductInWarehouse">
                        <div class="form-group">
                            <span class="input-text">Склад : </span>
                            <select required="required" class="form-control" name="warehouse">
                                <option disabled="disabled">Выбор склада</option>
                                <#list warehouses as warehouse>
                                        <option value="${warehouse.getId()}">${warehouse.getCity()} : ${warehouse.getAddress()}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="form-group">
                            <span class="input-text">Товар : </span>
                            <select required="required" class="form-control" name="product">
                                <option disabled="disabled">Выбор товара</option>
                                <#list products as product>
                                    <option value="${product.getId()}">${product.getName()}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="form-group">
                            <span class="input-text">Кол-во: </span>
                            <input required="required" class="text" autocomplete="on" type="number" min="0" maxlength="255" value="" name="count">
                        </div>

                        <br><br>
                        <p></p>
                        <input align="center" type="submit" value="Добавить" class="button_red_big"
                               style="left: 110px; top: -31px">

                    </@form.form>
                </div>
            </div>
        </div>

    </div>
</div>
</@body>