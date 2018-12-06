package public_chat_room;

public class UserInfo {
	private String name, icon, language;

	public UserInfo(String name, String icon, String language) {
		this.name = name;
		this.icon = icon;
		this.language = language;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
}
