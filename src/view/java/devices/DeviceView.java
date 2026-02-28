package devices;

import java.awt.Color;
import java.awt.event.ActionListener;

public interface DeviceView {
    void updateStatusLabel(String status, Color color);
    void addOnButtonListener(ActionListener listener);
    void addOffButtonListener(ActionListener listener);
    void addResetButtonListener(ActionListener listener);
    void setVisible(boolean visible);
}
