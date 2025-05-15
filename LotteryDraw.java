import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

//导入所需的包

public class LotteryDraw extends JFrame {
    private JTextArea inputArea;//文本输入区
    private JButton drawButton;//按钮
    private JLabel resultLabel;//结果标签
    private DefaultListModel<String> poolModel;//列表模型
    private JList<String> poolList;//列表
    private List<String> candidates;//候选人列表
    private boolean initialized = false;//是否初始化

    public LotteryDraw() {

        //设置窗口标题、大小、关闭操作、位置、布局
        setTitle("随机抽签程序");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //创建文本输入区，设置边框标题，添加到中心
        inputArea = new JTextArea(10, 30);
        inputArea.setBorder(BorderFactory.createTitledBorder("请输入候选人名单（每行一个）："));
        add(new JScrollPane(inputArea), BorderLayout.CENTER);

        //创建列表模型，设置边框标题，添加到右边
        poolModel = new DefaultListModel<>();
        poolList = new JList<>(poolModel);
        poolList.setBorder(BorderFactory.createTitledBorder("剩余未被抽中的人："));
        poolList.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        add(new JScrollPane(poolList), BorderLayout.EAST);

        //创建按钮，设置文本，添加到底部
        drawButton = new JButton("抽签");
        add(drawButton, BorderLayout.SOUTH);

        //创建结果标签，设置文本，添加到顶部，创建空的文本标签为了后续可以加内容，与JTextField的区别是JTextField是输入框，JLabel是显示文本的标签
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        add(resultLabel, BorderLayout.NORTH);

        //添加按钮点击事件
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!initialized || candidates == null) {
                    String text = inputArea.getText().trim();
                    if (text.isEmpty()) {
                        JOptionPane.showMessageDialog(LotteryDraw.this, "请先输入候选人名单！", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    String[] lines = text.split("\\n");
                    candidates = new ArrayList<>();
                    poolModel.clear();
                    for (String line : lines) {
                        String name = line.trim();
                        if (!name.isEmpty()) {
                            candidates.add(name);
                            poolModel.addElement(name);
                        }
                    }
                    if (candidates.isEmpty()) {
                        JOptionPane.showMessageDialog(LotteryDraw.this, "名单不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    initialized = true;
                }
                if (candidates.isEmpty()) {
                    JOptionPane.showMessageDialog(LotteryDraw.this, "所有人都已被抽中！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    resultLabel.setText("抽签结束");
                    return;
                }
                Random rand = new Random();
                int idx = rand.nextInt(candidates.size());
                String winner = candidates.remove(idx);
                poolModel.removeElement(winner);
                resultLabel.setText("抽中：" + winner);
            }
        });

        //添加文本输入区变化事件
        inputArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { resetPool(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { resetPool(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { resetPool(); }
            private void resetPool() {
                initialized = false;
                resultLabel.setText("");
                poolModel.clear();
                candidates = null;
            }
        });
    }

    //主方法
    //使用SwingUtilities.invokeLater()方法在事件调度线程中创建和显示窗口
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LotteryDraw().setVisible(true);
        });
    }
} 