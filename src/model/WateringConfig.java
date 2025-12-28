package model;

public class WateringConfig {
    // Pin Mappings
    public static final String PORT_NAME = "COM3"; 
    public static final int MOISTURE_SENSOR_PIN = 15; 
    public static final int PUMP_PIN = 2; 
    public static final byte I2C_ADDRESS = 0x3c;

    // Logic Thresholds
    public static final int THRESHOLD_DRY = 700; 
    public static final int THRESHOLD_WET = 550; 
    
    // Graph Settings
    public static final double MAX_VOLTAGE = 5.0;
    public static final int MAX_ANALOG_VALUE = 1023;
}