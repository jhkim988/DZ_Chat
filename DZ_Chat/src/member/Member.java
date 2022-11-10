package member;

import java.util.Objects;

import org.json.JSONObject;

public class Member {
	private String userId;
	private String password;
	private String name;
	private String birth;
	private JSONObject json = new JSONObject();

	public Member(String userId, String password, String name, String birth) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.birth = birth;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getBirth() {
		return birth;
	}

	public boolean validatePw(String pw) {
		return password.equals(pw);
	}
	
	public String nickname() {
		return name + "( " + userId + " )";
	}
	
	public JSONObject getJSON() {
		json.put("userId", userId);
		json.put("password", password);
		json.put("name", name);
		json.put("birth", birth);
		return json;
	}
	
	public static Member parseJSON(JSONObject json) {
		return new Member(
				json.getString("userId")
				, json.getString("password")
				, json.getString("name")
				, json.getString("birth")
				);			
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		return Objects.equals(userId, other.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

	@Override
	public String toString() {
		return userId + "," + password + "," + name + "," + birth;
	}
}