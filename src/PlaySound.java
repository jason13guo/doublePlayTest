import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * 本类描述声音播放
 *
 * @author JasonG
 * @create 2022/2/11
 */
public class PlaySound {

    /**
     * 音频文件
     * File类主要是JAVA为文件这块的操作(如删除、新增等)而设计的相关类
     * 类的包名是java.io，其实现了Serializable, Comparable两大接口以便于其对象可序列化和比较
     */
    private File file;

    /**
     * 音频输入流是具有指定音频格式和长度的输入流。长度以样本帧而不是字节表示。
     * 提供了几种方法用于从流中读取一定数量的字节，或未指定数量的字节。
     * 音频输入流跟踪读取的最后一个字节。您可以跳过任意数量的字节以到达稍后的位置进行读取。
     * 音频输入流可能支持标记。
     */
    private AudioInputStream stream;

    /**
     * 音频格式
     * AudioFormat是指定声音流中特定数据排列的类。通过检查以音频格式存储的信息
     * AudioFormat对象可以包含一组属性。
     * 属性是一对键和值：键是String类型，关联的属性值是任意对象。
     */
    private AudioFormat format;

    /**
     * 音频行信息
     * DataLine是一个接口,继承了Line.
     * 功能包括启动、停止、排出和刷新通过线路的音频数据的传输控制方法。
     */
    private DataLine.Info info;

    /**
     * 音频行
     * Clip接口继承了DataLine
     * Clip接口代表一种特殊的数据线，其音频数据可以在播放之前加载，而不是实时流式传输。
     */
    private Clip clip;

    /**
     * 控制声音播放
     */
    static boolean[] b = new boolean[]{true, true, true, true};

    /**
     * 打开声音文件方法
     */
    void open(String s) {
        //音频文件对象
        file = new File(s);
        try {
            /**音频输入流对象
             * getAudioInputStream从提供的File获取音频输入流。 File必须指向有效的音频文件数据
             */
            stream = AudioSystem.getAudioInputStream(file);
            //音频格式对象
            format = stream.getFormat();
            //UnsupportedAudioFileException 是表示操作失败的异常，因为文件不包含可识别文件类型和格式的有效数据。
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立播放音频的音频行
     */
    void play() {
        //音频行信息对象
        info = new DataLine.Info(Clip.class, format);
        try {
            //音频行对象
            clip = (Clip) AudioSystem.getLine(info);
            //将音频数据读入音频行
            clip.open(stream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放
     */
    void stop() {
        //暂停音频播放
        clip.stop();
    }

    /**
     * 开始播放
     */
    void start() {
        //播放音频行
        clip.start();
    }

    /**
     * 回访背景音乐设置
     */
    void loop() {
        //回放,从当前位置开始循环播放。播放将继续到循环的结束点，然后循环回到循环开始点count次，最后继续播放到剪辑的结尾
        clip.loop(20);
    }
}
