import javax.swing.*;
import java.awt.*;

/**
 * 闯关界面
 *
 * @author JasonG
 * @create 2022/2/15
 */
@SuppressWarnings("serial")
public class LevelPanel extends JPanel {

    private JPanel level0, level;
    JFrame f;

    public LevelPanel(JFrame f) {
        //设置此容器的布局管理器为空
        setLayout(null);
        //组件可能不会绘制其部分或全部像素，从而允许底层像素显示出来。
        setOpaque(false);

        //返回按钮
        showReturn();

        //关数按钮
        showLevle();
        this.f = f;
    }

    private void showLevle() {
        //关数按钮
        level = new JPanel();
        /*
         * setLayout设置容器的布局管理器
         * GridLayout 创建具有指定行数和列数的网格布局。布局中的所有组件都具有相同的大小。
         * 此外，水平和垂直间隙设置为指定值。水平间隙放置在每列之间。垂直间隙放置在每一行之间。
         */
        level.setLayout(new GridLayout(4, 5, 20, 20));
        level.setBounds(70, 130, 500, 200);
        JButton bt[] = new JButton[20];
        for (int i = 0; i < 20; i++) {
            final int t = i;
            bt[i] = new JButton("第" + (i + 1) + "关");
            bt[i].setBackground(new Color(239 - i * 6, 223 - i * 6, 39 + i * 9));
            level.add(bt[i]);
            bt[i].addActionListener(e -> {
                Point p = f.getLocation();
                f.dispose();
                GameFrame gameFrame;
                gameFrame = new GameFrame(t > 2 ? 8 : (int) Math.pow(2, t + 1), t + 1);
                //监听关闭窗体按钮
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //清除布局管理器
                gameFrame.setLayout(null);

                //设置不可拉伸
                gameFrame.setResizable(false);
                gameFrame.setLocation(p);
            });

        }
        level.setOpaque(false);
        add(level);
    }

    private void showReturn() {
        level0 = new JPanel();
        level0.setLayout(new GridLayout(1, 10));
        level0.setBounds(25, 30, 100, 40);
        level0.setOpaque(false);
        add(level0);

        //返回按钮
        JButton jb = new JButton("返回主菜单");
        jb.setBackground(Color.cyan);
        level0.add(jb);

        jb.addActionListener(e -> {
            Point p = f.getLocation();
            f.dispose();
            StartMain.main(null);
            StartMain.e1.setLocation(p);
        });
    }
}
