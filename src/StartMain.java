import javax.swing.*;

/**
 * @author JasonG
 * @create 2022/2/11
 */
public class StartMain {
    static FirstFrame e1;
    static PlaySound p;

    public static void main(String[] args) {

        e1=new FirstFrame(0);
        //监听关闭窗体按钮
        e1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置可视
        e1.setVisible(true);
        //设置不可拉伸
        e1.setResizable(false);

        if (p==null){
            //声音设置
            p=new PlaySound();
            p.open("sounds/background.wav");
            p.play();
            p.loop();
            p.start();
        }
    }
}
