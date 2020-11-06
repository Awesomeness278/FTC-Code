package org.firstinspires.ftc.teamcode.stateMachine;


import com.qualcomm.hardware.lynx.LynxEmbeddedIMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class Autonomous extends LinearOpMode {
    StateManager manager = new StateManager();
    DcMotor[] motors;
    ColorRangeSensor[] colors;
    Servo[] servos;
    LynxEmbeddedIMU imu;
    @Override
    public void runOpMode() throws InterruptedException {
        /*
        Copy the following line and change the new State to new {name of your state}
        manager.addState(new State());
        */
        manager.addState(new Start());
        manager.addState(new Move());
        waitForStart();
        manager.states.get(0).Run(manager,this);
        while(opModeIsActive()){
            telemetry.update();
        }
    }
    /*
    * Any methods that you use more than once can be defined here.
    * */
}
