package public_chat_room;

public class Room {
	private int id;
	private String roomName;
	private String roomMeb;
	private String roomMes;
	private int nowNum;
	private int maxNum;
	public Room(int id, String roomName, int count, int sum,String meb, String mes) {
		super();
		this.id = id;
		this.roomName = roomName;
		this.nowNum = count;
		this.maxNum = sum;
		this.roomMeb = meb;
		this.roomMes = mes;
	}
	public Room(String roomName, int count, int sum,String meb, String mes) {
		this.roomName = roomName;
		this.nowNum = count;
		this.maxNum = sum;
		this.roomMeb = meb;
		this.roomMes = mes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public int getnowNum() {
		return nowNum;
	}
	public void setnowNum(int count) {
		this.nowNum = count;
	}
	public String getRoomMeb() {
		return roomMeb;
	}
	public void setRoomMeb(String Meb) {
		this.roomMeb = Meb;
	}
	public String getRoomMes() {
		return roomMes;
	}
	public void setRoomMes(String Mes) {
		this.roomName = Mes;
	}
	public int getmaxNum() {
		return maxNum;
	}
	public void setmaxNum(int sum) {
		this.maxNum = sum;
	}
	
}
