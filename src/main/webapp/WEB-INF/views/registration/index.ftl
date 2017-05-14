<#include "../layouts/body.ftl"/>
<@body title="Регистрация">
<div id="content">
    <div class="narrow">
        <div id="right">
            <div class="both"></div>
            <h1>Регистрация</h1>
            <div id="pop-form">
                <div class="container_form">
                    <div class="bx-auth">
                        <@form.form modelAttribute="user" method="post" action="/registration">
                            <div class="registration-block">
                                <span class="input_text">E-Mail:<span class="starrequired"> *</span></span>
                                <@form.input path="email" type="text" required="required" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$"/>
                                <@form.errors path="email" cssStyle="color: red;" />
                            </div>
                            <div class="registration-block">
                                <span class="input_text">Username:<span class="starrequired"> *</span></span>
                                <@form.input path="username" title="The length from 3 to 15, with no special characters" pattern="^[a-zA-Z0-9_-]{3,15}$" type="text" required="required"/>
                                <@form.errors path="username" cssStyle="color: red;" />
                            </div>
                            <div class="registration-block">
                                <span class="input_text">Пароль:<span class="starrequired"> *</span> </span>
                                <@form.password path="password" title="UpperCase, LowerCase, Number/SpecialChar and min 8 Chars" required="required" pattern="(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$"/>
                                <@form.errors path="password" cssStyle="color: red;" />
                            </div>
                            <div class="registration-block">
                                <span class="input_text">Подтверждение пароля:<span
                                        class="starrequired"> *</span> </span>
                                <@form.password title="UpperCase, LowerCase, Number/SpecialChar and min 8 Chars" pattern="(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$" path="matchingPassword" required="required"/>
                                <@form.errors path="matchingPassword" cssStyle="color: red;" />
                            </div>

                            <div class="block_button">
                                <a href="/login"
                                   class="button_gray_big" rel="nofollow"><b>Авторизоваться </b> </a>
                                <input
                                        type="submit" name="z"
                                        value="Зарегистрироваться"
                                        class="button_red_big"
                                        style="left: 213px; top: -31px">
                            </div>
                        </@form.form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@body>