import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主界面设计
 *
 * @author JasonG
 * @create 2022/2/11
 */
@SuppressWarnings("serial")
public class FirstFrame extends JFrame {

    /**
     * 游戏背景
     */
    private JLabel back;
    private JButton button01, button02, button03;
    private FirstFrame f;

    public FirstFrame(int i) {
        super("水果连连看");
        //设置java图标
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
        //设置窗体大小
        setSize(650, 500);
        //清楚布局管理器
        setLayout(null);
        //设置背景
        showBackground();
        if (i == 1) {
            LevelPanel level = new LevelPanel(this);
            level.setBounds(0, 0, 650, 500);
            add(level);

            //监听关闭窗体按钮
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //设置可视
            setVisible(true);
            //设置不可拉伸
            setResizable(false);
        } else {
            //显示界面
            showButton();
            //监听
            adapter();
        }
        f = this;
    }

    /**
     * 监听
     */
    private void adapter() {
        button01.addActionListener(e -> toLevel());

        button02.addActionListener(e -> {
            //在这之前要创建StartMain启动类,并创建e1对象
            Point p = StartMain.e1.getLocation();
            StartMain.e1.dispose();
            //在这之前要创建GameFrame类,并继承JFrame
            GameFrame gameFrame;
            gameFrame = new GameFrame(8, -1);
            //监听关闭窗体按钮
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //清除布局管理器
            gameFrame.setLayout(null);
            //设置不可拉伸
            gameFrame.setResizable(false);
            gameFrame.setLocation(p);

            button03.addActionListener(e1 -> new Dialog(f,0,0,null));

        });
    }

    /**
     * 跳转到闯关界面
     */
    public void toLevel() {
        Point p = this.getLocation();
        this.dispose();
        FirstFrame f = new FirstFrame(1);
        f.setLocation(p);
    }

    /**
     * 设置显示界面
     */
    private void showButton() {
        //设置按钮
        button01 = new JButton("经典模式");
        button01.setFont(new Font("acefont-family", Font.BOLD, 25));
        button01.setBackground(Color.RED);
        button01.setBounds(260, 200, 150, 40);

        button02 = new JButton("计时模式");
        button02.setFont(new Font("acefont-family", Font.BOLD, 25));
        button02.setBackground(Color.GREEN);
        button02.setBounds(260, 250, 150, 40);

        button03 = new JButton("设置");
        button03.setFont(new Font("acefont-family", Font.BOLD, 25));
        button03.setBackground(Color.BLUE);
        button03.setBounds(260, 300, 150, 40);

        add(button01);
        add(button02);
        add(button03);
    }


    /**
     * 设置背景
     */
    private void showBackground() {

        //背景图片
        ImageIcon background = new ImageIcon(getClass().getResource("/images/background.jpg"));
        //设置背景标签
        back = new JLabel(background);
        //设置背景图片位置大小
        back.setBounds(0, 0, getWidth(), getHeight());
        //面板透明
        JPanel j = (JPanel) getContentPane();
        j.setOpaque(false);
        //设置背景
        getLayeredPane().add(back, new Integer(Integer.MIN_VALUE));
    }
}
