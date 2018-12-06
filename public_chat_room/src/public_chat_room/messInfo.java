package public_chat_room;

public class messInfo {
	private String message, userName, icon;

	public messInfo(String message, String userName, String icon) {
		super();
		this.message = message;
		this.userName = userName;
		this.icon = icon;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
