package mobagame.launcher;

//Carson Mango
// We decided to bypass this part since we only have one character at the moment

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import org.omg.PortableServer.ServantRetentionPolicyValue;

import mobagame.core.game.GameTeams;
import mobagame.core.game.InGamePlayer;
import mobagame.core.networking.packets.CharacterSelectShowPlayer;
import mobagame.core.networking.packets.DEBUG_ClientForceStartGame;

import mobagame.core.game.GameCharcters;
import mobagame.core.networking.packets.RequestEnterGameResponsePacket;
import mobagame.launcher.networking.RspHandler;
import mobagame.launcher.networking.ServerConnection;
import mobagame.server.database.PlayerAccount;

public class CharSelect implements Runnable, MobaGameLauncher {
	final String PLAYER_NAME_TEMP_STRING = " Temp";
	private int timeLeft = 90;
	private JLabel timer = new JLabel("" + 90);
	ArrayList<InGamePlayer> teamOne = new ArrayList<>();
	ArrayList<InGamePlayer> teamTwo = new ArrayList<>();

	JFrame selectionScreen = new JFrame("Character Select");

	JPanel blueTeamSelect = new JPanel();
	JPanel charSelectMenu = new JPanel();
	JPanel redTeamSelect = new JPanel();
	JPanel charStats = new JPanel();
	JPanel blue1 = new JPanel();
	JPanel blue2 = new JPanel();
	JPanel blue3 = new JPanel();
	JPanel blue4 = new JPanel();
	JPanel blue5 = new JPanel();
	JPanel red1 = new JPanel();
	JPanel red2 = new JPanel();
	JPanel red3 = new JPanel();
	JPanel red4 = new JPanel();
	JPanel red5 = new JPanel();

	JLabel blue1User;
	JLabel blue2User;
	JLabel blue3User;
	JLabel blue4User;
	JLabel blue5User;
	JLabel red1User;
	JLabel red2User;
	JLabel red3User;
	JLabel red4User;
	JLabel red5User;


	JScrollPane JSP = new JScrollPane(charSelectMenu);
	public ImageIcon placeHolderImage = new ImageIcon("resources/Black.png");
	public ImageIcon reaperCharPic = new ImageIcon("resources/Character/Reaper.png");
	public ImageIcon jackCharPic = new ImageIcon("resources/Character/Jack.png");
	JButton startButton;
	InGamePlayer player;
	ServerConnection conn;
	Thread thisThread;
	boolean gameStarted = false;

	//thread to run the countdown timer
	public void run() {
		long startTime = System.currentTimeMillis();
		System.out.println("countdown timer started");
		while (timeLeft > 0) {
			try {
				Thread.sleep(100); // 10 times per second should be enough
				RequestEnterGameResponsePacket game = (RequestEnterGameResponsePacket) RspHandler.getInstance().getResponse(RequestEnterGameResponsePacket.class);
				if (game != null) {
					selectionScreen.setVisible(false);
					new GameScreen(game.gameID, playerAcc, player, GameCharcters.reaper, teamOne);
					gameStarted = true;
					return;
				}
				CharacterSelectShowPlayer cssp = (CharacterSelectShowPlayer) RspHandler.getInstance().getResponse(CharacterSelectShowPlayer.class);
				if (cssp != null) {
					if (cssp.teamID == 0) {
						teamOne.add(new InGamePlayer(cssp.playerID, cssp.playerUsername, GameTeams.gameTeams[cssp.teamID]));
					} else {
						teamTwo.add(new InGamePlayer(cssp.playerID, cssp.playerUsername, GameTeams.gameTeams[cssp.teamID]));
					}
				}

			} catch (InterruptedException e) {
			}
			timer.setText("" + timeLeft);
			System.out.println(timeLeft);
			timeLeft--;
			selectionScreen.setVisible(true);
//			gameStarted = true;
			if (gameStarted) return;
		}
//		RequestEnterGamePacket req = new RequestEnterGamePacket(player.id, 1);
//		RspHandler h = RspHandler.getInstance();
//			conn.send(req.getBytes().array(), h);
//			h.waitForResponse();
		RspHandler.getInstance().waitForResponse();
		selectionScreen.setVisible(false);

		RequestEnterGameResponsePacket game = (RequestEnterGameResponsePacket) RspHandler.getInstance().getResponse(RequestEnterGameResponsePacket.class);
		teamOne.addAll(teamTwo);
		new GameScreen(game.gameID, playerAcc, player, GameCharcters.reaper, teamOne);
		return;

	}

	//Meathod to start countdown timer thread
	public void start() {
		thisThread = new Thread(this);
		thisThread.start();
	}

	private GridBagConstraints gbc = new GridBagConstraints();
	private PlayerAccount playerAcc;

	public CharSelect(PlayerAccount acc, InGamePlayer player, int lobbyID) {

		this.player = player;
		try {
			conn = ServerConnection.getInstance(ServerConnection.ip, ServerConnection.port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		playerAcc = acc;
		charSelectMenu.setLayout(new GridLayout(10, 10));
		JSP.setSize((int) (WINDOW_WIDTH / 1.5), (int) (WINDOW_HEIGHT / 2));
		JSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//Setting up character select menu
		blueTeamSelect.setSize((int) (WINDOW_WIDTH / 2.6), (int) (WINDOW_HEIGHT / 3.125));
		redTeamSelect.setSize((int) (WINDOW_WIDTH / 2.6), (int) (WINDOW_HEIGHT / 3.125));
		blue1.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_HEIGHT / 3.125));
		blue2.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_HEIGHT / 3.125));
		blue3.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_HEIGHT / 3.125));
		blue4.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_HEIGHT / 3.125));
		blue5.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_HEIGHT / 3.125));
		red1.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_HEIGHT / 3.125));
		red2.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_HEIGHT / 3.125));
		red3.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_HEIGHT / 3.125));
		red4.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_HEIGHT / 3.125));
		red5.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_HEIGHT / 3.125));
		JLabel blue1CharImage = new JLabel(placeHolderImage);
		JLabel blue2CharImage = new JLabel(reaperCharPic);
		JLabel blue3CharImage = new JLabel(jackCharPic);
		JLabel blue4CharImage = new JLabel(placeHolderImage);
		JLabel blue5CharImage = new JLabel(placeHolderImage);
		JLabel red1CharImage = new JLabel(placeHolderImage);
		JLabel red2CharImage = new JLabel(placeHolderImage);
		JLabel red3CharImage = new JLabel(placeHolderImage);
		JLabel red4CharImage = new JLabel(placeHolderImage);
		JLabel red5CharImage = new JLabel(placeHolderImage);
		red5CharImage.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel test = new JLabel(reaperCharPic);
		JLabel test2 = new JLabel("Hi");
		blue1CharImage.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_WIDTH / 10));
		blue2CharImage.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_WIDTH / 10));
		blue3CharImage.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_WIDTH / 10));
		blue4CharImage.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_WIDTH / 10));
		blue5CharImage.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_WIDTH / 10));
		red1CharImage.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_WIDTH / 10));
		red2CharImage.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_WIDTH / 10));
		red3CharImage.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_WIDTH / 10));
		red4CharImage.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_WIDTH / 10));
		red5CharImage.setSize((int) (WINDOW_WIDTH / 10), (int) (WINDOW_WIDTH / 10));
		blue1User = new JLabel("  " + acc.getUsername());
		blue2User = new JLabel(PLAYER_NAME_TEMP_STRING);
		blue3User = new JLabel(PLAYER_NAME_TEMP_STRING);
		blue4User = new JLabel(PLAYER_NAME_TEMP_STRING);
		blue5User = new JLabel(PLAYER_NAME_TEMP_STRING);
		red1User = new JLabel(PLAYER_NAME_TEMP_STRING);
		red2User = new JLabel(PLAYER_NAME_TEMP_STRING);
		red3User = new JLabel(PLAYER_NAME_TEMP_STRING);
		red4User = new JLabel(PLAYER_NAME_TEMP_STRING);
		red5User = new JLabel(PLAYER_NAME_TEMP_STRING);
		red1User.setHorizontalAlignment(JLabel.RIGHT);
		red2User.setHorizontalAlignment(JLabel.RIGHT);
		red3User.setHorizontalAlignment(JLabel.RIGHT);
		red4User.setHorizontalAlignment(JLabel.RIGHT);
		red5User.setHorizontalAlignment(JLabel.RIGHT);
		selectionScreen.setLayout(new GridBagLayout());
		selectionScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectionScreen.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		blueTeamSelect.setLayout(new GridLayout(5, 1, 5, 5));
		redTeamSelect.setLayout(new GridLayout(5, 1, 5, 5));
		blue1.setLayout(new GridLayout(1, 2, 0, 0));
		blue2.setLayout(new GridLayout(1, 2, 0, 0));
		blue3.setLayout(new GridLayout(1, 2, 0, 0));
		blue4.setLayout(new GridLayout(1, 2, 0, 0));
		blue5.setLayout(new GridLayout(1, 2, 0, 0));
		red1.setLayout(new GridLayout(1, 2, 0, 0));
		red2.setLayout(new GridLayout(1, 2, 0, 0));
		red3.setLayout(new GridLayout(1, 2, 0, 0));
		red4.setLayout(new GridLayout(1, 2, 0, 0));
		red5.setLayout(new GridLayout(1, 2, 0, 0));
		blue1.add(blue1CharImage);
		blue2.add(blue2CharImage);
		blue3.add(blue3CharImage);
		blue4.add(blue4CharImage);
		blue5.add(blue5CharImage);
		red1.add(red1User);
		red2.add(red2User);
		red3.add(red3User);
		red4.add(red4User);
		red5.add(red5User);
		red1.add(red1CharImage);
		red2.add(red2CharImage);
		red3.add(red3CharImage);
		red4.add(red4CharImage);
		red5.add(red5CharImage);
		blue1.add(blue1User);
		blue2.add(blue2User);
		blue3.add(blue3User);
		blue4.add(blue4User);
		blue5.add(blue5User);
		selectionScreen.setResizable(false);
		blueTeamSelect.add(blue1);
		blueTeamSelect.add(blue2);
		blueTeamSelect.add(blue3);
		blueTeamSelect.add(blue4);
		blueTeamSelect.add(blue5);
		redTeamSelect.add(red1);
		redTeamSelect.add(red2);
		redTeamSelect.add(red3);
		redTeamSelect.add(red4);
		redTeamSelect.add(red5);
		charSelectMenu.add(test);
		JSP.add(charSelectMenu);
		charStats.add(test2);
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.gridwidth = 3;
		selectionScreen.add(timer, gbc);
		gbc.gridheight = 2;
		gbc.gridwidth = 1;
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.WEST;
		selectionScreen.add(blueTeamSelect, gbc);
		gbc.gridx = 2;
		gbc.anchor = GridBagConstraints.EAST;
		selectionScreen.add(redTeamSelect, gbc);
		gbc.gridx = 1;
		//Don't question the majestic number it was needed to center the char select menu
		gbc.weightx = 23401051;
		gbc.anchor = GridBagConstraints.CENTER;
		selectionScreen.add(JSP, gbc);
		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.weightx = 1;
		selectionScreen.add(charStats, gbc); // new line
		startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: second arguement is character id
				System.out.println("sending with player id " + player.getPlayerID());
				DEBUG_ClientForceStartGame req = new DEBUG_ClientForceStartGame();
				req.lobbyID = lobbyID;
				RspHandler h = RspHandler.getInstance();
				try {
					conn.send(req.getBytes().array(), h);
					h.waitForResponse();
					RequestEnterGameResponsePacket game = (RequestEnterGameResponsePacket) h.getResponse(RequestEnterGameResponsePacket.class);
					do {
						h.waitForResponse();
						game = (RequestEnterGameResponsePacket) h.getResponse(RequestEnterGameResponsePacket.class);

					} while (game == null);
					System.out.println(game.playerID);
					timeLeft = 1;
					gameStarted = true;
					new GameScreen(game.gameID, acc, player, GameCharcters.reaper, teamOne);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		gbc.gridy = 4;
		selectionScreen.add(startButton);
		selectionScreen.setVisible(true);
		start();
	}
}