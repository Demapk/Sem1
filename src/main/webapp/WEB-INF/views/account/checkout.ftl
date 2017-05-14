<#include "../layouts/body.ftl"/>
<@body title="Чекаут">
<div class="narrow">
    <div id="left">
        <div id="left-menu">
            <ul class="left-menu-list">
                <li>
                    <div><a href="/account/basket">Моя корзина</a></div>
                </li>
                <li>
                    <div><a href="/account/orders">Мои заказы</a></div>
                </li>
                <li>
                    <div><a href="/account/configuration">Мои настройки</a></div>
                </li>
            </ul>
        </div>
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
        <#if RequestParameters.error??>
            <p><span class="errortext">По видимому, кто-то успел сделать заказ и товара не достаточно на складе, пожалуйста, измените кол-во товара в корзине.<br></span></p>
        </#if>
        <div id="order_form_div" class="order-checkout">
            <div class="bx_order_make">
               <@form.form action="/account/checkout" method="POST" name="ORDER_FORM" id="ORDER_FORM">
                    <div id="order_form_content">

                        <div class="bx_section">
                            <h2>
                                Информация для оплаты и доставки заказа </h2>
                            <div id="sale_order_props" class="sale_order_props">
                                <div>
                                    <h3>Данные для доставки</h3>
                                    <div data-property-id-row="27">

                                        <br>
                                        <div class="bx_block r1x3 pt8">
                                            <span class="bx_sof_req">*</span>
                                            Адрес доставки:
                                        </div>

                                        <div class="bx_block r3x1">
                                            <textarea required="required" minlength="20" rows="3" cols="30" name="address"
                                                      id="address"></textarea>
                                        </div>
                                        <div style="clear: both;"></div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="bx_ordercart">
                        <div>
                            <table class="content">
                                <thead>
                                </thead>
                                <tbody>
                                <tr>
                                    <td align="right" class="itog">
                                        Итого:
                                    </td>
                                    <td align="right" colspan="6">
                                        <#if sum??>
                                        ${sum}
                                    </#if></td>

                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="bx_ordercart_order_pay_center">
                        <input type="submit" value="Оформить заказ" class="button_red_big"/>
                    </div>
                   </@form.form>
            </div>
        </div>
    </div>
</div>
</@body>