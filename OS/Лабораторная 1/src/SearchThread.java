import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vladasheleg on 19.02.17.
 */
public class SearchThread extends Thread {
    SearchInfo searchInfo;
    JTextArea paneForPrint;
    boolean inPause = false, isStop = false;

    private final static Object lock = new Object();

    private SearchThread() {
    }

    public SearchThread(String name, SearchInfo searchInfo) {
        super(name);
        this.searchInfo = searchInfo;
    }

    public SearchThread(String name, SearchInfo searchInfo, JTextArea pane) {
        super(name);
        this.searchInfo = searchInfo;
        paneForPrint = pane;
    }

    @Override
    public void run() {
        try {
            find(searchInfo.path, searchInfo.template);
            paneForPrint.append("Поток завершен");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void find(String path, String template) throws FileNotFoundException {
        while (inPause) {
            try {
                this.sleep(1);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        if (isStop) {
            //this.interrupt();
            //paneForPrint.append("Поток завершен");
            return;
        }

        File f = new File(path);
        String[] dirList = f.list();

        List<String> allFiles = new ArrayList<>();
        List<String> packages = new ArrayList<>();
        List<String> needFiles = new ArrayList<>();

        if (dirList != null) {
            for (String s : dirList) {
                if (s.charAt(0) == '.') {
                    continue;
                }
                File tempFile = new File(path + "/" + s);
                if (!tempFile.isFile()) {
                    packages.add(s);
                } else {
                    allFiles.add(s);
                }
            }

            for (String s : allFiles) {
                Matcher matcher = Pattern.compile(getRegex(template)).matcher(s);
                while (matcher.find()) {
                    needFiles.add(path + "/" + matcher.group());
                }
            }

            if (searchInfo.isLineForSearch) {
                findInLine(needFiles);
            }

            if (searchInfo.isSubdirectory) {
                findInSubdirectory(packages, path, template);
            }

            if (!searchInfo.isLineForSearch) {
                for (String s : needFiles) {
                    //System.out.println(this.getName() + s);
                    //paneForPrint.setText(paneForPrint.getText() + s + "\n");
                    paneForPrint.append(s + "\n");

                }

            }
        }
    }

    private void findInLine(List<String> files) throws FileNotFoundException {
        String[] names = files.stream().toArray(String[]::new);
        files.clear();
        for (String fileName : names) {
//            if (fileName.charAt(0) == '.') {
//                continue;
//            }
            File tempFile = new File(fileName);
            if (containsLine(tempFile)) {
                //System.out.println(this.getName() + tempFile.getAbsolutePath() + "\n");
                //paneForPrint.setText(paneForPrint.getText() + tempFile.getAbsolutePath() + "\n");
                paneForPrint.append(tempFile.getAbsolutePath() + "\n");
            }
        }
    }

    private boolean containsLine(File tempFile) {
        String line;
        synchronized (lock) {
            try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
                while ((line = reader.readLine()) != null)
                    if (line.contains(searchInfo.lineForSearch))
                        return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void findInSubdirectory(List<String> packages, String path, String template) {
        try {
            for (String fileName : packages) {
                find(path + "/" + fileName, template);
            }
        } catch (NullPointerException e) {
            //paneForPrint.setText(paneForPrint.getText() + "Доступ к файлу запрещен.\n");
            System.out.println("Доступ к файлу запрещен.\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getRegex(String template) {
        String regex = "^";
        for (int i = 0; i < template.length(); i++) {
            switch (template.charAt(i)) {
                case '.':
                    regex += "\\.";
                    ;
                    break;
                case '?':
                    regex += ".";
                    break;
                case '*':
                    regex += ".*";
                    break;
                default:
                    regex += template.charAt(i);
                    break;
            }
        }
        return regex;
    }
}
