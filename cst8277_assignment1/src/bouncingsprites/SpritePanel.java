/* File name: SpritePanel.java
 * Author name: Algonquin College
 * Modified By: William Raymond
 * Date: 2017-01-31
 * Description: Models the JPanel containing the moving sprites
 */

/**
 * bouncingsprites is a group of classes used to create the bouncing sprite animation.
 */
package bouncingsprites;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * This class consists of methods that draw manage the addition of sprites in the panel.
 * This class also contains code to draw a circle in the center of the panel.
 * @author Algonquin College
 * @author William Raymond
 * @since 1.0
 */
@SuppressWarnings("serial")
public class SpritePanel extends JPanel
{	
	/**
	 * Variable storing the x coordinate used to draw the centered circle.
	 */
	private int circleX;
	/**
	 * Variable storing the y coordinate used to draw the centered circle.
	 */
	private int circleY;
	/**
	 * Variable used to keep a count of sprites in the centered circle's boundaries.
	 */
	private int spritesInCircle = 0;
	/**
	 * ArrayList used to store references to all sprite objects created.
	 */
	private ArrayList<Sprite> sprites = new ArrayList<>();
	
	/**
	 * Default constructor of the SpritePanel class. Calls addMouseListener() and passes 
	 * a new Mouse object as a parameter.
	 */
	public SpritePanel()
	{
		addMouseListener(new Mouse());
	}
	
	/**
	 * Creates a new Sprite object. Creates a new Thread and passes the new Sprite object as a parameter.
	 * Adds the new sprite object to the sprites ArrayList.
	 * @param event
	 */
	private void newSprite(MouseEvent event)
	{
		Sprite sprite = new Sprite(this);
		new Thread(sprite).start();
		sprites.add(sprite);
		System.out.println("New ball created");
	}
	
	/**
	 * Loops infinitely. Calls the repaint() method repeatedly.
	 */
	public void animate()
	{
		while(true)
		{
			repaint();
		}
	}
	
	/**
	 * Called when a sprite has exited the circle. Checks if there are at least 2 other sprites in the circle.
	 * Causes the Thread to wait if this is not true. Decrements the spritesInCircle variable if the sprite
	 * successfully exits. Notifies all threads before completing.
	 * @throws InterruptedException
	 */
	public synchronized void produce() throws InterruptedException
	{
		while(spritesInCircle <= 2)
		{
			wait();
		}
		spritesInCircle--;
		notifyAll();
	}
	
	/**
	 * Called when a sprite has entered the circle. Checks if there are less than 4 other sprites in the circle.
	 * Causes the Thread to wait if this is not true. Increments the spritesInCircle variable if the sprite
	 * successfully enters. Notifies all threads before completing.
	 * @throws InterruptedException
	 */
	public synchronized void consume() throws InterruptedException
	{
		while(spritesInCircle == 4)
		{
			wait();
		}
		spritesInCircle++;
		notifyAll();
	}
	
	/**
	 * Nested class within the SpritePanel class. Extends the MouseAdapter class. Overrides the mousePressed()
	 * method so that it calls the newSprite() method.
	 * @author Algonquin College
	 *
	 */
	private class Mouse extends MouseAdapter
	{
		@Override
		public void mousePressed(final MouseEvent event)
		{
			newSprite(event);
		}
	}
	
	/**
	 * Calls the UI delegate's paint method, if the UI delegate is non-null. Sets values for the x and y
	 * coordinates of the centered circle. Draws the centered circle in the panel. Draws contents of the sprites
	 * ArrayList on the panel.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		this.circleX = (this.getWidth() - 300) / 2;
		this.circleY = (this.getHeight() - 300) / 2;
		
		g.drawOval(circleX, circleY, 300, 300);
		
		for(Sprite sprite : sprites)
		{
			sprite.draw(g);
		}
	}
}