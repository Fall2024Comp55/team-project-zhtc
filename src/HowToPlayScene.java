import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

// The how to play scene that contains instructions on how to play
// and a button to return to main menu scene..

public class HowToPlayScene extends Scene{
	private GLabel label = new GLabel("HowToPlayScene", MainApplication.getResolutionWidth() / 2, MainApplication.getResolutionHeight() / 2);
	
	private GButton returnButtom;
	public static final String IMG_FILENAME_PATH = "media/";
	public static final String IMG_EXTENSION = ".png";
	
	public HowToPlayScene(MainApplication mainApp)
	{
		super(mainApp);
	}
	
	private void drawReturnButton() {
		String filename = IMG_FILENAME_PATH + "returnButton" + IMG_EXTENSION;
		// change from robot to "X" button
		GImage returnButtonImage = new GImage(filename);
		this.returnButtom = new GButton(returnButtonImage,0,0);
		addElement(returnButtom);
		returnButtom.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				returnButtom.onClick();
				// trigger return to main menu
				System.out.println("Return Button clicked!");
				mainApp.switchSceneTo(mainApp.MainMenuScene);
			}
			public void mouseEntered (MouseEvent e) {
				returnButtom.onHover();
			}
		});
	}
	
	
	public void showContents()
	{
		System.out.println("Show contents from this point..");
		mainApp.add(label);
		drawReturnButton();
	}
	
	public void hideContents()
	{
		System.out.println("Hide contents from this point..");
		for (GObject obj : activeContents)
		{
			removeElement(obj);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse pressed.");
	}
	
	public void mouseReleased(MouseEvent e) {
		System.out.println("Mouse released.");
	}
	
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse clicked.");
	}
	
	public void mouseDragged(MouseEvent e) {
		System.out.println("Mouse dragged.");
	}

	public static void main(String[] args) {

	}
	

}
