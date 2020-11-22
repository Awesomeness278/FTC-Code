package org.firstinspires.ftc.teamcode.stateMachine;


import com.qualcomm.hardware.lynx.LynxEmbeddedIMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Odometry.OdometryGlobalCoordinatePosition;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class Autonomous extends LinearOpMode {
    StateManager manager = new StateManager();
    DcMotor[] motors;
    ColorRangeSensor[] colors;
    Servo[] servos;
    OdometryGlobalCoordinatePosition globalPositionUpdate;
    DcMotor verticalLeft;
    DcMotor verticalRight;
    DcMotor horizontal;
    double COUNTS_PER_INCH = 307.699557;

    @Override
    public void runOpMode() throws InterruptedException {
        globalPositionUpdate = new OdometryGlobalCoordinatePosition(verticalLeft, verticalRight, horizontal, COUNTS_PER_INCH, 75);
        Thread positionThread = new Thread(globalPositionUpdate);
        globalPositionUpdate.reverseRightEncoder();
        globalPositionUpdate.reverseNormalEncoder();
        positionThread.start();
        /*
        Copy the following line and change the new State to new {name of your state}
        manager.addState(new Start());
        manager.addState(new Tensorflow());
        manager.addState(new Move(squareX,squareY));
        manager.addState(new Stop());
        manager.addState(new WobbleDropped());
        manager.addState(new Move(shootX,shootY));
        manager.addState(new Stop());
        manager.addState(new Oriented());
        manager.addState(new Shoot());
        manager.addState(new CheckRings());
        manager.addState(new Move(ringX,ringY));
        manager.addState(new Stop());
        manager.addState(new Tensorflow());
        manager.addState(new RingsCollected());
        manager.addState(new Move());
        manager.addState(new Stop());
        manager.addState(new Finish());
        */
        manager.addState(new Start());
        manager.addState(new Move(12,72,1));
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
