/* File name: ClientHandler.java
 * Author name: William Raymond
 * Date: 2017-03-03
 * Description: Handles individual Echo Client connections.
 */

/**
 * Contains classes related to the Echo Server.
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class handles individual Echo Client connections. It is responsible
 * for writing a response back to the client. This class implements Runnable.
 * @author William Raymond
 * @version 1.0
 */
public class ClientHandler implements Runnable
{
	/**
	 * Socket used to store connection information such as port number
	 * and Internet address.
	 */
	private Socket connection;
	/**
	 * Writes the message String object to the connection (Socket) OutputStream.
	 */
	private ObjectOutputStream output = null;
	/**
	 * Reads objects from the connection (Socket) InputStream.
	 */
	private ObjectInputStream input = null;
	/**
	 * String object containing the message from the client.
	 */
	private String message = "";
	/**
	 * Integer variable that keeps track of the message number.
	 */
	private int messageNum;
	
	/**
	 * Default constructor for the ClientHandler class. Accepts a Socket as
	 * a parameter and assigns it to the connection variable.
	 * @param connection: Socket containing connection information (such as
	 * port number).
	 */
	public ClientHandler(Socket connection)
	{
		this.connection = connection;
	}
	
	/**
	 * This method handles communication with the Echo Client. It reads the 
	 * Echo Client's message from the ObjectInputStream and writes a message
	 * back to the ObjectOutputStream. It then flushes the ObjectOutputStream.
	 * Loops infinitely unless the message is null.
	 */
	@Override
	public void run() 
	{
		try
		{
			// get the InputStream from the connection Socket
			input = new ObjectInputStream(connection.getInputStream());
			// get the OutputStream from the connection Socket
			output = new ObjectOutputStream(connection.getOutputStream());
			do
			{
				// read the message from the ObjectInputStream
				message = (String) input.readObject();
				// write message back to the ObjectOutputStream
				output.writeObject(messageNum ++ + " FromServer> " + message);
				// flush the ObjectOutputStream to force writing to destination
				output.flush();
			} while(message != null);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		finally // avoid resource leaks
		{
			try
			{
				if(input != null)
					input.close(); // close input if not null
			}
			catch(IOException ioException)
			{
				ioException.printStackTrace();
			}
			try
			{
				if(output != null)
					output.close(); // close output if not null
			}
			catch(IOException ioException)
			{	
				ioException.printStackTrace();
			}
			try
			{
				if(connection != null)
					connection.close(); // close connection if not null
			}
			catch(IOException ioException)
			{
				ioException.printStackTrace();
			}
		}
	}
}