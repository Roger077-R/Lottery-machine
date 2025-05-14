import java.awt.*;
import javax.swing.*;



public class DemoJScraollPaneTest extends JFrame {
    //实现constructor
    public DemoJScraollPaneTest() {
        //获取一个主容器，
        Container container = getContentPane();
        JTextArea jta = new JTextArea(20, 50);
        JScrollPane jsp = new JScrollPane(jta);
        container.add(jsp);
        setTitle("DemoJScraollPaneTest");
        setSize(300, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //屏幕居中显示
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        DemoJScraollPaneTest jst = new DemoJScraollPaneTest();
        //创建了一个DemoJScraollPanTest的实例之后因为是继承的JFframe所以会调用构造函数并且自动创建JFrame的实例这样就会初始化容器
    }
}
