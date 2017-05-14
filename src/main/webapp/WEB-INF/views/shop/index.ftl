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

    </div><!--/Левая колонка-->

    <div id="right"><!--Правая колонка-->
        <ul class="breadcrumb-navigation">
            <li><a href="/" title=""><img src="/resources/images/home.png" width="12"
                                          height="12"></a></li>
            <li><span>&nbsp;/&nbsp;</span></li>
            <li>Запчасти</li>
        </ul>
        <div class="both"></div>
        <h1>Запчасти</h1>
        <div class="selling_block">
            <@security.authorize access="hasRole('CUSTOMER')">
                <span class="basket">
		<a href="/account/basket">Корзина</a><br>

	</span>
            </@security.authorize>
        </div>
        <@security.authorize access="hasRole('ADMIN')">
            <div class="wrap_table">
                <table class="content gray_table">
                    <tbody>
                    <tr class="even">
                        <th><a href="/admin/addProduct" class="button_red_big">Добавить товар</a></th>
                        <th><a href="/admin/addWarehouse" class="button_red_big">Добавить склад</a></th>
                        <th><a href="/admin/addProductInWarehouse" class="button_red_big">Добавить товар на склад</a>
                        </th>
                    </tr>
                    </tbody>
                </table>
            </div>
        </@security.authorize>
        <div class="catalog_block" id="550">
            <table class="deals">

                <tbody id="55">
                    <#list products as product>
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
                            <p class="name"><a href="/shop/${product.getId()}/">${product.getName()}</a></p>
                            <p class="annotation">Количество на складах:  ${x}
                                <#if x==0>
                                    <br>Невозможно добавить заказ в корзину, отсутствует на всех складах.
                                </#if>
                            </p>

                        </td>
                        <td class="detail">
                            <p class="price"><b>${product.getPrice()} руб</b></p>
                            <@security.authorize access="hasRole('CUSTOMER')">
                                <#if x != 0>
                                    <div class="operation">
                                        <button class="button_red_smaller link_detail"
                                                rel="nofollow" value="${product.getId()}">В корзину
                                        </button>
                                    </div>
                                </#if>
                            </@security.authorize>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
            <p></p>
        </div>
    </div>
</div>
<script type="application/javascript">
    $('.button_red_smaller').on('click', function () {
        $.ajax({
            url: "/shop/add",
            type: "POST",
            data: {
                product_id: $(this).val()
            },
            dataType: 'json'
        }).success(function (result) {
            alert(result);
        });
    })
</script>
</@body>