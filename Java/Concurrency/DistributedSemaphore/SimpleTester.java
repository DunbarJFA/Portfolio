package dist_sem;

import dist_sem.DistSem;

public class SimpleTester{
    DistSem sem;
    int acctBalance;

    public SimpleTester(int amt, DistSem sem){
        this.sem = sem;
        acctBalance = amt;
    }

    public void donate(int amt){
        sem.P();
        acctBalance += amt;
        try {
            Thread.sleep(amt);
        } catch (Exception e) {
            //TODO: handle exception
        }
        sem.V();
    }

    public void spend(int amt){
        sem.P();
        acctBalance -= amt;
        try {
            Thread.sleep(amt*10);
        } catch (Exception e) {
            //TODO: handle exception
        }
        sem.V();
    }

    public static void main(String[] args) {
        int id = Integer.parseInt(args[0]);
        int port = Integer.parseInt(args[1]);
        DistSem sem = new DistSemaphoreImpl(id, args[1], port);
        Tester tester = new Tester(1000,sem);
        tester.donate(100);
        tester.spend(10);
    }
}//handles four helper nodes