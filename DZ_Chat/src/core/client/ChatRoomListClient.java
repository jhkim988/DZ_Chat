package core.client;
import java.io.*;

import message.chat.Message;

public class ChatRoomListClient extends Client {

	@Override
	public void receive() throws IOException, ClassNotFoundException {
		Integer num = (Integer) is.readObject();
		for (int i = 1; i <= num; i++) {
			System.out.println(i + ": " + ((String) is.readObject()));
		}
	}

	@Override
	public void send(Object obj) throws IOException {
		
	}

	@Override
	public void run() {
		System.out.println("채팅방 목록");
		Client client = new ChatRoomListClient();
		try {
			client.connect();
			client.receive();	
			
		} catch (IOException e) {
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
