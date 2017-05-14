<#include "../layouts/body.ftl"/>
<@body title="Запчасти">
<div class="narrow">
    <div id="left"><!--Левая колонка-->
        <!--Левое меню-->
        <div id="left-menu"><!--Левое меню-->
            <ul class="left-menu-list">Категории:
                <#if categories??>
                    <#list categories as category>
                        <li>
                            <div><a href="?&category=${category}">${category}</a></div>
                        </li>
                    </#list>
                </#if>
            </ul>
        </div>
    </div>
    <div id="right">
        <ul class="breadcrumb-navigation">
            <li><a href="/" title=""><img src="/resources/images/home.png" width="12"
                                          height="12"></a></li>
            <li><span>&nbsp;/&nbsp;</span></li>
            <li>Запчасти</li>
            <li><span>&nbsp;/&nbsp;</span></li>
            <li>${product.getName()}</li>
        </ul>
        <div class="both"></div>
        <h1>${product.getName()}</h1>
        <div class="selling_block">
            <@security.authorize access="hasRole('CUSTOMER')">
                <span class="basket">
		<a href="/account/basket">Корзина</a><br>

	</span>
            </@security.authorize>
        </div>
        <div class="catalog_block" id="550">
            <table class="deals">
                <tbody id="55">
                    <tr>
                        <td class="foto">
                            <a>
                                <img src="/resources/images/no-auto.png">
                            </a>
                        </td>
                        <#assign x = 0>
                        <#list product.getProductInWarehouses() as piw>
                            <#assign x += piw.getCount()>
                        </#list>
                        <td class="description">
                            <p class="name"><a>${product.getName()}</a></p>
                            <p class="annotation">${product.getCategory()}</p>
                            <p class="annotation">${product.getDescription()}</p>
                            <p class="annotation">Количество на складах:  ${x}
                                <#if x==0>
                                    <br>Невозможно добавить заказ в корзину, отсутствует на всех складах.
                                </#if>
                            </p>
                        </td>
                        <td class="detail">
                            <p class="price"><b>${product.getPrice()} руб</b></p>
                        </td>
                    </tr>

                </tbody>
            </table>
            <p></p>
        </div>
    </div>
</div>
</@body>