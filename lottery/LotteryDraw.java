package lottery;
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
        setTitle("随机抽签程序");//设置窗口标题
        setSize(400, 300);//设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭操作，当点击关闭按钮时，程序退出
        setLocationRelativeTo(null);//设置窗口位置，居中显示
        setLayout(new BorderLayout());//采用BorderLayout布局，将窗口分为5个区域，分别是北、南、东、西、中
        setVisible(true);//设置窗口可见

        //创建文本输入区，设置边框标题，添加到中心
        inputArea = new JTextArea(10, 30);
        inputArea.setBorder(BorderFactory.createTitledBorder("请输入候选人名单（每行一个）："));
        add(new JScrollPane(inputArea), BorderLayout.CENTER);//加入到滚动页面中并居中放置

        //创建列表模型，设置边框标题，添加到右边
        poolModel = new DefaultListModel<>();
        poolList = new JList<>(poolModel);
        poolList.setBorder(BorderFactory.createTitledBorder("剩余未被抽中的人："));
        poolList.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        add(new JScrollPane(poolList), BorderLayout.EAST);

        //创建按钮，设置文本，添加到底部
        drawButton = new JButton("抽签");
        add(drawButton, BorderLayout.SOUTH);//将按钮添加到底部

        //创建结果标签，设置文本，添加到顶部，创建空的文本标签为了后续可以加内容，与JTextField的区别是JTextField是输入框，JLabel是显示文本的标签
        resultLabel = new JLabel("", SwingConstants.CENTER);//创建一个空的文本标签
        resultLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));//设置字体
        add(resultLabel, BorderLayout.NORTH);//将结果标签添加到顶部

        //添加按钮点击事件
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//重写actionPerformed方法，当按钮被点击时，执行以下代码
                if (!initialized || candidates == null) {//如果未初始化或候选人列表为空
                    String text = inputArea.getText().trim();//获取输入框中的文本并去除前后空格
                    if (text.isEmpty()) {//如果文本为空
                        JOptionPane.showMessageDialog(LotteryDraw.this, "请先输入候选人名单！", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    String[] lines = text.split("\\n");//将文本按行分割成字符串数组，其中两个斜杠表示转义字符，表示换行符
                    candidates = new ArrayList<>();//创建一个空的候选人列表
                    poolModel.clear();//清空列表模型
                    for (String line : lines) {//遍历字符串数组
                        String name = line.trim();//去除前后空格
                        if (!name.isEmpty()) {//如果字符串不为空
                            candidates.add(name);//将字符串添加到候选人列表中
                            poolModel.addElement(name);//将字符串添加到列表模型中
                        }
                    }
                    if (candidates.isEmpty()) {//如果候选人列表为空
                        JOptionPane.showMessageDialog(LotteryDraw.this, "名单不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    initialized = true;//初始化
                }
                if (candidates.isEmpty()) {//如果候选人列表为空
                    JOptionPane.showMessageDialog(LotteryDraw.this, "所有人都已被抽中！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    resultLabel.setText("抽签结束");
                    return;
                }
                Random rand = new Random();//创建一个随机数生成器
                int idx = rand.nextInt(candidates.size());//生成一个随机数，范围是0到候选人列表的大小减1
                String winner = candidates.remove(idx);//从候选人列表中移除一个随机元素
                poolModel.removeElement(winner);//从列表模型中移除一个随机元素
                resultLabel.setText("抽中：" + winner);//设置结果标签的文本为抽中的元素
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
        //使用SwingUtilities.invokeLater()方法在事件调度线程中创建和显示窗口,保证线程安全
        SwingUtilities.invokeLater(() -> {
            new LotteryDraw();
        });
    }
} 