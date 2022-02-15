import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author JasonG
 * @create 2022/2/15
 * JDialog是用于创建对话窗口的主类。
 * 可以使用该类来创建自定义对话框，或者调用JOptionPane中的许多类方法来创建各种标准对话框。
 */
@SuppressWarnings("serial")
public class Dialog extends JDialog {

    //短文本字符串或图像或两者的显示区域。
    private JLabel sounds, about, pass_level;
    //点击按钮的实现
    private JButton to_main, to_next, to_new;
    //复选框的实现——可以选择或取消选择的项目，并向用户显示其状态。
    private JCheckBox jcb_1, jcb_2;
    //显示纯文本的多行区域
    private JTextArea jta;
    /**
     * 关数
     */
    private int LEVEL;

    public Dialog(JFrame f, int i, int LEVEL, String s) {
        super(f, true);
        this.LEVEL = LEVEL;
        //设置布局管理器为空
        setLayout(null);
        //设置用户在此对话框上启动“关闭”时默认发生的操作。DO_NOTHING_ON_CLOSE无操作默认窗口关闭操作。
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        //设置对话框不可拉,setResizable设置此对话框是否可以由用户调整大小,true为可以,false为不可以
        setResizable(false);
        if (i == 0)
            //显示设置对话框
            showSetting(f);
        else if (i == 1)
            showOver(f);
        else if (i == 2)
            showTimerOver(f, s);
        //设置对话框显示
        setVisible(true);
    }

    /**
     * @param f
     * @param s 在参数列表里加final,传过来的参数，机器里面就无法改了，在机器里的这个参数，一直指向的都是你传进来的参数。
     *          无论参数是基本数据类型，还是引用数据类型，只要加了final，
     *          该参数不可以再赋值（实参传进来给形参，就相当于初始化完成）。。。。可以防止在方法里面不小心重新赋值，造成一些不必要的麻烦
     */
    private void showTimerOver(final JFrame f, String s) {

        //setTitle设置对话框的标题
        setTitle("计时模式");
        //设置对话框位置大小
        setBounds(f.getBounds().x + 125, f.getBounds().y + 150, 400, 200);
        //恭喜过关标签,JLabel创建具有指定文本的JLabel实例。标签与其显示区域的前沿对齐，并垂直居中
        pass_level = new JLabel(s);
        pass_level.setFont(new Font("acefont-family", Font.BOLD, 40));
        pass_level.setBounds(110, 30, 200, 40);
        add(pass_level);

        //返回主菜单按钮,JButton创建一个带有文本的按钮
        to_main = new JButton("返回主菜单");
        to_main.setBounds(50, 120, 100, 30);
        add(to_main);

        //ActionListener用于接收动作事件的监听器接口.addActionListener将ActionListener添加到按钮
        to_main.addActionListener(e -> {
            Point p = f.getLocation();
            //dispose 这个方法，在程序中是用来关闭一个GUI页面的(图形化界面)
            f.dispose();
            StartMain.main(null);

            /*
             * setLocation将此组件移动到新位置。新位置的左上角由点p指定。点p在父坐标空间中给出。
             *此方法更改与布局相关的信息，因此使组件层次结构无效。
             */
            StartMain.e1.setLocation(p);
        });

        //下一关按钮,新建一个带有文本的按钮
        to_new = new JButton("重新开始");
        //设置组件大小
        to_new.setBounds(250, 120, 100, 30);
        add(to_new);

        to_new.addActionListener(e -> {
            Point p = f.getLocation();
            f.dispose();
            GameFrame gameFrame;
            gameFrame = new GameFrame(8, -1);
            //监听关闭窗体按钮
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //清楚布局管理器
            gameFrame.setLayout(null);

            //设置不可拉伸
            gameFrame.setResizable(false);
            gameFrame.setLocation(p);
        });
    }

    private void showOver(final JFrame f) {
        setTitle("过关");
        //设置对话框位置大小
        setBounds(f.getBounds().x + 125, f.getBounds().y + 150, 400, 200);

        //恭喜过关标签
        pass_level = new JLabel("恭喜过关");
        pass_level.setFont(new Font("acefont-family", Font.BOLD, 40));
        pass_level.setBounds(110, 30, 200, 40);
        add(pass_level);

        //返回主菜单按钮
        to_main = new JButton("返回主菜单");
        to_main.setBounds(50, 120, 100, 30);
        add(to_main);

        to_main.addActionListener(e -> {
            Point p = f.getLocation();
            f.dispose();
            StartMain.main(null);
            StartMain.e1.setLocation(p);
        });

        //下一关按钮
        to_next = new JButton("下一关");
        to_next.setBounds(250, 120, 100, 30);
        if (LEVEL == 20)
            to_next.setEnabled(false);
        add(to_next);

        to_next.addActionListener(e -> {
            Point p = f.getLocation();
            f.dispose();
            GameFrame gameFrame;
            gameFrame = new GameFrame(LEVEL > 2 ? 8 : (int) Math.pow(2, LEVEL + 1), LEVEL + 1);
            //监听关闭窗体按钮
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //清除布局管理器
            gameFrame.setLayout(null);
            //设置不可拉伸
            gameFrame.setResizable(false);
            gameFrame.setLocation(p);
        });
    }

    private void showSetting(JFrame f) {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("设置");
        //设置对话框位置大小
        setBounds(f.getBounds().x + 75, f.getBounds().y + 75, 500, 350);

        //音效标签
        sounds = new JLabel("音效:");
        sounds.setFont(new Font("acefont-family", Font.BOLD, 15));
        sounds.setBounds(10, 10, 50, 20);
        add(sounds);

        //关于作者标签
        about = new JLabel("关于作者:");
        about.setFont(new Font("acefont-family", Font.BOLD, 15));
        about.setBounds(10, 75, 100, 20);
        add(about);

        //音效选项多选按钮
        jcb_1 = new JCheckBox("背景音乐");
        jcb_1.setBounds(20, 40, 80, 20);
        if (PlaySound.b[0])
            jcb_1.setSelected(true);
        add(jcb_1);
        jcb_1.addItemListener(e -> {
            PlaySound.b[1] = !PlaySound.b[1];
        });


        //关于作者内容
        jta = new JTextArea();
        jta.setBounds(20, 10, 300, 150);
        jta.setEditable(false);
        jta.setText("GH无霸哥");
        add(jta);
    }

}
