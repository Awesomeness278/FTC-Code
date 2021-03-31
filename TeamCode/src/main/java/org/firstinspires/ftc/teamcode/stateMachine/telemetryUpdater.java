package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;

public class telemetryUpdater {
    public telemetryUpdater(Autonomous opMode){
        this.opMode = opMode;
    }
    public void addTelemetry(){
        opMode.telemetry.addData("X position",opMode.odometry.returnXCoordinate()/opMode.COUNTS_PER_INCH);
        opMode.telemetry.addData("Y position",opMode.odometry.returnYCoordinate()/opMode.COUNTS_PER_INCH);
        opMode.telemetry.addData("Orientation",opMode.odometry.returnOrientation());
        if(opMode.recognition!=null){
            opMode.telemetry.addData("Ring Stack",opMode.recognition.getLabel());
        }else{
            opMode.telemetry.addData("Ring Stack","None");
        }
        opMode.telemetry.addData("Runtime",opMode.getRuntime());
        opMode.telemetry.addData("PIDF Coefficients",opMode.Shooter.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));
        opMode.telemetry.update();
    }
    Autonomous opMode;
}
