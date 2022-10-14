package core.server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import message.chat.ChatRoom;

public class Server {
	public static final Map<String, ChatRoom> chatRoomMap = Collections.synchronizedMap(new HashMap<>()); 
	static final ExecutorService threadPool = Executors.newFixedThreadPool(16);

	private static final int PORT_NUMBER = 50_001;
	private ServerSocket serverSocket;

	public void start() throws IOException {
		serverSocket = new ServerSocket(PORT_NUMBER);
		System.out.println("[서버] 시작");
		threadPool.execute(() -> {
			try {
				while (true) {
					Socket socket = serverSocket.accept();
					ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
					ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
					System.out.println("New Socket Accept");
					new MapperService(is, os).request();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void stop() throws IOException {
		serverSocket.close();
		threadPool.shutdown();
		System.out.println("[서버] 종료");
	}

	public static void main(String[] args) {
		// Mock
		for (int i = 0; i < 20; i++) {
			chatRoomMap.put("TEST ROOM" + i, new ChatRoom("TEST ROOM" + i));			
		}

		try {
			Server server = new Server();
			server.start();
			Scanner scanner = new Scanner(System.in);
			while (!"q".equals(scanner.nextLine().toLowerCase()))
				;
			scanner.close();
			server.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
