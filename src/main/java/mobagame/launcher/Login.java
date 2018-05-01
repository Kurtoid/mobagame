package mobagame.launcher;
//Carson Mango 4/24/18
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import mobagame.core.DebugSettings;
import mobagame.core.networking.packets.LoginPacket;

public class Login implements ActionListener{
	public JFrame login = new JFrame("Welcome to _______________________");
	public JPanel picture = new JPanel();
	public JPanel boxes = new JPanel();
	public JTextField Username = new JTextField("");
	public JTextField Password = new JPasswordField("");
	private GridBagConstraints gbc = new GridBagConstraints();
	public JFrame forgotPassword = new JFrame("Forgot Password");
	public JTextField email = new JTextField("");
	public JButton fogotButto = new JButton("Forgot Password");
	public JButton loginButto = new JButton("Login");
	public JButton createAccButto = new JButton("Create Account");
	public JTextField secureQuestion = new JTextField("");
	DebugSettings state;
	ServerConnection conn;
	//private PlayerAccount temp;
	//PlayerAccountDBO playerDBO = new PlayerAccountDBO();
	Login(){
//		state = DebugSettings.getInstance();
//		if(state.isServerEnabled){
//			conn = new ServerConnection();
//			try {
//				conn.initConnect("localhost", 8666);
//				conn.start();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		//Creates all of the windows
		forgotPassword.setLayout(new GridLayout(5, 1, 6, 6));
		forgotPassword.setAlwaysOnTop(true);
		forgotPassword.setSize(400,250);
		JLabel emailIndicator = new JLabel("Enter Email");
		emailIndicator.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel question = new JLabel("Security Question");
		JButton getPassword = new JButton("Get Password");
		getPassword.addActionListener(this);
		forgotPassword.add(emailIndicator);
		forgotPassword.add(email);
		forgotPassword.add(question);
		forgotPassword.add(secureQuestion);
		forgotPassword.add(getPassword);
		forgotPassword.setResizable(false);
		login.setLayout(new GridLayout(2, 1, 5, 5));
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setSize(1500, 800);
		login.setResizable(false);
		picture.setLayout(new GridBagLayout());
		picture.setSize(1500, 750);
		boxes.setLayout(new GridBagLayout());
		boxes.setSize(1500, 50);
		fogotButto.addActionListener(this);
		loginButto.addActionListener(this);
		createAccButto.addActionListener(this);
		ImageIcon icon1 = new ImageIcon("F://ImpressMeProject/pixil-frame-0.png");
		JLabel a = new JLabel("Username:");
		JLabel b = new JLabel("Password:");
		JLabel e = new JLabel("");
		JLabel f = new JLabel("");
		JLabel g = new JLabel("");
		JLabel h = new JLabel("");
		JLabel j = new JLabel("");
		JLabel k = new JLabel("");
		JLabel l = new JLabel("");
		JLabel m = new JLabel("");
		JLabel lb2 = new JLabel(icon1);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 3;
		picture.add(lb2, gbc);
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 2.5;
		gbc.gridwidth = 1;
		boxes.add(h, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 2;
		boxes.add(a, gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 7;
		boxes.add(Username, gbc);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.weightx = 2.5;
		boxes.add(g, gbc);
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.weightx = 2;
		boxes.add(b, gbc);
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.weightx = 7;
		boxes.add(Password, gbc);
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.weightx = 2;
		boxes.add(m, gbc);
		gbc.gridx = 7;
		gbc.gridy = 0;
		gbc.weightx = 5;
		gbc.weighty = .75;
		gbc.fill = GridBagConstraints.BOTH;
		boxes.add(fogotButto, gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 8;
		gbc.gridy = 0;
		gbc.weightx = 2.5;
		gbc.weighty = 1;
		gbc.gridheight = 1;
		boxes.add(e, gbc);
		gbc.gridx = 9;
		gbc.gridy = 0;
		gbc.weightx = 2.5;
		boxes.add(f, gbc);
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.weightx = 1;
		gbc.gridwidth = 2;
		boxes.add(j, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 2;
		gbc.gridwidth = 3;
		gbc.weighty = 1.5;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		boxes.add(loginButto, gbc);
		gbc.gridy = 3;
		gbc.gridx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
		boxes.add(k, gbc);
		gbc.gridy = 2;
		gbc.gridx = 5;
		gbc.gridwidth = 3;
		gbc.weightx = 2.5;
		boxes.add(l, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = 2;
		gbc.gridx = 7;
		gbc.weightx = 5;
		gbc.weighty = .75;
		boxes.add(createAccButto, gbc);
		gbc.gridx = 6;
		gbc.gridy = 2;
		gbc.weightx = 1.5;
		gbc.weighty = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		boxes.add(m, gbc);
		login.add(picture);
		login.add(boxes);
		login.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals("Login")) {
			String User = Username.getText();
			String pass = Password.getText();
			//if(state.isServerEnabled) {
				//LoginPacket p = new LoginPacket(User, pass);
				//conn.queuePacket(p);
			//}
			/*try {
				temp = playerDBO.loginAccount(User, pass);
			}catch(SQLException e) {
				JOptionPane.showMessageDialog(login, "Invalid Username or Password", "ERROR", JOptionPane.ERROR_MESSAGE);
			}*/
			//to do check database for if this is a valid user and their correct password then if it is valid log them in
			//and send them to the main menu
			//Add if statement to check if use is admin
			new Menu(User, true);
			login.setVisible(false);
		}else if(ae.getActionCommand().equals("Forgot Password")) {
			//opens the menu to get your password back
			forgotPassword.setVisible(true);
			//Enter email and database checks the email and sends that accounts password
		}else if(ae.getActionCommand().equals("Create Account")) {
			//Sends user to create account page
			new SignUp();
		}else if(ae.getActionCommand().equals("Get Password")) {
			//If password is valid send password to email display message that the email has been sent and then go to login menu
			//if password is invalid say it is invalid then close to the login menu
			JOptionPane.showMessageDialog(forgotPassword, "Invalid Email", "ERROR", JOptionPane.ERROR_MESSAGE);
			email.setText("");
			loginButto.setEnabled(true);
			fogotButto.setEnabled(true);
			createAccButto.setEnabled(true);
			forgotPassword.setVisible(false);
		}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Login();
			}
		});
	}
}
