import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
// import java.util.Scanner;

public class TwoWayClient implements Runnable {
	private Socket sClient ; // Implements the client socket
	private LinkedList<String> recMessages = new LinkedList<String>() ; // LinkedList used to store all the received messages
	private static Object verrou  = new Object() ; // A Thread Lock
	// private static Scanner sc = new Scanner(System.in) ;
	
	public TwoWayClient(Socket sClient, LinkedList<String> recMessages) // class constructor with a socket client and its Message List
	{
		this.sClient = sClient ;
		this.recMessages = recMessages ;
	}
	
	public LinkedList<String> getRecMessages() {
		return recMessages;
	}
	
	public void PrintRecMessages() // Procedure to print all the received messages in the console when the client is disconnecting
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
			PrintWriter output = new PrintWriter(sClient.getOutputStream(), true) ; // This BufferedReader set the output of the client socket. Every message to send are stored here.
			
			while(true)
			{
				System.out.println("Waiting for a new client message $> \n") ;
				BufferedReader inputClient = new BufferedReader(new InputStreamReader(System.in)) ; // This BufferedReader set the input for the console. Every characters wrote by the user in the console are stored here.
				String message = inputClient.readLine() ; // Copy of the line read by the BufferedReader in a String
				if (message == null || message.equals("exit")) // Works here
				{ 
					output.println(message) ;
	                System.out.println("Client disconnected") ;
	                break;  // Break the loop because the client wrote exit in order to disconnect of the server
	            }
				output.println("new message $> " + message) ; // Write the inputed message in the client socket to send it to the server
					try {
						input = new BufferedReader(new InputStreamReader(sClient.getInputStream())) ; // This BufferedReader set the input of the client. Every characters wrote by the user in the client socket are stored here.
					} catch (IOException e) {
						e.printStackTrace() ;
					}
				try {
					message = input.readLine() ; // Update the message with the next line wrote by the user
					if (message == null || message.equals("exit"))
					{
						System.out.println("Client disconnected") ;
						System.out.println("Received messages : ") ;
						this.PrintRecMessages() ; // Before disconnect the client, display the procedure to print all the received messages in the console
						break ; // Break the loop because the client wrote exit in order to disconnect of the server
					} else {
						String serverMessage = message ; // Copy of the stored message in an other String for display and store the server Message in the LinkedList
						System.out.println("New Server message $> " + serverMessage) ; // Display on the console the newest message sent by the server to the client
						serverMessage = "Serveur $> " + serverMessage ; // Update the server Message with this chain. This chain will be directly store in the LinkedList
						
						synchronized(verrou) { // Put the Lock on the following instruction to protect the write access to the received message LinkedList.
							this.recMessages.add(serverMessage) ; // Its very important because the List could be modified by 2 Threads at the same time.
						}
					}
				} catch(IOException e)
				{
					e.printStackTrace() ;
				}
			}
		} catch(IOException e)
		{
			e.printStackTrace() ;
		}
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, Exception {
		// TODO Auto-generated method stub
		
		/*
		 *  
		LinkedList<String> recMessages = new LinkedList<String>() ;
		System.out.println("Please connect by typing the port used by the server or Type -1 to exit the program $> \n") ;
		int portServeur = sc.nextInt() ;
		if(portServeur < 49152 || portServeur > 65535 || portServeur == -1)
		{
			System.out.println("Bye") ;
			System.exit(0) ;
		}
		
		Socket sClient = new Socket("127.0.0.1", portServeur) ;
		
		int thisPortClient = sClient.getPort() ;
		System.out.println("Client communicate with : IP-> " + sClient.getLocalAddress() + " | Port-> " + thisPortClient) ;
		
		try {
			new Thread(new TwoWayClient(sClient, recMessages)).start() ;
		} catch(Exception e) {
			e.printStackTrace() ;
		} */
	}
}
