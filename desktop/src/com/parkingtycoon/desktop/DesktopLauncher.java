package com.parkingtycoon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.parkingtycoon.Game;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DesktopLauncher extends JComponent implements ActionListener {

	private int xCoordinaat = 650;
	private int yCoordinaat = 250;
	private int k = 0;

	private static Timer t;
	private static JFrame window;

	public static void main (String[] arg) {
		DesktopLauncher h = new DesktopLauncher();
		File Clap = new File("core/assets/splashscreen/honk-sound.wav");
		PlaySound(Clap);
		window = new JFrame("Parking Simulator");
		window.setPreferredSize(new Dimension(640,360));
		window.setUndecorated(true);
		window.add(h);
		window.pack();
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		t = new Timer(10, h);
		startTimer(true);
	}

	public Dimension getPreferredSize() { return new Dimension(640, 360); }

	@Override
	protected void paintComponent(Graphics g) {
		Image background;
		background = new ImageIcon("core/assets/splashscreen/garage-background.png").getImage();
		//image
		g.drawImage(background, 0, 0, this);

		Image car;
		car = new ImageIcon("core/assets/splashscreen/moving-car.png").getImage();
		//image
		g.drawImage(car, xCoordinaat, yCoordinaat, this);

		Image logo;
		logo = new ImageIcon("core/assets/splashscreen/parkingsimulatortycoon-logo.png").getImage();
		//image
		g.drawImage(logo, 190, 10, this);
	}

	public static void startTimer(boolean aan){
		if(aan == true){ t.start(); }
		else if (aan == false) { t.stop(); }
		else { }
	}

	public void startSimulation(){
		startTimer(false);
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Game(), config);
		System.out.println("iets");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		xCoordinaat += -5;
		if (xCoordinaat < -115) {
			xCoordinaat = 650;
			k +=1;
			System.out.println(k);
			if(k >= 5){ startSimulation(); }
		}
		repaint();
	}
	static void PlaySound(File Sound){
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
		}
		catch(Exception e) { }
	}
}
