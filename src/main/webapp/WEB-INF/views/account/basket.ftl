<#include "../layouts/body.ftl"/>
<@body title="Корзина">
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
            <li>Моя корзина</li>
        </ul>
        <div class="both"></div>
        <h1>Моя корзина</h1>
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
                                <td class="margin"></td>
                            </tr>
                            </thead>

                            <tbody id="products">
                                <#if order??>
                                    <#list order.getProductInOrders() as prodInOr>
                                    <tr id="18701">
                                        <td class="margin"></td>
                                        <td class="itemphoto">
                                            <div class="bx_ordercart_photo_container">
                                                <a href="/shop/shiny-13-quot/12728/">
                                                    <div class="bx_ordercart_photo"
                                                         style="background-image:url('/resources/images/no-auto.png')"></div>
                                                </a></div>
                                        </td>
                                        <td class="item">
                                            <h2 class="bx_ordercart_itemtitle">
                                                <a>${prodInOr.getProduct().getName()}</a></h2>
                                            <div class="bx_ordercart_itemart">
                                            </div>
                                        </td>
                                        <td class="price">
                                            <div class="current_price"
                                                 id="price_${prodInOr.getProduct().getId()}">${prodInOr.getProduct().getPrice()}</div>
                                        </td>
                                        <td class="custom">
                                            <span>:</span>
                                            <div class="centered">
                                                <table cellspacing="0" cellpadding="0" class="counter">
                                                    <tbody>
                                                    <tr>
                                                        <td>
                                                            <input class="count" type="number" size="3"
                                                                   id="QUANTITY_INPUT_18701"
                                                                   name="count" maxlength="18" min="0"
                                                                   max="40" step="0" style="max-width: 50px"
                                                                   product_id="${prodInOr.getProduct().getId()}"
                                                                   prev_val="${prodInOr.getCount()}"
                                                                   value="${prodInOr.getCount()}">
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </td>
                                        <td class="custom">
                                            <span>:</span>
                                            <div id="sum_${prodInOr.getProduct().getId()}">${prodInOr.getCount()*prodInOr.getProduct().getPrice()}</div>
                                        </td>
                                        <td class="margin"></td>
                                    </tr>
                                    </#list>
                                </#if>
                            </tbody>
                        </table>
                    </div>
                    <#if order??>
                        <div class="bx_ordercart_order_pay">
                            <div class="bx_ordercart_order_pay_left" id="coupons_block">
                                <div class="bx_ordercart_coupon">
                                    <span></span>
                                </div>
                            </div>
                            <div class="bx_ordercart_order_pay_right">
                                <table class="bx_ordercart_order_sum">
                                    <tbody>
                                    <tr>
                                        <td class="fwb">Итого:</td>
                                        <td class="fwb" id="allSum_FORMATED">
                                        ${sum}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div style="clear:both;"></div>
                            </div>
                            <#if sum!=0>
                                <div style="clear:both;"></div>
                                <div class="bx_ordercart_order_pay_center">
                                    <a href="/account/checkout" class="button_red_big count"
                                       style="float:right;">Оформить заказ</a>
                                </div>
                            </#if>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="application/javascript">
    $("#products").on('input', '.count', function () {
        let input = $(this);
        let product_id = $(this).attr("product_id");
        let count = $(this).val();
        let prev_val = $(this).attr("prev_val");
        let sum = $('#allSum_FORMATED');
        $.ajax({
            url: "/account/basket/addCount",
            type: "POST",
            data: {
                product_id: product_id,
                count: count
            },
            dataType: 'json'
        }).success(function (result) {
            let product_price = $('#price_' + product_id).text();
            if (parseInt(result) === parseInt(count)) {
                $('#sum_' + product_id).text(parseInt(product_price) * result);
                input.css('border', '2px solid black');
            } else {
                $('#sum_' + product_id).text(parseInt(product_price) * result);
                input.val(result);
                input.css('border', '2px solid red');
            }
            sum.text(parseInt(sum.text()) - ((prev_val) * parseInt(product_price)) + result * parseInt(product_price));
            if (sum.text() == "0") {
                $(".bx_ordercart_order_pay_center").hide();
            } else {
                $(".bx_ordercart_order_pay_center").show();
            }
            input.attr("prev_val", result);
        });
    });
</script>
</@body>