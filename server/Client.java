package server;
import java.net.*;
import java.io.*;

public class Client {
	private static Client instance = null;
	private static Socket server = null;
	private static DataInputStream in = null;
	private static DataOutputStream out =null;
	
	public static Client getInstance() throws IOException{
		if(instance == null)
			instance = new Client();
		return instance;
	}
	
	private Client() throws IOException{
		server = new Socket("41.236.137.50",25565);
		in = new DataInputStream(server.getInputStream());
		out = new DataOutputStream(server.getOutputStream());
	}
	
	protected void finalize() {
		try {
			in.close();out.close();
			server.close();
			instance = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void reconnect() {
		try {
			instance = new Client();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String send(String s) {
		try {
			out.writeUTF(s);
			return in.readUTF();
		} 
		catch (IOException e) {
			e.printStackTrace();
			reconnect();
			return "Request failed, please retry,";
		}
	}
}
