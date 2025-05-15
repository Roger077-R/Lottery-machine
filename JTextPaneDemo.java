import javax.swing.*;
import javax.swing.text.*;

public class JTextPaneDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JTextPane 示例");
        JTextPane textPane = new JTextPane();

        // 设置部分文本为红色加粗
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setForeground(attr, java.awt.Color.RED);
        StyleConstants.setBold(attr, true);

        try {
            doc.insertString(doc.getLength(), "红色加粗文本\n", attr);
            doc.insertString(doc.getLength(), "普通文本", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        frame.add(new JScrollPane(textPane));
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
