import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladasheleg on 19.04.17.
 */
public class ThreadForm extends JFrame{
    private JTextArea textArea;
    private JPanel rootPanel;
    private JButton stopButton;
    private JButton openFolderButton;
    private JTextField directoryText;
    private JLabel chooseDirectoryLabel;
    private JButton startButton;


    private InputDataCheckThread inputDataCheckThread;
    private SolvingProblemThread solvingProblemThread;
    private OutputDataCheckThread outputDataCheckThread;
    private String folderSymbol = "/";

    public boolean isOutCreated = false;

    public ThreadForm() {
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        setSize(855, 500);
        setVisible(true);

        openFolderButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int ret = fileChooser.showDialog(null, "Choose directory..");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    directoryText.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
        });

        startButton.addActionListener(e -> {
            openFolderButton.setEnabled(false);
            directoryText.setEnabled(false);
            inputDataCheckThread = new InputDataCheckThread();
            solvingProblemThread = new SolvingProblemThread(this);
            outputDataCheckThread = new OutputDataCheckThread();
            start();
        });
        stopButton.addActionListener(e -> {
            if (solvingProblemThread.isAlive()) {
                solvingProblemThread.interrupt();
            }
        });
    }

    private void start() {
        checkData();
    }

    private void checkData() {
        String pathToTests = directoryText.getText();

        String[] arrayOfPaths = new File(pathToTests).list();
        List<String> inPaths = new ArrayList<>();

        for (String s : arrayOfPaths) {
            if (s.contains(".in")) {
                inPaths.add(pathToTests + folderSymbol + s);
            }
        }

        for (int i = 1; i < inPaths.size() + 1; i++) {
            inputDataCheckThread.setPath(inPaths.get(i - 1));
            if (inputDataCheckThread.startCheck()) {
                textArea.append("Test №" + i + " is correct\n");
                startSolving(inPaths.get(i - 1));
                solvingProblemThread.isOutCreated = false;
                textArea.append(outputDataCheckThread.startCheck(inPaths.get(i - 1).split("\\.")[0] + ".out"));
            }
            else
                textArea.append("Test №" + i + " isn't correct\n");
            //TODO: Что делать после?
        }
    }

    private void startSolving(String path) {
        solvingProblemThread.setPathToTest(path);
        solvingProblemThread.run();
        while (!isOutCreated) {
                System.out.println("1");
        }
    }
}
