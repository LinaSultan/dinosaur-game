
package prakhar17.developer.dinosaurgame;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javax.imageio.ImageIO;
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

    private static final Font PIXEL = loadPixelFont();

private static Font loadPixelFont() {
    try (InputStream in = GameFrame.class.getResourceAsStream("/resource/PressStart2P-Regular.ttf")) {
        if (in != null) {
            return Font.createFont(Font.TRUETYPE_FONT, in);
        }
    } catch (Exception ignored) {
    }
    return new Font("Monospaced", Font.BOLD, 12);
}

private static class MenuPanel extends JPanel {
    private final BufferedImage cloud, cactusSmall, cactusBig, ground;

    MenuPanel() throws IOException {
        BufferedImage sprite = ImageIO.read(Objects.requireNonNull(
                GameFrame.class.getResource("/resource/sprite-dino.png")));
        ground = ImageIO.read(Objects.requireNonNull(
                GameFrame.class.getResource("/resource/ground-dino.png")));
        cloud = sprite.getSubimage(164, 0, 96, 31);
        cactusSmall = sprite.getSubimage(445, 1, 35, 72);
        cactusBig = sprite.getSubimage(651, 1, 51, 98);
        setBackground(new Color(247, 247, 247));
    }

    @Override
    protected void paintComponent(Graphics pen) {
        super.paintComponent(pen);
        Graphics2D g = (Graphics2D) pen;
        int w = getWidth();
        int h = getHeight();
        int groundY = h - 70;

     
        g.drawImage(cloud, 40, 40, 192, 62, null);
        g.drawImage(cloud, w - 232, 90, 192, 62, null);

      
        int srcW = Math.min(ground.getWidth(), w);
        g.drawImage(ground, 0, groundY, w, groundY + 48, 0, 0, srcW, 24, null);

        
        g.drawImage(cactusBig, 60, groundY + 10 - 98, null);
        g.drawImage(cactusSmall, 125, groundY + 10 - 72, null);
        g.drawImage(cactusSmall, w - 160, groundY + 10 - 72, null);
        g.drawImage(cactusBig, w - 115, groundY + 10 - 98, null);
    }
}

    private void showCharacterSelection() {
    try {
        Dinosaur boy = new Dinosaur();
        Dinosaur girl = new Dinosaur().asGirl();

        JPanel panel = new MenuPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("DINOSAUR GAME");
title.setFont(PIXEL.deriveFont(30f));
title.setForeground(new Color(83, 83, 83));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel message = new JLabel("CHOOSE YOUR CHARACTER");
message.setFont(PIXEL.deriveFont(12f));
message.setForeground(new Color(83, 83, 83));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        row.add(new CharacterCard(boy, "Boy", () -> startGame(false)));
        row.add(new CharacterCard(girl, "Girl", () -> startGame(true)));

        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createVerticalStrut(15));
        panel.add(message);
        panel.add(Box.createVerticalStrut(30));
        panel.add(row);
        panel.add(Box.createVerticalGlue());

        setContentPane(panel);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
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


    private static class CharacterCard extends JPanel {
    private final Dinosaur dino;
    private final String name;
    private boolean hover = false;

    CharacterCard(Dinosaur dino, String name, Runnable onSelect) {
        this.dino = dino;
        this.name = name;
        setPreferredSize(new Dimension(220, 280));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                onSelect.run();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics pen) {
        super.paintComponent(pen);
        Graphics2D g = (Graphics2D) pen;

        g.setColor(hover ? new Color(235, 235, 235) : Color.WHITE);
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

        g.setColor(hover ? new Color(83, 83, 83) : new Color(190, 190, 190));
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 20, 20);

        dino.drawPreview(g, (getWidth() - 178) / 2, 40, 2);

        g.setColor(new Color(83, 83, 83));
        g.setFont(PIXEL.deriveFont(18f));
        int textWidth = g.getFontMetrics().stringWidth(name);
        g.drawString(name, (getWidth() - textWidth) / 2, 262);
    }
}

    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
    }
}
