package devices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StandardDeviceGUITest {

    private StandardDeviceGUI gui;

    // Helper method to find a component by name recursively
    private Component findComponentByName(Container container, String name) {
        for (Component component : container.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            }
            if (component instanceof Container) {
                Component found = findComponentByName((Container) component, name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    @BeforeEach
    void setUp() {
        gui = new StandardDeviceGUI();
    }

    @Test
    void givenNewGUI_whenInspected_thenHasCorrectTitleAndComponents() {
        assertAll("GUI Frame Properties",
            () -> assertEquals("StandardDevice Controller (MVC)", gui.getTitle()),
            () -> assertEquals(JFrame.EXIT_ON_CLOSE, gui.getDefaultCloseOperation())
        );

        Component[] components = gui.getContentPane().getComponents();
        assertEquals(4, components.length, "GUI should have exactly 4 main components");

        JLabel statusLabel = (JLabel) findComponentByName(gui, "statusLabel");
        JButton onButton = (JButton) findComponentByName(gui, "onButton");
        JButton offButton = (JButton) findComponentByName(gui, "offButton");
        JButton resetButton = (JButton) findComponentByName(gui, "resetButton");

        assertAll("GUI Component Presence and Text",
            () -> assertNotNull(statusLabel, "statusLabel should exist"),
            () -> assertEquals("Device Status: OFF", statusLabel.getText()),
            () -> assertEquals(SwingConstants.CENTER, statusLabel.getHorizontalAlignment()),

            () -> assertNotNull(onButton, "onButton should exist"),
            () -> assertEquals("Turn ON", onButton.getText()),

            () -> assertNotNull(offButton, "offButton should exist"),
            () -> assertEquals("Turn OFF", offButton.getText()),

            () -> assertNotNull(resetButton, "resetButton should exist"),
            () -> assertEquals("Reset Device", resetButton.getText())
        );
    }

    @Test
    void givenGUI_whenUpdateStatusLabelCalled_thenLabelUpdatesTextAndColor() {
        gui.updateStatusLabel("TESTING_STATE", Color.BLUE);
        JLabel statusLabel = (JLabel) findComponentByName(gui, "statusLabel");
        
        assertAll("Status Label Updates",
            () -> assertNotNull(statusLabel),
            () -> assertEquals("Device Status: TESTING_STATE", statusLabel.getText()),
            () -> assertEquals(Color.BLUE, statusLabel.getForeground())
        );
    }

    @Test
    void givenGUI_whenOnButtonClicked_thenRegisteredListenerIsFired() {
        JButton onButton = (JButton) findComponentByName(gui, "onButton");
        assertNotNull(onButton);
        
        ActionListener mockListener = mock(ActionListener.class);
        gui.addOnButtonListener(mockListener);
        onButton.doClick();
        
        verify(mockListener, times(1)).actionPerformed(any(ActionEvent.class));
    }

    @Test
    void givenGUI_whenOffButtonClicked_thenRegisteredListenerIsFired() {
        JButton offButton = (JButton) findComponentByName(gui, "offButton");
        assertNotNull(offButton);
        
        ActionListener mockListener = mock(ActionListener.class);
        gui.addOffButtonListener(mockListener);
        offButton.doClick();
        
        verify(mockListener, times(1)).actionPerformed(any(ActionEvent.class));
    }

    @Test
    void givenGUI_whenResetButtonClicked_thenRegisteredListenerIsFired() {
        JButton resetButton = (JButton) findComponentByName(gui, "resetButton");
        assertNotNull(resetButton);
        
        ActionListener mockListener = mock(ActionListener.class);
        gui.addResetButtonListener(mockListener);
        resetButton.doClick();
        
        verify(mockListener, times(1)).actionPerformed(any(ActionEvent.class));
    }
}
