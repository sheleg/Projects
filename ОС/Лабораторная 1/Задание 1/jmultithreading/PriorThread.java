class MyThread extends Thread {
    MyThread( String name ) {
        super(name);
    }
    public void run() {
        for ( int i = 0; i < 20; i++) {
            System.out.print( getName() + " " + i + "; ");
            try {
                sleep(1); //test with sleep(0)
            } catch ( InterruptedException e ) {
                System.err.print("Error" + e);
            }
        }
        System.out.println( getThreadGroup() + ":" + getPriority() +":" + getName() +  " finished." );
    }
}

public class PriorThread {
    public static void main( String[] args) throws InterruptedException {
        Thread min = new MyThread("Min");//1
        Thread max = new MyThread("Max");//10
        Thread norm = new MyThread("Norm");//5
        min.setPriority(Thread.MIN_PRIORITY);
        max.setPriority(Thread.MAX_PRIORITY);
        norm.setPriority(Thread.NORM_PRIORITY);
        max.start();
        norm.start();
        min.start();

        max.join();
        norm.join();
        min.join();       
   }
}
