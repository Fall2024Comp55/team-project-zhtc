import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

// this class handles creating the player unit, or the units that the player will use to defeat enemies
public class UnitGrenade extends Unit{
	public static final String IMG_FILENAME_PATH = "media/";
	public static final String IMG_EXTENSION = ".png";
	
	// private GImage image = new GImage(IMG_FILENAME_PATH + "grenade" + IMG_EXTENSION);
	private UnitType unitType = UnitType.GRENADE;

	public UnitGrenade(GameScene gameScene)
	{	// TO DO CHANGE ATTRIBUTES - NOT FINAL
		super(gameScene);
		this.image = new GImage(unitType.getImagePath());
		this.health = 100;
        this.cost = 100;
        this.frequency = 10;
        this.numTimes = 0;
        this.enemyDetected = false;
	}
	
	public void startTimer()
	{
		routineTimerGrenade = new GameTimer(100, "Grenade");
		routineTimerGrenade.start();
		
		ActionListener listener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	numTimes = numTimes + 1;
		    	 
		    	if (numTimes > 20) {
		    		routine();
		    		numTimes = 0;
		    	}
		    }};
		    
		    routineTimerGrenade.createActionListener(listener);
	}
	
	public void routine () {
		double projectileStartX = image.getX() + image.getWidth(); // Right edge of the Grenade
        double projectileStartY = image.getY() + 0.15 * image.getHeight(); // Slightly below the top
		Projectile projectile = new Projectile(
				new GImage(IMG_FILENAME_PATH + "paintball_Yellow" + IMG_EXTENSION),
				10,
				10,
				10,
				10,
				projectileStartX,
				projectileStartY,
				gameScene
				);
		gameScene.instantiateProjectile(projectile, projectileStartX, projectileStartY);
		System.out.println("Instantiated projectile from " + this);
	}
	
	// checks if a player unit is upgradable to a stronger unit
	public boolean isItUpgradable(boolean upgrade) {
		return this.upgradable && upgrade && unitToUpgradeTo != null;
	}
	
	// checks if an enemy is in the same lane as the player unit. If it is, the player unit starts attacking the enemy
	public boolean checkForEnemy(boolean robotLocation) {
		return robotLocation;
	}
	
	// handles enemies dealing damage to the player unit
	public int takeDamage(int damage) {
		health -= damage;
        if (health <= 0) { 
            health = 0;
            System.out.println("Unit destroyed");
        } 
        else {
            System.out.println("Unit took " + damage + " damage. Health: " + health);
        }
        return health;
	}
	// if the player unit's health hits zero
	public boolean isDeath() {
		return health <= 0; 
	}
	
	public static void main(String[] args) {
		
	}
}
