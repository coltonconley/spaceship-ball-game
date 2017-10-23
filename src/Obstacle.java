/**
 * Obstacle Class
 * 
 *	Stores the angle, speed, radius, coordinates, and the minimum radius for all obstacles.
 *	Provides methods to reduce the radius of a given obstacle and to move an obstacle.
 */
public class Obstacle {
	private double angle; //angle at which bullet is travelling
	private int radius; //radius of the bullet
	private int xCoord; //x coord of center
	private int yCoord;  //y coord of center
	private int speed = 0; //speed at which it is moving
	public static final int MIN_RADIUS = 15;
	
	/**
	 * Constructor
	 * 
	 * @param newSpeed		Speed the obstacle travels at
	 * @param newAngle		Angle along which the obstacle travels
	 */
	public Obstacle(int newSpeed, double newAngle)
	{
		radius = (int)(Math.random() * 35) + MIN_RADIUS;
		xCoord = (int)(Math.random() * SpaceshipProject.WINDOW_WIDTH);	
		yCoord = (int)(Math.random() * SpaceshipProject.WINDOW_HEIGHT);	
		speed = newSpeed;
		angle = newAngle;
	}
	
	
	/*
	 * setters and getters
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
	 * Reduces the radius of an obstacle by 5
	 * 
	 * @return		Returns true if radius is no longer greater than 5
	 */
	public boolean reduceRadius()
	{
		radius -= MIN_RADIUS;
		if(radius < MIN_RADIUS)
		{
			return true;
		}
		return false;
	}

	/**
	 * moves the obstacle and wraps it around the screen
	 * 
	 * @return	Returns false if bullet is off the screen
	 */
	public void move()
	{
		this.xCoord = (int) (xCoord + speed*Math.cos(angle));
		this.yCoord = (int) (yCoord + speed*Math.sin(angle));
		
		//wrap around for x coordinates
		if(xCoord > SpaceshipProject.WINDOW_WIDTH)
		{
			xCoord = 0;
		}
		else if (xCoord < 0)
		{
			xCoord = SpaceshipProject.WINDOW_WIDTH;

		}

		//wrap around for y coordinates
		if(yCoord > SpaceshipProject.WINDOW_HEIGHT)
		{
			yCoord = 0;
		}
		else if(yCoord < 0)
		{
			yCoord = SpaceshipProject.WINDOW_HEIGHT;
		}
	}

}



