public class SimpleThread extends Thread {

        // ��� ������, ������� ����� ����������� ����������� �������� ������.
        public void run() {
            System.out.println("������ ����� ��������...");
        }

        // ������� ����� ����������.
        public static void main( String[] args) {

            System.out.println("������ ����� - ������ ��� ���� �����.");

            Thread t = new SimpleThread();
            t.start();

            System.out.println("������� ����� - ������ ����� �������.");
        }
}


