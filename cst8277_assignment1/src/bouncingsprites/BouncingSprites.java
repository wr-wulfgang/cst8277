/* File name: BouncingSprites.java
 * Author name: Algonquin College
 * Modified by: William Raymond
 * Date: 2017-01-22
 * Description: Sets the size of the panel and starts the animation
 */

/**
 * bouncingsprites is a group of classes used to create the bouncing sprite animation.
 */
package bouncingsprites;

import javax.swing.JFrame;

/**
 * This class consists of methods that start the bouncing sprite animation.
 * @author Algonquin College
 * @since 1.0
 */
public class BouncingSprites
{
	/**
	 * JFrame containing the sprite panel.
	 */
	private JFrame frame;
	/**
	 * Panel in which the bouncing sprites animation takes place.
	 */
	private SpritePanel panel = new SpritePanel();
	
	/**
	 * Default constructor of the BouncingSprites class. Instantiates a new JFrame with a size of 500 x 500.
	 * Adds the sprite panel to the frame.
	 */
	public BouncingSprites()
	{
		frame = new JFrame("Bouncing Sprites 2017W");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setVisible(true);
	}
	
	/**
	 * Calls the panel's animate() method and never returns due to an infinite loop.
	 */
	public void start()
	{
		panel.animate(); //never returns due t infinite loop in animate method
	}
	
	/**
	 * Creates a new instance of BouncingSprites and calls the start() method.
	 * @param args
	 */
	public static void main(String[] args)
	{
		new BouncingSprites().start();
	}
}