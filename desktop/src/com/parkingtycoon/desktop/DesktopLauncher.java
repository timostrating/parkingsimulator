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

/**
 * @author GGG
 * @version 1.0
 * A class that launches the SplashScreen and after that it close it and launches the Game
 */

public class DesktopLauncher extends JComponent implements ActionListener {

	private int xCoordinaat = 650;
	private int yCoordinaat = 250;
	private int k = 0;

	private static Timer t;
	private static JFrame window;

	/**
	 * Painting the background image, moving car and the logo on the screen.
	 * Overriding the moving car so that it looks like it is driving from right to left in a parking garage
	 * @param t the object Timer
	 * @param window the object of a JFrame
	 * @see   Image
	 */
	public static void main (String[] arg) {
		// launching the DesktopLauncher
		DesktopLauncher h = new DesktopLauncher();
		// Open the sound file to hear in the background when the SplashScreen appears
		File Clap = new File("core/assets/splashscreen/honk-sound.wav");
		// Playing the sound
		PlaySound(Clap);
		// Create a new JFrame called Parking Simulator and setting preferences and add the DesktopLauncher
		window = new JFrame("Parking Simulator");
		window.setPreferredSize(new Dimension(640,360));
		window.setUndecorated(true);
		window.add(h);
		window.pack();
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		// Create new Timer
		t = new Timer(10, h);
		startTimer(true);
	}

	/**
	 * Painting the background image, moving car and the logo on the screen.
	 * Overriding the moving car so that it looks like it is driving from right to left in a parking garage
	 * @see Image background, Image car and Image logo
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// image of the garage used as background of the splashscreen
		Image background;
		background = new ImageIcon("core/assets/splashscreen/garage-background.png").getImage();
		g.drawImage(background, 0, 0, this);

		// image of the car that moves from right to left at the bottom of the screen
		Image car;
		car = new ImageIcon("core/assets/splashscreen/moving-car.png").getImage();
		g.drawImage(car, xCoordinaat, yCoordinaat, this);

		//image of logo displayed in the top middle of the screen
		Image logo;
		logo = new ImageIcon("core/assets/splashscreen/parkingsimulatortycoon-logo.png").getImage();
		g.drawImage(logo, 190, 10, this);
	}

	/**
	 * Method that starts or stop the timer depending if on is true or false
	 * @param on true to turn on and false to turn off
	 */
	public static void startTimer(boolean on){
		// Start Timer if boolean on is true
		if(on == true){ t.start(); }
		// Stop Timer if boolean on is false
		else if (on == false) { t.stop(); }
		// Do nothing
		else { }
	}

	/**
	 * Method that turns of the splashscreen and starts the game.
	 */
	public void startSimulation(){
		// Stop Timer
		startTimer(false);
		// Hide JFrame window
		window.setVisible(false);
		// Create object config
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// Launch config and start the Game
		new LwjglApplication(new Game(), config);
	}

	/**
	 * ActionEvent that changes every 10 milliseconds the car forward
	 * When the car is off the screen on the left side it appears again on the right side.
	 * The car will appear 5 times, after that the method startSimulation is called.
	 * @param xCoordinaat the position of the car will be raised with 5 every update
	 * @parak k           the times the car has past, if it is 5 or more the method startSimulation is called.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Decrease xCoordinaat with 5
		xCoordinaat -= 5;
		// When the car is off the screen it appears again on the other side
		if (xCoordinaat < -115) {
			xCoordinaat = 650;
			k +=1;
			System.out.println(k);
			if(k >= 5){ startSimulation(); }
		}
		repaint();
	}
	/**
	 * Opening and playing a sound, you will hear this when the SplashScreen appears
	 * @param Sound the filepath of the sound you hear when the SplashScreen appears
	 */
	static void PlaySound(File Sound){
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
		}
		catch(Exception e) { }
	}
}
