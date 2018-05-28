/**
 * Katelynn Morrison
 */

package mobagame.launcher;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import mobagame.core.networking.packets.SignupPacket;
import mobagame.core.networking.packets.SignupResponsePacket;
import mobagame.launcher.networking.RspHandler;
import mobagame.launcher.networking.ServerConnection;

@SuppressWarnings("serial")
public class SignUp extends JFrame implements ActionListener, MobaGameLauncher  {

	private static String OK = "ok";
	private static String DROP = "drop";

	private static JFrame controllingFrame; // needed for dialogs
	private static boolean testing = false;
	ServerConnection conn;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTextField emailField;
	private JComboBox<String> questionField;
	private JTextField answerField;

	public SignUp() {
		super("Sign Up");
		try {
				conn = ServerConnection.getInstance("localhost", 8666);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setSize((int) (WINDOW_WIDTH / 3.75), (int) (WINDOW_HEIGHT / 1.6));
		setResizable(false);

		// Create everything.
		String[] questions = { "What is the name of the hospital you were born at?",
				"What was the name of your first pet?",
				"What is your mother's maiden name?",
				"What is your fathers middle name?" };

		questionField = new JComboBox<String>(questions);
		questionField.setSelectedIndex(0);
		questionField.setActionCommand(DROP);
		questionField.addActionListener(this);

		usernameField = new JTextField(10);
		usernameField.addActionListener(this);

		passwordField = new JPasswordField(10);
		passwordField.addActionListener(this);

		emailField = new JTextField(10);
		emailField.addActionListener(this);

		answerField = new JTextField(10);
		answerField.addActionListener(this);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand(OK);
		okButton.addActionListener(this);

		JLabel usernameLabel = new JLabel("Enter your username: ");
		usernameLabel.setLabelFor(usernameField);
		JLabel passwordLabel = new JLabel("Enter your password: ");
		passwordLabel.setLabelFor(passwordField);
		JLabel emailLabel = new JLabel("Enter your email: ");
		emailLabel.setLabelFor(emailField);
		JLabel questionLabel = new JLabel("What is your security question: ");
		questionLabel.setLabelFor(questionField);
		JLabel answerLabel = new JLabel("What is your security question answer: ");
		answerLabel.setLabelFor(answerField);

		// Font Setup
		usernameLabel.setFont(MENU_FONT);
		usernameField.setFont(MENU_FONT);
		passwordLabel.setFont(MENU_FONT);
		passwordField.setFont(MENU_FONT);
		emailLabel.setFont(MENU_FONT);
		emailField.setFont(MENU_FONT);
		questionLabel.setFont(MENU_FONT);
		questionField.setFont(new Font(FONT, Font.PLAIN, (int) (FONT_SIZE / 1.5)));
		answerLabel.setFont(MENU_FONT);
		answerField.setFont(MENU_FONT);
		okButton.setFont(MENU_FONT);

		// Lay out everything.
		JPanel pane = new JPanel(new GridLayout(0, 1, 5, 5));
		pane.add(usernameLabel);
		pane.add(usernameField);
		pane.add(passwordLabel);
		pane.add(passwordField);
		pane.add(emailLabel);
		pane.add(emailField);
		pane.add(questionLabel);
		pane.add(questionField);
		pane.add(answerLabel);
		pane.add(answerField);
		pane.add(okButton);
		add(pane);

		if (testing) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		setVisible(true);
	}

	// To be valid, a password must be >= 6 chars and must only have charaters that
	// fall between char values of 23 and 126
	private static boolean isPasswordValid(char[] input) {
		boolean isValid = true;

		if (input.length <= 1) {
			JOptionPane.showMessageDialog(controllingFrame, "Your password must be at least 6 charaters long.",
					"Error Message", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		for (int x = 0; x < input.length && isValid; x++) {
			// Check is unicode value is between 32 and 126
			if (input[x] < 32 || input[x] > 126) {
				isValid = false;
			}
		}
		if (!isValid) {
			JOptionPane.showMessageDialog(controllingFrame,
					"You cannot have charaters like ê, Ω, », and ò in your password.", "Error Message",
					JOptionPane.ERROR_MESSAGE);
		}
		return isValid;
	}

	public static void main(String[] args) {
		testing = true;
		new SignUp();
	}

	public void actionPerformed(ActionEvent ae) {
		String cmd = ae.getActionCommand();

		if (OK.equals(cmd)) { // Process the password.

			char[] passwordChar = passwordField.getPassword();
			String password = "";
			for (int x = 0; x < passwordChar.length; x++) {
				password += passwordChar[x];
			}
			String username = usernameField.getText();
			String email = emailField.getText();
			String answer = answerField.getText();
			byte question = (byte) questionField.getSelectedIndex();
			if (isPasswordValid(passwordChar)) {
				if (isEmailValid(email)) {
					SignupPacket p = new SignupPacket(username, password, email, question, answer);
					RspHandler h = RspHandler.getInstance();
					try {
						conn.send(p.getBytes().array(), h);
						h.waitForResponse(3000);
						SignupResponsePacket resp = (SignupResponsePacket) h.getResponse(SignupResponsePacket.class);
						switch (resp.status) {
						case SignupResponsePacket.SUCCESSFUL:
							JOptionPane.showMessageDialog(controllingFrame, "Success! You can now log in", "Success",
									JOptionPane.INFORMATION_MESSAGE);
							break;
						case SignupResponsePacket.FAILED_EMAIL:
							JOptionPane.showMessageDialog(controllingFrame, "This email address has been used", "Error",
									JOptionPane.ERROR_MESSAGE);
							break;
						case SignupResponsePacket.FAILED_USERNAME:
							JOptionPane.showMessageDialog(controllingFrame, "This username has been used", "Error",
									JOptionPane.ERROR_MESSAGE);
							break;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TimeoutException e) {
						JOptionPane.showMessageDialog(controllingFrame, "There was an error contacting the server",
								"Oops", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
//					JOptionPane.showMessageDialog(controllingFrame, "Username: " + username + " Password: " + password
//							+ " Email: " + email + " Question: " + question + " Answer: " + answer);
				}

			} else {
				JOptionPane.showMessageDialog(controllingFrame, "Invalid password. Try again.", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			}
			// Zero out the possible password, for security.
			Arrays.fill(passwordChar, '0');

		} else if (DROP.equals(cmd)) {

		}
	}

	// To be valid, a password must be have a username, an @, and a domain
	private boolean isEmailValid(String email) {
		int numOfAtSigns = 0, numOfDots = 0;
		for (int x = 1; x < email.length() - 1; x++) {
			if (email.charAt(x) == 64) {
				numOfAtSigns++;
				x++;
			} else if (email.charAt(x) == 46) {
				numOfDots++;
			}
		}
		if (numOfAtSigns != 1 || numOfDots != 1) {
			JOptionPane.showMessageDialog(controllingFrame, "Invalid email. Try again.", "Error Message",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}