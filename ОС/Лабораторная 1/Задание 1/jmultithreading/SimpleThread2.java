public class SimpleThread2 extends Thread {

        // Код потока, который будет выполняться параллельно главному потоку.
        public void run() {
            System.out.println("Второй поток работает...");
            int sleepTime = 5000;
            System.out.printf("Второй поток засыпает на %d секунд.%n", 
			sleepTime/1000);
            try { 
                Thread.sleep(sleepTime);
            } catch ( InterruptedException ex ) {}
            System.out.println("Второй поток снова работает.");
        }

        // Главный поток приложения.
        public static void main( String[] args) {

            System.out.println("Главнй поток - создаём ещё один поток.");

            Thread t = new SimpleThread();
            t.start();

            System.out.println("Главный поток - второй поток запущен.");
                        
        }
}


