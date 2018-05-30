/*
*Small programming assignment designed to exemplify the use of
*Semaphores to regulate thread access to common resources.
*
*Prompt:
* "Three persons are involved in processing pizza orders. Person A
* reads the order from the computer and put the pizza into oven. 
* Person B takes the pizza from oven to quality control desk (QCD).
* Finally, Person C takes the pizza from QCD and hands it over to 
* the customer. (I know, this is oversimplification. Also, consider, 
* it requires no time to prepare/cook/quality-control the pizza.) 
* Assume all three persons operate on one pizza at a time, both 
* oven and QCD have capacity of one pizza. Write a program (in a
* programming language you like) to coordinate the three persons 
* using semaphores."
*
*
*In this case, three threads (A, B, and C) are organized such 
*that A and B share a common resource while B and C share a 
*common resource as well. Here, the semaphores "oven" and "qCD"
*(quality control desk) are used as bottlenecks in the system
*such that each can contain a MAXIMUM of one permit at any given
*time. This results in a sort of "assembly line" (in this case,
*a pizza assembly line) system where each thread is dependent upon
*the others to continue functioning.
*
*The program works by first creating a list of unique pizzas and
*then using three threads to bring each pizza to the oven, bring 
*the cooked pizza from the oven to the QCD, and clear the 
*completed pizza for delivery (one thread for each) using 
*semaphores to synchronize the processes.  
*
*Program is written in Java.
*
*There is no input required from the user.
*
*The output is a series of printed statements that track the status
*of each individual pizza as it runs through the assembly line.
*
*@author Jerod Dunbar
*@version 3/21/18
*/

import java.util.Random;
import java.util.concurrent.Semaphore;

public class pizza{
	static String[] pizzaList;//the list of orders
	static String[] ovenRack = new String[1];//array containing the pizza currently in the "oven"
	static String[] QCDPlate = new String[1];//array containing the pizza currently on the "QCD"
	private static Semaphore oven;//simulated binary semaphore for synchronizing A and B around the "oven"
	private static Semaphore qCD;//simulated binary semaphore for synchronizing B and C around the "QCD"
	static Random rand = new Random();
	static int count = 0;//number of pies completed
	static String aPie;//name of pie currently handled by A
	static String bPie;//name of pie currently handled by B
	static String cPie;//name of pie currently handled by C

	public static void main (String[] args){
		//generate the pizza orders
		orders();
		//make semaphores "Oven" and "qCD
		oven = new Semaphore(0);
	 	qCD = new Semaphore(0);
		//create workers A, B, and C and the threads to run them
		Thread a = new Thread(new Worker()){//create the first thread using custom Runnable class "Worker" as the target
			public void run(){//implement the Worker's run()
				for (int i = 0; i < 10; i++){//for 10 pizzas...
					aPie = takePie(i);//...pull a pizza name from pizzaList[]...
					System.out.println("Worker A: " + aPie + " ready to bake!");//...print the status...
					ovenRack[0] = aPie;//...insert the current "pizza" into an array to pass to b
					bakePie();//...release a permit to the "oven" semaphore
				}
			}
		};
		
		Thread b = new Thread(new Worker()){// create the next thread using Worker as the target, again
			public void run(){//implement run()
				for (int i = 0; i < 10; i++){//for 10 pizzas
					try{
					hotPie();//attempt to acquire a permit from the "oven" Semaphore
					bPie = ovenRack[0];//grab the pizza name from the array
					QCDPlate[0] = bPie;//prepare to pass the current pizza name on to c
					System.out.println("Worker B: " + bPie + " ready for Quality Check!");	
					qC();//release a permit to "qCD" semaphore and pass the current pizza name on to c 
					}catch(InterruptedException e){
						Thread.currentThread().interrupt();
					}
				}
			}
		};
		
		Thread c = new Thread(new Worker()){// create the next thread using Worker as the target, again
			public void run(){//implement run()
				for (int i = 0; i < 10; i++){//for 10 pizzas
					try{
					boxedPie();//attempt to acquire a permit from the "qCD" Semaphore
					count++;//increment number of delivered pizzas
					System.out.println("Worker C: " + count + " pizzas delivered!");
					}catch(InterruptedException e){
						Thread.currentThread().interrupt();
					}
				}
			}
		};

		a.start();//kickstart the thread
		b.start();//kickstart the thread
		c.start();//kickstart the thread

	}//end of main

	public static String takePie(int whichPie){
		String myPie = pizzaList[whichPie];//get pie name
		return myPie;
	}

	public static void bakePie(){
		while (oven.availablePermits() != 0){//if "oven" already contains a pizza, sleep until it is empty
			try{
				Thread.currentThread().sleep(1000);
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}	
		}
		oven.release();//release a new "pizza" (permit) to "oven"

		System.out.println("Worker A: " + aPie + " in the oven!");
	}

	public static void hotPie() throws InterruptedException{
		oven.acquire();//attempt to acquire a "pizza" (permit) from "oven"
	}

	public static void qC(){
		while(qCD.availablePermits() != 0){//while "qCD" already contains a pizza, sleep until it is empty
			try{
				Thread.currentThread().sleep(1000);
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}			}
		System.out.println("Worker B: "+bPie+" on the Quality Control Desk!");
		cPie = QCDPlate[0];
		qCD.release();//release a new "pizza" (permit) to "qCD"
	}

	public static void boxedPie() throws InterruptedException{
		qCD.acquire();//attempt to acquire a "pizza" (permit) from "qCD"
		System.out.println("Worker C: " + cPie + " out for delivery!");
	}

	public static void orders (){//creates 50 random pizza names by combining any three words from this array of Strings
		pizzaList = new String[50];
		String pizza;
		String[] types = new String[23];
		types[0] = "Pepperoni";
		types[1] = "Three Cheese";
		types[2] = "Anchovy";
		types[3] = "Margherita";
		types[4] = "Mushroom";
		types[5] = "Olives";
		types[6] = "Ham";
		types[7] = "Hawaiian";
		types[8] = "Sport Peppers";
		types[9] = "Grilled Chicken";
		types[10] = "Spinach";
		types[11] = "Garlic";
		types[12] = "Hamburger";
		types[13] = "Italian Sausage";
		types[14] = "Extra Sauce";
		types[15] = "Red Onion";
		types[16] = "Stuffed Crust";
		types[17] = "Hand-Tossed";
		types[18] = "Double Meat";
		types[19] = "Bacon";
		types[20] = "Banana Peppers";
		types[21] = "Deep Dish";
		types[22] = "Thin Crust";

		for (int i = 0; i < pizzaList.length; i++){
			pizza = types[rand.nextInt(23)]+", "+types[rand.nextInt(23)]+", "+types[rand.nextInt(23)]+" pizza";
			pizzaList[i] = pizza;
		}
	}

}//end of class Pizza
class Worker implements Runnable{
	Worker(){
		//basic constructor
	}
	public void run(){
		//empty run(), to be overridden at construction
	}
}//end of class Worker
/*Thread personA = new Thread(new Runnable(){
	public void run(){

	}
})*/