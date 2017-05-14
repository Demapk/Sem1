<#include "../layouts/body.ftl"/>
<@body title="">
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
        <p></p>
    </div>
    <div id="right">
        <ul class="breadcrumb-navigation">
            <li><a href="/" title=""><img src="/resources/images/home.png" width="12" height="12"></a></li>
            <li><span>&nbsp;/&nbsp;</span></li>
            <li>Личный кабинет</li>
        </ul>
        <div class="both"></div>
        <h1>Личный кабинет</h1>
        <p>
            <a href="/account/basket"><strong>Моя корзина</strong></a><br>
            Добавленные в корзину товары из нашего интернет-магазина.</p>
        <p>
            <a href="/account/orders"><strong>Мои заказы</strong></a><br>
            Просмотр и оформление заказов интернет-магазина.</p>
        <p>
            <a href="/account/configuration"><strong>Мои настройки</strong></a><br>
            Настройки вашего профиля.</p>
    </div>
</div>
</@body>