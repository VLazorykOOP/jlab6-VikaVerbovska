import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class SimpleMatrixApp extends JFrame {

    private JTextField filePathField;
    private JTextArea outputArea;
    private boolean[] logicVector;

    public SimpleMatrixApp() {
        setTitle("Matrix Logic App");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель для введення файлу
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel fileLabel = new JLabel("Введіть шлях до файлу:");
        inputPanel.add(fileLabel);

        filePathField = new JTextField(20);
        inputPanel.add(filePathField);

        JButton loadButton = new JButton("Завантажити");
        inputPanel.add(loadButton);
        loadButton.addActionListener(new LoadButtonClickListener());

        // Панель для результату
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Клас для обробки натискання кнопки "Завантажити"
    private class LoadButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath = filePathField.getText();
            try {
                readMatrixFromFile(filePath);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Файл не знайдено.", "Помилка", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidFormatException ex) {
                JOptionPane.showMessageDialog(null, "Невірний формат файлу.", "Помилка", JOptionPane.ERROR_MESSAGE);
            } catch (MatrixValueException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Помилка читання файлу.", "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Метод для завантаження матриці з файлу
    private void readMatrixFromFile(String filePath) throws IOException, InvalidFormatException, MatrixValueException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        int size = scanner.nextInt();

        if (size > 15 || size <= 0) {
            throw new InvalidFormatException("Невірний розмір матриці.");
        }

        int[][] matrix = new int[size][size];
        logicVector = new boolean[size];

        outputArea.append("Матриця:\n");

        for (int i = 0; i < size; i++) {
            int negatives = 0, positives = 0;
            for (int j = 0; j < size; j++) {
                if (!scanner.hasNextInt()) {
                    throw new InvalidFormatException("Некоректні дані у файлі.");
                }
                matrix[i][j] = scanner.nextInt();
                outputArea.append(matrix[i][j] + " ");

                // Перевірка на дуже великі значення
                if (matrix[i][j] > 100) {
                    throw new MatrixValueException("Значення більше 100: " + matrix[i][j]);
                }

                if (matrix[i][j] < 0) negatives++;
                else if (matrix[i][j] > 0) positives++;
            }
            outputArea.append("\n");
            logicVector[i] = negatives > positives;
        }

        outputArea.append("\nЛогічний вектор:\n");
        for (boolean value : logicVector) {
            outputArea.append(value + " ");
        }
    }

    // Власне виключення для значень
    class MatrixValueException extends ArithmeticException {
        public MatrixValueException(String message) {
            super(message);
        }
    }

    // Виключення для неправильного формату
    class InvalidFormatException extends Exception {
        public InvalidFormatException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimpleMatrixApp().setVisible(true);
        });
    }
}
