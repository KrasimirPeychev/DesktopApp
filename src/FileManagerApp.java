import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class FileManagerApp extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField filePath;
    private JButton browseButton;
    private JButton deleteButton;
    private JButton renameButton;
    private JList fileList;
    private JScrollPane scrollPane;
    private Container contentPane;
    private File currentDir;
    private File selectedFile;
    private String[] fileNames;

    public FileManagerApp() {
        setTitle("File Manager App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 300));
        setLocationRelativeTo(null);

        filePath = new JTextField();
        browseButton = new JButton("Browse...");
        browseButton.addActionListener(this);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        renameButton = new JButton("Rename");
        renameButton.addActionListener(this);
        fileList = new JList();
        scrollPane = new JScrollPane(fileList);

        JPanel topPanel = new JPanel();
        topPanel.add(filePath);
        topPanel.add(browseButton);
        topPanel.add(deleteButton);
        topPanel.add(renameButton);

        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseButton) {
            JFileChooser fileChooser = new JFileChooser(currentDir);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                filePath.setText(selectedFile.getAbsolutePath());
                currentDir = selectedFile.getParentFile();
                fileNames = currentDir.list();
                fileList.setListData(fileNames);
            }
        } else if (e.getSource() == deleteButton) {
            if (selectedFile != null) {
                selectedFile.delete();
                fileNames = currentDir.list();
                fileList.setListData(fileNames);
                filePath.setText("");
                selectedFile = null;
            }
        } else if (e.getSource() == renameButton) {
            if (selectedFile != null) {
                String newName = filePath.getText();
                selectedFile.renameTo(new File(newName));
                fileNames = currentDir.list();
                fileList.setListData(fileNames);
            }
        }
    }

    public static void main(String[] args) {
        new FileManagerApp();
    }
}

