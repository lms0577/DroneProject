package kosa.team3.drone.device.actuator;

import com.pi4j.io.gpio.*;

public class ElectroMagnet {
    private final static String ATTACH = "attach";
    private final static String DETACH = "detach";

    private GpioPinDigitalOutput lagerPin; 
    
    private String status;
    
    public ElectroMagnet(Pin laserPinNo){
        GpioController gpioController = GpioFactory.getInstance();
        lagerPin = gpioController.provisionDigitalOutputPin(laserPinNo, PinState.LOW);
        lagerPin.setShutdownOptions(true, PinState.LOW);
    }
    
    public void attach(){
        lagerPin.high();
        this.status = ATTACH;
    }
    
    public void detach(){
        lagerPin.low();
        this.status = DETACH;
    }
    
    public String getStatus(){
        return status;
    }
    
    public static void main(String[] args) throws InterruptedException{
        ElectroMagnet laserEmitter1 = new ElectroMagnet(RaspiPin.GPIO_24);
        ElectroMagnet laserEmitter2 = new ElectroMagnet(RaspiPin.GPIO_25);
        laserEmitter1.attach();
        laserEmitter2.attach();
        Thread.sleep(20000);
        laserEmitter1.detach();
        laserEmitter2.detach();
    }
}
