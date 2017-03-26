package business;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import data.Sprite;

public interface SpriteServerInterface extends Remote
{
	int getSize() throws RemoteException;
	
	List<Sprite> getSprites() throws RemoteException;
	
	void createSprite(int x, int y) throws RemoteException;
	
	 void moveSprites() throws RemoteException;
}