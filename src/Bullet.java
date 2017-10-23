/**
 * Bullet class
 * 
 *	Stores the angle, speed, radius, and coordinates for an individual bullet.
 *	Provides methods to reduce the radius of a given obstacle and to move an obstacle.
 */
public class Bullet {

	private double angle; //angle at which bullet is travelling
	private int radius; //radius of the bullet
	private int xCoord; //x coord of center
	private int yCoord;  //y coord of center
	private int speed = 0; //speed at which it is moving

	/*
	 * Constructor
	 */
	public Bullet(int newRadius, int newXCoord, int newYCoord, int newSpeed, double newAngle)
	{
		radius = newRadius;
		xCoord = newXCoord;
		yCoord = newYCoord;	
		speed = newSpeed;
		angle = newAngle;
	}

	/*
	 * Setters and getters
	 */
	public int xCoord()
	{
		return xCoord;
	}

	public void setXCoord(int newXCoord)
	{
		xCoord = newXCoord;
	}

	public int yCoord()
	{
		return yCoord;
	}

	public void setYCoord(int newYCoord)
	{
		yCoord = newYCoord;
	}

	public void setRadius(int newRadius)
	{
		radius = newRadius;
	}

	public int radius()
	{
		return radius;
	}

	/**
	 * moves the bullet and determines whether or not it is off the screen
	 * 
	 * @return	Returns false if bullet is off the screen
	 */
	public boolean move()
	{
		this.xCoord = (int) (xCoord + speed*Math.cos(angle));
		this.yCoord = (int) (yCoord + speed*Math.sin(angle));
		
		//check to see if bullet has left screen
		if(xCoord > SpaceshipProject.WINDOW_WIDTH ||
				xCoord < 0 ||
				yCoord > SpaceshipProject.WINDOW_HEIGHT ||
				yCoord < 0)
		{
			return false;
		}
		return true;
	}

}
