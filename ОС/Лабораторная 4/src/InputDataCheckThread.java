import com.sun.xml.internal.ws.server.ServerRtException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladasheleg on 19.04.17.
 */
public class InputDataCheckThread extends Thread {
    String pathToTest;
    boolean isValid = false;

    public InputDataCheckThread(String pathToTests) {
        this.pathToTest = pathToTests;
    }

    public InputDataCheckThread() {
        this.pathToTest = "";
    }

    @Override
    public synchronized void run() {
        isValid = checkTest();
    }


    private boolean checkTest() {
        FileData fileData = new FileData();
        fileData.getDataFromFile(new File(pathToTest));

        if (fileData.m > fileData.n || fileData.n > 100 || fileData.m > 100 || fileData.m < 0 || fileData.n < 0) {
            return false;
        }

        if (fileData.n > fileData.k || fileData.m > fileData.k || fileData.k > 10000) {
            return false;
        }

        if (fileData.k != fileData.prices.size() - 1) {
            return false;
        }

        return true;
    }

    public void setPath(String path) {
        this.pathToTest = path;
    }

    public boolean startCheck() {
        run();
        return isValid;
    }
}
