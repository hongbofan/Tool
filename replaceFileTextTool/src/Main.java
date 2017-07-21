import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by DELL on 2017/7/19.
 */
public class Main extends JFrame {

    private static JPanel contentPane = new JPanel();
    private static JButton localPath = new JButton("选择文件");
    private static JTextField fileNameKey = new JTextField();
    private static JLabel localPathLabel = new JLabel("本地文件路径");
    private static JLabel fileNameKeyLabel = new JLabel("文件名关键字");
    private static JTextField originalContent = new JTextField();
    private static JTextField replaceContent = new JTextField();
    private static JLabel originalContentLabel = new JLabel("待替换内容");
    private static JLabel replaceContentLabel = new JLabel("替换内容");
    private static JButton replace = new JButton("替换");
    private static JTextArea outMessage = new JTextArea("输出信息:\n");
    private static String path;

    public Main() throws HeadlessException {
        this.setTitle("文件内容替换工具");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(300, 300, 900, 500);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(1, 2, 10, 10));
        JPanel pane1 = new JPanel();
        JPanel pane2 = new JPanel();
        JPanel pane3 = new JPanel();
        JPanel pane4 = new JPanel();
        JPanel pane5 = new JPanel();
        JPanel pane6 = new JPanel(new GridLayout(5, 1, 10, 10));
        pane6.add(pane1);
        pane6.add(pane2);
        pane6.add(pane3);
        pane6.add(pane4);
        pane6.add(pane5);
        contentPane.add(pane6);


        localPath.addActionListener(e -> {
            JFileChooser fcDlg = new JFileChooser();
            fcDlg.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fcDlg.setDialogTitle("请选择文件...");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "文本文件(*.xml;*.doc;*.txt)", "xml","doc","txt");
            fcDlg.setFileFilter(filter);
            int returnVal = fcDlg.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                path = fcDlg.getSelectedFile().getPath();
                if (path.length() > 40) {
                    Main.localPath.setText("..." + path.substring(path.length() - 40, path.length() - 1));
                } else {
                    Main.localPath.setText(path);
                }

            }
        });

        pane1.add(localPathLabel);
        pane1.add(localPath);

        fileNameKey.setColumns(10);
        pane2.add(fileNameKeyLabel);
        pane2.add(fileNameKey);

        originalContent.setColumns(10);
        pane3.add(originalContentLabel);
        pane3.add(originalContent);

        replaceContent.setColumns(10);
        pane4.add(replaceContentLabel);
        pane4.add(replaceContent);

        replace.addActionListener(lister -> {
            String fileNameKeyText = fileNameKey.getText();
            String replaceContentText = replaceContent.getText();
            String originalContentText = originalContent.getText();
            if ("".equals(fileNameKeyText) || "".equals(replaceContentText) || "".equals(originalContentText)) {
                outMessage.append("参数不能为空\n");
                return;
            }
            File folder = new File(path);

            if (!folder.exists()) {
                outMessage.append("目录不存在：" + folder.getAbsolutePath() + "\n");
                return;
            }
            List<File> result = searchFile(folder, fileNameKeyText);
            outMessage.append("查到" + result.size() + "个文件\n");
            result.stream()
                    //.parallel()
                    .forEach(file -> {
                        try {
                            String fileContent = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())),"UTF-8");
                            String[] lines = fileContent.split("\\n");
                            List<String> replaceLines = new ArrayList<>();
                            for (int i=0;i<lines.length;i++) {
                                if ((lines[i]).contains(originalContentText)) {
                                    replaceLines.add("第" + i+1 + "行 " + lines[i]);
                                }
                            }
                            Files.write(Paths.get(file.getAbsolutePath()), matchAndReplace(fileContent, originalContentText, replaceContentText).getBytes());
                            replaceLines.forEach(line -> outMessage.append(file.getName()+":"+line+"\n"));
                            outMessage.append(file.getName() + "完成\n");

                        } catch (IOException e) {
                            outMessage.append(file.getName() + " 异常\n" + e.getCause() + "\n");
                        }
                    });
        });
        pane5.add(replace);

        JScrollPane sp = new JScrollPane(outMessage);
        contentPane.add(sp);
        this.setVisible(true);
    }


    public static void main(String[] args) {
        Main main = new Main();
    }

    //正则匹配并且替换文本
    public static String matchAndReplace(String fileContent, String originalContent, String replaceContent) {
        return Pattern.compile(originalContent).matcher(fileContent).replaceAll(replaceContent);
    }

    // 递归查找文件名包含关键字的文件
    public static List<File> searchFile(File folder, final String keyWord) {
        List<File> result = new ArrayList<>();
        if (folder.isFile()) {
            if (folder.getName().toLowerCase().contains(keyWord.toLowerCase())) {
                result.add(folder);
            }
        } else {
            File[] subFolders = folder.listFiles((File file) -> file.isDirectory() || (file.isFile() && file.getName().toLowerCase().contains(keyWord.toLowerCase())));

            Arrays.stream(subFolders)
                    .forEach(sf -> {
                        if (sf.isFile()) {
                            result.add(sf);
                        } else {
                            searchFile(sf, keyWord).forEach(result::add);
                        }
                    });
        }
        return result;
    }
}
