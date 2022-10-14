package core.server;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

import member.Member;
import message.chat.ChatRoom;
import message.chat.Message;
import message.chat.SystemMessage;

public class ChatService extends Service<ObjectInputStream, ObjectOutputStream> {
	private final Member me;
	private final String chatRoomName;
	private final ChatRoom chatRoom;
	public ChatService(ObjectInputStream is, ObjectOutputStream os, Object... args) throws IOException {
		super(is, os);
		this.chatRoomName = (String) args[0];
		this.chatRoom = Server.chatRoomMap.get(chatRoomName);
		this.me = (Member) args[1];
	}

	@Override
	public void request() {
		chatRoom.entrance(this);
		Server.threadPool.execute(() -> {
			try {
				while (true) {
					Message message = (Message) is.readObject();
					message.setChatRoom(chatRoom);
					message.push();
					System.out.println("[Server]" + message);
				}
			} catch (IOException e) {
				chatRoom.getChatServiceList().remove(this);
				new SystemMessage(chatRoom, me + "님이 퇴장하셨습니다. 남은 인원 수: " + chatRoom.size()).push();
			} catch (ClassNotFoundException cfe) {
				cfe.printStackTrace();
			}
		});
	}
	


	public Member getMe() {
		return me;
	}
	
	public ObjectOutputStream getOs() {
		return os;
	}
	
	public boolean equalsUser(String name) {
		return me.getName().equals(name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(chatRoomName, me);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatService other = (ChatService) obj;
		return Objects.equals(chatRoomName, other.chatRoomName) && Objects.equals(me, other.me);
	}
}
