import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by vladasheleg on 19.04.17.
 */
public class SolvingProblemThread extends Thread {
    public boolean isStopped = false;
    public boolean isOutCreated = false;
    ThreadForm threadForm;

    String pathToTest = "";

    public SolvingProblemThread(ThreadForm threadForm) {
        this.threadForm = threadForm;
    }

    public void setPathToTest(String pathToTest) {
        this.pathToTest = pathToTest;
    }

    @Override
    public void run() {
        try {
            System.out.println("2");
            for (int i = 0; i < 100000000; i++) {
                int a = i * 10 + 15;
            }
            solve();
            for (int i = 0; i < 100000000; i++) {
                int a = i * 10 + 15;
            }
            System.out.println("3");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void solve() throws IOException {
        FileData fileData = new FileData();
        fileData.getDataFromFile(new File(pathToTest));
        String answer = fileData.easyTraiding();

        synchronized (new Object()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToTest.split("\\.")[0] + ".out"))) {
                threadForm.isOutCreated = true;
                writer.write(answer);
            }
        }
    }

}
