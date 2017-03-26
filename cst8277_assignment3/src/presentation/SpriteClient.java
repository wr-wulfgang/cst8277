package presentation;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import business.SpriteServerInterface;
import data.Sprite;

public class SpriteClient extends JPanel
{
	private SpriteServerInterface server = null;
	private String hostname = "localhost";
	private int port = 8082;
	private JFrame frame;
	
	public SpriteClient() 
	{
		addMouseListener(new Mouse());
		frame = new JFrame("CST8277 Assignment 3");
		
		try
		{
			server = (SpriteServerInterface)
				Naming.lookup("rmi://" + hostname + ":" + port + "/SpriteService");
			
			frame.setSize(server.getSize(), server.getSize());
		}
		catch(MalformedURLException murle)
		{
			murle.printStackTrace();
		}
		catch(RemoteException re)
		{
			re.printStackTrace();
		}
		catch(NotBoundException nbe)
		{
			nbe.printStackTrace();
		}
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setVisible(true);
		frame.setResizable(false);	
	}
	
	public static void main(String[] args) 
	{
		SpriteClient client = new SpriteClient();
		client.animate();
	}
	
	public void animate()
	{
		while(true)
		{
			repaint();
			
			try
			{
				Thread.sleep(40);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void newSprite(MouseEvent event) throws RemoteException
	{
		int x = event.getX();
		int y = event.getY();
		
		server.createSprite(x, y);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		try
		{
			for(int i = 0; i < server.getSprites().size(); i++)
			{
				server.getSprites().get(i).draw(g);
			}
		}
		catch(RemoteException e)
		{
			e.printStackTrace();
		}
	}
	
	private class Mouse extends MouseAdapter
	{
		@Override
		public void mousePressed(final MouseEvent event)
		{
			try
			{
				newSprite(event);
			}
			catch(RemoteException e)
			{
				e.printStackTrace();
			}
		}
	}
}