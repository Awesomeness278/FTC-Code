package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class telemetryUpdater {
    int count = 0;
    public telemetryUpdater(Autonomous opMode){
        this.opMode = opMode;
    }
    public void addTelemetry(){
        count++;
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
        opMode.telemetry.addData("Shooter Velocity",opMode.Shooter.getVelocity(AngleUnit.DEGREES));
        if(count%20==0) {
            opMode.velocities.add(opMode.Shooter.getVelocity());
        }
        opMode.telemetry.update();
    }
    Autonomous opMode;
}
