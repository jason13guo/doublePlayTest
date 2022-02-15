import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 游戏框架
 *
 * @author JasonG
 * @create 2022/2/11
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame {

    private JLabel back;
    private GameFrame g;

    //返回按钮
    private JButton cancel, restart;

    /**
     * 关数
     */
    private int LEVEL;

    /**
     * 布局大小即行列数
     */
    private int GameSize;

    /**
     * 时间进度条
     */
    private JProgressBar jpb;

    /**
     * 计时器
     */
    private Timer timer;

    public GameFrame(int GameSize, int t) {

        super(t == 1 ? "计时模式" : "第" + t + "关");

        /*
         *  设置java图标.
         *  Toolkit此类是抽象窗口工具包的所有实际实现的抽象超类.
         *  getDefaultToolkit获取默认工具包.
         *  getImage返回从指定 URL 获取像素数据的图像
         *  getClass返回此Object的运行时类。
         *  getResource查找具有给定名称的资源
         */
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
        LEVEL = t;
        this.GameSize = GameSize + 2;
        //设置窗体大小
        setSize(700, 600);

        /*
         * ImageIcon从指定的 URL 创建一个 ImageIcon。
         * 通过使用 MediaTracker 监控图像的加载状态，将预加载图像。
         * 图标的描述被初始化为 URL 的字符串表示。
         */
        ImageIcon background = new ImageIcon(getClass().getResource("/images/background.png"));
        //设置背景标签
        back = new JLabel(background);

        /*
         * 设置背景图片位置大小
         * setBounds移动此组件并调整其大小。左上角的新位置由x和y指定，新大小由width和height指定。
         * 此方法更改与布局相关的信息，因此使组件层次结构无效。
         * 参数：
         * x – 该组件的新x坐标
         * y – 该组件的新y坐标
         * width – 该组件的新width
         * height – 该组件的新height
         */
        back.setBounds(0, 0, getWidth(), getHeight());
        //面板透明
        JPanel j = (JPanel) getContentPane();

        /*
         * setOpaque为false,组件是透明的,允许底层像素显示出来
         */
        j.setOpaque(false);
        //设置背景
        getLayeredPane().add(back, new Integer(Integer.MIN_VALUE));
        /*
         * 窗口可见
         * setVisible是Window类中的方法,如果参数为true,则窗口可见,反之则隐藏窗口
         */
        setVisible(true);

        showMenu();
        if (t == -1) {
            showTime();
        }
        //添加游戏面板
        GamePanel jpanel = new GamePanel();
        add(jpanel);
        g = this;
    }

    /**
     * 添加定时设置
     */
    private void showTime() {

        //new JProgressBar()创建一个显示边框但没有进度字符串的水平进度条。初始值和最小值为 0，最大值为 100。
        jpb = new JProgressBar();
        //进度条的方向
        jpb.setOrientation(JProgressBar.HORIZONTAL);
        //设置进度条最小值
        jpb.setMinimum(0);
        //设置进度条最大值
        jpb.setMaximum(100);
        //设置进度条当前值
        jpb.setValue(0);
        //进度条背景颜色
        jpb.setBackground(new Color(238, 226, 29));
        //进度条尺寸
        jpb.setBounds(175, 25, 350, 12);
        add(jpb);

        //创建一个新的计时器
        timer = new Timer();
        //scheduleAtFixedRate  重复固定速率执行计时器
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                /*setValue底层 调用BoundedRangeModel数据模型,
                 *getValue进度条的当前值,传给数据模型,
                 *数据模型会传回setValue更新当前值
                 */
                jpb.setValue(jpb.getValue() + 1);
                if (jpb.getValue() > 80) {
                    //设置此组件的前景色
                    jpb.setForeground(Color.RED);
                }
                if (jpb.getValue() == 100) {
                    /*终止此计时器，丢弃任何当前计划的任务。不干扰当前正在执行的任务（如果存在）。
                    一旦定时器被终止，它的执行线程就会优雅地终止，并且不能在其上安排更多的任务。
                     */
                    timer.cancel();
                    new Dialog(g, 2, LEVEL, "闯关失败");
                }

            }
        }, 0, 900);
    }

    /**
     * 添加返回按钮
     * LEVEL==-1?"返回主菜单":"返回选关"
     */
    private void showMenu() {
        //创建一个没有设置文本或图标的按钮
        cancel = new JButton();
        //设置按钮大小
        cancel.setBounds(10, 10, 60, 40);
        /* setIcon设置按钮图标,会生成默认图标,如果有变化,底层会调用 setDisabledIcon()方法去除掉默认图标
         * new ImageIcon从指定的 URL 创建一个 ImageIcon
         * getClass返回此Object的运行时类。
         * getResource查找具有给定名称的资源
         */
        cancel.setIcon(new ImageIcon(getClass().getResource("/images/home.png")));
        add(cancel);

        //创建一个没有设置文本或图标的按钮
        restart = new JButton();
        //设置按钮大小
        restart.setBounds(10, 60, 60, 40);
        restart.setIcon(new ImageIcon(getClass().getResource("/images/restart.png")));
        add(restart);

        //将监听器ActionListener添加到按钮,重写actionPerformed方法
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //getLocation获取此组件的语言环境
                Point p = g.getLocation();
                //释放资源和Window
                g.dispose();

                /*此时启动类需要写main方法和e1属性
                 *
                 */
                StartMain.main(null);
                //setLocation将此组件移动到新位置。
                StartMain.e1.setLocation(p);
                //如果LEVEL关数不等于-1
                if (LEVEL != -1) {
                    //跳到选关界面
                    StartMain.e1.toLevel();
                } else {
                    //计时器退出
                    timer.cancel();
                }
            }
        });

        restart.addActionListener(e -> {
            Point p=g.getLocation();
            g.dispose();
            GameFrame gameFrame;
            gameFrame=new GameFrame(GameSize-2,LEVEL);
            //监听关闭窗口按钮
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //清除布局管理器
            gameFrame.setLayout(null);
            //设置不可拉伸
            gameFrame.setResizable(false);
            gameFrame.setLocation(p);
            if (timer!=null)
                timer.cancel();
        });

    }

    /**
     * GamePanel游戏面板
     * MouseListener鼠标监听器
     */
    class GamePanel extends JPanel implements MouseListener {

        //动物方块图案的宽度
        private int W = 50;
        //水果图片组数
        private Icon icon[];
        //连线图片数组
        private Icon icon_line[];

        @SuppressWarnings("rawtypes")
        //水果图片过度地图
        private ArrayList images_t;

        @SuppressWarnings("rawtypes")
        //label数组
        private ArrayList label_arr;
        //消除路径对应图片数组
        private int[] path_line;
        //记录有边框的label
        private int index = -1;

        @SuppressWarnings("unused")
        //记录有边框的label
        private Point p_index;
        //记录第二个点中方块
        private int k;
        //记录剩下的方块
        private int sum;

        @SuppressWarnings("rawtypes")
        //记录消除路径
        private ArrayList path;
        //是否消除标志位
        private int can;

        public GamePanel() {
            //网格布局
            setLayout(new GridLayout(GameSize, GameSize));
            setBounds((700 - GameSize * W) / 2, (600 - GameSize * W) / 2, GameSize * W, GameSize * W);
            setOpaque(false);
            //初始化地图
            initMap();
            //显示游戏
            showGame();
        }

        /**
         * 游戏显示
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private void showGame() {
            label_arr = new ArrayList();
            //显示水果图片
            for (int i = 0; i < GameSize * GameSize; i++) {
                if (i % GameSize == 0 || i % GameSize == GameSize - 1 || i / GameSize == 0 || i / GameSize == GameSize - 1) {
                    JLabel j = new JLabel();
                    //定义此组件将显示的图标。
                    j.setIcon(null);
                    //写入数组
                    label_arr.add(j);
                    add(j);
                    continue;
                }
                //images_t水果图片过度地图
                int nIndex = new Random().nextInt(images_t.size());

                JLabel j = new JLabel(icon[(int) images_t.get(nIndex)]);
                label_arr.add(j);
                j.addMouseListener(this);
                add(j);
                images_t.remove(nIndex);
            }
        }

        /**
         * 地图
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private void initMap() {
            images_t = new ArrayList();
            path = new ArrayList();
            //Icon固定尺寸的小图片，通常用于装饰组件。
            icon = new Icon[10];
            icon_line = new Icon[6];
            //GameSize*GameSize行列数相乘得出方阵
            path_line = new int[GameSize * GameSize];

            //通过遍历icon的长度获取水果图片
            for (int i = 0; i < icon.length; i++) {
                icon[i] = new ImageIcon(getClass().getResource("/images/" + "fruit_" + (i + 1) + ".jpg"));
            }
            //遍历连接线获取水果连线图片
            for (int i = 0; i < icon_line.length; i++) {
                icon_line[i] = new ImageIcon(getClass().getResource("/images/" + "line_" + (i + 1) + ".png"));
            }
            //遍历获取过度图片
            for (int i = 0; images_t.size() < (GameSize - 2) * (GameSize - 2); i++) {
                images_t.add(i % 10);
                if (images_t.size() == (GameSize - 2) * (GameSize - 2)) {
                    continue;
                }
                images_t.add(i % 10);
            }
            sum = images_t.size();
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        private boolean isOK(Point p) {
            k = p.x / W + p.y / W * GameSize;

            if (index == k) {
                return false;
            }
            if (((JLabel) label_arr.get(index)).getIcon() != ((JLabel) label_arr.get(k)).getIcon()) {
                return false;
            }

            /*
            消除判断
            第一个方块四个方向最长延伸
            */
            int index_top = index, index_left = index, index_right = index, index_bottum = index;
            //第二个方块四个方向最长延伸
            int k_top = k, k_left = k, k_right = k, k_bottum = k;

            for (int i = index - GameSize; i > 0; i -= GameSize) {
                if (((JLabel) label_arr.get(i)).getIcon() != null) {
                    index_top = i + GameSize;
                    break;
                }
                if (i < GameSize)
                    index_top = i;
            }
            for (int i = index - 1; i > index / GameSize * GameSize - 1; i--) {
                if (((JLabel) label_arr.get(i)).getIcon() != null) {
                    index_left = i + 1;
                    break;
                }
                if (i == index / GameSize * GameSize)
                    index_left = index / GameSize * GameSize;
            }
            for (int i = index + 1; i < (index / GameSize + 1) * GameSize; i++) {
                if (((JLabel) label_arr.get(i)).getIcon() != null) {
                    index_right = i - 1;
                    break;
                }
                if (i == (index / GameSize + 1) * GameSize - 1)
                    index_right = (index / GameSize + 1) * GameSize - 1;
            }
            for (int i = index + GameSize; i < GameSize * GameSize; i += GameSize) {
                if (((JLabel) label_arr.get(i)).getIcon() != null) {
                    index_bottum = i - GameSize;
                    break;
                }
                if (i > GameSize * GameSize - 1 - GameSize)
                    index_bottum = i;
            }
            for (int i = k - GameSize; i > 0; i -= GameSize) {
                if (((JLabel) label_arr.get(i)).getIcon() != null) {
                    k_top = i + GameSize;
                    break;
                }
                if (i < GameSize)
                    k_top = i;
            }

            for (int i = k - 1; i > k / GameSize * GameSize - 1; i--) {
                if (((JLabel) label_arr.get(i)).getIcon() != null) {
                    k_left = i + 1;
                    break;
                }
                if (i == k / GameSize * GameSize)
                    k_left = k / GameSize * GameSize;

            }
            for (int i = k + 1; i < (k / GameSize + 1) * GameSize; i++) {
                if (((JLabel) label_arr.get(i)).getIcon() != null) {
                    k_right = i - 1;
                    break;
                }
                if (i == (k / GameSize + 1) * GameSize - 1)
                    k_right = (k / GameSize + 1) * GameSize - 1;
            }
            for (int i = k + GameSize; i < GameSize * GameSize; i += GameSize) {
                if (((JLabel) label_arr.get(i)).getIcon() != null) {
                    k_bottum = i - GameSize;
                    break;
                }
                if (i > GameSize * GameSize - 1 - GameSize) {
                    k_bottum = i;
                }
            }

            //水平方向
            if (index / GameSize > k / GameSize) {  //第一个在第二个下方
                int i = index, p1 = index + 1, f = 0;
                while (true) {
                    if (i < index_left && p1 > index_right) {
                        break;
                    }
                    ArrayList arr = new ArrayList();
                    if (f == 0) {
                        if (i >= index_left && i % GameSize >= k_left % GameSize && i % GameSize <= k_right % GameSize) {
                            for (int t = i; t <= index; t++) {
                                arr.add(t);
                                path_line[t] = 0;
                            }
                            path_line[i] = 3;

                            for (int j = i - GameSize; j > 0; j -= GameSize) {
                                System.out.println("aaal");
                                arr.add(j);
                                path_line[j] = 1;
                                if (j / GameSize == k / GameSize) {
                                    for (int t = 0; t < arr.size(); t++) {
                                        path.add(arr.get(t));
                                    }
                                    if (j < k) {
                                        for (int t = j + 1; t <= k; t++) {
                                            path.add(t);
                                            path_line[t] = 0;
                                        }
                                        path_line[j] = 4;
                                    } else {
                                        for (int t = j - 1; t >= k; t--) {
                                            path.add(t);
                                            path_line[t] = 0;
                                        }
                                        path_line[j] = 5;
                                    }
                                    return true;
                                }
                                if (((JLabel) label_arr.get(j)).getIcon() != null) {
                                    arr.clear();
                                    break;
                                }
                            }
                        }
                        i--;
                        f = 1;
                    } else {
                        if (p1 <= index_right && p1 % GameSize >= k_left % GameSize && p1 % GameSize <= k_right % GameSize) {
                            for (int t = p1; t >= index; t--) {
                                arr.add(t);
                                path_line[t] = 0;
                            }
                            path_line[p1] = 2;
                            for (int j = p1 - GameSize; j > 0; j -= GameSize) {
                                System.out.println("aaar");
                                arr.add(j);
                                path_line[j] = 1;
                                if (j / GameSize == k / GameSize) {
                                    for (int t = 0; t < arr.size(); t++) {
                                        path.add(arr.get(t));
                                    }
                                    if (j < k) {
                                        for (int t = j + 1; t <= k; t++) {
                                            path.add(t);
                                            path_line[t] = 0;
                                        }
                                        path_line[j] = 4;
                                    } else {
                                        for (int t = j - 1; t >= k; t--) {
                                            path.add(t);
                                            path_line[t] = 0;
                                        }
                                        path_line[j] = 5;
                                    }
                                    return true;
                                }
                                if (((JLabel) label_arr.get(j)).getIcon() != null) {
                                    arr.clear();
                                    break;
                                }
                            }
                        }
                        p1++;
                        f = 0;
                    }
                }
            } else if (index / GameSize < k / GameSize) {      //第二个在第一个下方
                int i = index, p1 = index + 1, f = 0;
                while (true) {
                    if (i < index_left && p1 > index_right)
                        break;
                    ArrayList arr = new ArrayList();
                    if (f == 0) {
                        if (i >= index_left && i % GameSize >= k_left % GameSize && i % GameSize <= k_right % GameSize) {
                            for (int t = i; t <= index; t++) {
                                arr.add(t);
                                path_line[t] = 0;
                            }
                            path_line[i] = 4;
                            for (int j = i + GameSize; j > 0; j += GameSize) {
                                System.out.println("bbbl");
                                arr.add(j);
                                path_line[j] = 1;
                                if (j / GameSize == k / GameSize) {
                                    for (int t = 0; t < arr.size(); t++) {
                                        path.add(arr.get(t));
                                    }
                                    if (j < k) {
                                        for (int t = j + 1; t <= k; t++) {
                                            path.add(t);
                                            path_line[t] = 0;
                                        }
                                        path_line[j] = 3;
                                    } else {
                                        for (int t = j - 1; t >= k; t--) {
                                            path.add(t);
                                            path_line[t] = 0;
                                        }
                                        path_line[j] = 2;
                                    }
                                    return true;
                                }
                                if (((JLabel) label_arr.get(j)).getIcon() != null) {
                                    arr.clear();
                                    break;
                                }
                            }
                        }
                        i--;
                        f = 1;
                    } else {
                        if (p1 <= index_right && p1 % GameSize >= k_left % GameSize && p1 % GameSize <= k_right % GameSize) {
                            for (int t = p1; t >= index; t--) {
                                arr.add(t);
                                path_line[t] = 0;
                            }
                            path_line[p1] = 5;
                            for (int j = p1 + GameSize; j > 0; j += GameSize) {
                                System.out.println("bbbr");
                                arr.add(j);
                                path_line[j] = 1;
                                if (j / GameSize == k / GameSize) {
                                    for (int t = 0; t < arr.size(); t++) {
                                        path.add(arr.get(t));
                                    }
                                    if (j < k) {
                                        for (int t = j + 1; t <= k; t++) {
                                            path.add(t);
                                            path_line[t] = 0;
                                        }
                                        path_line[j] = 3;
                                    } else {
                                        for (int t = j - 1; t >= k; t--) {
                                            path.add(t);
                                            path_line[t] = 0;
                                        }
                                        path_line[j] = 2;
                                    }
                                    return true;
                                }
                                if (((JLabel) label_arr.get(j)).getIcon() != null) {
                                    arr.clear();
                                    break;
                                }
                            }
                        }
                        p1++;
                        f = 0;
                    }
                }
            }
            //垂直方向
            if (index % GameSize < k % GameSize) {           //第一个在第二个左方
                int i = index, p1 = index + GameSize, f = 0;
                while (true) {
                    if (i < index_top && p1 > index_bottum)
                        break;
                    ArrayList arr = new ArrayList();
                    if (f == 0) {
                        if (i >= index_top && i / GameSize >= k_top / GameSize && i / GameSize <= k_bottum / GameSize) {
                            for (int t = i; t <= index; t += GameSize) {
                                arr.add(t);
                                path_line[t] = 1;
                            }
                            path_line[i] = 4;
                            for (int j = i + 1; j < (i / GameSize + 1) * GameSize - 1; j++) {
                                System.out.println("ccct");
                                arr.add(j);
                                path_line[j] = 0;
                                if (j % GameSize == k % GameSize) {
                                    for (int t = 0; t < arr.size(); t++) {
                                        path.add(arr.get(t));
                                    }
                                    if (j < k) {
                                        for (int t = j + GameSize; t <= k; t += GameSize) {
                                            path.add(t);
                                            path_line[t] = 1;
                                        }
                                        path_line[j] = 5;
                                    } else {
                                        for (int t = j - GameSize; t >= k; t -= GameSize) {
                                            path.add(t);
                                            path_line[t] = 1;
                                        }
                                        path_line[j] = 2;
                                    }
                                    return true;
                                }
                                if (((JLabel) label_arr.get(j)).getIcon() != null) {
                                    arr.clear();
                                    break;
                                }
                            }
                        }
                        i -= GameSize;
                        f = 1;
                    } else {
                        if (p1 <= index_bottum && p1 / GameSize >= k_top / GameSize && p1 / GameSize <= k_bottum / GameSize) {
                            for (int t = p1; t >= index; t -= GameSize) {
                                arr.add(t);
                                path_line[t] = 1;
                            }
                            path_line[p1] = 3;
                            for (int j = p1 + 1; j < (p1 / GameSize + 1) * GameSize - 1; j++) {
                                System.out.println("cccb");
                                arr.add(j);
                                path_line[j] = 0;
                                if (j % GameSize == k % GameSize) {
                                    for (int t = 0; t < arr.size(); t++) {
                                        path.add(arr.get(t));
                                    }
                                    if (j < k) {
                                        for (int t = j + GameSize; t <= k; t += GameSize) {
                                            path.add(t);
                                            path_line[t] = 1;
                                        }
                                        path_line[j] = 5;
                                    } else {
                                        for (int t = j - GameSize; t >= k; t -= GameSize) {
                                            path.add(t);
                                            path_line[t] = 1;
                                        }
                                        path_line[j] = 2;
                                    }
                                    return true;
                                }
                                if (((JLabel) label_arr.get(j)).getIcon() != null) {
                                    arr.clear();
                                    break;
                                }
                            }
                        }
                        p1 += GameSize;
                        f = 0;
                    }
                }
            } else if (index % GameSize > k % GameSize) {       //第一个在第二个右方
                int i = index, p1 = index + GameSize, f = 0;
                while (true) {
                    if (i < index_top && p1 > index_bottum)
                        break;
                    ArrayList arr = new ArrayList();
                    if (f == 0) {
                        if (i >= index_top && i / GameSize >= k_top / GameSize && i / GameSize <= k_bottum / GameSize) {
                            for (int t = i; t <= index; t += GameSize) {
                                arr.add(t);
                                path_line[t] = 1;
                            }
                            path_line[i] = 5;
                            for (int j = i - 1; j > i / GameSize * GameSize; j--) {
                                System.out.println("dddt");
                                arr.add(j);
                                path_line[j] = 0;
                                if (j % GameSize == k % GameSize) {
                                    for (int t = 0; t < arr.size(); t++) {
                                        path.add(arr.get(t));
                                    }
                                    if (j < k) {
                                        for (int t = j + GameSize; t <= k; t += GameSize) {
                                            path.add(t);
                                            path_line[t] = 1;
                                        }
                                        path_line[j] = 4;
                                    } else {
                                        for (int t = j - GameSize; t >= k; t -= GameSize) {
                                            path.add(t);
                                            path_line[t] = 1;
                                        }
                                        path_line[j] = 3;
                                    }
                                    return true;
                                }
                                if (((JLabel) label_arr.get(j)).getIcon() != null) {
                                    arr.clear();
                                    break;
                                }
                            }
                        }
                        i -= GameSize;
                        f = 1;
                    } else {
                        if (p1 <= index_bottum && p1 / GameSize >= k_top / GameSize && p1 / GameSize <= k_bottum / GameSize) {
                            for (int t = p1; t >= index; t -= GameSize) {
                                arr.add(t);
                                path_line[t] = 1;
                            }
                            path_line[p1] = 2;
                            for (int j = p1 - 1; j > p1 / GameSize * GameSize; j--) {
                                System.out.println("dddb");
                                arr.add(j);
                                path_line[j] = 0;
                                if (j % GameSize == k % GameSize) {
                                    for (int t = 0; t < arr.size(); t++) {
                                        path.add(arr.get(t));
                                    }
                                    if (j < k) {
                                        for (int t = j + GameSize; t <= k; t += GameSize) {
                                            path.add(t);
                                            path_line[t] = 1;
                                        }
                                        path_line[j] = 4;
                                    } else {
                                        for (int t = j - GameSize; t >= k; t -= GameSize) {
                                            path.add(t);
                                            path_line[t] = 1;
                                        }
                                        path_line[j] = 3;
                                    }
                                    return true;
                                }
                                if (((JLabel) label_arr.get(j)).getIcon() != null) {
                                    arr.clear();
                                    break;
                                }
                            }
                        }
                        p1 += GameSize;
                        f = 0;
                    }
                }
            }

            return false;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            //获取点击的label
            JLabel j = (JLabel) e.getComponent();
            //获取位置
            Point p = j.getLocation();
            if (j.getIcon() != null)
                if (index != -1) {
                    //取消上一个边框
                    ((JLabel) label_arr.get(index)).setBorder(null);
                    if (!isOK(p)) {
                        //点击出现边框
                        j.setBorder(BorderFactory.createLineBorder(Color.RED));
                        //记录位置
                        p_index = p;
                        index = p.x / W + p.y / W * GameSize;
                    } else {
                        //路径
                        for (int i = 0; i < path.size(); i++) {
                            System.out.print(path.get(i) + "a");
                            if ((int) path.get(i) == index || (int) path.get(i) == k)
                                continue;
                            ((JLabel) label_arr.get((int) path.get(i))).setIcon(icon_line[path_line[(int) path.get(i)]]);
                        }
                        can = 1;
                    }
                } else {
                    //点击出现边框
                    j.setBorder(BorderFactory.createLineBorder(Color.RED));
                    //记录位置
                    p_index = p;
                    index = p.x / W + p.y / W * GameSize;
                }
        }

        @SuppressWarnings("static-access")
        @Override
        public void mouseReleased(MouseEvent e) {
            if (can == 1) {
                //获取点击的label
                JLabel j = (JLabel) e.getComponent();
                //当前暂停1秒
                try {
                    Thread.currentThread().sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                //消除路径
                for (int i = 0; i < path.size(); i++) {
                    ((JLabel) label_arr.get((int) path.get(i))).setIcon(null);
                }
                //路径数组复位
                path.clear();
                //消除
                ((JLabel) label_arr.get(index)).setIcon(null);
                j.setIcon(null);

                //消除音乐,b为PlaySound类中控制声音播放
                if (PlaySound.b[1]) {
                    PlaySound ps = new PlaySound();
                    ps.open("sounds/ClearSound.wav");
                    ps.play();
                    ps.start();
                }
                can = 0;
                sum -= 2;
                if (sum == 0) {
                    if (LEVEL == -1) {
                        timer.cancel();
                        new Dialog(g, 2, LEVEL, "闯关成功");
                    } else
                        new Dialog(g, 1, LEVEL, null);
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
