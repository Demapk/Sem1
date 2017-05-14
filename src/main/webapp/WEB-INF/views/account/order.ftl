<#include "../layouts/body.ftl"/>
<@body>
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
            <li><a href="/" title=""><img src="/resources/images/home.png" width="12"
                                          height="12"></a></li>
            <li><span>&nbsp;/&nbsp;</span></li>
            <li><a href="/account" title="Личный кабинет">Личный кабинет</a></li>
            <li><span>&nbsp;/&nbsp;</span></li>
            <li>Заказ # ${order.getId()}</li>
        </ul>
        <div class="both"></div>
        <h1>Заказ # ${order.getId()}</h1>
        <div id="warning_message">
        </div>

        <div id="basket_form_container">
            <div class="bx_ordercart">
                <div id="basket_items_list">
                    <div class="bx_ordercart_order_table_container">
                        <table id="basket_items">
                            <thead>
                            <tr>
                                <td class="margin"></td>
                                <td class="item" colspan="2" id="col_NAME">
                                    Наименование
                                </td>
                                <td class="price" id="col_PRICE">
                                    Цена
                                </td>
                                <td class="custom" id="col_QUANTITY">
                                    Кол-во
                                </td>
                                <td class="custom" id="col_SUM">
                                    Сумма
                                </td>
                                <td class="custom"></td>
                                <td class="margin"></td>
                            </tr>
                            </thead>
                            <tbody>
                                    <#list order.getProductInOrders() as product>
                                    <tr id="18701">
                                        <td class="margin"></td>
                                        <td class="itemphoto">
                                            <div class="bx_ordercart_photo_container">

                                                <a href="/shop/${product.getProduct().getId()}">
                                                    <div class="bx_ordercart_photo"
                                                         style="background-image:url('/resources/images/no-auto.png')"></div>
                                                </a></div>
                                        </td>
                                        <td class="item">
                                            <h2 class="bx_ordercart_itemtitle">
                                                <a>${product.getProduct().getName()}</a></h2>
                                            <div class="bx_ordercart_itemart">
                                            </div>
                                        </td>
                                        <td class="price">
                                            <div class="current_price"
                                                 id="current_price_18701">${product.getProduct().getPrice()}</div>
                                        </td>

                                            <td class="custom">
                                                <span>:</span>
                                                <div class="centered">
                                                    <table cellspacing="0" cellpadding="0" class="counter">
                                                        <tbody>
                                                        <tr>
                                                            <td>

                                                                <p   style="max-width: 50px"

                                                                >${product.getCount()}</p>
                                                            </td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </td>
                                        <td class="custom">
                                            <span>:</span>
                                            <div id="sum_18701">${product.getCount()*product.getProduct().getPrice()}</div>
                                        </td>
                                        <td class="margin"></td>
                                    </tr>
                                    </#list>

                            </tbody>
                        </table>
                    </div>
                        <div class="bx_ordercart_order_pay">
                            <div class="bx_ordercart_order_pay_left" id="coupons_block">
                                <div class="bx_ordercart_coupon">
                                    <span>Адрес: ${order.getAddress()}</span>
                                </div>
                            </div>
                            <div class="bx_ordercart_order_pay_right">
                                <table class="bx_ordercart_order_sum">
                                    <tbody>
                                    <tr>
                                        <td class="fwb">Итого:</td>
                                        <td class="fwb" id="allSum_FORMATED">
                                                    ${order.getSum()}
                                                </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div style="clear:both;"></div>
                            </div>
                        </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@body>