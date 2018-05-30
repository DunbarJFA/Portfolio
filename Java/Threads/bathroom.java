/*Small programming assignment intended to visually represent 
*use of semaphores to regulate thread access to a resource.
*
*Prompt:
* "Suppose that a university wants to show off how politically 
* correct it is by applying the U.S. Supreme Court’s ‘‘Separate 
* but equal is inherently unequal’’ doctrine to gender as well as 
* race, ending its long-standing practice of gender-segregated 
* bathrooms on campus. However, as a concession to tradition, it 
* decrees that when a woman is in a bath-room, other women may 
* enter, but no men, and vice versa.
*
* In some programming language you like, write the following 
* procedures: woman_wants_to_enter, man_wants_to_enter, woman_leaves,
* man_leaves. You may use whatever counters and synchronization 
* techniques you like."
*
*
*In this case, the resource is a set of gender neutral bathrooms,
*and the threads attempting to access the resource are "men" and 
*"women." The access rule is that any one sex may enter if the
*"bathroom" is vacant or contains a member of that same sex. 
*Otherwise, the "person" must wait for an available "bathroom."
*
*Program is written in Java.
*
*There is no input required from the user.
*
*The output consists of printed statements that indicate  
*
*@author Jerod Dunbar
*@version 3/21/18
*/
import java.util.Random;
import java.util.concurrent.Semaphore;

public class bathroom{
		private static int mCount = 0;
		private static int wCount = 0;
		private static Semaphore manLock;
		private static Semaphore womanLock;
		private static Semaphore bathroom;
		static Random rand = new Random();

	public static void main (String[] args){
		manLock = new Semaphore(0, true);
		womanLock = new Semaphore(0, true);
		bathroom = new Semaphore(1);
		
		Thread manEnter = new Thread(new person()){
			public void run(){
				try{
					Thread.currentThread().sleep(rand.nextInt(10000));
					for (int i = 0; i < 10; i++){
						bathroom.acquireUninterruptibly();
						man_wants_to_enter();
						man_leaves();
					}
				}catch(InterruptedException e){
					Thread.currentThread().interrupt();
				}
			}
		};
		
		Thread womanEnter = new Thread(new person()){	
			public void run(){
				try{
					Thread.currentThread().sleep(rand.nextInt(10000));
					for (int i = 0; i < 10; i++){
						bathroom.acquireUninterruptibly();
						woman_wants_to_enter();
						woman_leaves();
					}
				}catch(InterruptedException e){
					Thread.currentThread().interrupt();
				}
			}
		};

		manEnter.start();
		womanEnter.start();
		
	}//end of main
	public static synchronized void woman_wants_to_enter(){
		if (womanLock.availablePermits() == 0){//if woman semaphore unlocked, enter bathroom
				wCount++;
			manLock.release();//increment man's semaphore
			System.out.println("A woman enters!");
		}
	}//end of woman enter
	public static synchronized void man_wants_to_enter(){
		if (manLock.availablePermits() == 0){//if man semaphore unlocked, enter bathroom
					mCount++;
				womanLock.release();//increment woman's semaphore
				System.out.println("A man enters!");
		}
	}//end of man enter
	public static synchronized void woman_leaves() throws InterruptedException{
		if(manLock.availablePermits() > 0){
			System.out.println("Woman leaving!");
			manLock.acquire();//decrement man's semaphore
			wCount--;
			if (wCount == 0){
				bathroom.release();
			}
		}
	}//end of woman leaves
	public static synchronized void man_leaves() throws InterruptedException{
		if(womanLock.availablePermits() > 0){
			System.out.println("Man leaving!");
			womanLock.acquire();//decrement woman's semaphore
			mCount--;
			if (mCount == 0){
				bathroom.release();
			}
		}
	}//end of man leaves
}//end of class bathroom
class person implements Runnable{
	person(){
		//basic constructor
	}
	public void run(){
		//empty method to be overridden at construction
	}
}//end of class person