public class SimpleThread2 extends Thread {

        // ��� ������, ������� ����� ����������� ����������� �������� ������.
        public void run() {
            System.out.println("������ ����� ��������...");
            int sleepTime = 5000;
            System.out.printf("������ ����� �������� �� %d ������.%n", 
			sleepTime/1000);
            try { 
                Thread.sleep(sleepTime);
            } catch ( InterruptedException ex ) {}
            System.out.println("������ ����� ����� ��������.");
        }

        // ������� ����� ����������.
        public static void main( String[] args) {

            System.out.println("������ ����� - ������ ��� ���� �����.");

            Thread t = new SimpleThread();
            t.start();

            System.out.println("������� ����� - ������ ����� �������.");
                        
        }
}


