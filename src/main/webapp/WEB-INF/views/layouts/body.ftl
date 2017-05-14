<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]/>
<#assign spring=JspTaglibs["http://www.springframework.org/tags"]/>
<#macro body title="Автосервис">
<!DOCTYPE html>
<html lang="ru">
<head>
    <script type="application/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
    <link href="/resources/ccs/kernel_main0.css.css"
          type="text/css" rel="stylesheet"/>
    <meta charset="UTF-8">
    <link href="/resources/ccs/page.css"
          type="text/css" rel="stylesheet"/>
    <link href="/resources/ccs/popup.min.css"
          type="text/css"
          rel="stylesheet"/>
    <link href="/resources/ccs/default.css"
          type="text/css" rel="stylesheet"/>
    <link href="/resources/ccs/template_two.css"
          type="text/css" data-template-style="true" rel="stylesheet"/>
    <link href="/resources/ccs/template_one.css"
          type="text/css" data-template-style="true" rel="stylesheet"/>
    <title>${title}</title>
</head>
<body>
<div class="main-page" id="wrap">
    <div id="header">
        <div id="logo">
            <a href="/">Автозапчасти</a>
            <div id="slogan">
                Только самое качественное!
            </div>
        </div>
        <@security.authorize access="isAnonymous()">
            <div class="manage">
                Следите за
                историей
                заказов.<br/>
                <a href="/login" class="button_red_smaller enter_button">Вход</a>
                <a href="/registration"
                   class="button_gray_small registration">Регистрация</a>
            </div>
            <div class="user-info">
                <div class="pop_block">
                    <div class="spacer_block">
                        <div class="bx-system-auth-form">
                            <@form.form id="sys_auth_from" name="login_form" action="/login_processing" method="post" target="_top">
                                <input type="email" name="name" maxlength="50" class="text" placeholder="Email"
                                       size="17"/>
                                <input type="password" title="UpperCase, LowerCase, Number/SpecialChar and min 8 Chars"
                                       pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                                       name="password" maxlength="50" size="17" class="text"
                                       placeholder="Пароль"
                                />
                                <p class="remember-enter">
                                    <input type="checkbox" checked="checked" id="USER_REMEMBER_frm" name="remember-me"
                                           title="Запомнить" class="check"/>
                                    <label class="remember">Запомнить меня</label>
                                    <a href="/login?reset_password">Забыли пароль?</a>
                                    <input class="button_red_smaller" type="submit" name="Login" value="Войти"/>
                                </p>

                                <a rel="nofollow" class="registration"
                                   href="/registration"
                                >Регистрация</a>
                            </@form.form>
                        </div>
                    </div>
                    <div class="bottom_part">
                        <div class="arrow_link"></div>
                    </div>
                </div>
            </div>
        </@security.authorize>

        <@security.authorize  access="hasAnyRole('CUSTOMER', 'ADMIN')">
            <div class="user-info">
                <div class="pop_block">
                    <div id="bxdynamic_auth_form"></div>
                    <div id="bxdynamic_personal_form">
                        <div class="spacer_block auth">
            <@security.authorize  access="hasRole('CUSTOMER')"> <div class="login" style="width: 160px"><a
                    href="/account"><@security.authentication property="principal.user.username"/></a> </@security.authorize>
                            <@security.authorize  access="hasRole('ADMIN')"> <div class="login" style="width: 160px"><a
                                    href="/admin"><@security.authentication property="principal.user.username"/></a> </@security.authorize>
                            </div>
                            <@form.form style="float:right; overflow: auto" action="/logout" method="post">
                                <input type="submit"
                                       style="border:none;border-bottom: 1px dashed black; float:right; background: none; cursor: pointer"
                                       value="Выйти">
                            </@form.form>
                        </div>
                    </div>
                </div>
            </div>
        </@security.authorize>

        <div id="contact_top">
            <div class="link_city">
                <span city="moskva" id="city_current"><var>Москва(Главная точка)</var></span>
            </div>
            <div class="contacts" id="contacts">
                <div class="contact_city active" >
                    <p class="time">Время работы: будни 9:00 – 18:00</p>
                    <p class="phone"> +7 (978) 000-00-00 </p>
                    <a class="email" href="mailto:moscow@autozap4asti.ru">moscow@autozap4asti.ru</a><br>
                </div>
            </div>
        </div>
    </div>
    <div id="content">
        <div class="narrow">
            <div class="menu-search">
                <ul class="menu">
                    <li>
                        <@security.authorize access="hasRole('ADMIN')">
                        <a href="/admin">
                        </@security.authorize>
                        <@security.authorize access="!hasRole('ADMIN')">
                        <a href="/account">
                        </@security.authorize>
                        <nobr>Личный кабинет</nobr>
                    </a>
                        <@security.authorize access="!hasRole('ADMIN')">
                            <div class="dropdown">
                                <ul>
                                    <li class="top_level"><a
                                            href="/account/basket"
                                    >Моя корзина</a>
                                    </li>
                                </ul>
                            </div>
                        </@security.authorize>
                    </li>
                    <li>
                        <a href="/shop">
                            <nobr>Запчасти</nobr>
                        </a>
                    </li>

                </ul>
                <div id="search">
                    <form action="/shop" method="get">
                        <input type="text" maxlength="50" class="text" size="15" name="name"
                               autocomplete="off"> <input type="image"
                                                          src="/resources/images/search-button.png"
                                                          class="button">
                    </form>
                </div>
                <div class="menu-clear-left"></div>
                <i class="ls"></i><i class="rs"></i>
            </div>
        </div>
        <#nested>
    </div>
    <div id="spacer"></div>
    <div id="footer">
        <div class="narrow inner_block with_corner footer">
            <ul class="menu_footer">
                <@security.authorize access="hasRole('ROLE_ADMIN')">
                    <li><a href="/admin" class="root-item">Личный кабинет</a></li>
                </@security.authorize>
                <@security.authorize access="!hasRole('ROLE_ADMIN')">
                    <li><a href="/account" class="root-item">Личный кабинет</a></li>
                </@security.authorize>
                <li>
                    <a href="/shop"
                       class="root-item">Запчасти</a></li>
            </ul>
            <div class="menu-clear-left"></div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/resources/js/template.js"></script>
<script src="/resources/js/cphttprequest.js" type="application/javascript"></script>
<script type="application/javascript">
    $(document).ready(function () {
        $("div.jCarouselLite").find("a[rel=vis-tur]").fancybox({
            'transitionIn': 'none',
            'transitionOut': 'none',
            'titlePosition': 'outside',
            'titleFormat': function (title, currentArray, currentIndex, currentOpts) {
                return '&lt;span id="fancybox-title-over"> ' + (currentIndex + 1) + ' / ' + currentArray.length + (title.length ? ' &nbsp; ' + title : '') + '</span>';
            }
        });
    });
</script>
</body>
</html>
</#macro>