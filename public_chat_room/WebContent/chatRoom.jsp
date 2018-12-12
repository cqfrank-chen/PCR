<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="CSS/chat-css.css" />
<title>PCR</title>
<script type="text/javascript" src="jquery-3.0.0.js"></script>
<script type="text/javascript">
	function setOn(id) {
		var input = document.getElementById(id)
		input.style.opacity = 1;
	}
	function setOut(id) {
		var input = document.getElementById(id);
		input.style.opacity = 0.5;
	}
	function postForm() {
		$.ajax({
			type : "GET",
			url : "chatController",
			data : $('#form').serialize()
		});
		$('#form').reset();
	}
	function queryMember() {
		$.ajax({
			type : "GET",
			url : "chatController",
			data : $('#queryMember').serialize(),
			datatype : "json",
			success : function(data) {
				$('#memberList').empty();
				var obj = JSON.parse(data);
				for (var i = 0; i < obj.length; i++) {
					$('#memberList').append('<li>' + obj[i].name + '</li>');
				}
			}
		});
	}
	function queryMessage() {
		$.ajax({
			type : "GET",
			url : "chatController",
			data : $('#queryMessage').serialize(),
			datatype : "json",
			success : function(data) {

				$('#contentList').empty();
				var obj = JSON.parse(data);
				for (var i = 0; i < obj.length; i++) {
					var dl = $('<dl></dl>');
					var dt = $('<dt></dt>');
					var dd = $('<dd></dd>');
					var dIcon = $('<div class=\"' + obj[i].icon + '\"></div>');
					var dName = $('<div><span>' + obj[i].name + '</span></div>');
					var dMessage = $('<div><p>' + obj[i].message + '</p></div>')

					dMessage.attr('class', "message");
					dd.attr('style', "margin-top: 10px;");
					dt.append(dIcon);
					dt.append(dName);
					dd.append(dMessage);

					dl.append(dt);
					dl.append(dd);
					$('#contentList').append(dl);
				}
			}
		});
	}
</script>
</head>


<body>
	<div class="zhuobu">
		<div class="tou">
			<form id="form" method="get" style="display: inline-block;">
				<input name="command" value="post" style="display: none;">
				<div>
					<textarea name="message" class="message" id="message"></textarea>
				</div>
				<div>
					<input type="button" value="POST!" class="post" id="post"
						onmouseover="setOn('post')" onmouseout="setOut('post')">
				</div>
			</form>
			<form action="chatController" method="get"
				style="display: inline-block;">
				<div>
					<input type="submit" name="command" value="leave" class="post"
						id="leave" onmouseover="setOn('leave')"
						onmouseout="setOut('leave')">
				</div>
			</form>
		</div>
		<div class="tail">
			<div class="memberList">
				<ul id="memberList" style="margin-left: 20px;">
				</ul>
			</div>
			<div class="contentList" id="contentList"></div>
		</div>
	</div>

	<input id="queryMember" name="command" value="queryMember"
		style="display: none;">
	<input id="queryMessage" name="command" value="queryMessage"
		style="display: none;">
	<input id="dropMember" name="command" value="leave"
		style="display: none;">
	<script type="text/javascript">
		queryMember();
		queryMessage();
		document.getElementById("post").onclick = postForm;
		//document.getElementById("leave").onclick = leave;
		document.getElementById("memberList").onclick = queryMember;
		document.getElementById("contentList").onclick = queryMessage;
		//window.setInterval(queryMember, 1000);
		//window.setInterval(queryMessage, 1000);
	</script>
</body>

</html>