/**
 * Spaceship Class
 * 
 *	Stores all the values pertaining to the size and position of the spaceship. 
 *	
 *  Provides methods to move the spaceship and create bullets the spaceship is 
 *  shooting
 */
public class Spaceship {
	private double canonAngle = 0; //angle from top in radians
	private int canonRadius; //radius of canon
	private int radius; //radius of ship
	private int xCoord; //x coord of center
	private int yCoord;  //y coord of center
	private int speed = 0; //speed at which it is moving
	private int bulletRadius;  //radius of the bullets the spaceship shoots
	private int bulletSpeed = 50;  //the speed difference between bullets and the spaceship

	/*
	 * Constructor
	 */
	public Spaceship (int newRadius, int newCanonRadius, int newXCoord, int newYCoord)
	{
		radius = newRadius;
		canonRadius = newCanonRadius;
		xCoord = newXCoord;
		yCoord = newYCoord;
		bulletRadius = canonRadius / 2;
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

	public void setCanonRadius(int newCanonRadius)
	{
		canonRadius = newCanonRadius;
	}

	public int canonRadius()
	{
		return canonRadius;
	}

	public void setCanonAngle (double newCanonAngle)
	{
		canonAngle = newCanonAngle;
	}

	public double canonAngle()
	{
		return canonAngle;
	}

	public void thrustForward()
	{
		speed += 2;
	}

	public void thrustBackward()
	{
		speed -= 2;
	}

	/**
	 * Moves the spaceship on its current trajectory at its current speed
	 */
	public void move()
	{
		this.xCoord = (int) (xCoord + speed*Math.cos(canonAngle));
		this.yCoord = (int) (yCoord + speed*Math.sin(canonAngle));
		
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

	/**
	 * Rotates the cannon clockwise
	 */
	public void angleClockwise()
	{
		canonAngle += .15;
	}

	
	/**
	 * Rotates the cannon counterclockwise
	 */
	public void angleCounterClockwise()
	{
		canonAngle -= .15;
	}
	
	/**
	 * Creates a bullet object based on the current velocity and position of the spaceship
	 * 
	 * @return		returns the bullet object created
	 */
	public Bullet shoot()
	{
		int canonXCoord = (int) (xCoord + (radius + canonRadius) * Math.cos(canonAngle));
		int canonYCoord = (int) (yCoord + (radius + canonRadius) * Math.sin(canonAngle));
		return new Bullet(bulletRadius, canonXCoord, canonYCoord, speed + bulletSpeed, canonAngle);
		
	}

	
}

