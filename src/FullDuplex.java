import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Scanner;

public class FullDuplex {
	private static Scanner sc = new Scanner(System.in) ; // Scanner for input the port of the server
	
	public static void main(String[] args) throws UnknownHostException, IOException, Exception {
		// TODO Auto-generated method stub
		LinkedList<String> recMessages = new LinkedList<String>() ; // LinkedList used to store all the received messages
		
		// Create the server Socket
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
		
		// Create a server Socket with the port wrote by the user
		ServerSocket ss = new ServerSocket(setPort) ;
		
		// Display the information where the Socket server is listening (IP and Port)
		int thisPort = ss.getLocalPort() ; // return the port on which this socket is listening
		System.out.println("Server Socket is listening on : IP-> " + ss.getInetAddress() + " | Port-> " + thisPort) ;
		
		// Create the client Socket with the localhost IP and the port wrote by the user
		Socket sClient = new Socket("127.0.0.1", thisPort) ;
		
		// Create and start a new Thread for the server
		try {
			new Thread(new TwoWayServer(ss.accept(), recMessages)).start() ; // This new Thread and the server socket accepts the connection request and send a Linkedlist for making the List of received messages for the object TwoWayClient
		} catch(IOException e)
		{
			e.printStackTrace() ;
		} finally { // Ending of the try/catch properly by closing the server socket
			ss.close() ;
		}
		
		// Display the information where the client Socket is communicating (IP and Port)
		int thisPortClient = sClient.getPort() ; // return the port number to which this socket is connected.
		System.out.println("Client communicate with : IP-> " + sClient.getLocalAddress() + " | Port-> " + thisPortClient) ;
		
		// Create and start a new Thread for the client
		try {
			new Thread(new TwoWayClient(sClient, recMessages)).start() ; // This new Thread use the socket Client and send a Linkedlist for making the List of received messages for the object TwoWayClient
		} catch(Exception e) {
			e.printStackTrace() ;
		}
	}
}
