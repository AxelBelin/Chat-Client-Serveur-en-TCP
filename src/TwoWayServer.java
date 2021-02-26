import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
// import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
// import java.util.Scanner;

public class TwoWayServer implements Runnable {
	private Socket sService ; // Implements the server socket
	private LinkedList<String> recMessages = new LinkedList<String>() ; // LinkedList used to store all the received messages
	private static Object verrou  = new Object() ; // A Thread Lock
	// private static Scanner sc = new Scanner(System.in) ;
	
	public TwoWayServer(Socket sService, LinkedList<String> recMessages) // class constructor with a socket and a Message List
	{
		this.sService = sService ;
		this.recMessages = recMessages ;
	}
	
	public void PrintRecMessages()  // Procedure to print all the received messages in the console when the client is disconnecting
	{
		for(int i = 0; i < recMessages.size(); i++)
		{
			System.out.println(this.recMessages.get(i)) ; // Print all the elements of the list
		}
	}
	
	@Override
	public void run() // Method executed by every Thread of this class
	{
		BufferedReader input = null ;
		try {
			PrintWriter output = new PrintWriter(sService.getOutputStream(), true) ; // This BufferedReader set the output of the server socket. Every message to send to the client are stored here.
			
			try {
				input = new BufferedReader(new InputStreamReader(sService.getInputStream())) ; // This BufferedReader set the server input. Every characters wrote in the server socket are stored here.
			} catch(IOException e)
			{
				e.printStackTrace() ;
			}
			
			while(true) {
				try {
					String message = input.readLine() ; // Copy of the line read by the BufferedReader in a String
					if (message == null || message.equals("exit")) // If the user wants to disconnect
					{
						System.out.println("Server disconnected") ;
						System.out.println("Received messages : ") ;
						this.PrintRecMessages() ; // Before disconnect the server, display the procedure to print all the received messages in the console
						break ; // Break the loop because the user wrote exit in order to disconnect
					} else {
						String clientMessage = message ; // Copy of the stored message in an other String for display and store the client Message in the LinkedList
						System.out.println("New Client message $> " + clientMessage) ; // Display on the console the newest message sent by the client to the server
						clientMessage = "Client $> " + clientMessage ; // Update the server Message with this chain. This chain will be directly store in the LinkedList
						
						synchronized(verrou) { // Put the Lock on the following instruction to protect the write access to the received message LinkedList.
							this.recMessages.add(clientMessage) ; // Its very important because the List could be modified by 2 Threads at the same time.
						}
					}
				} catch(IOException e)
				{
					e.printStackTrace() ;
				}
				
				System.out.println("Waiting for a new serveur message $> \n") ; // Display that its the server turn to write a message to answer the client
				BufferedReader inputServeur = new BufferedReader(new InputStreamReader(System.in)) ; // This BufferedReader set the input for the console. Every characters wrote by the user in the console are stored here.
				String message = inputServeur.readLine() ; // Copy of the line read by the BufferedReader in a String
				if (message == null || message.equals("exit")) // If the server wrote exit to disconnect 
				{ 
					output.println(message) ;
	                System.out.println("Server disconnected") ;
	                break ; // Break the loop because the server wrote exit in order to disconnect of the server
	            }
				output.println(message) ; // else print the last server message in the socket
				
				}
			} catch(IOException e) {
				e.printStackTrace() ;
		}
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, Exception {
		// TODO Auto-generated method stub
		
		/*
		 *  
		LinkedList<String> recMessages = new LinkedList<String>() ;
		System.out.println("Set the server port between 49152 and 65535 or Type -1 to exit the program $> \n") ;
		int setPort = sc.nextInt() ;
		if(setPort == -1)
		{
			System.out.println("Bye") ;
			System.exit(0) ;
		}
		
		if(setPort < 49152 || setPort > 65535)
		{
			setPort = 50015 ;
		}
		
		ServerSocket ss = new ServerSocket(setPort) ;
		
		int thisPort = ss.getLocalPort() ;
		System.out.println("Server Socket is listening on : IP-> " + ss.getInetAddress() + " | Port-> " + thisPort) ;
		
		try {
			new Thread(new TwoWayServer(ss.accept(), recMessages)).start() ;
		} catch(IOException e)
		{
			e.printStackTrace() ;
		} finally {
			ss.close() ;
		} */
	}
}
