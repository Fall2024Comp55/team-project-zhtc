import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
import java.util.Set;

// The how to play scene that contains instructions on how to play
// and a button to return to main menu scene..

public class GameScene extends Scene{
	private String labelText;
	private GLabel label;
	private GImage selectedUnit = null;
	private String chosenUnitName;
	private GImage currencyBackground;
	
	private Game game;
	private Set<Projectile> projectileCache;
	private Set<Robot> robotCache;
	private GameTimer gameTimer;
	private int numTimes;
	
	private GButton pauseButton;
	private UnitBar unitBar;
	
	public static final String IMG_FILENAME_PATH = "media/";
	public static final String IMG_EXTENSION = ".png";
	
	public GameScene(MainApplication mainApp, String difficulty)
	{
		super(mainApp);
		labelText = difficulty;
		unitBar = new UnitBar();
	}
	
	private void drawPauseButton() {
		String filename = IMG_FILENAME_PATH + "pauseButton" + IMG_EXTENSION;
		GImage pauseButtonImage = new GImage(filename);
		int pauseButtonX = (int)(MainApplication.getResolutionWidth() * 0.90);
		int pauseButtonY = (int)((MainApplication.getResolutionHeight() * 0.02) + 20);
		this.pauseButton = new GButton(pauseButtonImage,pauseButtonX,pauseButtonY);
		addElement(pauseButton);
		pauseButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// trigger return to main menu
				System.out.println("Pause Button clicked!");
				PauseMenu pauseMenu = new PauseMenu ("media/pauseMenu.png", mainApp);
				pauseMenu.showPopup(mainApp); // Display the pause menu
			}
		});
	}
	
	private void drawCurrencyBackground() {
		String filename = IMG_FILENAME_PATH + "currencyBackground" + IMG_EXTENSION;
		currencyBackground = new GImage(filename);
		int currencyBackgroundX = (int)(MainApplication.getResolutionWidth() * 0.70);
		int currencyBackgroundY = (int)((MainApplication.getResolutionHeight() * 0.02) + 20);
		this.currencyBackground.setLocation(currencyBackgroundX, currencyBackgroundY);
		addElement(currencyBackground);
	}
	
	// when clicking certain buttons, a new screen will pop up. showContents makes the screens pop up, while hideContents makes them disappear
	public void showContents()
	{
		System.out.println("Show contents from this point..");
		addElement(new GLabel(labelText, MainApplication.getResolutionWidth() / 2, MainApplication.getResolutionHeight() / 2));
		drawPauseButton();
		unitBar.drawUnitBar(this);
		drawCurrencyBackground();
		startGame();
	}
	
	public void hideContents()
	{
		System.out.println("Hide contents from this point..");
		for (GObject obj : new ArrayList<>(activeContents)) // create a copy to avoid modification
		{
			removeElement(obj);
		}
	}
	
	public void startGame()
	{
		System.out.println("Starting game.");
		game = new Game();
		gameTimer = new GameTimer(5, "Game");
		
		ActionListener listener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	for (Projectile item : projectileCache)
		    	{
		    		item.move();
		    	}
		    }};
		   
	    gameTimer.createActionListener(listener);
	}
	
	public void instantiateUnit(String unitName, int x, int y)
	{
		Unit unit = null;
		if (unitName == "soldier") {unit = new UnitSoldier(this);}
		if (unit != null)
		{
			unit.setImagePos(x, y);
			unit.startTimer();
			addElement(unit.getImageFromUnit());
		}
		System.out.println("Instantiated unit:" + unit);
	}
	
	public void createProjectile(Projectile projectile)
	{
		projectileCache.add(projectile);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse pressed.");
		// Check if the mouse click is on one of the squares in unit bar
		chosenUnitName = unitBar.handleMousePressed(e.getX(), e.getY());
		selectedUnit = new GImage(IMG_FILENAME_PATH + chosenUnitName + IMG_EXTENSION); 
		addElement(selectedUnit);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println("Mouse released.");
		// When mouse is released, print out the coordinates and placed unit
		if (selectedUnit != null) {
			System.out.println("Unit placed at x: " + selectedUnit.getX() + "; y: " + selectedUnit.getY());
			unitBar.clearSelectedUnit();
			instantiateUnit(chosenUnitName, e.getX(), e.getY());
			chosenUnitName = null;
			removeElement(selectedUnit);
			selectedUnit = null;
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		//System.out.println("Mouse clicked.");
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		//System.out.println("Mouse dragged.");
		// Move the selected unit with the mouse
		if (selectedUnit != null) {
			selectedUnit.setLocation(e.getX() - selectedUnit.getWidth() / 2, e.getY() - selectedUnit.getHeight() / 2);
		}
	}

	public static void main(String[] args) {

	}
	

}
