import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DigitalCalculator extends JFrame {
    private JTextField display;
    private double firstOperand = 0;
    private String operator = "";
    private boolean startNewInput = true;

    public DigitalCalculator() {
        setTitle("Digital Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        JPanel panel = new JPanel(new BorderLayout());
        display = new JTextField();
        display.setEditable(false);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.GREEN);
        display.setFont(new Font("Digital-7", Font.PLAIN, 40));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Digital-7", Font.PLAIN, 30));
            button.setBackground(Color.BLACK);
            button.setForeground(Color.GREEN);
            button.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }
        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String buttonText = ((JButton) e.getSource()).getText();
            switch (buttonText) {
                case "C":
                    clearDisplay();
                    break;
                case "=":
                    calculateResult();
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                    setOperator(buttonText);
                    break;
                default:
                    appendDigit(buttonText);
                    break;
            }
        }
    }

    private void clearDisplay() {
        display.setText("");
        startNewInput = true;
    }

    private void appendDigit(String digit) {
        if (startNewInput) {
            display.setText(digit);
            startNewInput = false;
        } else {
            display.setText(display.getText() + digit);
        }
    }

    private void setOperator(String op) {
        if (!display.getText().isEmpty()) {
            firstOperand = Double.parseDouble(display.getText());
            operator = op;
            startNewInput = true;
        }
    }

    private void calculateResult() {
        if (!operator.isEmpty() && !startNewInput && !display.getText().isEmpty()) {
            double secondOperand = Double.parseDouble(display.getText());
            double result = 0;
            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        display.setText("Error: Division by zero");
                        return;
                    }
                    break;
            }
            display.setText(String.valueOf(result));
            startNewInput = true;
            operator = "";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DigitalCalculator::new);
    }
}
