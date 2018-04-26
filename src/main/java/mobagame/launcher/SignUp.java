/**
 * Katelynn Morrison
 * Apr 26, 2018
 */

package mobagame.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

@SuppressWarnings("serial")
public class SignUp extends JFrame implements ActionListener {

	public static int windowHeight = 800; // 800
	public static int windowWidth = (int) (windowHeight * 1.875); // 1500
	public static int fontSize = (int) (windowWidth / 100); // 15 // Font test

	private static String OK = "ok";
	private static String DROP = "drop";

	private static JFrame controllingFrame; // needed for dialogs

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTextField emailField;
	private JComboBox<String> questionField;
	private JTextField answerField;
	
	private static Boolean testing = false;

	public SignUp() {
		super("Sign Up");
		setSize((int) (windowWidth / 3.75), (int) (windowHeight / 1.6));
		setResizable(false);
		setFont(new Font("Arial", Font.PLAIN, fontSize)); // Font test
		// Create everything.
		String[] questions = { "What is the name of the hospital you were born at?",
				"What was the name of your first pet?", "What is your mother's maiden name?",
				"What is your fathers middle name?"};

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

		// Lay out everything.
		JPanel pane = new JPanel(new GridLayout(0, 1, 5, 5));
		pane.add(usernameLabel);
		pane.add(usernameField);
		pane.add(passwordLabel);
		pane.add(passwordField);
		pane.add(emailLabel);
		pane.add(emailField);
		pane.add(questionLabel);
		pane.add(questionField, BorderLayout.PAGE_START);
		pane.add(answerLabel);
		pane.add(answerField);
		pane.add(okButton);
		add(pane);
		
		if (testing) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
		}
		setVisible(true);
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
			String question = (String) questionField.getSelectedItem();
			if (isPasswordValid(passwordChar)) {
				if (isAvalable(username, "Username")) {
					if (isEmailValid(email)) {
						if (isAvalable(email, "Email")) {
							JOptionPane.showMessageDialog(controllingFrame, "Username: " + username + " Password: "
									+ password + " Email: " + email + " Question: " + question + " Answer: " + answer);
							// TODO Send info above to database
						}
					}
				}
			}

			// Zero out the possible password, for security.
			Arrays.fill(passwordChar, '0');

		} else if (DROP.equals(cmd)) {

		} else {
			JOptionPane.showMessageDialog(controllingFrame, "Invalid password. Try again.", "Error Message",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean isAvalable(String check, String colume) {
		// TODO Check to see if already in database
		return true;
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
}