import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class Main extends JPanel implements ActionListener, PropertyChangeListener{

    JPanel mainPanel = new JPanel();
    JPanel jPanelFirst = new JPanel();
    JLabel jLabelFrom = new JLabel("From");
    JTextField textFieldFrom = new JTextField();
    JButton jButtonFrom = new JButton("...");
    FlowLayout flowLayoutFirst = new FlowLayout();

    JPanel jPanelTo = new JPanel();
    JLabel jLabelTo = new JLabel("To     ");
    JTextField textFieldTo = new JTextField();
    JButton jButtonTo = new JButton("...");
    FlowLayout flowLayoutTo = new FlowLayout();
    JPanel jPanelProgress = new JPanel();

    JProgressBar jProgressBar = new JProgressBar(0, 100);
    JPanel jPanelButton = new JPanel();
    JButton buttonStart = new JButton("Start");
    JButton buttonStop = new JButton("Stop");
    FlowLayout flowLayoutButton = new FlowLayout();
    BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
    JFileChooser fileChooser = new JFileChooser();
    File chooseFile;
    File createdFile;
    ByteCopy byteCopy;

    public Main() {

        textFieldFrom.setPreferredSize(new Dimension(180, 30));

        jButtonFrom.addActionListener(this);
        jButtonFrom.setActionCommand("chooseFrom");

        flowLayoutFirst.setVgap(10);
        flowLayoutFirst.setHgap(10);
        jPanelFirst.setLayout(flowLayoutFirst);
        jPanelFirst.add(jLabelFrom);
        jPanelFirst.add(textFieldFrom);
        jPanelFirst.add(jButtonFrom);
        mainPanel.add(jPanelFirst);
        textFieldTo.setPreferredSize(new Dimension(180, 30));

        jButtonTo.addActionListener(this);
        jButtonTo.setActionCommand("addTo");

        flowLayoutTo.setVgap(10);
        flowLayoutTo.setHgap(10);
        jPanelFirst.setLayout(flowLayoutTo);
        jPanelTo.add(jLabelTo);
        jPanelTo.add(textFieldTo);
        jPanelTo.add(jButtonTo);
        mainPanel.add(jPanelTo);
        jProgressBar.setValue(0);
        jProgressBar.setStringPainted(true);
        jProgressBar.setSize(new Dimension(120, 50));

        jPanelProgress.add(jProgressBar);
        mainPanel.add(jPanelProgress);
        jPanelButton.add(buttonStart);

        // Start button get ActionListener:
        buttonStart.addActionListener(this);
        buttonStart.setActionCommand("copy");

        buttonStop.addActionListener(this);
        buttonStop.setActionCommand("exit");

        jPanelButton.add(buttonStop);
        flowLayoutButton.setVgap(10);
        flowLayoutButton.setHgap(70);
        jPanelButton.setLayout(flowLayoutButton);
        mainPanel.add(jPanelButton);
        mainPanel.setLayout(boxLayout);

        add(mainPanel);
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent jComponent = new Main();
        frame.setContentPane(jComponent);

        frame.pack();
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand().equals("chooseFrom")){
            int fileChooserResponse = fileChooser.showOpenDialog(Main.this);
            if (fileChooserResponse == JFileChooser.APPROVE_OPTION) {
                System.out.println("you select this: " + fileChooser.getSelectedFile().getAbsolutePath());
                chooseFile = fileChooser.getSelectedFile();
                textFieldFrom.setText(chooseFile.getAbsolutePath());
            }
        }

        if(actionEvent.getActionCommand().equals("addTo")) {
            int fileChooserResponse = fileChooser.showSaveDialog(Main.this);
            if (fileChooserResponse == JFileChooser.APPROVE_OPTION) {
                System.out.println("you save to here: " + fileChooser.getSelectedFile().getAbsolutePath());
                createdFile = fileChooser.getSelectedFile();
                textFieldTo.setText(createdFile.getAbsolutePath());
            }
        }

        if(actionEvent.getActionCommand().equals("copy")) {
            byteCopy = new ByteCopy(chooseFile, createdFile);
            byteCopy.addPropertyChangeListener(this);
            byteCopy.execute();
        }

        if(actionEvent.getActionCommand().equals("exit")) {
            byteCopy.cancel(true);
            System.out.println("You stopped it you géhed");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if(pce.getPropertyName() == "progress") {
            Integer progress = (Integer) pce.getNewValue();
            jProgressBar.setValue(progress);
        }
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
