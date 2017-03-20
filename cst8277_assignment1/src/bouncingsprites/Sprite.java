/* File name: Sprite.java
 * Author name: Algonquin College
 * Modified By: William Raymond
 * Date: 2017-01-31
 * Description: Models a single Sprite
 */

/**
 * bouncingsprites is a group of classes used to create the bouncing sprite animation.
 */
package bouncingsprites;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * This class consists of methods that make up the behavior of a sprite. This class
 * imlements Runnable.
 * @author Algonquin College
 * @author William Raymond
 * @since 1.0
 */
public class Sprite implements Runnable
{
	/**
	 * Variable storing a reference to a Random object.
	 */
	public final static Random random = new Random();
	/**
	 * Variable storing a reference to the color blue.
	 */
	private Color color = Color.BLUE;
	/**
	 * Reference SpritePanel variable.
	 */
	SpritePanel panel;
	
	/**
	 * Static integer variable storing the size (diameter) of the sprite.
	 */
	final static int SIZE = 10;
	/**
	 * Static integer variable storing the maximum speed of the sprite.
	 */
	final static int MAX_SPEED = 5;
	
	/**
	 * Variable storing the x coordinate of the sprite.
	 */
	private int x;
	/**
	 * Variable storing the y coordinate of the sprite.
	 */
	private int y;
	/**
	 * Variable storing the previous x coordinate of the sprite.
	 */
	private int lastX = -1;
	/**
	 * Variable storing the previous y coordinate of the sprite.
	 */
	private int lastY = -1;
	
	/**
	 * Variable storing the horizontal speed of the sprite.
	 */
	private int dx;
	/**
	 * Variable storing the vertical speed of the sprite.
	 */
	private int dy;
	
	/**
	 * Variable storing the radius of the sprite.
	 */
	private int spriteRadius = 5;
	/**
	 * variable storing the radius of the centered circle in the panel.
	 */
	private int circleRadius = 150;
	
	/**
	 * Default constructor of the Sprite class. Assigns the panel passed as a parameter to the
	 * panel reference variable. Generates random values for x and y using random. Generates
	 * random values for dx and dy using random.
	 * @param panel
	 */
	public Sprite(SpritePanel panel)
	{
		this.panel = panel;
		
		x = random.nextInt(panel.getWidth());
		y = random.nextInt(panel.getHeight());
		
		dx = random.nextInt(2 * MAX_SPEED) - MAX_SPEED;
		dy = random.nextInt(2 * MAX_SPEED) - MAX_SPEED;
	}
	
	/**
	 * Sets the color of the sprite and draws the sprite using the fillOval() method.
	 * @param g
	 */
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillOval(x, y, SIZE, SIZE);
	}
	
	/**
	 * Manages movement of the sprite. Changes direction of the sprite if the sprite 
	 * reaches the end of the panel.
	 */
	public void move()
	{
		// check for bounce and make the ball bounce if necessary
		if(x < 0 && dx < 0)
		{
			// bounce off left wall
			x = 0;
			dx = -dx;
		}
		if(y < 0 && dy < 0)
		{
			// bounce off the top wall
			y = 0;
			dy = -dy;
		}
		if(x > panel.getWidth() - SIZE && dx > 0)
		{
			// bounce off right wall
			x = panel.getWidth() - SIZE;
			dx = -dx;
		}
		if(y > panel.getHeight() - SIZE && dy > 0)
		{
			// bounce off bottom wall
			y = panel.getHeight() - SIZE;
			dy = -dy;
		}
		
		// make the ball move
		x += dx;
		y += dy;
	}
	
	/*
	 * The spriteIsInCircle() method is based on code from the following sources:
	 * 
	 * Distance formula:
	 * D. Jamieson, "When Worlds Collide: Simulating Circle-Circle Collisions," 27 September 2012. 
	 * [Online]. Available: https://gamedevelopment.tutsplus.com/tutorials/when-worlds-collide-
	 * simulating-circle-circle-collisions--gamedev-769. [Accessed 23 01 2017].
	 * 
	 * If statement condition:
	 * Battleroid and Dampsquid, "Finding if a circle is inside another circle," StackOverflow, 28 February 2012. 
	 * [Online]. Available: http://stackoverflow.com/questions/9486520/finding-if-a-circle-is-inside-
	 * another-circle. [Accessed 23 01 2017].
	 */
	/**
	 * This method checks if the sprite is currently in the circle by calculating the distance between the 
	 * centered (x, y) coordinates of the sprite and centered (x, y) coordinates of the centered circle. If
	 * the distance between the two centers is less than the difference between the sprite's radius and the
	 * centered circle's radius.
	 * @param x
	 * @param y
	 * @return true or false
	 */
	private boolean spriteIsInCircle(int x, int y)
	{
		int centeredCircleX = panel.getWidth() / 2; // centered x coordinate of the centered circle
		int centeredCircleY = panel.getHeight() / 2; // centered y coordinate of the centered circle
		
		// calculate distance between 2 centers
		double distance = Math.sqrt(((x - centeredCircleX) * (x - centeredCircleX)) + ((y - centeredCircleY) * (y - centeredCircleY)));
		
		// check there is a collision between 2 circles
		if(distance <= Math.abs(spriteRadius - circleRadius))
			return true;
		else
			return false;
	}
	
	/**
	 * This method checks if the sprite spawns in the circle by comparing the previous and current (x, y) coordinates of
	 * the sprite. If previous values of x and y are both -1, and the sprite is currently in the circle, the method returns true.
	 * Otherwise, the method returns false.
	 * @return
	 */
	private boolean spriteSpawnedInCircle()
	{
		// check if last coordinates are -1 indicating that the ball hadn't spawned yet
		if(this.lastX == -1 && this.lastY == -1)
		{
			if(spriteIsInCircle(this.x, this.y))
				return true;
			else
				return false;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * This method checks if the sprite is entering the circle by comparing the previous and current (x, y) coordinates of
	 * the sprite. If the previous (x, y) coordinates were not in the circle, and the current (x, y) coordinates are in the
	 * circle, the method returns true. Otherwise, the method returns false.
	 * @return
	 */
	private boolean spriteIsEntering()
	{
		int centeredSpriteX = this.x + spriteRadius;
		int centeredSpriteY = this.y + spriteRadius;
		int centeredLastSpriteX = this.lastX + spriteRadius;
		int centeredLastSpriteY = this.lastY + spriteRadius;
		
		if(!spriteIsInCircle(centeredLastSpriteX, centeredLastSpriteY)) // check if sprite wasn't in circle last frame
		{
			if(spriteIsInCircle(centeredSpriteX, centeredSpriteY)) // check if sprite is in circle this frame
				return true;
			else
				return false;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * This method checks if the sprite is exiting the circle by comparing the previous and current (x, y) coordinates of
	 * the sprite. If the previous (x, y) coordinates were in the circle, and the current (x, y) coordinates are outside of
	 * the circle, the method returns true. Otherwise, the method returns false.
	 * @return
	 */
	private boolean spriteIsExiting()
	{
		int centeredSpriteX = this.x + spriteRadius;
		int centeredSpriteY = this.y + spriteRadius;
		int centeredLastSpriteX = this.lastX + spriteRadius;
		int centeredLastSpriteY = this.lastY + spriteRadius;
		
		if(spriteIsInCircle(centeredLastSpriteX, centeredLastSpriteY)) // check if sprite is in circle last frame
		{
			if(!spriteIsInCircle(centeredSpriteX, centeredSpriteY)) // check if sprite is outside circle this frame
				return true;
			else
				return false;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Override of the run() method from the Runnable interface. The method checks if the sprite is
	 * entering or leaving the centered circle. If the sprite is entering the circle, the panel's
	 * consume() method is called. If the sprite is exiting the circle, the panel's produce() method
	 * is called. This method never returns due to an infinite while loop.
	 */
	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				if(spriteSpawnedInCircle())
					panel.consume(); // increments the number of sprites in circle if sprite spawns in circle
				
				this.lastX = this.x; // saves last x coordinate before moving
				this.lastY = this.y; // saves last y coordinate before moving
				
				move();
				
				if(spriteIsEntering())
					panel.consume();
				if(spriteIsExiting())
					panel.produce();
				
				Thread.sleep(40);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}
}