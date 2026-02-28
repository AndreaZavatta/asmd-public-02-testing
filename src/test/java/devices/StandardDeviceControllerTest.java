package devices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class StandardDeviceControllerTest {

    private Device mockModel;
    private DeviceView mockView;
    private StandardDeviceController controller;

    @BeforeEach
    void setUp() {
        mockModel = mock(Device.class);
        mockView = mock(DeviceView.class);
    }

    @Test
    void givenDeviceIsOff_whenControllerInitialized_thenViewDisplaysOff() {
        when(mockModel.isOn()).thenReturn(false);
        controller = new StandardDeviceController(mockModel, mockView);
        verify(mockView).updateStatusLabel("OFF", Color.BLACK);
    }

    @Test
    void givenDeviceIsOn_whenControllerInitialized_thenViewDisplaysOn() {
        when(mockModel.isOn()).thenReturn(true);
        controller = new StandardDeviceController(mockModel, mockView);
        verify(mockView).updateStatusLabel("ON", Color.GREEN);
    }

    @Test
    void givenDeviceIsOff_whenTurnOnClicked_thenModelTurnsOnAndViewDisplaysOn() {
        when(mockModel.isOn()).thenReturn(false);
        controller = new StandardDeviceController(mockModel, mockView);
        ArgumentCaptor<ActionListener> captor = ArgumentCaptor.forClass(ActionListener.class);
        verify(mockView).addOnButtonListener(captor.capture());
        ActionListener onListener = captor.getValue();

        onListener.actionPerformed(mock(ActionEvent.class));
        verify(mockModel).on();
        verify(mockView).updateStatusLabel("ON", Color.GREEN);
    }

    @Test
    void givenDeviceIsOff_whenTurnOnThrowsException_thenViewDisplaysError() {
        when(mockModel.isOn()).thenReturn(false);
        controller = new StandardDeviceController(mockModel, mockView);
        doThrow(new IllegalStateException()).when(mockModel).on();
        ArgumentCaptor<ActionListener> captor = ArgumentCaptor.forClass(ActionListener.class);
        verify(mockView).addOnButtonListener(captor.capture());
        ActionListener onListener = captor.getValue();
        onListener.actionPerformed(mock(ActionEvent.class));
        verify(mockModel).on();
        verify(mockView).updateStatusLabel("FAILED (Exception)", Color.RED);
    }

    @Test
    void givenDeviceIsOn_whenTurnOffClicked_thenModelTurnsOffAndViewDisplaysOff() {
        when(mockModel.isOn()).thenReturn(true);
        controller = new StandardDeviceController(mockModel, mockView);
        ArgumentCaptor<ActionListener> captor = ArgumentCaptor.forClass(ActionListener.class);
        verify(mockView).addOffButtonListener(captor.capture());
        ActionListener offListener = captor.getValue();
        offListener.actionPerformed(mock(ActionEvent.class));
        verify(mockModel).off();
        verify(mockView, times(1)).updateStatusLabel("OFF", Color.BLACK); 
        verify(mockView).updateStatusLabel("OFF", Color.BLACK);
    }

    @Test
    void givenDeviceIsOn_whenResetClicked_thenModelResetsAndViewDisplaysOffReset() {
        when(mockModel.isOn()).thenReturn(true);
        controller = new StandardDeviceController(mockModel, mockView);
        ArgumentCaptor<ActionListener> captor = ArgumentCaptor.forClass(ActionListener.class);
        verify(mockView).addResetButtonListener(captor.capture());
        ActionListener resetListener = captor.getValue();
        resetListener.actionPerformed(mock(ActionEvent.class));
        verify(mockModel).reset();
        verify(mockView).updateStatusLabel("OFF (Reset)", Color.BLACK);
    }
}
