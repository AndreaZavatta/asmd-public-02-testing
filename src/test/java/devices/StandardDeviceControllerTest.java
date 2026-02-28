package devices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class StandardDeviceControllerTest {

    private Device mockModel;
    private DeviceView mockView;
    private StandardDeviceController controller;
    @Captor private ArgumentCaptor<ActionListener> actionListenerCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
        verify(mockModel, atLeastOnce()).isOn();
        verifyNoMoreInteractions(mockModel);
    }

    @Test
    void givenDeviceIsOff_whenTurnOnClicked_thenModelTurnsOnAndViewDisplaysOn() {
        when(mockModel.isOn()).thenReturn(false);
        controller = new StandardDeviceController(mockModel, mockView);
        verify(mockView).addOnButtonListener(actionListenerCaptor.capture());
        ActionListener onListener = actionListenerCaptor.getValue();

        onListener.actionPerformed(mock(ActionEvent.class));
        verify(mockModel).on();
        verify(mockView).updateStatusLabel("ON", Color.GREEN);
    }

    @Test
    void givenDeviceIsOff_whenTurnOnThrowsException_thenViewDisplaysError() {
        when(mockModel.isOn()).thenReturn(false);
        controller = new StandardDeviceController(mockModel, mockView);
        doThrow(new IllegalStateException()).when(mockModel).on();
        verify(mockView).addOnButtonListener(actionListenerCaptor.capture());
        ActionListener onListener = actionListenerCaptor.getValue();
        onListener.actionPerformed(mock(ActionEvent.class));
        verify(mockModel).on();
        verify(mockView).updateStatusLabel("FAILED (Exception)", Color.RED);
        verify(mockModel, atLeastOnce()).isOn();
        verifyNoMoreInteractions(mockModel);
    }

    @Test
    void givenDeviceIsOn_whenTurnOffClicked_thenModelTurnsOffAndViewDisplaysOff() {
        when(mockModel.isOn()).thenReturn(true);
        controller = new StandardDeviceController(mockModel, mockView);
        verify(mockView).addOffButtonListener(actionListenerCaptor.capture());
        ActionListener offListener = actionListenerCaptor.getValue();
        offListener.actionPerformed(mock(ActionEvent.class));
        verify(mockModel).off();
        verify(mockView, times(1)).updateStatusLabel("OFF", Color.BLACK); 
        verify(mockView).updateStatusLabel("OFF", Color.BLACK);
        verify(mockModel, atLeastOnce()).isOn();
        verifyNoMoreInteractions(mockModel);
    }

    @Test
    void givenDeviceIsOn_whenResetClicked_thenModelResetsAndViewDisplaysOffReset() {
        when(mockModel.isOn()).thenReturn(true);
        controller = new StandardDeviceController(mockModel, mockView);
        verify(mockView).addResetButtonListener(actionListenerCaptor.capture());
        ActionListener resetListener = actionListenerCaptor.getValue();
        resetListener.actionPerformed(mock(ActionEvent.class));
        verify(mockModel).reset();
        verify(mockView).updateStatusLabel("OFF (Reset)", Color.BLACK);
        verify(mockModel, atLeastOnce()).isOn();
        verifyNoMoreInteractions(mockModel);
    }
}
