<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>创建房间-PCR聊天室</title>
<link rel="stylesheet" type="text/css" href="CSS/create_a_room.css">
</head>
<body>
	<div id="bg">
		<div id="Application">
			<form action="CreateRoomServlet" method="POST">
				<div>
					<label for="rm"><b>房间名称<br></b></label> <input type="text"
						name="room_name" id="rm" class="Input"><br>
				</div>
				<br>

				<div>
					<label for="rd"><b>房间描述<br></b></label> <input type="text"
						name="room_describe" id="rd" class="Input"><br>
				</div>
				<br>
				<div>
					<label for="pn" class='cy'><b>成员人数<br></b></label> <input
						type="number" name="people_num" id="pn" class="Input"><br>
				</div>
				<br>
				<div>
					<label for="Language"><b>房间语言<br></b></label> <select
						id="Language">
						<option value="中文" class="slt">中文(简体)</option>
						<option value="英文" class="slt">English</option>
					</select>
				</div>

				<div>
					<br> <input type="checkbox" name="attr" value="音乐房">音乐房<br>
					<input type="checkbox" name="attr" value="书房">书房<br> <input
						type="checkbox" name="attr" value="隐藏房间">隐藏房间<br>
				</div>
				<input type="submit" name="Submit" value="提交" id="sbt"
					style="cursor: pointer;"><br>
			</form>
		</div>
	</div>
</body>
</html>