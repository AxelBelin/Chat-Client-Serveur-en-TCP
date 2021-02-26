import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SimpleServer {
	private static Scanner sc = new Scanner(System.in) ; // Scanner for input the port of the server
	
	static class Responder implements Runnable { // SubClass Responder
		private Socket socketServer; // Implements the socket of the server

		Responder(Socket socketServer) {
			this.socketServer = socketServer ;
		}

		@Override
		public void run() // Method executed by every Thread of this class
		{
			System.out.println("A new client is connected : IP-> " + socketServer.getInetAddress() + " Port-> " + socketServer.getPort()) ; // Display the information about a client connected to this server
			BufferedReader input = null ;
			try {
				input = new BufferedReader(new InputStreamReader(socketServer.getInputStream())) ; // This BufferedReader set the input of the server socket. Every characters wrote in the server socket are stored here.
			} catch (IOException e) {
				e.printStackTrace();
			}
			while(true) // Infinite loop
			{
				System.out.println("Serveur is waiting for new clients or messages.\n") ;
				try {
					String message = input.readLine() ; // Copy of the line read by the BufferedReader in a String message
					if (message == null || message.equals("exit")) // Doesnt work here.
					{
						System.out.println("Client disconnected") ;
						break ; // Should break the loop because the client wrote exit in order to disconnect of the server
					} else {
						System.out.println(message) ; // Display the client message in the server console 
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Set the server port between 49152 and 65535 or Type -1 to exit the program $> \n") ;
		int setPort = sc.nextInt() ;
		if(setPort == -1)
		{
			System.out.println("Bye") ;
			System.exit(0) ; // exit the current process
		}
		
		if(setPort < 49152 || setPort > 65535) // If the inputed port is not between 49152 and 65535
		{
			setPort = 50015 ; // Set the default port server value 50015
		}
		
		ServerSocket ss = new ServerSocket(setPort) ; // Creation of the server Socket with the port wrote by the user
		int thisPort = ss.getLocalPort() ; // return the port on which this socket is listening
		System.out.println("Server Socket is listening on : IP-> " + ss.getInetAddress() + " | Port-> " + thisPort) ; // Display the information where the Socket server is listening (IP and Port)
		try {
			(new Thread(new Responder(ss.accept()))).start() ; // Create and start a new Thread when a client is connecting and the server socket accept the connection request
		} catch(IOException e)
		{
			e.printStackTrace() ;
		} finally { // Ending of the try/catch properly by closing the server socket
			ss.close() ;
		}
		
		/* if(ss.isClosed())
		{
			System.exit(0) ;
		} */
	}
}