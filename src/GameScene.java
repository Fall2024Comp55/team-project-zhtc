import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.*;
import java.util.List;
import java.util.Set;

// The how to play scene that contains instructions on how to play
// and a button to return to main menu scene..

public class GameScene extends Scene{
	private static final int CURRENCY_START = 100;
	private String labelText;
	private GLabel currencyLabel;
	private GImage selectedUnit = null;
	private UnitType chosenUnitName;
	private GImage currencyBackground;
	
	private Game game;
	private ArrayList<Projectile> projectileCache = new ArrayList<>();
	private ArrayList<Robot> robotCache = new ArrayList<>();
	private ImageToUnitMap imageToUnitMap = new ImageToUnitMap();
	private ImageToRobotMap imageToRobotMap = new ImageToRobotMap();
	private GameTimer gameTimer;
	private int numTimes;
	private int currency;
	
	private GButton pauseButton;
	private UnitBar unitBar;
	
	private Grid grid;
	
	public static final String IMG_FILENAME_PATH = "media/";
	public static final String IMG_EXTENSION = ".png";
	
	public GameScene(MainApplication mainApp, String difficulty)
	{
		super(mainApp);
		labelText = difficulty;
		unitBar = new UnitBar();
		grid = new Grid(5, 9);
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
	
	private void drawCurrencyCounter() {
		this.currency = CURRENCY_START;
		this.currencyLabel = new GLabel("" + currency);
		this.currencyLabel.setFont("Arial-Bold-50");
		this.currencyLabel.setColor(Color.BLACK);
		this.currencyLabel.setLocation(currencyBackground.getX() + 0.8 * (currencyBackground.getWidth() / 2), currencyBackground.getY() + 1.3 * (currencyBackground.getHeight() / 2));
		addElement(currencyLabel);
	}
	
	public void addCurrency(int amount) {
		this.currency += amount;
		updateCurrencyLabel();
	}
	
	public boolean spendMoney(int amount) {
		if (this.currency >= amount) {
			currency -= amount;
			updateCurrencyLabel();
			return true;
		}
		// should show notification to player when they do not have enough money. DO IT LATER
		return false;
	}
	private void updateCurrencyLabel() {
		this.currencyLabel.setLabel("" + currency);
	}
	
	// when clicking certain buttons, a new screen will pop up. showContents makes the screens pop up, while hideContents makes them disappear
	public void showContents()
	{
		System.out.println("Show contents from this point..");
		addElement(new GLabel(labelText, MainApplication.getResolutionWidth() / 2, MainApplication.getResolutionHeight() / 2));
		drawPauseButton();
		unitBar.drawUnitBar(this);
		drawCurrencyBackground();
		drawCurrencyCounter();
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
		gameTimer = new GameTimer(50, "Game");
		
		ActionListener listener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	for (Projectile item : projectileCache)
		    	{
		    		item.step();
		    	}
		    }};
		   
	    gameTimer.createActionListener(listener);
	    gameTimer.start();
	}
	
	public void instantiateUnit(UnitType unitName, int x, int y)
	{
		Unit unit = null;
		switch (unitName) {
			case SOLDIER -> unit = new UnitSoldier(this, x, y);
			// case MACHINE_GUNE;
			// case GRENADE;
			// case ROCK;
		}
		if (unit != null)
		{
			GImage unitImage = unit.getImageFromUnit();
			unit.setImagePos(x, y);
			unit.startTimer();
			imageToUnitMap.addPair(unitImage, unit);
			addElement(unitImage);
		}
		System.out.println("Instantiated unit:" + unitName.getName());
	}
	
	public void instantiateProjectile(Projectile projectile, double x, double y)
	{
		GImage projImage = projectile.getImage();
		projectile.setLocation(x, y);
		addElement(projImage);
		projectileCache.add(projectile);
		System.out.println("Added projectile " + projectile + " to cache");
	}
	
	public void handlePlaceUnit() {
		if (selectedUnit != null) {
			UnitType chosenUnitType = unitBar.getSelectedUnit();
			// Make sure instantiateUnit() method will not cause an unexpected behavior
			if (chosenUnitType != null) {
				if (spendMoney(chosenUnitType.getCost())) {
					instantiateUnit(chosenUnitType, (int)selectedUnit.getX(), (int)selectedUnit.getY());
					System.out.println("Unit placed at x: " + selectedUnit.getX() + "; y: " + selectedUnit.getY());
				} else {
					System.out.println("NOT ENOUGH MONEY");
				}
			}

			unitBar.clearSelectedUnit();
			chosenUnitName = null;
			removeElement(selectedUnit);
			selectedUnit = null;
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse pressed.");
		// Check if the mouse click is on one of the squares in unit bar
		chosenUnitName = unitBar.handleMousePressed(e.getX(), e.getY());
		if (chosenUnitName == null)
		{
			return;
		}
		selectedUnit = new GImage(chosenUnitName.getImagePath()); 
		selectedUnit.setLocation(e.getX() - selectedUnit.getWidth() / 2, e.getY() - selectedUnit.getHeight() / 2);
		addElement(selectedUnit);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		handlePlaceUnit();
	}
	
	public void mouseClicked(MouseEvent e) {
		//System.out.println("Mouse clicked.");
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// Move the selected unit with the mouse
		if (selectedUnit != null) {
			selectedUnit.setLocation(e.getX() - selectedUnit.getWidth() / 2, e.getY() - selectedUnit.getHeight() / 2);
		}
	}

	public static void main(String[] args) {

	}
	

}
