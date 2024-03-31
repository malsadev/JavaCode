public class Philosopher extends Thread {
	private GraphicTable table;
	private Chopstick left;
	private boolean gotLeft = false;
	private Chopstick right;
	private boolean gotRight = false;
	private int ID;
	final int timeThink_max = 5000;
	final int timeNextFork = 100;
	final int timeEat_max = 5000;

	Philosopher(int ID, GraphicTable table, Chopstick left, Chopstick right) {
		this.ID = ID;
		this.table = table;
		this.left = left;
		this.right = right;
		setName("Philosopher " + ID);
	}

	public void run() {
		while (true) {

			// Tell the table GUI that I am thinking
			table.isThinking(ID);
			// Print to console that I am thinking
			System.out.println(getName() + " thinks");

			// Let the thread sleep (in order to simulate thinking time)
			try {
				sleep((long) (Math.random() * timeThink_max));
			} catch (InterruptedException e) {
				System.out.println(e);
			}

			// Done with thinking
			System.out.println(getName() + " finished thinking");

			// and now I am hungry!
			System.out.println(getName() + " is hungry");
			// Tell the GUI I am hungry...
			table.isHungry(ID);

			if (!gotLeft) {
				// Let's try to get the left chopstick
				System.out.println(getName() + " wants left chopstick");
				try {
					gotLeft = left.take();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (gotLeft) {
				// Tell the GUI that I took the left chopstick
				table.takeChopstick(ID, left.getID());
				System.out.println(getName() + " got left chopstick");
			} else {
				System.out.println(getName() + " could not get left chopstick");
				try {

					if (gotRight) {
						right.release();
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// I'll wait a bit before I try to get the next chopstick (it's philosopher's
			// etiquette)
			try {
				sleep(timeNextFork);
			} catch (InterruptedException e) {
				System.out.println(e);
			}

			if (!gotRight) {
				// Ok, enough etiquette nonesense, now I need my right chopstick
				System.out.println(getName() + " wants right chopstick");
				try {
					gotRight = right.take();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (gotRight) {
				// Tell the GUI that I took the right chopstick
				table.takeChopstick(ID, right.getID());
				System.out.println(getName() + " got right chopstick");
			} else {
				System.out.println(getName() + " could not get right chopstick");
				try {

					if (gotLeft) {
						left.release();
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (gotLeft && gotRight) {
				// Sweet taste of steamed rice....
				System.out.println(getName() + " eats");
				try {
					sleep((long) (Math.random() * timeEat_max));
				} catch (InterruptedException e) {
					System.out.println(e);
				}

				// Ok, I am really full now
				System.out.println(getName() + " finished eating");

				// I just realized I did not wash these chopsticks
				// and the philosopher on my right is coming down with a flu

				// I'll release the left chopstick
				table.releaseChopstick(ID, left.getID());
				try {
					left.release();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(getName() + " released left chopstick");

				// I'll release the right chopstick
				table.releaseChopstick(ID, right.getID());
				try {
					right.release();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(getName() + " released right chopstick");
			}

		}
	}
}
