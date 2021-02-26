import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Scanner;

public class Robot9kTCPServer implements Runnable {
	private Socket sService ; // Implements the server socket
	private LinkedList<String> recMessages = new LinkedList<String>() ; // LinkedList used to store all the received messages
	private static Object verrou  = new Object() ; // A Thread Lock

    public Robot9kTCPServer(Socket sService, LinkedList<String> recMessages) // class constructor with a socket and a Message List
    {
        this.sService = sService ;
        this.recMessages = recMessages ;
    }
    
    public void PrintRecMessages() // Procedure to print all the received messages in the console when the client is disconnecting
	{
		for(int i = 0; i < this.recMessages.size(); i++)
		{
			System.out.println(this.recMessages.get(i)) ; // Print all the elements of the list
		}
	}
    
    @Override
    public void run() // Method executed by every Thread of this class
    {
    	System.out.println("New connection : IP-> " + sService.getInetAddress() + " Port-> " + sService.getPort()) ;  // Display the information about a client connected to this server
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(sService.getInputStream())) ; // This BufferedReader set the server input. Every characters wrote in the server socket are stored here.
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            boolean inputNumClient = false ; // True if the id of the client was already transmitted before. Else false
            String numClient = input.readLine() ; // Getting the first information from a client (his id)
            while (true) {
                if(inputNumClient)
                { 
                	numClient = input.readLine() ; // Udpdate the String with the next line entered by the client
                }
            	System.out.println("Server is waiting for a new Client message $> \n") ;
                String message = input.readLine() ; // Copy of the line read by the BufferedReader in a String
                String messageClient = "New Client message : " + message ; // Create a String containing a client Message with this chain. This chain will be directly store in the LinkedList
                 
                // If this chain is in the LinkedList or the client wrote exit to leave the program
                if (this.recMessages.contains(messageClient) || message == null || message.equals("exit"))
                {
                    System.out.println("Client with num " + numClient + " disconnected") ; // Display the id of the client disconnected. The client got disconnect because he wrote more than one time a word in the chat
                    System.out.println("Received messages : ") ;
                    this.PrintRecMessages() ; // Before disconnect the client, display the procedure to print all the received messages in the console
                    Scanner sc = new Scanner(System.in) ;
                    System.out.println("Do you want to shutdown the server ? \n") ; // This question is asked when a client just disconnected
            		System.out.println("y : yes | n : no $> ") ;
                    String reponse = sc.nextLine() ;
                    // while ((!reponse.equals("y") && !reponse.equals("n")))
                    if((!reponse.equals("y") && !reponse.equals("n"))) // If the answer is wrong
                    {
                    	do { // with do the following instructions must be treat a minimum of one time
                            System.out.println("Error, Bad usage") ;
                            System.out.println("Usage = y : yes | n : no $> ") ;
                            reponse = sc.nextLine() ; // Answer again to the question...
                    	} while ((!reponse.equals("y") && !reponse.equals("n"))) ; //...while the answer is not the good format
                    }
                    if (reponse.equals("y")) // If answer = yes
                    	break ; // break the loop in order to shutdown the server
                    else { 
                    	inputNumClient = true ; // Change the boolean value because the id of the client is set
                        continue ;
                    }
                } else {
                	messageClient = "Client number " + numClient + " message : " + message ; // Update the chain message Client with its id and the last message transmitted
                    System.out.println(messageClient) ; // Display this chain
                    String messageClientLog = "New Client message : " + message ; // Create a new String with a new chain. This chain will be stored in the LinkedList
                    
                    synchronized(verrou) { // Put the Lock on the followings instructions to protect the write access to the received message LinkedList.
                    	this.recMessages.add(messageClientLog) ; // Its very important because the List could be modified by 2 Threads at the same time.
                    	inputNumClient = false ;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace() ;
        }
    }
    
	public static void main(String[] args) throws UnknownHostException, IOException, Exception {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in) ;
		LinkedList<String> recMessages = new LinkedList<String>() ; // Declaration of the LinkedList used to store all the received messages
		
		System.out.println("Set the server port between 49152 and 65535 or Type -1 to exit the program $> \n") ;
		int setPort = sc.nextInt() ;
		if(setPort == -1)
		{
			System.out.println("Bye") ;
			System.exit(0) ; // exit the current process
		}
		
		if(setPort < 49152 || setPort > 65535) // If the inputed port is not between 49152 and 65535
		{
			setPort = 50015 ;  // Set the default port server value 50015
		}
		
		// Create a server Socket with the port wrote by the user
		ServerSocket ss = new ServerSocket(setPort) ;
		
		// Display the information where the Socket server is listening (IP and Port)
		int thisPort = ss.getLocalPort() ; // return the port on which this socket is listening
		System.out.println("Server Socket is listening on : IP-> " + ss.getInetAddress() + " | Port-> " + thisPort) ;
		
		// Create and start a new Thread for the server
		try {
            new Thread(new Robot9kTCPServer(ss.accept(), recMessages)).start() ; // This new Thread and the server socket accepts the connection request and send a Linkedlist for making the List of received messages for the new object Robot9kTCPServer
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally { // Ending of the try/catch properly by closing the server socket
            ss.close() ;
            // sc.close() ;
        }
	}
}
