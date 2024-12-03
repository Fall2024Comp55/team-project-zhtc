import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

// this class handles creating the player unit, or the units that the player will use to defeat enemies
public class UnitSoldier extends Unit{
	public static final String IMG_FILENAME_PATH = "media/";
	public static final String IMG_EXTENSION = ".png";
	private static final int Y_UNIT_BAR = 20;
	private static final int X_UNIT_BAR = 20;
	
	public static GameTimer cooldownTimer;
	protected static GLabel cooldownLabel;
	protected static boolean isOnCooldown = false;
	protected static int cooldown; // in function calls per 500MS.
	protected static int numTimesCooldown;
	
	// private GImage image = new GImage(IMG_FILENAME_PATH + "soldier" + IMG_EXTENSION);
	private UnitType unitType = UnitType.SOLDIER;
	
	private Game game;

	public UnitSoldier(GameScene gameScene, Game game)
	{
		super(gameScene, game);
		this.image = new GImage(unitType.getImagePath());
        this.health = unitType.getHealth();
        this.cost = unitType.getCost();
        this.frequency = unitType.getFrequency();
        UnitSoldier.cooldown = unitType.getCooldown();
        
        this.numTimes = 0;
        this.enemyDetected = false;
        this.lane = lane;
	}
	
	public void startTimer()
	{
		routineTimerSoldier = new GameTimer(100, "Soldier");
		routineTimerSoldier.start();
		cooldownLabel = new GLabel("");
		
		ActionListener listener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	numTimes = numTimes + 1;
		    	 
		    	if (numTimes > frequency) {
		    		routine();
		    		numTimes = 0;
		    	}
		    }};
		    
		    routineTimerSoldier.createActionListener(listener);
	}
	
	@Override
	public void routine () {
		if (gameScene.isPaused()) {
			return;
		}
		if (checkForEnemy()) {
			shoot();
		}
		else {
			System.out.println("No enemy in lane " + lane);
			return;
		}
		
	}
	
	@Override
	public void shoot() {
		double projectileStartX = image.getX() + image.getWidth(); // Right edge of the soldier
		double projectileStartY = image.getY() + 0.15 * image.getHeight(); // Slightly below the top
		if (!gameScene.isPaused() && gameScene.game.grid.getUnitAtSpace(lane, getCurrentColumn()) != null
			&& !gameScene.getRobotsInLane(lane).isEmpty()) {
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
	}
	
	public void startCooldown()
	{
		cooldownTimer = new GameTimer(500, "Cooldown");
		cooldownTimer.start();
		String filename = IMG_FILENAME_PATH + "unitBar_soldier_cooldown" + IMG_EXTENSION;
		GImage unitBarSoldierImage_cooldown = new GImage(filename);
		double xStart = X_UNIT_BAR;
		double yStart = Y_UNIT_BAR;
		unitBarSoldierImage_cooldown.setLocation(xStart + unitBarSoldierImage_cooldown.getWidth() * unitType.getNum(), yStart );
		gameScene.addElement(unitBarSoldierImage_cooldown);
		
		numTimesCooldown = 0;
		int seconds = cooldown / 2;
		cooldownLabel.setLabel(Integer.toString(seconds));
		reconfigureLabel(cooldownLabel, unitType);
		gameScene.addElement(cooldownLabel);
		
		isOnCooldown = true;
		
		ActionListener listener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if (numTimesCooldown >= cooldown - 1)
		    	{
		    		gameScene.removeElement(cooldownLabel);
		    		cooldownTimer.stop();
		    		cooldownTimer.removeActionListener(this);
		    		cooldownTimer = null;
		    		isOnCooldown = false;
		    		gameScene.removeElement(unitBarSoldierImage_cooldown);
		    	}
		    	else
		    	{
		    		numTimesCooldown = numTimesCooldown + 1;
		    		if (numTimesCooldown % 2 == 0)
		    		{
		    			cooldownLabel.setLabel(Integer.toString(seconds - numTimesCooldown / 2));
		    		}
		    	}
		    }};
		    
		cooldownTimer.createActionListener(listener);
	}
	
	public boolean isCooldownActive()
	{
		
		if (isOnCooldown == true)
		{
    		
			return true;
		}
		else
		{
			
			return false;
		}
	}
	
	public boolean isItUpgradable(boolean upgrade) {
		return this.upgradable && upgrade && unitToUpgradeTo != null;
	}
	
	public boolean isDeath() {
		return health <= 0; 
	}
	
	public static void main(String[] args) {
		
	}
}
