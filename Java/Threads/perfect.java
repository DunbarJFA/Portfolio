/*
*Small programming assignment designed to exemplify the use of
*multiple threads to speed up computation.
*
*Prompt:
* "Implement a multithreaded solution to find if a given number is
* a perfect number. N is a perfect number if the sum of all its 
* factors, excluding itself, is N; examples are 6 and 28 The input 
* is an integer, N. The output is true if the number is a perfect 
* number and false otherwise. The main program will read the numbers
* N and P from the command line. The main process will spawn a set 
* of P threads. The numbers from 1 to N will be partitioned among 
* these threads so that two threads do not work on the same number. 
* or each number in this set, the thread will determine if the number
* is a factor of N. If it is, it adds the number to a shared buffer 
* that stores factors of N. The parent process waits till all the
* threads complete. Use the appropriate synchronization primitive 
* here. The parent will then determine if the input number is perfect,
* that is, if N is a sum of all its factors andthen report accordingly."
*
*This program is written in Java.
*
*The program requires two positive integers (N and P, respectively) 
*as commandline arguments. N is the number to be tested, P is the 
*number of threads to create and utilize. 
*
*The output is a printed list of the factors of the number in question
*followed by the boolean determination of whether the test number is
*a perfect number.
*
*@author Jerod Dunbar
*@version 3/21/18
*/
import java.lang.Math;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

public class perfect{
	static int n;
	static int p;
	static int result;
	static ArrayList<Integer> factors;
	static ArrayList<Thread> threads;
	static ArrayList<Integer> potentialFactors;
	static Iterator<Integer> iter;
	static Semaphore lock;
	public static void main (String[] args){
		if(args.length > 0){	
			n = Integer.parseInt(args[0]); 
			p = Integer.parseInt(args[1]);
		}
		result = 0;
		factors = new ArrayList<Integer>();
		threads = new ArrayList<Thread>(p);
		potentialFactors = new ArrayList<Integer>(n);
		lock = new Semaphore(1);
		for(int i = 1; i <= n/2; i++){
			potentialFactors.add(i);
		}

		iter = potentialFactors.iterator();

		for(int i = 0; i < p; i++){
			threads.add(new Thread(new IntHolder()));
		}

		for(Thread t:threads){
			t.start();	
		}

		for(Thread t:threads){
			try{
				t.join();
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}		
		}


		for(int i : factors){
			if(i != n){
				result = result + i;
			}
			System.out.println("factor: " + i);
		}

		if (result == n){
			System.out.println("Perfect Number: True");
		}
		else{
			System.out.println("Perfect Number: False");
		}
	}//end of main
}//end of class perfect
class IntHolder implements Runnable{
	int myInt;
	int num = perfect.n;
	IntHolder(){}//placeholder constructor
	public void run(){
		try{
			perfect.lock.acquire();
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
		}
		if(perfect.iter.hasNext()){
			myInt = perfect.iter.next();
		}		
		perfect.lock.release();
		test(myInt);
	}//end of run
	public void test(int potentialFactor){		
		if (potentialFactor != 0 && num%potentialFactor == 0 && perfect.factors.contains(potentialFactor)==false){
			perfect.factors.add(potentialFactor);
		}
		if (perfect.iter.hasNext()){
			try{
				Thread.sleep(500);
				perfect.lock.acquire();
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}			
			if(perfect.iter.hasNext()){	
				myInt = perfect.iter.next();
			}
			perfect.lock.release();
			test(myInt);
		}
	}
}//end of class intHolder