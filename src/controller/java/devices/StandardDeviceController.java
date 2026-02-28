package devices;

import java.awt.Color;
import java.awt.event.ActionEvent;

public class StandardDeviceController {

    private final Device model;
    private final DeviceView view;

    public StandardDeviceController(Device model, DeviceView view) {
        this.model = model;
        this.view = view;
        
        // Attach listeners to the view
        this.view.addOnButtonListener(this::handleTurnOn);
        this.view.addOffButtonListener(this::handleTurnOff);
        this.view.addResetButtonListener(this::handleReset);
        
        updateViewFromModel();
    }

    private void handleTurnOn(ActionEvent e) {
        try {
            model.on();
            view.updateStatusLabel("ON", Color.GREEN);
        } catch (IllegalStateException ex) {
            view.updateStatusLabel("FAILED (Exception)", Color.RED);
        }
    }

    private void handleTurnOff(ActionEvent e) {
        model.off();
        view.updateStatusLabel("OFF", Color.BLACK);
    }

    private void handleReset(ActionEvent e) {
        model.reset();
        view.updateStatusLabel("OFF (Reset)", Color.BLACK);
    }

    private void updateViewFromModel() {
        if (model.isOn()) {
            view.updateStatusLabel("ON", Color.GREEN);
        } else {
            view.updateStatusLabel("OFF", Color.BLACK);
        }
    }
}
