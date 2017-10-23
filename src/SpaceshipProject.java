import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;             
import java.awt.image.BufferStrategy;
/******************************************************************************
*
* Name: Colton Conley
* Block: C
* Date: 9/10/15
*
* Spaceship Program
* Description:
* The objective of this program was to learn how to use graphics in java and use 
* keystrokes as input. The program displays a circular ship with a cannon that 
* rotates around the ship. The cannon fires bullets, which can be used to eliminate 
* obstacles. The obstacles appear in random spots at random times, and yield a point
* every time a bullet hits them and 10 points every time one is destroyed.  Some 
* obstacles have velocities, and all objects in play will wrap around the screen 
* (with the exception of bullets). 
******************************************************************************/
public class SpaceshipProject extends JFrame implements KeyListener, ActionListener
{

	private static final int RADIUS = 30; //starting radius of the spaceship
	private static final int CANON_RADIUS = 10; //starting radius of the canon
	private static final int X_COORD = 100; //starting xCoord
	private static final int Y_COORD = 100; //starting yCoord
	public static final int WINDOW_HEIGHT = 1000; //window height
	public static final int WINDOW_WIDTH = 1000; // window width
	public static final int PERIOD = 10;
	public static int score = 0; //number of obstacles killed * 10 plus number of hits

	private static Spaceship spaceship = 
			new Spaceship(RADIUS, CANON_RADIUS, X_COORD, Y_COORD); //spaceship object to be used
	private static ArrayList<Bullet> bullets = new ArrayList<Bullet>(); //all bullets in play
	private static ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>(); //all obstacles in play

	public static void main(String[] args) {
		SpaceshipProject spaceshipProgram = new SpaceshipProject();
		spaceshipProgram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		spaceshipProgram.setTitle("Spaceship Project");
		spaceshipProgram.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); 
		spaceshipProgram.setVisible(true);
		
		//fixes a problem that causes the screen to flash
		spaceshipProgram.createBufferStrategy(2);
		spaceshipProgram.addKeyListener(spaceshipProgram); 
		Timer pulse = new Timer(PERIOD , spaceshipProgram);
		pulse.start();

	}

	/**
	 * Responds appropriately to all arrow keys and the space bar
	 */
	public void keyPressed(KeyEvent e) 
	{
		int keyCode = e.getKeyCode();

		//rotate spaceship
		if (keyCode == KeyEvent.VK_LEFT)
		{
			spaceship.angleCounterClockwise();
		}
		else if (keyCode == KeyEvent.VK_RIGHT)
		{
			spaceship.angleClockwise();
		}

		//move spaceship
		else if (keyCode == KeyEvent.VK_UP)
		{
			spaceship.thrustForward();
		}
		else if (keyCode == KeyEvent.VK_DOWN)
		{
			spaceship.thrustBackward();
		}

		//fire bullet
		else if (keyCode == KeyEvent.VK_SPACE)
		{
			bullets.add(spaceship.shoot());
		}
		repaint();
	}

	/**
	 * Called when typing of a key is completed
	 * Required for any KeyListener
	 * 
	 * @param e                Contains info about the key typed
	 */
	public void keyTyped(KeyEvent e)
	{
	}

	/**
	 * Called when a key is released
	 * Required for any KeyListener
	 * 
	 * @param e                Contains info about the key released
	 */
	public void keyReleased(KeyEvent e)
	{
	}

	/**
	 * Moves the spaceship and bullets.  
	 * Also checks to see if any obstacles were hit by bullets
	 */
	public void actionPerformed (ActionEvent e)
	{
		spaceship.move();
		createObstacles();
		moveObstaclesAndBullets();
		repaint();
	}

	/**
	 * moves the bullets and obstacles, then checks to see if any 
	 * obstacles were destroyed
	 */
	private static void moveObstaclesAndBullets()
	{
		moveObstacles();
		for(int index = 0; index < bullets.size(); index++)
		{
			if(!bullets.get(index).move())
			{
				bullets.remove(index);
				index --;
			}
			else
			{
				//test against all obstacles
				for(int counter = 0; counter < obstacles.size(); counter++)
				{
					Obstacle obs = obstacles.get(counter);

					if(checkForCollision(bullets.get(index), obs))
					{
						score += 1;
						if(obs.reduceRadius())
						{
							obstacles.remove(counter);
							score += 10;
							counter --;
						}

						//bullet has been used, no longer exists
						bullets.remove(index);
						index--;
						break;
					}
				}
			}
		}
	}

	/**
	 * Moves every obstacle in play
	 */
	private static void moveObstacles()
	{
		for(Obstacle obs : obstacles)
		{
			obs.move();
		}
	}

	/**
	 * Checks to see if a given bullet is within the bounds of an obstacle.
	 * 
	 * @param bulletX		X coordinate of the bullet
	 * @param bulletY		Y coordinate of the bullet
	 * @param bulletRadius  Radius of bullet
	 * @param obs			Obstacle to check for collision with
	 * @return		returns true if the bullet and obstacle have collided
	 */
	private static boolean checkForCollision(Bullet bullet, Obstacle obs)
	{
		
		int bulletX = bullet.xCoord();
		int bulletY = bullet.yCoord();
		int bulletRadius = bullet.radius();
		
		return bulletX + bulletRadius >= obs.xCoord() - obs.radius() && 
				bulletX - bulletRadius <= obs.xCoord() + obs.radius() &&
				bulletY + bulletRadius >= obs.yCoord() - obs.radius() && 
				bulletY - bulletRadius <= obs.yCoord() + obs.radius();
	}

	/**
	 * Creates a random number of obstacles at random sizes
	 */
	private static void createObstacles()
	{
		if(Math.random() > .98)
		{
			obstacles.add(new Obstacle((int)(Math.random() * 4), Math.random() * 7));
		}
	}

	/**
	 * Uses a buffer strategy to avoid 
	 */
	public void paint(Graphics g)                 
	{ 
		BufferStrategy bf = this.getBufferStrategy();
		if (bf == null)
			return;

		Graphics g2 = null;

		try 
		{
			g2 = bf.getDrawGraphics();

			// myPaint does the actual drawing
			myPaint(g2);
		} 
		finally 
		{
			g2.dispose();
		}

		// Shows the contents of the backbuffer on the screen.
		bf.show();

		//Tell the System to do the Drawing now
		Toolkit.getDefaultToolkit().sync();	
	}

	/**
	 * First clears the screen, then draws the spaceship, bullets then obstacles
	 */
	public void myPaint(Graphics g)
	{
		g.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		drawSpaceship(g);
		for(Bullet b : bullets)
		{
			drawBullet(b, g);
		}

		for(Obstacle obs : obstacles)
		{
			drawObstacle(obs, g);
		}
		
		displayScore(g);
	}

	/**
	 * Draws the spaceship object
	 * 
	 * @param g		uses a graphics object to draw the spaceship
	 */
	private static void drawSpaceship(Graphics g)
	{
		g.setColor(Color.blue);
		g.fillOval(spaceship.xCoord() - spaceship.radius(), 
				spaceship.yCoord() - spaceship.radius(), 
				spaceship.radius() * 2, 
				spaceship.radius() * 2);

		drawCanon(g);
	}

	/**
	 * Draws the cannon
	 * 
	 * @param g		uses a graphics object to draw
	 */
	private static void drawCanon(Graphics g)
	{
		g.setColor(Color.black);
		g.fillOval((int)(spaceship.xCoord() + 
				(spaceship.radius() + spaceship.canonRadius())*Math.cos(spaceship.canonAngle()) - 
				(spaceship.canonRadius())), 
				(int)(spaceship.yCoord() + 
						(spaceship.radius() + spaceship.canonRadius())*Math.sin(spaceship.canonAngle()) - 
						(spaceship.canonRadius())), 
						spaceship.canonRadius() * 2, spaceship.canonRadius() * 2); //center center width height 
	}

	/**
	 * Draws a single bullet
	 * 
	 * @param b		bullet to draw
	 * @param g		uses a graphics object to draw bullet
	 */
	private static void drawBullet (Bullet b, Graphics g)
	{
		g.setColor(Color.red);
		g.fillOval(b.xCoord() - b.radius(), b.yCoord() - b.radius(), b.radius() * 2, b.radius() * 2);
	}

	/**
	 * Draws a single obstacle
	 * 
	 * @param obs	obstacle to be drawn
	 * @param g		Graphics object used to draw obstacle
	 */
	private static void drawObstacle (Obstacle obs, Graphics g)
	{
		g.setColor(Color.magenta);
		g.fillOval(obs.xCoord() - obs.radius(), obs.yCoord() - obs.radius(), obs.radius() * 2, obs.radius() * 2);
	}
	
	/**
	 * Displays the score in the upper right hand corner
	 * @param g		Graphics object to draw the score
	 */
	private static void displayScore(Graphics g)
	{
		g.setColor(Color.black);
		g.drawString("Score: " + score, WINDOW_WIDTH - 90, 50);
		
	}
}
