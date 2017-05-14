<#include "../layouts/body.ftl"/>
<@body title="Заказы">
<div class="narrow">
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
            <li>Мои заказы</li>
        </ul>
        <div class="both"></div>
        <h1>Мои заказы</h1>

        <div class="order_list current-status">
            <div class="wrap_table">
                <table class="gray_table">
                    <tbody>
                    <tr class="even">
                        <th class="number">№ заказа
                        </th>
                        <th class="status">Статус
                        </th>
                        <th class="sum">Сумма
                    </tr>
                        <#list orders as order>
                        <tr class="even">
                            <td align="center"><a href="/account/orders/${order.getId()}">${order.getId()}</a></td>
                            <td>${order.getStatus()}</td>
                            <td align="right">${order.getSum()}</td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</@body>