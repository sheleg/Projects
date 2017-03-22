import javax.swing.*;

/**
 * Created by vladasheleg on 19.02.17.
 */
public class ThreadForm extends JFrame{
    private boolean isStopedThread2 = false, isStopedThread1 = false;
    boolean isCheckThread1 = false, isCheckThread2 = false;
//    boolean suspendThread1 = false, suspendThread2 = false;
//    boolean isPauseThread1 = false, isPauseThread2 = false;

    private JPanel rootPanel;
    private JButton settingsButton2;
    private JButton settingsButton1;
    private JButton pauseThread1;
    private JButton playThread1;
    private JButton stopThread1;
    private JButton playThread2;
    private JButton pauseThread2;
    private JButton stopThread2;
    private JTextArea textPane1;
    private JTextArea textPane2;

    SearchInfo searchInfo1;
    SearchInfo searchInfo2;

    private SearchThread thread1;
    private SearchThread thread2;


    ThreadForm() {
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        textPane1.setEditable(false);

        setSize(855, 500);
        setVisible(true);

        settingsButton1.addActionListener(e -> {
            isCheckThread1 = true;
            callDialog();
        });
        settingsButton2.addActionListener(e -> {
            isCheckThread2 = true;
            callDialog();
        });

        playThread1.addActionListener(e -> {
            if (thread1 != null && thread1.inPause) {
                thread1.inPause = false;
            } else {
                if (!isStopedThread1) {
                    textPane1.setText("Первый поток работает\n");
                    thread1 = new SearchThread("Первый поток", searchInfo1, textPane1);
                    thread1.start();
                }
            }
        });
        playThread2.addActionListener(e -> {
            if (thread2 != null && thread2.inPause) {
                thread2.inPause = false;
            } else {
                if (!isStopedThread2) {
                    textPane2.setText("Второй поток работает\n");
                    thread2 = new SearchThread("Второй поток", searchInfo2, textPane2);
                    thread2.start();
                }
            }
        });


        stopThread1.addActionListener(e -> {
            try {
                if (!isStopedThread1) {
                    //thread1.interrupt();
                    thread1.isStop = true;
                    isStopedThread1 = true;
                    //textPane1.setText(textPane1.getText() + "Первый поток завершен.");
                }
            } catch (java.lang.Error ignored) {}
        });
        stopThread2.addActionListener(e -> {
            try {
                if (!isStopedThread2) {
                    //thread2.interrupt();
                    thread2.isStop = true;
                    isStopedThread2 = true;
                    //textPane2.setText(textPane2.getText() + "Второй поток завершен");
                }
            }
            catch (java.lang.Error ignored) {}
        });


        pauseThread1.addActionListener(e -> thread1.inPause = true);
        pauseThread2.addActionListener(e -> thread2.inPause = true);
    }

    private void callDialog() {
        ThreadSettings threadSettingsDialog = new ThreadSettings(this);
        threadSettingsDialog.setVisible(true);
    }

}
