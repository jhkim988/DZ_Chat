package client.member;

import static client.Main.*;

import java.io.IOException;

import org.json.JSONObject;

import client.Client;

public class UpdatePWClient extends Client {
	private final String validatePW;
	private final String newPW;
	private JSONObject json = new JSONObject();
	
	public UpdatePWClient(String validatePW, String newPW) {
		this.validatePW = validatePW;
		this.newPW = newPW;
	}

	@Override
	public JSONObject run() {
		try {
			if (!validatePW.equals(getMe().getPassword())) {
				return new JSONObject().put("success", false);
			}
			json.put("member", getMe().getJSON());
			json.put("validatePW", validatePW);
			json.put("newPW", newPW);
			connect("member.UpdatePWService");
			send(json);
			JSONObject response = receive();
			unconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
