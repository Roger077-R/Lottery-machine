import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LotteryDraw extends JFrame {
    private JTextArea inputArea;
    private JButton drawButton;
    private JLabel resultLabel;
    private DefaultListModel<String> poolModel;
    private JList<String> poolList;
    private List<String> candidates;
    private boolean initialized = false;

    public LotteryDraw() {
        setTitle("随机抽签程序");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inputArea = new JTextArea(10, 30);
        inputArea.setBorder(BorderFactory.createTitledBorder("请输入候选人名单（每行一个）："));
        add(new JScrollPane(inputArea), BorderLayout.CENTER);

        poolModel = new DefaultListModel<>();
        poolList = new JList<>(poolModel);
        poolList.setBorder(BorderFactory.createTitledBorder("剩余未被抽中的人："));
        poolList.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        add(new JScrollPane(poolList), BorderLayout.EAST);

        drawButton = new JButton("抽签");
        add(drawButton, BorderLayout.SOUTH);

        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        add(resultLabel, BorderLayout.NORTH);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LotteryDraw().setVisible(true);
        });
    }
} 