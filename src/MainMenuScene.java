import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

// The main menu scene that contains buttons for staring the game
// viewing instruction, and exiting

public class MainMenuScene extends Scene {
	private GLabel label = new GLabel("MainMenuScene", MainApplication.getResolutionWidth() / 2, MainApplication.getResolutionHeight() / 2);
	private GButton startButton;
	private GButton helpButton;
	
	public static final String IMG_FILENAME_PATH = "media/";
	public static final String IMG_EXTENSION = ".png";
	
	public MainMenuScene(MainApplication mainApp)
	{
		super(mainApp);
	}
	
	private void drawStartButton() {
		String filename = IMG_FILENAME_PATH + "startButton" + IMG_EXTENSION;
		GImage startButtonImage = new GImage(filename);
		int startButtonX = (RESOLUTION_WIDTH - (int) startButtonImage.getWidth()) / 2;
		int startButtonY = (int)(RESOLUTION_HEIGHT * 0.6);
		this.startButton = new GButton(startButtonImage,startButtonX,startButtonY);
		addElement(startButton);
		startButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				startButton.onClick();
				// trigger action to start the game
				System.out.println("Start Button clicked!");
				mainApp.switchSceneTo(mainApp.LevelSelectScene);
			}
			
			public void mouseEntered (MouseEvent e) {
				startButton.onHover();
			}
		});
	}
	
	private void drawHelpButton() {
		String filename = IMG_FILENAME_PATH + "helpButton" + IMG_EXTENSION;
		GImage helpButtonImage = new GImage(filename);
		int helpButtonX = (int)(RESOLUTION_WIDTH * 0.02);
		int helpButtonY = (int)(RESOLUTION_HEIGHT * 0.02);
		this.helpButton = new GButton(helpButtonImage,helpButtonX,helpButtonY);
		addElement(helpButton);
		helpButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				helpButton.onClick();
				// trigger action to start the game
				System.out.println("Help Button clicked!");
				mainApp.switchSceneTo(mainApp.HowToPlayScene);
			}
			public void mouseEntered (MouseEvent e) {
				helpButton.onHover();
			}
		});
	}
	
	public void showContents() {
		drawStartButton();
		drawHelpButton();
		System.out.println("Show contents from this point...");
		addElement(label);
	}
	
	public void hideContents() {
		removeElement(label);
		removeElement(startButton);
		removeElement(helpButton);
	}
	
	public static void main(String[] args) {
		
	}
}
