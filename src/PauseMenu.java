import acm.graphics.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PauseMenu extends PopupMenu{
	private static final double Y_RATIO = 0.4;
	private static final double Y_RATIO_RESUME_BUTTON = 0.65;

	private GLabel label = new GLabel("pauseMenu", MainApplication.getResolutionWidth() / 2, MainApplication.getResolutionHeight() / 4);
	
	private GImage pauseMenu;
	private GButton biggerRestartButton;
	private GButton resumeButton;
	private MainApplication mainApp;
	private RestartConfirmation restartConfirmation;

	public static final String IMG_FILENAME_PATH = "media/";
	public static final String IMG_EXTENSION = ".png";
	
	public PauseMenu(String imagePath, MainApplication mainApp)
	{
		super(imagePath);  // Use the image as the background for the popup
		this.mainApp = mainApp;
		
		String filename1 = IMG_FILENAME_PATH + "pauseMenu" + IMG_EXTENSION;
		this.pauseMenu = new GImage(filename1);
		this.biggerRestartButton = drawRestartButton("biggerRestartButton", pauseMenu);
		this.resumeButton = drawResumeButton("resumeButton", pauseMenu);
		
		addMenuElement(biggerRestartButton);
		addMenuElement(resumeButton);
		
		addMouseListeners();
		
	}
	
	private GButton drawRestartButton(String lable, GImage backgroundImage) {
		GImage image = new GImage(IMG_FILENAME_PATH + lable + IMG_EXTENSION);
		
		double x = (MainApplication.getResolutionWidth() - backgroundImage.getWidth()) / 2 + 30;
		double y = MainApplication.getResolutionHeight() * Y_RATIO;
		GButton button = new GButton(image, x, y);
		
		return button;
	}
	
	private GButton drawResumeButton(String lable, GImage backgroundImage) {
		GImage image = new GImage(IMG_FILENAME_PATH + lable + IMG_EXTENSION);
		
		double x = (MainApplication.getResolutionWidth() - image.getWidth()) / 2;
		double y = MainApplication.getResolutionHeight() * Y_RATIO_RESUME_BUTTON;
		GButton button = new GButton(image, x, y);
		
		return button;
	}
	
	//TODO: ADD FOR ANOTHER BUTTONS
	private void addMouseListeners() {
		biggerRestartButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				handleRestart();
			}
		});
		
		resumeButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				handleResume();
			}
		});
	}
	
	private void handleRestart() {
		System.out.println("Restart button clicked: Showing confirmation dialog.");
		hidePopup(this.mainApp);
		// Logic to restart the game (e.g., restarting level)
		String filename = IMG_FILENAME_PATH + "restartBackground" + IMG_EXTENSION;
		this.restartConfirmation = new RestartConfirmation(filename, mainApp);
		this.restartConfirmation.showPopup(mainApp); // Display the confirmation menu
	}
	private void handleResume() {
		System.out.println("Resuming game...");
		hidePopup(this.mainApp);
	}
	
	private void handleExit() {
        System.out.println("Exiting to main menu...");
        // Logic to exit to main menu (e.g., loading MainMenuScene)
    }

}
