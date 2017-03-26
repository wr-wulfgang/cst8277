package data;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sprite")
public class Sprite implements Serializable
{
	public final static Random random = new Random();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int x;
	
	@Column
	private int y;
	
	@Column
	private int dx;
	
	@Column
	private int dy;
	
	@Column
	@Convert(converter = ColorConverter.class)
	private Color color;
	
	private final static int SIZE = 10;
	private final static int MAX_SPEED = 5;
	private final static int WINDOW_SIZE = 500;
	
	public Sprite()
	{
        dx = random.nextInt(2 * MAX_SPEED) - MAX_SPEED;
        dy = random.nextInt(2 * MAX_SPEED) - MAX_SPEED;
	}
	
	public int getId() { return this.id; }
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getX() { return this.x; }
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getY() { return this.y; }
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getDx() { return this.dx; }
	public void setDx(int dx)
	{
		this.dx = dx;
	}
	
	public int getDy() { return this.dy; }
	public void setDy(int dy)
	{
		this.dy = dy;
	}
	
	public Color getColor() { return this.color; }
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillOval(x, y, SIZE, SIZE);
	}
	
	public void move()
	{
		if(x < 0 && dx < 0)
		{
			x = 0;
			dx = -dx;
		}
		if(y < 0 && dy < 0)
		{
			y = 0;
			dy = -dy;
		}
		if(x > WINDOW_SIZE - SIZE && dx > 0)
		{
			x = WINDOW_SIZE - SIZE;
			dx = -dx;
		}
		if(y > WINDOW_SIZE - SIZE && dy > 0)
		{
			y = WINDOW_SIZE - SIZE;
			dy = -dy;
		}
		
		x += dx;
		y += dy;
	}
}