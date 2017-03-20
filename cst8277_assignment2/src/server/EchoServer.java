/* File name: EchoServer.java
 * Author name: Todd Kelly
 * Modified By: William Raymond
 * Date: 2017-03-03
 * Description: Defines an Echo Server and accepts client
 * connections.
 */

/**
 * Contains classes related to the Echo Server.
 */
package server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.ServerSocket;

/**
 * This class defines an Echo Server and consists of methods that
 * run the server and accept client connections.
 * @author Todd Kelly
 * @author William Raymond
 * @version 1.0
 */
public class EchoServer 
{
	/**
	 * Socket used to store connection information such as port number
	 * and Internet address.
	 */
	private Socket connection;
	/**
	 * ServerSocket that listens for client connection requests and accepts
	 * client connections.
	 */
	private ServerSocket server;
	/**
	 * Integer variable containing the port number of the Echo Server.
	 */
	private int portNum;
	/**
	 * Static final Integer variable containing the Echo Server's default
	 * port number. Used if the port number argument is not specified when
	 * executing.
	 */
	public static final int DEFAULT_PORT = 8081;
	
	/**
	 * A thread executor that manages threads and their life cycles while the
	 * server is running.
	 */
	public static ExecutorService threadExecutor = Executors.newCachedThreadPool();
	
	/**
	 * Default constructor for the EchoServer class. Accepts the server port number
	 * as a parameter and assigns it value to the portNum variable.
	 * @param portNum: The Echo Server's port number.
	 */
	public EchoServer(int portNum)
	{
		this.portNum = portNum;
	}
	
	/**
	 * This method handles new client connections by creating a new ClientHandler
	 * objects with the connection parameter and passing them to the thread executor.
	 * @param connection: Socket containing connection information (such as port number
	 * and Internet address).
	 */
	public void handleClient(final Socket connection)
	{
		// create a new ClientHandler using the connection Socket
		ClientHandler handler = new ClientHandler(connection);
		// use threadExecutor to run new ClientHandler on its own thread
		threadExecutor.execute(handler);
	}
	
	/**
	 * This method runs the Echo Server. Is listens for new client connections and
	 * accepts client connections. It then calls the handleClient() method, passing
	 * the new connection as a parameter.
	 */
	public void runServer()
	{
		try
		{
			server = new ServerSocket(portNum);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Echo Server Started...");
		while(true)
		{
			try
			{
				// accept new client connection
				connection = server.accept();
				// pass the client connection to handleClient()
				handleClient(connection);
			}
			catch(IOException exception)
			{
				exception.printStackTrace();
			}
		}
	}
	
	/**
	 * Calls the runServer() method using specified arguments or the default
	 * port number value.
	 * @param args
	 */
	public static void main(String[] args)
	{
		if(args.length > 0)
			(new EchoServer(Integer.parseInt(args[0]))).runServer();
		else
			(new EchoServer(DEFAULT_PORT)).runServer();
	}
}