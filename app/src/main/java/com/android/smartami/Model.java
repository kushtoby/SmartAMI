package com.android.smartami;
public class Model {

    private String voltageInput,current, power, energyConsumed, energyAvailable;

    private Model (){

    }

    public Model(String voltageInput, String current, String power, String energyConsumed, String energyAvailable) {
        this.voltageInput = voltageInput;
        this.current = current;
        this.power = power;
        this.energyConsumed = energyConsumed;
        this.energyAvailable = energyAvailable;

    }

    public String getVoltageInput() {
        voltageInput = "Voltage Input: " + voltageInput;
        return voltageInput;
    }

    public void setVoltageInput(String voltageInput) {

        this.voltageInput = voltageInput;
    }

    public String getCurrent() {
            current = "Current: " + current;
        return current;
    }

    public void setCurrent(String current) {

        this.current = current;
    }

    public String getPower() {
        power = "Power: " +power;
        return power;
    }

    public void setPower(String power) {

        this.power = power;
    }
    public String getEnergyConsumed() {
        energyConsumed = "Energy Consumed: " + energyConsumed;
        return energyConsumed;
    }

    public void setEnergyConsumed(String energyConsumed) {

        this.energyConsumed = energyConsumed;
    }
    public String getEnergyAvailable() {
        energyAvailable = "Energy Available: " + energyAvailable;
        return energyAvailable;
    }

    public void setEnergyAvailable(String energyAvailable) {

        this.energyAvailable = energyAvailable;
    }
}
