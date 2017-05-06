import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by vladasheleg on 19.04.17.
 */
public class OutputDataCheckThread extends Thread {
    String fileToCheck = "";

    private String answer = "";

    public OutputDataCheckThread() {}
    public OutputDataCheckThread(String fileToCheck) {
        this.fileToCheck = fileToCheck;
    }

    public String startCheck(String path) {
        fileToCheck = path;
        run();
        return answer;
    }

    @Override
    public void run() {
        int numberOfTest = Integer.parseInt(fileToCheck.split("\\.")[0].substring(fileToCheck.length() - 5, fileToCheck.length() - 4));
        if (checkData()) {
            answer = "The data in the out file " + numberOfTest + " does match the reference file\n";
        }
        else
            answer = "The data in the out file " + numberOfTest + " doesn't match the reference file\n";
    }

    public void setFileToCheck(String fileToCheck) {
        this.fileToCheck = fileToCheck;
    }

    private boolean checkData() {
        String tempPath  = (fileToCheck.split("\\."))[0] + "_reference.out";

        String line;

        synchronized (new Object()) {
            try (BufferedReader readerReference = new BufferedReader(new FileReader(new File(tempPath)))) {
                try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileToCheck)))) {
                    while ((line = reader.readLine()) != null) {
                        if (!line.equals(readerReference.readLine()))
                            return false;
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
