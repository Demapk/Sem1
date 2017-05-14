<#include "../layouts/body.ftl"/>
<@body title="Пользователи">
<div class="narrow">
    <h1 align="center">Управление юзерами</h1>
    <div style=" margin:0 auto ">
        <div style="text-align: center" class="both"></div>
        <div class="wrap_table">
            <table class="gray_table request">
                <tbody id="body">
                <tr class="even">
                    <th class="type">№ Юзера</th>
                    <th class="type">Юзернейм</th>
                    <th class="type">Email</th>
                    <th class="type">Активация</th>
                    <th class="type">Удаление</th>
                </tr>
                    <#list users as user>
                    <tr id="user_${user.getId()}" class="even">
                            <td>${user.getId()}</td>
                            <td>${user.getUsername()}</td>
                            <td >${user.getEmail()}</td>
                            <#if user.isEnabled()>
                                <td><input class="button_red_small en" user_id="${user.getId()}" type="submit" value="Выключить"></td>
                                <#else>
                                    <td><input class="button_red_small" type="submit" value="Включить"></td>
                            </#if>
                            <td><input class="button_red_small del" user_id="${user.getId()}" type="submit" value="Удалить"></td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="application/javascript">
    $('#body').on('click', '.button_red_small.en', function () {
        let user_id = $(this).attr('user_id');
        let val = $(this).val();
        let input = $(this);
        $.ajax({
            url: "/admin/users/enable",
            type: "POST",
            data: {
                user_id: user_id
            },
            dataType: 'json'
        }).success(function (result) {
            if(result == 'OK'){
                if(val == 'Выключить'){
                    input.val('Включить');
                }else{
                    input.val('Выключить');
                }
            }
        });
    });


    $('#body').on('click', '.button_red_small.del', function () {
        let user_id = $(this).attr('user_id');
        $.ajax({
            url: "/admin/users/delete",
            type: "POST",
            data: {
                user_id: user_id
            },
            dataType: 'json'
        }).success(function (result) {
            if(result == 'OK'){
                $('#user_'+user_id).remove();
            }
        });
    });
</script>
</@body>