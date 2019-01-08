import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
/*
*Takes commandline argument "processes," schedules and runs them according 
*to the FirstComeFirstServed (FCFS), ShortestJobFirst (SJF), and 
*ShortestRemainingTimeNext (SRTN) scheduling algorithms. 
*
*User-made "processes" must be entered in the format "ID startTime runTime" 
*with "ID" being the name of the process, "startTime" referring to the arrival 
*time of the process, and "runTime" referring to the burst time of the process. 
*
*The first user-created "process" must have a "startTime" of 0 (simulating how 
*the scheduler does not perform until processes begin to arrive). 
*
*@author Jerod Dunbar
*@version 03/29/18
*/
public class Schedule{
	public static void main (String[] args){
		ArrayList<String> argsList = new ArrayList<String>(Arrays.asList(args));
		Iterator<String> argsIter = argsList.iterator();
		ArrayList<Process> pArrayList = new ArrayList<Process>();
		//accepts process info from command line
		while (argsIter.hasNext()){
			pArrayList.add(new Process(argsIter.next(),argsIter.next(),argsIter.next()));
		}
		
		Process[] pArray = pArrayList.toArray(new Process[0]);
		FCFS(pArray);
		SJF(pArray);
		SRTN(pArray);

	}//end of main method
public static void FCFS(Process[] list){
	//first come first served
	ArrayList<Process> aList = new ArrayList<>(Arrays.asList(list));
	int time = 0;
	int burst = 0;
	int turnaroundSum = 0;
	ArrayList<Integer> att = new ArrayList<Integer>(aList.size());
	boolean queue = true;
	Process currentProc;
	ArrayList<Process> arrived = new ArrayList<>();
	ArrayList<Process> ordered = new ArrayList<>(Arrays.asList(list));
	ArrayList<Process> reordered = new ArrayList<>(Arrays.asList(list));
	Iterator<Process> iter = null;

	if (aList.isEmpty()){	
		System.out.println("\nNo processes given.\n");
		//queue = false;
	}
	else{
		System.out.println("\nFCFS:\n");
		Collections.sort(ordered,new Comparator<Process>(){
			@Override
    		public int compare(Process a, Process b) {
        		return (Integer)a.getOrder().compareTo((Integer)b.getOrder());
    		}
		});
		for (time = 0; time < aList.size(); time++){
			currentProc = ordered.get(0);
			if (queue){	
				System.out.print("At time " + currentProc.getOrder() + ", proc " + currentProc.getName() + " arrives and is scheduled to run since the queue is empty.\n");
				arrived.add(currentProc);
				ordered.remove(0);
				iter = arrived.iterator();
				currentProc.setStart(time);
				queue = false;
			}
			else{
				System.out.print("At time " + currentProc.getOrder() + ", proc " + currentProc.getName() + " arrives and is put on the queue: [");
				arrived.add(currentProc);
				currentProc.setStart(time);
				ordered.remove(0);		
				iter = arrived.iterator();
				iter.next();
				System.out.print(iter.next().getName());
				while(iter.hasNext()){ 
					System.out.print(", " + iter.next().getName());
				}
				System.out.println("]");
			}
		}
		queue = true;
		Collections.sort(reordered,new Comparator<Process>(){
			@Override
    		public int compare(Process a, Process b) {
        		return (Integer)a.getOrder().compareTo((Integer)b.getOrder());
    		}
		});
		iter = reordered.iterator();
		iter.next();
		currentProc = aList.get(0);
		burst = time;
		while (queue == true){
			if (burst == currentProc.getRunTime() && iter.hasNext()){
				System.out.print("At time " + time + ", proc " + currentProc.getName() + " completes (turnaround = " + (time - currentProc.getStart()) + "), and proc " + iter.next().getName() + " starts. Queue: [");				
				att.add(time - currentProc.getStart());
				if (iter.hasNext()){
					System.out.print(iter.next().getName());
				}				
				while(iter.hasNext()){
					System.out.print(", " + iter.next().getName());
				}
				System.out.println("]");
				if (!aList.isEmpty()){
					aList.remove(0);
					currentProc = aList.get(0);
					iter = aList.iterator();
					if (iter.hasNext()){
						iter.next();
					}
					burst = 0;
				}
			}
			else if (burst == currentProc.getRunTime() && !iter.hasNext()){
				System.out.println("At time " + time + ", proc " + currentProc.getName() + " completes (turnaround = " + (time - currentProc.getStart()) + "). Queue is empty.");
				att.add(time - currentProc.getStart());
				queue = false;
			}

			time++;
			burst++;
		}
			
			for (int i = 0; i < att.size();i++){
				turnaroundSum = turnaroundSum + att.get(i);
			}			
			System.out.println("\nAverage FCFS Turnaround = " + ((float)turnaroundSum / att.size()));
	}	
}//end of FCFS
public static void SJF(Process[] list){
	//at end of current process, checks for next shortest process
	//only compares run times of remaining processes at the end of current process' run time
	//longest process should always finish last
	ArrayList<Process> aList = new ArrayList<>(Arrays.asList(list));
	int time = 0;
	int burst = 0;
	int turnaroundSum = 0;
	ArrayList<Integer> att = new ArrayList<Integer>(aList.size());
	boolean queue = true;
	boolean rem = true;
	Process currentProc;
	ArrayList<Process> arrived = new ArrayList<Process>();
	ArrayList<Process> ordered = new ArrayList<Process>(Arrays.asList(list));
	ArrayList<Process> reordered = new ArrayList<Process>(Arrays.asList(list));
	Iterator<Process> iter = null;
	Iterator<Process> itera = null;

	if (aList.isEmpty()){	
		System.out.println("\nNo processes given.\n");
		queue = false;
	}
	else{
		System.out.println("\nSJF:\n");
		Collections.sort(ordered,new Comparator<Process>(){
			@Override
    		public int compare(Process a, Process b) {
        		return (Integer)a.getOrder().compareTo((Integer)b.getOrder());
    		}
		});
		for (time = 0; time < aList.size(); time++){
			currentProc = ordered.get(0);
			if (time == 0){	
				System.out.print("At time " + currentProc.getOrder() + ", proc " + currentProc.getName() + " arrives and is scheduled to run since the queue is empty.\n");
				arrived.add(currentProc);
				currentProc.setStart(time);
				ordered.remove(0);
				iter = arrived.iterator();
			}
			else{
				System.out.print("At time " + currentProc.getOrder() + ", proc " + currentProc.getName() + " arrives and is put on the queue: [");
				arrived.add(currentProc);
				currentProc.setStart(time);
				ordered.remove(0);		
				iter = arrived.iterator();
				iter.next();
				System.out.print(iter.next().getName());
				while(iter.hasNext()){ 
					System.out.print(", " + iter.next().getName());
				}
				System.out.println("]");
			}
		}
		iter = aList.iterator();
		iter.next();
		currentProc = aList.get(0);
		burst = time;
		while(queue == true){
			if(burst == currentProc.getRunTime() && iter.hasNext()){
				//print end of procedure
				System.out.print("At time " + time + ", proc " + currentProc.getName() + " completes (turnaround = " + (time - currentProc.getStart()) + "),");//proc finished
				att.add(time - currentProc.getStart());
				//set next procedure
				itera = aList.iterator();
				while(rem){
					if(!aList.isEmpty() && itera.hasNext()){
						if(itera.next().getName() == currentProc.getName()){
							itera.remove();
							rem = false;
						}
					}
				}
				rem = true;
				
				currentProc = aList.get(0);
				for(int i = 0; i < aList.size(); i++){
					if (aList.get(i).getRunTime() < currentProc.getRunTime()){
						currentProc = aList.get(i);
					}
				}
				System.out.print(" and proc " + currentProc.getName() + " starts, since it has the next lowest run time. Queue: [");//new proc & queue
				iter = arrived.iterator();
				while(rem){
					if(!arrived.isEmpty() && iter.hasNext()){
						if(iter.next().getName() == currentProc.getName()){
							iter.remove();
							rem = false;
						}
					}
				}
				rem = true;
				iter = arrived.iterator();

				if (iter.hasNext()){
					iter.next();
				}
				if (iter.hasNext()){
					System.out.print(iter.next().getName());
				}				
				while(iter.hasNext()){
					System.out.print(", " + iter.next().getName());
				}
				System.out.println("]");
				burst = 0;

				iter = arrived.iterator();
				iter.next();
			}//end of if 
			else if (burst == currentProc.getRunTime() && !iter.hasNext()){
				System.out.println("At time " + time + ", proc " + currentProc.getName() + " completes (turnaround = " + (time - currentProc.getStart()) + "). Queue is empty.");
				att.add(time - currentProc.getStart());
				queue = false;
			}
			time++;
			burst++;
			
		}//end of while
		for (int i = 0; i < att.size();i++){
				turnaroundSum = turnaroundSum + att.get(i);
			}			
			System.out.println("\nAverage SJF Turnaround = " + ((float)turnaroundSum / att.size()));
	}
}//end of SJF
public static void SRTN(Process[] list){
	//compares run times at process completion and at process addition
	//decrement run time of current process and compare to that of added processes
	//shortest processes should always finish first 
	//use the "order" variable built into Process class
	//upon process addition, set currentProc remTime to runTime - burst, then compare to added process' runTime, currentProc = lower 
	int time = 0;
	int burst = 0;
	int turnaroundSum = 0;
	int tracker = 0;
	boolean queue = true;
	boolean rem = true;
	boolean first = true;
	Process currentProc;
	Process newProc;
	Process runningProc;
	ArrayList<Process> aList = new ArrayList<>(Arrays.asList(list));
	ArrayList<Integer> att = new ArrayList<Integer>(aList.size());
	ArrayList<Process> arrived = new ArrayList<Process>();
	ArrayList<Process> ordered = new ArrayList<Process>(Arrays.asList(list));
	ArrayList<Process> reordered = new ArrayList<Process>(Arrays.asList(list));
	TimeComparator tComp = new TimeComparator();
	Iterator<Process> iter = null;
	Iterator<Process> itera = null;

	if (aList.isEmpty()){	
		System.out.println("\nNo processes given.\n");
		queue = false;
	}
	else{
		System.out.println("\nSRTN:\n");
		Collections.sort(ordered,new Comparator<Process>(){
			@Override
    		public int compare(Process a, Process b) {
        		return (Integer)a.getOrder().compareTo((Integer)b.getOrder());
    		}
		});
			currentProc = ordered.get(0);
			runningProc = ordered.get(0);
			tracker = ordered.size();
		for (int i = 0; i < tracker; i++){
			newProc = ordered.get(0);
			if (time == newProc.getOrder() && first == true){	
				System.out.print("At time " + newProc.getOrder() + ", proc " + newProc.getName() + " arrives and is scheduled to run since the queue is empty.\n");
				arrived.add(newProc);
				newProc.setStart(time);
				runningProc = newProc;
				ordered.remove(0);
				iter = arrived.iterator();
				first = false;
			}
			else if (first == false){
				if(tComp.compare(currentProc, newProc) < 0){
					System.out.print("At time " + newProc.getOrder() + ", proc " + newProc.getName() + " arrives. Its run time is larger than the remaining time of the current process, so it is put on the queue: [");
					arrived.add(newProc);
					//currentProc.setStart(time);
					ordered.remove(0);		
					iter = arrived.iterator();
					iter.next();
					System.out.print(iter.next().getName());
					while(iter.hasNext()){ 
						System.out.print(", " + iter.next().getName());
					}
					System.out.println("]");
				}
				else{
					System.out.print("At time " + newProc.getOrder() + ", proc " + newProc.getName() + " arrives. Its run time is shorter than the remaining time of the current process, so it is scheduled to run while the current process is placed onto the queue: [");
					runningProc.setRemTime(burst);
					burst = 0;
					arrived.add(runningProc);
					newProc.setStart(time);
					runningProc = ordered.get(0);
					ordered.remove(0);
					iter = arrived.iterator();
					iter.next();
					System.out.print(iter.next().getName());
					while(iter.hasNext()){
						System.out.print(", " + iter.next().getName());
					}
					System.out.println("]");
				}
			}
			time++;
			burst++;
			currentProc = newProc;
		}
		iter = arrived.iterator();
		iter.next();
		queue = true;
		currentProc = runningProc;
		while(queue == true){
			if(iter.hasNext() && burst == currentProc.getRemTime()){
				//print end of procedure
				System.out.print("At time " + time + ", proc " + currentProc.getName() + " completes (turnaround = " + (time - currentProc.getStart()) + "),");//proc finished
				att.add(time - currentProc.getStart());
				//set next procedure
				itera = aList.iterator();
				while(rem){
					if(!aList.isEmpty() && itera.hasNext()){
						if(itera.next().getName() == currentProc.getName()){
							itera.remove();
							rem = false;
						}
					}
				}
				rem = true;
				
				currentProc = aList.get(0);
				for(int i = 0; i < aList.size(); i++){
					if (aList.get(i).getRemTime() < currentProc.getRemTime()){
						currentProc = aList.get(i);
					}
				}
				System.out.print(" and proc " + currentProc.getName() + " starts, since it has the lowest remaining run time. Queue: [");//new proc & queue
				iter = arrived.iterator();
				while(rem){
					if(!arrived.isEmpty()){
						if(iter.hasNext()){
							if(iter.next().getName().equals(currentProc.getName())){
								iter.remove();
								rem = false;
							}
						}
					}
				}
				rem = true;
				iter = arrived.iterator();

				if (iter.hasNext()){
					iter.next();
				}
				if (iter.hasNext()){
					System.out.print(iter.next().getName());
				}				
				while(iter.hasNext()){
					System.out.print(", " + iter.next().getName());
				}
				System.out.println("]");
				burst = 0;

				iter = arrived.iterator();
				iter.next();
			}//end of if 
			else if (burst == currentProc.getRemTime() && !iter.hasNext()){
				System.out.println("At time " + time + ", proc " + currentProc.getName() + " completes (turnaround = " + (time - currentProc.getStart()) + "). Queue is empty.");
				att.add(time - currentProc.getStart());
				queue = false;
			}
			time++;
			burst++;
			
		}//end of while
		for (int i = 0; i < att.size();i++){
				turnaroundSum = turnaroundSum + att.get(i);
			}			
			System.out.println("\nAverage SRTN Turnaround = " + ((float)turnaroundSum / att.size()));
	}
}//end of SRTN
}//end of class Schedule
class Process{
	private final String name;
	private Integer order = null;
	private Integer runTime = null; 
	private Integer remTime = null;
	private Integer start = null;
	public Process(String name, String order, String runTime){
		this.name = name;
		if (!order.equals("null")){	
			this.order = Integer.parseInt(order);
		}
		if (!runTime.equals("null")){
			this.runTime = Integer.parseInt(runTime);
			this.remTime = this.runTime;
		}
	}//end of constructor
	public void setRemTime(int time){
		remTime = runTime - time;
	}
	public void setStart(int time){
		start = time;
	}
	public Integer getStart(){
		return start;
	}
	public Integer getRemTime(){
		return remTime;
	}
	public String getName(){
		return this.name;
	}//end of getter
	public Integer getOrder(){
		if (this.order != null){
			return this.order;
		}
		return 0;
	}//end of getter
	public int getRunTime(){
		if (this.runTime != null){	
			return this.runTime;
		}
		return 0;
	}//end of getter
}//end of class Process
class TimeComparator implements Comparator<Process> {
    @Override
    public int compare(Process a, Process b) {
        return a.getRemTime().compareTo((Integer)b.getRunTime());
    }
}//end of TimeComparator
