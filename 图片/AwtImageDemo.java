package 图片;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;

public class AwtImageDemo extends Frame {
    private Image img;

    public AwtImageDemo() {
        // 加载图片
        try {
            img = ImageIO.read(new File("图片/test.jpg"));//虽然Windows是用\，但是Java会自动识别为/
            // 图片\test.jpg 中的\t会被识别为制表符，除非写成 图片\\test.jpg
            // 图片/test.jpg 是相对路径，相对于当前项目的根目录
            // C:/Users/Administrator/Desktop/图片/test.jpg 是绝对路径
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTitle("AWT图片显示示例");
        setSize(400, 300);
        setLocationRelativeTo(null); // 屏幕居中
        setVisible(true);

        // 关闭窗口
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    // 重写paint方法绘制图片
    public void paint(Graphics g) {
        if (img != null) {
            g.drawImage(img, 50, 50, this); // 在(50,50)位置绘制图片
        } else {
            g.drawString("图片加载失败", 100, 100);
        }
    }

    public static void main(String[] args) {
        new AwtImageDemo();
    }
}
