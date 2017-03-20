/* File name: EchoClient.java
 * Author name: Todd Kelly
 * Date: 2017-03-03
 * Description: Defines an Echo Client that is able to connect to
 * an echo server.
 */

/**
 * Containts classes related to the Echo Client.
 */
package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class defines an Echo Client and consists of methods that
 * run the client and connect it to the Echo Server.
 * @author Todd Kelly
 * @version 1.0
 */
public class EchoClient 
{
	/**
	 * Socket used to store connection information such as port number
	 * and Internet address.
	 */
	private Socket connection;
	/**
	 * Writes the message String object to the connection (Socket) OutputStream.
	 */
	private ObjectOutputStream output;
	/**
	 * Reads objects from the connection (Socket) InputStream.
	 */
	private ObjectInputStream input;
	/**
	 * String object containing the message from the client.
	 */
	private String message = "";
	/**
	 * String object containing the Echo Server's name
	 */
	private String serverName;
	/**
	 * String object containing the default Echo Server name (localhost).
	 * Used if the server name argument is not specified when executing.
	 */
	public static final String DEFAULT_SERVER_NAME = "localhost";
	/**
	 * Integer variable containing the port number of the Echo Server.
	 */
	private int portNum;
	/**
	 * BufferedReader used to read user input from the keyboard.
	 */
	BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	
	/**
	 * Default constructor for the EchoClient class. Accepts the server name and port number
	 * as parameter and assigns their values to the serverName and portNum variables.
	 * @param serverName: The Echo Server's name.
	 * @param portNum: The Echo Server's port number.
	 */
	public EchoClient(String serverName, int portNum)
	{
		this.serverName = serverName;
		this.portNum = portNum;
	}
	
	/**
	 * Calls the runClient() method using specified arguments or default server name 
	 * and port number values.
	 * @param args
	 */
	public static void main(String[] args)
	{
		switch(args.length)
		{
		case 2:
			(new EchoClient(args[0], Integer.parseInt(args[1]))).runClient();
			break;
		case 1:
			(new EchoClient(DEFAULT_SERVER_NAME, Integer.parseInt(args[0]))).runClient();
			break;
		default:
			(new EchoClient(DEFAULT_SERVER_NAME, server.EchoServer.DEFAULT_PORT)).runClient();
		}
	}
	
	/**
	 * Reads user input from the keyboard and saves it to the message String object.
	 * Writes the message String object to the ObjectOutputStream and then flushes
	 * the ObjectOutputStream. Reads the server's response from the ObjectInputStream
	 * and prints it to the screen. Loops infinitely unless message is null.
	 */
	public void runClient()
	{
		try
		{
			connection = new Socket(InetAddress.getByName(serverName), portNum);
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());
			System.out.println("To Quit, enter EOF (^Z on Windows); ^D on Linux/Mac)");
			do
			{
				System.out.print("Input> ");
				message = keyboard.readLine();
				if(message != null)
				{
					output.writeObject(message);
					output.flush();
					message = (String) input.readObject();
					System.out.println(message);
				}
			} while(message != null);
			input.close();
			output.close();
			connection.close();
		}
		catch(IOException ioException)
		{
			ioException.printStackTrace();
		}
		catch(ClassNotFoundException exception)
		{
			exception.printStackTrace();
		}
	}
}