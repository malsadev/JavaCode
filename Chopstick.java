public class Chopstick {
	private int ID;
	// hint: use a local variable to indicate whether the chopstick is free
	// (lying on the table), e.g. private boolean free;

	private boolean free = true;

	Chopstick(int ID) {
		this.ID = ID;

	}

	synchronized boolean take() throws InterruptedException {

		if (!free) {
			wait(1000);

			if (!free) {
				return false; // could not get chopstick
			}

		}

		free = false;
		return true; // got chopstick
	}

	synchronized void release() throws InterruptedException {

		free = true;

		notify();

	}

	public int getID() {
		return (ID);
	}
}
