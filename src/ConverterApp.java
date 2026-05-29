import javax.swing.*;
import java.awt.*;

public class ConverterApp {
    public static void main(String[] args) {
        // Прозорец
        JFrame frame = new JFrame("Конвертор: Арабски <-> Римски");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Делим на 3 равни части по височина
        frame.setLayout(new GridLayout(3, 1, 10, 10));
        frame.getContentPane().setBackground(new Color(198, 198, 198));

        Font myFont = new Font("Segoe UI", Font.BOLD, 14);

        // Елементи
        JTextArea inputArea = new JTextArea();
        inputArea.setFont(myFont);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(myFont);
        resultArea.setBackground(new Color(245, 245, 245));

        JButton convertButton = new JButton("КОНВЕРТИРАЙ");
        convertButton.setBackground(Color.BLACK);
        convertButton.setForeground(Color.WHITE);
        convertButton.setFocusPainted(false);
        convertButton.setFont(myFont);

        JButton helpButton = new JButton("ПРАВИЛА");
        helpButton.setBackground(new Color(38, 207, 26));
        helpButton.setForeground(Color.WHITE);
        helpButton.setFocusPainted(false);
        helpButton.setFont(myFont);

        JRadioButton arabToRoman = new JRadioButton("Арабски -> Римски", true);
        JRadioButton romanToArab = new JRadioButton("Римски -> Арабски");
        ButtonGroup group = new ButtonGroup();
        group.add(arabToRoman);
        group.add(romanToArab);

        // Панел за въвеждане - с бордюр за да не е до края на прозореца
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 0, 50));
        topPanel.setOpaque(false);
        topPanel.add(new JLabel("Въведи числа (по едно на ред):"), BorderLayout.NORTH);
        topPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        // Панел за бутони - центрирани
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        centerPanel.setOpaque(false);
        centerPanel.add(arabToRoman);
        centerPanel.add(romanToArab);
        centerPanel.add(convertButton);
        centerPanel.add(helpButton);

        // Панел за резултати - също с бордюр
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(-20, 50, 10, 50));
        bottomPanel.setOpaque(false);
        bottomPanel.add(new JLabel("РЕЗУЛТАТИ:"), BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Добавяне към рамката
        frame.add(topPanel);
        frame.add(centerPanel);
        frame.add(bottomPanel);

        // Логика
        convertButton.addActionListener(e -> {
            String[] lines = inputArea.getText().split("\n");
            StringBuilder output = new StringBuilder();
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                try {
                    if (arabToRoman.isSelected()) {
                        int num = Integer.parseInt(line.trim());
                        if (num < 1 || num > 3999) output.append(line).append(" -> Грешка!\n");
                        else output.append(line).append(" -> ").append(toRoman(num)).append("\n");
                    } else {
                        int res = fromRoman(line.trim());
                        if (res == 0) output.append(line).append(" -> Невалидно!\n");
                        else output.append(line).append(" -> ").append(res).append("\n");
                    }
                } catch (Exception ex) { output.append(line).append(" -> Невалидно!\n"); }
            }
            resultArea.setText(output.toString());
        });

        helpButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "ПРАВИЛА: I=1, V=5, X=10, L=50, C=100, D=500, M=1000. Диапазон: 1-3999."));

        frame.setVisible(true);
    }

    public static String toRoman(int number) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                res.append(roman[i]);
            }
        }
        return res.toString();
    }

    public static int fromRoman(String roman) {
        roman = roman.toUpperCase();
        int result = 0, prevValue = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = switch (roman.charAt(i)) {
                case 'I' -> 1; case 'V' -> 5; case 'X' -> 10;
                case 'L' -> 50; case 'C' -> 100; case 'D' -> 500;
                case 'M' -> 1000; default -> 0;
            };
            if (value == 0) return 0;
            if (value < prevValue) result -= value;
            else result += value;
            prevValue = value;
        }
        return result;
    }
}