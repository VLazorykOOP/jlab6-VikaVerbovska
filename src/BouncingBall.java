import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BouncingBall extends JPanel implements ActionListener {

    private int xPos = 50;  // Початкові координати
    private int yPos = 50;
    private int ballSize = 50;  // Розмір кола
    private int xSpeed = 2;  // Швидкість по X
    private int ySpeed = 2;  // Швидкість по Y
    private Timer myTimer;

    public BouncingBall() {
        myTimer = new Timer(10, this);  // Таймер для оновлення
        myTimer.start();  // Запуск таймера
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);  // Колір кола
        g.fillOval(xPos, yPos, ballSize, ballSize);  // Малюємо коло
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Зміна координат
        xPos += xSpeed;
        yPos += ySpeed;

        // Перевірка на торкання стінок
        if (xPos <= 0 || xPos + ballSize >= getWidth()) {
            xSpeed = -xSpeed;  // Міняємо напрямок по X
            ballSize -= 2;  // Зменшуємо розмір
        }

        if (yPos <= 0 || yPos + ballSize >= getHeight()) {
            ySpeed = -ySpeed;  // Міняємо напрямок по Y
            ballSize -= 2;  // Зменшуємо розмір
        }

        // Обмеження мінімального розміру
        if (ballSize < 30) {
            ballSize = 30;
        }

        repaint();  // Оновлюємо малюнок
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Bouncing Ball");
        BouncingBall ball = new BouncingBall();
        window.add(ball);
        window.setSize(400, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}
