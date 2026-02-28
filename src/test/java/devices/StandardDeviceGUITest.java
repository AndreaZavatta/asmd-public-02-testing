package devices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class StandardDeviceGUITest {

    private StandardDeviceGUI gui;

    @BeforeEach
    void setUp() {
        gui = new StandardDeviceGUI();
    }

    @Test
    void givenNewGUI_whenInspected_thenHasCorrectTitleAndComponents() {
        assertEquals("StandardDevice Controller (MVC)", gui.getTitle());
        assertEquals(JFrame.EXIT_ON_CLOSE, gui.getDefaultCloseOperation());
        Component[] components = gui.getContentPane().getComponents();
        assertEquals(4, components.length, "GUI should have exactly 4 main components");
        JLabel statusLabel = (JLabel) components[0];
        assertEquals("Device Status: OFF", statusLabel.getText());
        assertEquals(SwingConstants.CENTER, statusLabel.getHorizontalAlignment());
        JButton onButton = (JButton) components[1];
        assertEquals("Turn ON", onButton.getText());
        JButton offButton = (JButton) components[2];
        assertEquals("Turn OFF", offButton.getText());
        JButton resetButton = (JButton) components[3];
        assertEquals("Reset Device", resetButton.getText());
    }

    @Test
    void givenGUI_whenUpdateStatusLabelCalled_thenLabelUpdatesTextAndColor() {
        gui.updateStatusLabel("TESTING_STATE", Color.BLUE);
        JLabel statusLabel = (JLabel) gui.getContentPane().getComponent(0);
        assertEquals("Device Status: TESTING_STATE", statusLabel.getText());
        assertEquals(Color.BLUE, statusLabel.getForeground());
    }

    @Test
    void givenGUI_whenOnButtonClicked_thenRegisteredListenerIsFired() {
        JButton onButton = (JButton) gui.getContentPane().getComponent(1);
        AtomicBoolean clicked = new AtomicBoolean(false);
        gui.addOnButtonListener(e -> clicked.set(true));
        onButton.doClick();
        assertTrue(clicked.get(), "The ON button listener should have been triggered");
    }

    @Test
    void givenGUI_whenOffButtonClicked_thenRegisteredListenerIsFired() {
        JButton offButton = (JButton) gui.getContentPane().getComponent(2);
        AtomicBoolean clicked = new AtomicBoolean(false);
        gui.addOffButtonListener(e -> clicked.set(true));
        offButton.doClick();
        assertTrue(clicked.get(), "The OFF button listener should have been triggered");
    }

    @Test
    void givenGUI_whenResetButtonClicked_thenRegisteredListenerIsFired() {
        JButton resetButton = (JButton) gui.getContentPane().getComponent(3);
        AtomicBoolean clicked = new AtomicBoolean(false);
        gui.addResetButtonListener(e -> clicked.set(true));
        resetButton.doClick();
        assertTrue(clicked.get(), "The RESET button listener should have been triggered");
    }
}
