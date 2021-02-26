import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Scanner;

public class Robot9kTCPClient implements Runnable {
	private Socket sClient ; // Implements the client socket
	private LinkedList<String> recMessages = new LinkedList<String>() ; // LinkedList used to store all the received messages
	private String numClient ; // The id of the client (String because we need to use the readLine() method to read the messages)
    private static int idClient = 0 ; // the number of client (counter)
    private static Object verrou  = new Object() ; // A Thread Lock
	private static Scanner sc = new Scanner(System.in) ; // Scanner for input the port of the server
    
    public Robot9kTCPClient(Socket sClient, LinkedList<String> recMessages) // class constructor with a socket and a Message List
    {
        this.sClient = sClient ;
        this.recMessages = recMessages ;
        idClient++ ; // Increment the number of client each time an object of this type is created
        this.numClient = Integer.toString(idClient) ; // Convert this id into String with the toString() method
    }
    
    @Override
    public void run()  // Method executed by every Thread of this class
    {
    	try {
            PrintWriter output = new PrintWriter(sClient.getOutputStream(), true) ; // This BufferedReader set the output of the client socket. Every message to send are stored here.
            output.println(this.numClient) ; // Write the id of the client in the socket
            
            while (true) {
            	System.out.println("Type a message or exit to disconnect $> ") ;
            	BufferedReader input = new BufferedReader(new InputStreamReader(System.in)) ; // we need the console input for the client
                String message = input.readLine() ; // Copy of the line read by the BufferedReader in a String
                
               
                // If this chain is in the LinkedList or the client wrote exit to leave the program
                if(message == null || message.equals("exit") || this.recMessages.contains(message))
                {
                	output.println(message) ; // Write this message in the client socket
                	System.out.println("Client with num " + this.numClient + " disconnected") ;  // Display the id of the client disconnected. The client got disconnect because he wrote more than one time a word in the chat
                    break ; // exit the loop
                }
                
                synchronized(verrou) { // Put the Lock on the following instruction to protect the write access to the received message LinkedList.
                	this.recMessages.add(message) ; // Its very important because the List could be modified by 2 Threads at the same time.
                    output.println(message) ; // Also lock the message to avoid automatic repetition and to disconnect a client by accident
                }
            }
        } catch (IOException e) {
            e.printStackTrace() ;
        }
    }
    
	public static void main(String[] args) throws UnknownHostException, IOException, Exception, InterruptedException {
		// TODO Auto-generated method stub
		LinkedList<String> recMessages = new LinkedList<String>() ; // Declaration of the LinkedList used to store all the received messages
		
		System.out.println("Please connect by typing the port used by the server or Type -1 to exit the program $> \n") ;
		int portServeur = sc.nextInt() ;
		if(portServeur < 49152 || portServeur > 65535 || portServeur == -1)
		{
			System.out.println("Bye") ;
			System.exit(0) ; // exit the current process
		}
				
		// Create the client Socket with the localhost IP and the port wrote by the user
		Socket sClient = new Socket("127.0.0.1", portServeur) ;
				
		// Display the information where the client Socket is communicating (IP and Port)
		int thisPortClient = sClient.getPort() ; // return the port number to which this socket is connected.
		System.out.println("Client communicate with : IP-> " + sClient.getLocalAddress() + " | Port-> " + thisPortClient) ;
		
		// Create and start Threads for the client
        String reponse ;
        
        // With do the following instructions must be treat a minimum of one time
        do{
            Thread threadsClients = new Thread(new Robot9kTCPClient(sClient, recMessages)) ; // This new Thread use the socket Client and send a Linkedlist for making the List of received messages for the object Robot9kTCPClient
            threadsClients.start() ; // Start this Thread a minimum of one time with no maximum
            threadsClients.join() ; // We must wait that Threads started by the user finish before asking a the following question
            Scanner sc = new Scanner(System.in) ;
            System.out.println("Do you want to connect a new client ? \n") ; // This question is asked when a client went just disconnected
    		System.out.println("y : yes | n : no $> ") ;
    		reponse = sc.nextLine() ;
    		// while ((!reponse.equals("y") && !reponse.equals("n")))
    		if ((!reponse.equals("y") && !reponse.equals("n"))) // If the answer is wrong
    		{
                do {
    			System.out.println("Error, Bad usage") ;
                System.out.println("Usage = y : yes | n : no $> ") ;
                reponse = sc.nextLine() ; // Answer again to the question...
    			} while((!reponse.equals("y") && !reponse.equals("n"))) ; //...while the answer is not the good format
    		}
    		if(reponse.equals("n")) // If the answer is no...
            	break ; // ...break the loop and exit the program
        } while(reponse.equals("y")) ; // while the answer is yes
    }
}
