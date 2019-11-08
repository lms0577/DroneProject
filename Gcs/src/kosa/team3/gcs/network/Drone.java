package kosa.team3.gcs.network;

import kosa.team3.gcs.device.Electromagnet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import syk.gcs.network.Camera;
import syk.gcs.network.FlightController;

public class Drone {
    //------------------------------------------------------------------------------
    private static Logger logger = LoggerFactory.getLogger(Drone.class);
    //------------------------------------------------------------------------------
    public FlightController flightController;
    public Camera camera0;
    public Camera camera1;
    public Electromagnet electromagnet;
    //------------------------------------------------------------------------------
    public Drone() {
        flightController = new FlightController();
        camera0 = new Camera();
        camera1 = new Camera();
        electromagnet = new Electromagnet();
    }
    //------------------------------------------------------------------------------
    public void connect() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                NetworkConfig networkConfig = NetworkConfig.getInstance();

                flightController.mqttConnect(
                        networkConfig.mqttBrokerConnStr,
                        networkConfig.droneTopic + "/fc/pub",
                        networkConfig.droneTopic + "/fc/sub"
                );

                camera0.mqttConnect(
                        networkConfig.mqttBrokerConnStr,
                        networkConfig.droneTopic + "/cam0/pub",
                        networkConfig.droneTopic + "/cam0/sub"
                );

                camera1.mqttConnect(
                        networkConfig.mqttBrokerConnStr,
                        networkConfig.droneTopic + "/cam1/pub",
                        networkConfig.droneTopic + "/cam1/sub"
                );

                electromagnet.mqttConnect(
                        networkConfig.mqttBrokerConnStr,
                        networkConfig.droneTopic + "/electromagnet/pub",
                        networkConfig.droneTopic + "/electromagnet/sub"
                );
            }
        };
        thread.start();
    }

    public void disconnect() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                flightController.mqttDisconnect();
                camera0.mqttDisconnect();
                camera1.mqttDisconnect();
                electromagnet.mqttDisconnect();
            }
        };
        thread.start();
    }
}
