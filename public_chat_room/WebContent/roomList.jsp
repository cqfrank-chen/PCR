<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<script type="text/javascript" src="jquery-3.0.0.js"></script>
<script type="text/room">
<ul class="rooms" lang="zh-CN" >
	<li class="name">
		<form action="RoomControllerServlet" method="get">
			<input name="roomId" value={roomId} style="display:none" >
			<input name="roomName" value={roomName} style="display:none" >
			<input id="LoadRoom" name="command" value="loadRoom" style="display: none;"> 
			<button class="btn btn-link select-text lounger-room-name"
				type="submit" name="roomId">
				<span class="room-name" title="{roomName}">{roomName}</span>
			</button>
		</form>
	</li>
	<li class="status">
		<div class="progress-bar-label-wrap">
			<div class="progress-bar-label room-tooltip tooltipstered">{roomCount}/{roomSum}</div>
		</div>
		<div class="progress progress-desktop loaded" style="width: {roomCount}*5/100*100%;">
			<div class="progress-bar" role="progressbar" style="width: (1-{roomSum}*5/100)*100%;"></div>
		</div>
	</li>
	<!-- 展示房间内的人 -->
	<li class="members"></li>
</ul>
</script>

<script>
function display_alert(){
	alert("功能待开发!!!")
}
function formatRooms(dta, tmpl) {  
    var format = {  
        name: function(x) {  
            return x ; 
        }  
    };  
    return tmpl.replace(/{(\w+)}/g, function(m1, m2) {  
        if (!m2)  
            return "";  
        return (format && format[m2]) ? format[m2](dta[m2]) : dta[m2];  
    });  
}  
function queryRoom(){
	$.ajax({
	    url: "RoomControllerServlet",  
	    type: "GET",  
	    data: $('#queryRoom').serialize(),   
	    dataType: "json",  
	    success: function(dta) {  
	        if (!dta || !dta.rows || dta.rows.length <= 0) {  
	            return;  
	        }  
	        //获取模板上的HTML  
	        var html = $('script[type="text/room"]').html();  
	        //定义一个数组，用来接收格式化合的数据  
	        var arr = [];  
	        //对数据进行遍历  
	        $.each(dta.rows, function(i, o) {  
	            //这里取到o就是上面rows数组中的值, formatTemplate是最开始定义的方法.  
	            arr.push(formatRooms(o, html));  
	        });  
	        //好了，最后把数组化成字符串，并添加到table中去。  
	        $('.rooms-wrap').append(arr.join(''));  
	        //走完这一步其实就完成了，不会吧，这么简单，不错，就是这么简单!! 不信就自己动手去试试!
	    }  
	});
}
function addIcon() {
	var icon = "<%=session.getAttribute("icon")%>";
	$('#icon').addClass(icon);
}
</script>
<link rel="stylesheet" href="CSS/lounge-css.css" />
</head>

<body class="scheme" style="overflow-x: visible;">
	<div id="body">
		<div class="container">
			<div class="row">
				<!-- 右边栏 -->
				<div class="col-sm-4 col-sm-push-8">
					<!-- 头像，id，登出 -->
					<ul id="profile" class="lounge-profile">
						<li class="icon">
							<div id="icon"></div>
							<div class="name">${userName}</div>
							<div class="profile-links">
								<form action="RoomControllerServlet" method="get">
									<input id="logout" name="command" value="logout"
										style="display: none;">
									<input type="submit"
										class="btn btn-invert btn-link" value="登出">
								</form>
							</div>
						</li>

					</ul>
					<div class="sidebar-box note rooms-filter-wrap">
						<input type="search" class="form-control rooms-filter"
							id="rooms-filter" placeholder="搜索" onclick="display_alert()">
						<div class="checkbox">
							<label> <input class="toggle-members" type="checkbox"
								onclick="display_alert()" id="refresh"> 显示房间成员
							</label>
						</div>
					</div>
					<div class="sidebar-box not highlight">
						<ul>
							<li>一些公告</li>
							<li>此华丽的项目，应该能得到95分的好成绩吧！！！</li>
							<li>广告位招租</li>
						</ul>
					</div>
				</div>
				<!-- 房间列表 -->
				<div class="col-sm-8 col-sm-pull-4">
					<div class="wrap">
						<div class="lounge-top"></div>
						<div class="lounge-nav">
							<div class="create-room pull-left" id="create_room">
								<form action="RoomControllerServlet" method="get">
									<input id="createRoom" name="command" value="createRoom"
										style="display: none;"> <input type="submit"
										class="btn btn-default" value="创建房间">
								</form>
							</div>
							<div class="lounge-links pull-left" style="display: none;">
								<div class="lounge-counter on">
									<span class="rooms-count" id="rooms-count">0</span> rooms- 
									<span class="online-count" id="online-count">0</span> users
								</div>
							</div>
						</div>
						<div class="rooms-placeholder" id="rooms-placeholder">
							<div class="rooms-wrap" id="room-filter-support"></div>
						</div>
					</div>
				</div>
			</div>
			<!-- 底部信息 -->
			<div class="footer-row">
				<div class="col-sm-12">
					<div class="lounge-footer">
						<!-- other information -->
						<ul id="timesones" class="list-unstyled list-inline small">
							<li></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input id="queryRoom" name="command" value="queryRoom"
		style="display: none;">
	<script type="text/javascript">
		document.getElementById("refresh").onclick = queryRoom;
		addIcon();
		queryRoom();
	</script>
</body>
</html>
