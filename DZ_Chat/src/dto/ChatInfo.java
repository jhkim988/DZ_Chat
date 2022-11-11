package dto;

import lombok.Getter;

@Getter
public class ChatInfo {
    String command;
    String filePath;
    String roomName;

    public ChatInfo(String command, String filePath, String roomName) {
        this.command = command;
        this.filePath = filePath;
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return Transfer.toJSON(this).toString();
    }
}
