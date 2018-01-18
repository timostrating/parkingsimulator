package parkingsimulator;

/**
 * Created by Hilko on 18-1-2018.
 */
public class Settings {

	private static double subscribers;
	private static double gender;
	private static double doubleParked;

	public static double getGender() {
		return gender;
	}

	public static void setGender(double gender) {
		Settings.gender = gender;
	}

	public static double getDoubleParked() {
		return doubleParked;
	}

	public static void setDoubleParked(double doubleParked) {
		Settings.doubleParked = doubleParked;
	}

	public static double getSubscribers() {
		return subscribers;
	}

	public static void setSubscribers(double subscribers) {
		Settings.subscribers = subscribers;
	}


}
