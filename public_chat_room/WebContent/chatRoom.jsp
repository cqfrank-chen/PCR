<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<%
	request.getSession();
%>
<%
	session.setAttribute("uId", "3");
	session.setAttribute("userName", "111");
	session.setAttribute("messageTable", "message_test");
	session.setAttribute("memberTable", "member_test");
%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="CSS/chat-css.css" />
<title>PCR</title>
<script type="text/javascript" src="jquery-3.0.0.js"></script>
<script type="text/javascript">
	function setOn() {
		var input = document.getElementById("post")
		input.style.opacity = 1;
	}
	function setOut() {
		var input = document.getElementById("post");
		input.style.opacity = 0.5;
	}
	function postForm() {
		$.ajax({
			type : "POST",
			url : "chatController",
			data : $('#form').serialize()
		});
	}
	function queryMember() {
		$.ajax({
			type : "POST",
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
		$
				.ajax({
					type : "POST",
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
							var dName = $('<div><span>' + obj[i].name
									+ '</span></div>');
							var dMessage = $('<div><p>' + obj[i].message
									+ '</p></div>')

							dMessage.attr('class', "message");

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
			<form id="form" method="post">
				<input name="command" value="post" style="display: none;">
				<div>
					<textarea name="message" class="message" id="message"></textarea>
				</div>
				<div>
					<input type="submit" value="POST!" class="post" id="post">
				</div>
			</form>
		</div>
		<div class="tail">
			<div class="memberList">
				<ul id="memberList" style="margin-left: 20px;">
					<li>sdf</li>
				</ul>
			</div>
			<div class="contentList" id="contentList"></div>
		</div>
	</div>

	<input id="queryMember" name="command" value="queryMember"
		style="display: none;">
	<input id="queryMessage" name="command" value="queryMessage"
		style="display: none;">
	<script type="text/javascript">
		queryMember();
		queryMessage();
		document.getElementById("post").onmouseover = setOn;
		document.getElementById("post").onmouseout = setOut;
		document.getElementById("post").onclick = postForm;
		document.getElementById("memberList").onclick = queryMember;
		document.getElementById("contentList").onclick = queryMessage;
		//window.setInterval(queryMember, 3000);
	</script>
</body>

</html>