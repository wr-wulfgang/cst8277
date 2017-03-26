package business;

import java.awt.Color;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;

import data.Sprite;

public class SpriteServer implements SpriteServerInterface
{
	private SessionFactory factory;
	private int colorCounter = 0;
	private int size = 500;
	List<Sprite> sprites = new ArrayList<Sprite>();
	
	public SpriteServer()
	{
		new Thread(new SpriteMover()).start();
		
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure()
				.build();
		
		try
		{
			MetadataImplementor meta = (MetadataImplementor) new MetadataSources(registry)
					.addAnnotatedClass(Sprite.class).buildMetadata();
			
			factory = meta.buildSessionFactory();
		}
		catch(Exception e)
		{
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
	
	public static void main(String[] args)
	{
		int port = 8082;
		
		if(args.length > 0)
		{
			port = Integer.parseInt(args[0]);
		}
		
		try
		{
			SpriteServer server = new SpriteServer();
			LocateRegistry.createRegistry(port);
			System.out.println("Registry created.");
			UnicastRemoteObject.exportObject(server, 0);
			System.out.println("Exported.");
			Naming.rebind("//localhost:" + port + "/SpriteService", server);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public int getSize()
	{
		return this.size;
	}
	
	@Override
	public List<Sprite> getSprites()
	{
		return this.sprites;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public void createSprite(int x, int y)
	{
		Session session = factory.getCurrentSession();
		Sprite sprite = new Sprite();
		
		if(colorCounter >= 3)
		{
			colorCounter = 0;
		}
		
		try
		{
			session.beginTransaction();
			
			sprite.setX(x);
			sprite.setY(y);
			
			if(colorCounter == 0)
				sprite.setColor(Color.RED);
			else if(colorCounter == 1)
				sprite.setColor(Color.BLUE);
			else if(colorCounter == 2)
				sprite.setColor(Color.GREEN);
			
			session.save(sprite);
			
			sprites = (ArrayList<Sprite>) session.createCriteria(Sprite.class).list();
			
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		sprites.add(sprite);
		
		colorCounter++;
	}
	
	@Override
	public void moveSprites()
	{
		Session session = factory.getCurrentSession();
		try
		{
			session.beginTransaction();
			for(Sprite sprite : sprites)
			{
				sprite.move();
				session.update(sprite);
			}
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	
	private class SpriteMover implements Runnable
	{
		@Override
		public void run()
		{
			while(true)
			{
				moveSprites();
				
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
	}
}