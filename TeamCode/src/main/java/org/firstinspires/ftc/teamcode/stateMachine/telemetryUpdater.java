package org.firstinspires.ftc.teamcode.stateMachine;

public class telemetryUpdater {
    public telemetryUpdater(Autonomous opMode){
        this.opMode = opMode;
    }
    public void addTelemetry(){
        opMode.telemetry.addData("X position",opMode.odometry.returnXCoordinate());
        opMode.telemetry.addData("Y position",opMode.odometry.returnYCoordinate());
        opMode.telemetry.addData("Orientation",opMode.odometry.returnOrientation());
        if(opMode.recognition!=null){
            opMode.telemetry.addData("Ring Stack",opMode.recognition.getLabel());
        }else{
            opMode.telemetry.addData("Ring Stack","None");
        }
        opMode.telemetry.update();
    }
    Autonomous opMode;
}
