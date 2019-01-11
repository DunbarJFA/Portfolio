package dist_sem;

import dist_sem.DistSem;

public class SimpleTester{
    DistSemaphoreImpl sem;
    int acctBalance;

    public SimpleTester(int amt, DistSemaphoreImpl sem){
        this.sem = sem;
        acctBalance = amt;

        this.sem.connectToHelper();
    }

    public void donate(int amt){
        sem.P();
        acctBalance += amt;
        try {
            Thread.sleep(amt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sem.V();
    }

    public void spend(int amt){
        sem.P();
        acctBalance -= amt;
        try {
            Thread.sleep(amt*10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sem.V();
    }

    public int getBalance(){
        return acctBalance;
    }

    public static void main(String[] args) {
        int id = Integer.parseInt(args[0]);
        int port = Integer.parseInt(args[2]);
        DistSemaphoreImpl sem = new DistSemaphoreImpl(id, args[1], port);
        SimpleTester tester = new SimpleTester(1000,sem);
        System.out.println("BALANCE: " + tester.getBalance());
        tester.donate(100);
        System.out.println("BALANCE: " + tester.getBalance());
        tester.spend(10);
        System.out.println("BALANCE: " + tester.getBalance());
    }
}//handles four helper nodes