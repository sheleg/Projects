public class SimpleThread extends Thread {

        // Код потока, который будет выполняться параллельно главному потоку.
        public void run() {
            System.out.println("Второй поток работает...");
        }

        // Главный поток приложения.
        public static void main( String[] args) {

            System.out.println("Главнй поток - создаём ещё один поток.");

            Thread t = new SimpleThread();
            t.start();

            System.out.println("Главный поток - второй поток запущен.");
        }
}


