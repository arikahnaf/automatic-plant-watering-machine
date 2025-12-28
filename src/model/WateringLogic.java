package model;

public class WateringLogic {

    private boolean isPumpRunning = false;

    /*
     * Returns true if the pump should be ON, false if OFF.
     * Uses a buffer zone (hysteresis) to stop the pump from clicking on/off rapidly.
     */
    public boolean shouldPumpBeActive(long moistureLevel) {
        if (moistureLevel >= WateringConfig.THRESHOLD_DRY) {
            isPumpRunning = true; 
        } else if (moistureLevel <= WateringConfig.THRESHOLD_WET) {
            isPumpRunning = false; 
        }
        return isPumpRunning;
    }
    
    /*
     * Helper to turn raw sensor numbers (0-1023) into actual Voltage (0-5V).
     */
    public double convertToVoltage(long rawValue) {
        return (WateringConfig.MAX_VOLTAGE / WateringConfig.MAX_ANALOG_VALUE) * rawValue;
    }
}