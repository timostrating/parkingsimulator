package controllers;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Hilko on 16-1-2018.
 */
public class ApplicationController {

	public final static double TIME_STEP = 1000d / 60d;

	private boolean isSimutatorRunning = true;

	private int updates = 0;


	// Singleton
	private static ApplicationController instance = null;

	public static ApplicationController getInstance() {
		if(instance == null) {
			instance = new ApplicationController();
		}
		return instance;
	}


	// Can use CopyOnWriteArraySet too make it thread-safe
	private final Set<Listener> mListeners = Collections.newSetFromMap(
			new ConcurrentHashMap<Listener, Boolean>(0));

	/** This method adds a new Listener */
	public void registerListener(Listener listener) {
		if (listener == null) return;
		mListeners.add(listener);
	}

	/** This method removes an Listener */
	public void unregisterListener(Listener listener) {
		if (listener != null) {
			mListeners.remove(listener);
		}
	}

	/** This method notifies currently registered listeners about the change */
	private void notifyListeners() {
		for (Listener listener : mListeners) {
			listener.onObservableChanged();
		}
	}


	public ApplicationController() {
		if(instance == null)
			instance = this;

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

		notifyListeners();
	}
}
