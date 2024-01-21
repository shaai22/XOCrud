import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class GameForm {
    private JPanel rootPanel;
    private JPanel textPanel;
    private JLabel textLabel;
    private JPanel boardPanel;
    JButton[][] cells = new JButton[3][3];
    String sign = "X";
    String playerName;
    String winnerName;
    boolean gameOver = false;
    int turns = 0;

    GameForm(String newName) {
        playerName = newName;
        JFrame frame = new JFrame("Игра");
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(200, 220));
        frame.pack();
        frame.setVisible(true);

        textLabel.setText("Первый ход за " + playerName);

        // Добавление клеток на доску
        boardPanel.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton cell = new JButton();
                cells[i][j] = cell;
                boardPanel.add(cell);

                cell.addActionListener(this::cellActionPerformed);
            }
        }
    }

    // Нажатие на клетку
    private void cellActionPerformed(ActionEvent e) {
        if (gameOver) return;

        JButton tile = (JButton) e.getSource();
        if (tile.getText().isEmpty()) {
            tile.setText(sign);
            turns++;
            checkWinner();
            if (!gameOver) {
                sign = sign.equals("X") ? "O" : "X";
                textLabel.setText("Ходит компьютер");
                // Выключаем клетки и делаем задержку
                // перед ходом компьютера
                pauseInput(true);
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    computerMove();
                }).start();
            }
        }
    }

    // Выключение клеток
    private void pauseInput(Boolean pause) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j].setEnabled(!pause);
            }
        }
    }

    // Ход компьютера
    private void computerMove() {
        Random computerMove = new Random();
        int iRand = computerMove.nextInt(3);
        int jRand = computerMove.nextInt(3);
        while (!cells[iRand][jRand].getText().isEmpty()) {
            iRand = computerMove.nextInt(3);
            jRand = computerMove.nextInt(3);
        }
        cells[iRand][jRand].setText(sign);
        turns++;
        checkWinner();
        if (!gameOver) {
            sign = sign.equals("X") ? "O" : "X";
            pauseInput(false);
            textLabel.setText("Ходит " + playerName);
        }
    }

    // Проверка на победу
    private void checkWinner() {
        // по строчкам
        for (int r = 0; r < 3; r++) {
            if (cells[r][0].getText().isEmpty()) continue;

            if (cells[r][0].getText().equals(cells[r][1].getText()) &&
                    cells[r][1].getText().equals(cells[r][2].getText())) {
                for (int i = 0; i < 3; i++) {
                    cells[r][i].setBackground(Color.green);
                }
                gameOver = true;
                pauseInput(false);
                textLabel.setText("Победа " + winnerName);
                return;
            }
        }

        // по столбцам
        for (int c = 0; c < 3; c++) {
            if (cells[0][c].getText().isEmpty()) continue;

            if (cells[0][c].getText().equals(cells[1][c].getText()) &&
                    cells[1][c].getText().equals(cells[2][c].getText())) {
                for (int i = 0; i < 3; i++) {
                    cells[i][c].setBackground(Color.green);
                }
                setWinnerText();
                return;
            }
        }

        // по диагонали [\]
        if (cells[0][0].getText().equals(cells[1][1].getText()) &&
                cells[1][1].getText().equals(cells[2][2].getText()) &&
                !cells[0][0].getText().isEmpty()) {
            for (int i = 0; i < 3; i++) {
                cells[i][i].setBackground(Color.green);
            }
            setWinnerText();
            return;
        }

        // по диагонали [/]
        if (cells[0][2].getText().equals(cells[1][1].getText()) &&
                cells[1][1].getText().equals(cells[2][0].getText()) &&
                !cells[0][2].getText().isEmpty()) {
            cells[0][2].setBackground(Color.green);
            cells[1][1].setBackground(Color.green);
            cells[2][0].setBackground(Color.green);
            setWinnerText();
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    cells[r][c].setBackground(Color.orange);
                }
            }
            gameOver = true;
            pauseInput(false);
            textLabel.setText("Ничья");
        }
    }

    private void setWinnerText() {
        if (sign.equals("X")) {
            winnerName = playerName;
        } else {
            winnerName = "компютера";
        }
        gameOver = true;
        pauseInput(false);
        textLabel.setText("Победа " + winnerName);
    }
}
