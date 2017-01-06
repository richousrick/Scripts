import java.awt.AWTException;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * Locks your windows environment until a password has been entered
 * Note: does not hide keystrokes from global key listeners
 * 		Meaning some applications running in the background may still have macro functionality
 * 		For example many media players will still register media buttons.
 * 
 * @author Richousrick
 */
public class LockScreen {

	private JFrame frame;
	private Robot r;
	private int counter = 0;
	
	// Unlock password
	private char[] password = "password".toCharArray();
	
	// Mouse Lock
	private boolean lockMouse = false;
	int lockMouseX = 0;
	int lockMouseY = 0;
	
	// Graphics
	boolean coloured = false;
	
	public static void main(String[] args) {
		LockScreen window = new LockScreen();
		window.frame.setVisible(true);
	}

	/**
	 * Init the class
	 */
	public LockScreen() {
		frame = new JFrame();
		
		// Dont show in windows taskbar
		frame.setType(javax.swing.JFrame.Type.UTILITY);
		
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		// Set the bounds of the frame to be that of all screens
		// Used to lock all monitors
		Rectangle bound = new Rectangle();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		for (GraphicsDevice gd : ge.getScreenDevices()){
			bound.add(gd.getDefaultConfiguration().getBounds());
		}
		frame.setBounds(bound);
		
		// Make appear on top of all other applications
		frame.setAlwaysOnTop(true);
		
		// Disable minimise, maximise and exit buttons
		frame.setUndecorated(true);
		
		// Disable force closing
		// e.g. alt-f4
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		decorateWindow();
		
		// Allows detection of focus traversal keys
		// e.g. tab
		frame.setFocusTraversalKeysEnabled(false);
		
		// Add listeners
		frame.addKeyListener(new LockKeyListener());
		frame.addWindowListener(new LockWindowFocus());
		if(lockMouse){
			frame.addMouseMotionListener(new LockMouse());
		}
	}

	/**
	 * Used to dynamically change window
	 */
	private void decorateWindow(){
		if(coloured){
			int n = ((int) Math.ceil(((double)counter/(double)password.length)*254));
			frame.setBackground(new Color(0, 0, 0, 254 - n));
		}else
			// note alpha must be at least 1 otherwise the frame can be clicked through
			frame.setBackground(new Color(0,0,0,1));
	}
  

	/**
	 * Disables windows keybinds and enables password unlock
	 * 
	 * @author Richousrick
	 */
	class LockKeyListener extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			// If windows key pressed press escape
			// Disables windows bar from showing
			if (e.getKeyCode() == KeyEvent.VK_WINDOWS){
				r.keyPress(KeyEvent.VK_ESCAPE);
				r.keyRelease(KeyEvent.VK_ESCAPE);
			}
						
			// Check password
			if (password[counter]==e.getKeyChar()){
				counter++;
				decorateWindow();
				
				// If password entered close program
				if (counter == password.length){
					System.exit(0);
				}
			}else{
				counter = 0;
				decorateWindow();
			}
			
			// Release the key
			// Disables most keybinds as all of the keys will have to be pressed in a short window to not be released
			if(e.getKeyCode()!=KeyEvent.VK_UNDEFINED)
				r.keyRelease(e.getKeyCode());
		}
	
		@Override
		public void keyTyped(KeyEvent e) {
			// Release the key
			// Disables most keybinds as all of the keys will have to be pressed in a short window to not be released
			if(e.getKeyCode()!=KeyEvent.VK_UNDEFINED)
				r.keyRelease(e.getKeyCode());
		}
	}
	
	
	/**
	 * Locks mouse to a set position
	 * 
	 * @author Richousrick
	 */
	class LockMouse extends MouseMotionAdapter{
			@Override
			public void mouseMoved(MouseEvent e) {
				r.mouseMove(lockMouseX, lockMouseY);
		}
	}
  
	
	/**
	 * Makes the window un-focusable
	 * This disables many common windows keybinds including:
	 * 		alt-tab
	 * 		windows-tab
	 * 		taskmanager focus
	 * 
	 * @author Richousrick
	 */
	class LockWindowFocus extends WindowAdapter{
	
		@Override
		public void windowLostFocus(WindowEvent e) {
			frame.requestFocus();
		}
	}
}
