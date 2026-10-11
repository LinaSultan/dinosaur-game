
package prakhar17.developer.dinosaurgame;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import prakhar17.developer.dinosaurgame.components.Dinosaur;
import prakhar17.developer.dinosaurgame.utils.GameConstants;

public class GameFrame extends JFrame implements GameConstants {

    public GameFrame() {
        setSize(GW, GH);
        setTitle(TITLE);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        showCharacterSelection();

        setVisible(true);
    }

    private void showCharacterSelection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Dinosaur Game");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel message = new JLabel("Choose Your Character");
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton boyButton = new JButton("Boy");
        JButton girlButton = new JButton("Girl");

        Dimension buttonSize = new Dimension(180, 50);

        boyButton.setPreferredSize(buttonSize);
        boyButton.setMaximumSize(buttonSize);
        boyButton.setFont(new Font("Arial", Font.BOLD, 18));
        boyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        girlButton.setPreferredSize(buttonSize);
        girlButton.setMaximumSize(buttonSize);
        girlButton.setFont(new Font("Arial", Font.BOLD, 18));
        girlButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        boyButton.addActionListener(e -> startGame(false));
        girlButton.addActionListener(e -> startGame(true));

        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(message);
        panel.add(Box.createVerticalStrut(25));
        panel.add(boyButton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(girlButton);
        panel.add(Box.createVerticalGlue());

        setContentPane(panel);
    }

    private void startGame(boolean girl) {
        try {
            Dinosaur prototype = new Dinosaur();

            Dinosaur selectedDinosaur = girl
                    ? prototype.asGirl()
                    : prototype.clone();

            Board board = new Board(selectedDinosaur);

            setContentPane(board);
            revalidate();
            board.requestFocusInWindow();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
    }
}
