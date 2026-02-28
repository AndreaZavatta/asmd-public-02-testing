package devices;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StandardDeviceGUI extends JFrame implements DeviceView {

    private final JLabel statusLabel;
    private final JButton onButton;
    private final JButton offButton;
    private final JButton resetButton;

    public StandardDeviceGUI() {
        super("StandardDevice Controller (MVC)");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setLayout(new GridLayout(4, 1, 10, 10));

        this.statusLabel = new JLabel("Device Status: OFF", SwingConstants.CENTER);
        this.statusLabel.setName("statusLabel");
        this.statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(this.statusLabel);

        this.onButton = new JButton("Turn ON");
        this.onButton.setName("onButton");
        this.add(this.onButton);

        this.offButton = new JButton("Turn OFF");
        this.offButton.setName("offButton");
        this.add(this.offButton);

        // RESET Button
        this.resetButton = new JButton("Reset Device");
        this.resetButton.setName("resetButton");
        this.add(this.resetButton);

        // Center on screen
        this.setLocationRelativeTo(null);
    }

    public void updateStatusLabel(String status, Color color) {
        statusLabel.setText("Device Status: " + status);
        statusLabel.setForeground(color);
    }

    // Methods for the Controller to attach listeners
    public void addOnButtonListener(ActionListener listener) {
        onButton.addActionListener(listener);
    }

    public void addOffButtonListener(ActionListener listener) {
        offButton.addActionListener(listener);
    }

    public void addResetButtonListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Device model = new StandardDevice(new FailingPolicyImpl());
            StandardDeviceGUI view = new StandardDeviceGUI();
            StandardDeviceController controller = new StandardDeviceController(model, view);
            view.setVisible(true);
        });
    }
}
