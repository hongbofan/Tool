import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by DELL on 2017/7/20.
 */
public class Main {
    public static JFrame main = new JFrame("Scaner(V1.0)By Nevermore");    //注册框架类
    public static JTextArea Result = new JTextArea("", 4, 40);            //显示扫描结果
    public static JScrollPane resultPane = new
            JScrollPane( Result, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


    public static JTextField nameHost = new JTextField("localhost", 8 );//输入主机名文本框
    public static JTextField fromip1 = new JTextField("0", 3);            //输入IP地址前三位的文本框
    public static JTextField fromip2 = new JTextField("0", 3);            //输入IP地址4~6位的文本框
    public static JTextField fromip3 = new JTextField("0", 3);            //输入IP地址7~9位的文本框
    public static JTextField fromip4 = new JTextField("0", 3);            //输入IP地址后三位的文本框

    public static JTextField toip = new JTextField("0", 3);                //输入目标IP地址后四位

    public static JTextField minPort = new JTextField("0", 4);            //最小端口输入框
    public static JTextField maxPort = new JTextField("1000", 4);        //最大端口输入框

    public static JTextField maxThread = new JTextField("100", 3);        //最大线程数
    public static JDialog DLGError = new JDialog(main, "错误！");        //错误提示框
    public static JLabel DLGINFO = new JLabel("");
    public static JLabel type = new JLabel("请选择：");
    //扫描类型
    public static JRadioButton radioIP = new JRadioButton("IP地址：");
    public static JRadioButton radioHost = new JRadioButton("主机名：", true);
    //单选按钮组
    public static ButtonGroup group= new ButtonGroup();
    public static JLabel p1 = new JLabel("端口范围：");
    public static JLabel p2 = new JLabel("~");
    public static JLabel p3 = new JLabel("~");
    public static JLabel Pdot1 = new JLabel(".");
    public static JLabel Pdot2 = new JLabel(".");
    public static JLabel Pdot3 = new JLabel(".");
    public static JLabel TNUM = new JLabel("线程数：");
    public static JLabel RST = new JLabel("扫描结果：");
    public static JLabel con = new JLabel("");
    //定义按钮
    public static JButton Ok = new JButton("确定");
    public static JButton Submit = new JButton("开始扫描");
    public static JButton Cancel = new JButton("退出");
    public static JButton saveButton = new JButton("保存扫描结果");
    //菜单栏设计：这一块好好学习学习
    public static JMenuBar myBar = new JMenuBar();
    public static JMenu myMenu = new JMenu("文件(F)");
    public static JMenuItem saveItem = new JMenuItem("保存扫描结果(S)");
    public static JMenuItem exitItem = new JMenuItem("退出(Q)");
    public static JMenu myMenu2 = new JMenu("帮助");
    public static JMenuItem helpItem = new JMenuItem("阅读");

    public static void main(String[] args) {
        main.setSize(500, 400);
        main.setLocation(400, 400);
        main.setResizable(false);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DLGError.setSize(300, 100);
        DLGError.setLocation(400, 400);
        //添加"菜单栏"
        myMenu.add(saveItem);
        myMenu.add(exitItem);

        myMenu2.add(helpItem);

        myBar.add(myMenu);//将菜单条目添加到菜单
        myBar.add(myMenu2);

        main.setJMenuBar(myBar);//将菜单添加到框架
        //设置热键
        myMenu.setMnemonic('F');
        saveItem.setMnemonic('S');
        //为"另存为"组建设置快捷键CTRL + S
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        //采用表格包模式布局
        Container mPanel = main.getContentPane();
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 0, 0, 10);

        c.gridx = 0;// 设置表格坐标
        c.gridy = 0;
        c.gridwidth = 10;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(type, c);

        group.add(radioIP);
        group.add(radioHost);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(radioIP, c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.CENTER;
        mPanel.add(fromip1, c);

        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        mPanel.add(Pdot1, c);

        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(fromip2, c);

        c.gridx = 4;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(Pdot2, c);

        c.gridx = 5;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(fromip3, c);

        c.gridy = 1;
        c.gridx = 6;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(Pdot3, c);


        c.gridx = 7;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(fromip4, c);

        c.gridx = 8;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(p2, c);

        c.gridx = 9;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(toip, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(radioHost, c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(nameHost, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(p1, c);

        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(minPort, c);

        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(p3, c);

        c.gridx = 3;
        c.gridy = 3;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(maxPort, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(TNUM, c);

        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(maxThread, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(Submit, c);


        c.gridx = 3;
        c.gridy = 5;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(saveButton, c);

        c.gridx = 6;
        c.gridy = 5;
        c.gridwidth =4;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(Cancel, c);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 10;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(RST, c);

        //设置文本域可以换行
        Result.setLineWrap(true);
        //设置文本域不可编辑
        Result.setEditable(false);


        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 10;
        c.gridheight = 4;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.CENTER;
        mPanel.add(resultPane, c);

        Container dPanel = DLGError.getContentPane();
        dPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        dPanel.add(DLGINFO);
        dPanel.add(Ok);


        Submit.addActionListener(new SubmitAction());
        Cancel.addActionListener(new CancelAction());
        Ok.addActionListener(new OkAction());

        //实现保存功能
        saveItem.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(null);
                //单击保存按钮
                if( 0 == returnVal ){
                    File saveFile = fc.getSelectedFile();
                    try{
                        FileWriter writeOut = new FileWriter( saveFile );
                        writeOut.write(ThreadScan.Result.getText());
                        writeOut.close();

                    }catch( IOException ex ){ System.out.println("保存失败");}
                }
            }
        });
        //实现退出功能
        ActionListener li =  new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                System.exit(0);
            }
        };
        exitItem.addActionListener(li);
        //实现帮助功能

        ActionListener lil =  new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                new AboutDialog();//
            }
        };
        helpItem.addActionListener(lil);

        ActionListener lill =  new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(null);
                //单击保存按钮
                if( 0 == returnVal ){
                    File saveFile = fc.getSelectedFile();
                    try{
                        FileWriter writeOut = new FileWriter(saveFile);
                        writeOut.write(ThreadScan.Result.getText());
                        writeOut.close();
                    }catch(IOException ex ){ System.out.println("保存失败");}
                }else{
                    return;//单击取消
                }

            }
        };
        saveButton.addActionListener(lill);
        main.setVisible(true);
    }
}
