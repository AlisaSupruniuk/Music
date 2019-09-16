package supruniuk.alisa;

import javax.sound.midi.*;  //импортируем пакет midi
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;


public class Music  extends JFrame
        implements KeyListener,
        ActionListener {
    private Play play;
    private JTextArea displayArea;
    private HashMap<Integer,Integer> notesMap = new HashMap<>();
    private HashSet<Integer> playingNotes = new HashSet<>();
    private HashMap<Integer, String> nameKey = new HashMap<>();

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Music::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        Music frame = null;
        try {
            frame = new Music("Музыкальное приложение");
        } catch (MidiUnavailableException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка", JOptionPane.ERROR_MESSAGE);

            System.exit(0);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponentsToPane();

        frame.pack();
        frame.setVisible(true);
    }

    private void addComponentsToPane() {
        JButton button = new JButton("Очистить");
        button.addActionListener(this);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.addKeyListener(this);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(375, 125));

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(button, BorderLayout.PAGE_END);
    }

    private Music(String name) throws MidiUnavailableException {
        super(name);
        play = new Play();

        notesMap.put(KeyEvent.VK_1, 60);
        notesMap.put(KeyEvent.VK_Q, 61);
        notesMap.put(KeyEvent.VK_2, 62);
        notesMap.put(KeyEvent.VK_W, 63);
        notesMap.put(KeyEvent.VK_3, 64);
        notesMap.put(KeyEvent.VK_4, 65);
        notesMap.put(KeyEvent.VK_R, 66);
        notesMap.put(KeyEvent.VK_5, 67);
        notesMap.put(KeyEvent.VK_T, 68);
        notesMap.put(KeyEvent.VK_6, 69);
        notesMap.put(KeyEvent.VK_Y, 70);
        notesMap.put(KeyEvent.VK_7, 71);
        notesMap.put(KeyEvent.VK_8, 72);

        nameKey.put(KeyEvent.VK_1, "до");
        nameKey.put(KeyEvent.VK_Q, "до#");
        nameKey.put(KeyEvent.VK_2, "ре");
        nameKey.put(KeyEvent.VK_W, "ре#");
        nameKey.put(KeyEvent.VK_3, "ми");
        nameKey.put(KeyEvent.VK_4, "фа");
        nameKey.put(KeyEvent.VK_R, "фа#");
        nameKey.put(KeyEvent.VK_5, "соль");
        nameKey.put(KeyEvent.VK_T, "соль#");
        nameKey.put(KeyEvent.VK_6, "ля");
        nameKey.put(KeyEvent.VK_Y, "ля#");
        nameKey.put(KeyEvent.VK_7, "си");
        nameKey.put(KeyEvent.VK_8, "до");


    }

    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    public void keyPressed(KeyEvent e) {
        handleKeyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        handleKeyReleased(e);
    }

    public void actionPerformed(ActionEvent e) {
        displayArea.setText("");
        displayArea.requestFocusInWindow();
    }

    // region Private Methods
    //
    private void handleKeyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (isValid(event) && !playingNotes.contains(keyCode)){
            displayArea.append(nameKey.get(keyCode) + "\n ");
            playingNotes.add(keyCode);
            play.notePressed(notesMap.get(keyCode));
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        if (isValid(event)) {
            playingNotes.remove(event.getKeyCode());
            play.noteReleased(notesMap.get(event.getKeyCode()));
        }
    }

    private boolean isValid(KeyEvent event) {
        boolean result = notesMap.containsKey(event.getKeyCode());
        return result;
    }
    // endregion
}