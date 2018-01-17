package controllers;

/**
 * Created by Hilko on 16-1-2018.
 */
public class ApplicationController {

	public final static double TIME_STEP = 1000d / 60d;

	private boolean isSimutatorRunning = true;

	private int updates = 0;

	public ApplicationController() {

		/*

		Our application is dual-threaded.
		1 thread handles the User Interface.
		The other thread handles the simulation-updates.

		 */
		new Thread(new Runnable() { // create simulation-updates thread
			@Override
			public void run() {
				ApplicationController.this.run();
			}
		}).start();
	}

	/*

	The run method is called when the second Thread is created

	We make sure that update() is called guaranteed 60 times per second.

		while true { 							// infinite

			while deltaTime >= TIME_STEP		// 60x per second
				update();

			render(); 							// as many times as possible
		}

	 */

	public void run() {

		double prevTime = System.currentTimeMillis();
		double deltaTime = 0;

		while (isSimutatorRunning) { // loop
			while (deltaTime >= TIME_STEP) {
				update();
				deltaTime -= TIME_STEP;
			}

			// todo: here render();

			double time = System.currentTimeMillis();
			deltaTime += Math.min(time - prevTime, 250);
			prevTime = time;
		}
	}

	private void update() {

		updates++;

		System.out.println(updates);

	}



}
