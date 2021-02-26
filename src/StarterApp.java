import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class StarterApp {
	private SimpleServer.Responder serv ; // Implements a server ( a simple Server.Responder object)
	private SimpleClient client ; // Implements a client (a Simple Client object)
	private static Scanner sc = new Scanner(System.in) ; // Scanner for input the port of the server
	
	public StarterApp(SimpleServer.Responder serv, SimpleClient client) // App constructor with a server and a client
	{
		this.serv = serv ;
		this.client = client ;
	}
	
	
	public static void main(String[] args) throws IOException, InterruptedException, Exception {
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
		
		// Server socket
		ServerSocket ss = new ServerSocket(setPort) ; // Create a server Socket with the port wrote by the user
		int thisPort = ss.getLocalPort() ; // return the port on which this socket is listening
		System.out.println("Server Socket is listening on : IP-> " + ss.getInetAddress() + " | Port-> " + thisPort) ; // Display the information where the Socket server is listening (IP and Port)
		
		// Client
		Socket sClient = new Socket("127.0.0.1", thisPort) ; // Create the client Socket with the localhost IP and the port wrote by the user
		
		SimpleServer.Responder serv = new SimpleServer.Responder(ss.accept()) ; // Implements a server object and accept the connection requests
		SimpleClient client = new SimpleClient(sClient) ; // Implements a client object
		
		StarterApp Appli = new StarterApp(serv, client) ; // App Object implements an application linking a serv and a client
		
		try {
			new Thread(Appli.serv).start() ; // Create and start a new Thread for the server 
		} finally {  // Ending of the try/catch properly by closing the server socket
			ss.close() ;
		}
		
		// Display the information where the client Socket is communicating (IP and Port)
		int thisPortClient = sClient.getPort() ;  // return the port number to which this socket is connected.
		System.out.println("Client communicate with : IP-> " + sClient.getLocalAddress() + " | Port-> " + thisPortClient) ;
		
		try {
			new Thread(Appli.client).start() ; // Create and start a new Thread for the client
		} catch(Exception e) {
			e.printStackTrace() ;
		}
	}
}
