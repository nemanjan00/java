package top.nemanja.project;

import java.net.Socket;
import java.net.ServerSocket;
import java.lang.Runnable;
import java.io.IOException;
import java.lang.Thread;

import java.io.PrintWriter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.TreeMap;

class Main {
	public static void main(String args[]) {
		try {
			new Main();
		} catch (IOException e) {
		}
	}

	public Main() throws IOException {
		ServerSocket serverSocket = new ServerSocket(8080);

		while(true) {
			Socket socket = serverSocket.accept();

			Thread thread = new Thread(new ServerThread(socket));
			thread.run();
		}
	}
}

class ServerThread implements Runnable {
	Socket socket;

	TreeMap<String, String> arguments = new TreeMap<String, String>();

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run(){
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			String request = in.readLine();

			String[] requestParams = request.split(" ");

			if(requestParams.length != 3){
				socket.close();
				return;
			}

			if(!requestParams[2].equals("HTTP/1.1")){
				socket.close();
				return;
			}


			String line;

			while((line = in.readLine()) != null && !line.equals("")){
				System.out.println("<>" + line + "<>");

				String[] argument = line.split(":");

				this.arguments.put(argument[0].toLowerCase(), argument[1]);
			}

			//TODO: Verify request method
			//Error 501

			switch(requestParams[0]){
				case("GET"): 
					String response = "<h1>title<h1>";

					out.println("HTTP/1.1 200 OK");
					out.println("Content-Length: " + response.length() + "\n");
					out.println(response);

					break;
				default:
					socket.close();
			}
		} catch(IOException e){
		
		}
	}
}

