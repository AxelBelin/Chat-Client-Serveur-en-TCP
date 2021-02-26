import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SimpleClient implements Runnable{
	private static Scanner sc = new Scanner(System.in) ; // Scanner for type the port of the server to connect
	private Socket sClient ; // Implements the client socket
	
	public SimpleClient(Socket sClient)
	{
		this.sClient = sClient ;
	}
	
	@Override
	public void run() // Method executed by every Thread of this class
	{
		try {
			PrintWriter output = new PrintWriter(sClient.getOutputStream(), true) ; // This BufferedReader set the output of the client socket. Every message to send are stored here.
			
			while(true)
			{
				System.out.println("Type your message or exit to disconnect $> \n") ;
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in)) ; // This BufferedReader set the input for the console. Every characters wrote by the user in the console are stored here.
				String message = input.readLine() ; // Copy of the line read by the BufferedReader in a String
				/* if(message.equals("exit"))
					break ; */
				output.println("new message $> " + message) ; // Write the inputed message in the client socket to send it to the server
			}
			} catch(IOException e) {
				e.printStackTrace() ;
			}	
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, Exception {
		// TODO Auto-generated method stub
		System.out.println("Please connect by typing the port used by the server or Type -1 to exit the program $> \n") ;
		int portServeur = sc.nextInt() ;
		if(portServeur < 49152 || portServeur > 65535 || portServeur == -1) // If the inputed port is not between 49152 and 65535 or the user wants to quit the program
		{
			System.out.println("Bye") ;
			System.exit(0) ; // exit the current process
		}
		
		Socket sClient = new Socket("127.0.0.1", portServeur) ; // Create the client Socket with the localhost IP and the port wrote by the user
		
		int thisPortClient = sClient.getPort() ; // return the port number to which this socket is connected.
		System.out.println("Client communicate with : IP-> " + sClient.getLocalAddress() + " | Port-> " + thisPortClient) ; // Display the information where the client Socket is sending messages (IP and Port)
		
		try {
			new Thread(new SimpleClient(sClient)).start() ; // Create and start a new Thread to send the inputed messages to the server by using the client socket
		} catch(Exception e) {
			e.printStackTrace() ;
		}
		
		// sClient.close() ;
		// System.exit(0) ;
		}
	}
