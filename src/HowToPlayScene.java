import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

// This class is responsible for handling the current "scene"
// and switching between them.

public class HowToPlayScene extends Scene{
	private GLabel label = new GLabel("HowToPlayScene", MainApplication.getResolutionWidth(), MainApplication.getResolutionHeight());
	
	public void init() {
		setSize(MainApplication.getResolutionWidth(), MainApplication.getResolutionHeight());
		requestFocus();
	}
	
	public void run() {
		addMouseListeners();
	}
	
	public void showContents()
	{
		System.out.println("Show contents from this point..");
		addElement(label);
	}
	
	public void hideContents()
	{
		System.out.println("Hide contents from this point..");
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
		new HowToPlayScene().start();
	}

}
