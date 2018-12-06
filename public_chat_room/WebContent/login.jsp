<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="CSS/login-css.css" />
    <title>LOGIN</title>
    <script type="text/javascript">
        function setFunc() {
            var div = document.getElementById("loginExtra");
            if (div.style.display == "block")
                div.style.display = "none";
            else
                div.style.display = "block";
        }
        var theSelected = "icon-1";
        function changeSelect(){
            var div = document.getElementById(theSelected);
            div.style.opacity = "0.5";
            var id = loginForm.icon.value;
            div = document.getElementById(id);
            div.style.opacity = "1";
            theSelected = id;
        }
    </script>
</head>

<body>
    <div class="login-logo"></div>
    <form action="enterController" method="POST" name="loginForm">
        <div class="login">
            <label for="userName">USERNAME:</label>
            <input id="userName" class="userName-input" name="userName" />
        </div>
        <div class="login">
            <input type="submit" value="ENTER" class="enter">
            <a id="setting" href="#loginExtra">设置</a>
        </div>
        <div class="login" id="loginExtra" style="display:none; color:white">
            <select name="language" style="width:200px">
                <option value="zh-CN">中文</option>
                <option value="en-US">English</option>
            </select>
            <ul style="list-style:none; padding-left: 0;">
                <li class="icon">
                    <label style="text-align:center;">
                        <div id="icon-1" class="icon-1"></div>
                        <input type="radio" name="icon" value="icon-1" style="display:none" onclick="changeSelect()" checked/>
                    </label>
                    <label style="text-align:center;">
                        <div id="icon-2" class="icon-2"></div>
                        <input type="radio" name="icon" value="icon-2" style="display:none" onclick="changeSelect()"/>
                    </label>
                    <label style="text-align:center;">
                        <div id="icon-3" class="icon-3"></div>
                        <input type="radio" name="icon" value="icon-3" style="display:none" onclick="changeSelect()"/>
                    </label>
                    <label style="text-align:center;">
                        <div id="icon-4" class="icon-4"></div>
                        <input type="radio" name="icon" value="icon-4" style="display:none" onclick="changeSelect()"/>
                    </label>
                </li>
            </ul>
        </div>
    </form>
    <script type="text/javascript">
        document.getElementById("setting").onclick = setFunc;
    </script>
</body>

</html>