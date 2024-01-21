import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.*;

public class PlayerListForm {
    private JPanel rootPanel;
    private JTextField playerNameBox;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JList playerListBox;
    private JButton playButton;
    private final DefaultListModel playerListModel = new DefaultListModel();

    public PlayerListForm() {
        // Инициализация формы
        JFrame frame = new JFrame("Регистрация игроков");
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(300,400));
        frame.pack();
        frame.setVisible(true);

        // Создание пустого списка
        playerListBox.setModel(playerListModel);

        // Подключение обработчиков событий
        playerListBox.addListSelectionListener(this::listSelectionChanged);
        createButton.addActionListener(this::createButtonActionPerformed);
        updateButton.addActionListener(this::updateButtonActionPerformed);
        deleteButton.addActionListener(this::deleteButtonActionPerformed);
        playButton.addActionListener(this::playButtonActionPerformed);
    }

    // Выбор элемента в списке
    private void listSelectionChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            var source = (JList) e.getSource();
            if (source.getSelectedIndex() >= 0) {
                playerNameBox.setText(source.getSelectedValue().toString());
            }
        }
    }

    // Нажатие на кнопку "Записать"
    private void createButtonActionPerformed(ActionEvent e) {
        String playerName = playerNameBox.getText();
        if (!playerName.isEmpty()) {
            playerListModel.addElement(playerName);
        }
    }

    // Нажатие на кнопку "Изменить"
    private void updateButtonActionPerformed(ActionEvent e) {
        String playerName = playerNameBox.getText();
        if (playerListBox.getSelectedIndex() >= 0) {
            if (!playerName.isEmpty()) {
                playerListModel.setElementAt(playerName, playerListBox.getSelectedIndex());
            }
        }
    }

    // Нажатие на кнопку "Удалить"
    private void deleteButtonActionPerformed(ActionEvent e) {
        if (playerListBox.getSelectedIndex() >= 0) {
            playerListModel.removeElementAt(playerListBox.getSelectedIndex());
        }
    }

    // Нажатие на кнопку "Играть"
    private void playButtonActionPerformed(ActionEvent e) {
        Object playerObj = playerListBox.getSelectedValue();
        if(playerObj != null) {
            if(!playerObj.toString().isEmpty()) {
                new GameForm(playerObj.toString());
            }
        }
    }
}
