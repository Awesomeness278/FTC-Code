package org.firstinspires.ftc.teamcode.stateMachine;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Odometry.OdometryGlobalCoordinatePosition;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class Autonomous extends LinearOpMode {
    StateMachine machine = new StateMachine();
    DcMotor LeftFrontMotor;
    DcMotor RightFrontMotor;
    DcMotor LeftBackMotor;
    DcMotor RightBackMotor;
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
        machine.addState(new Start());
        machine.addState(new Tensorflow());
        machine.addState(new Move(squareX,squareY));
        machine.addState(new Stop());
        machine.addState(new WobbleDropped());
        machine.addState(new Move(shootX,shootY));
        machine.addState(new Stop());
        machine.addState(new Oriented());
        machine.addState(new Shoot());
        machine.addState(new CheckRings());
        machine.addState(new Move(ringX,ringY));
        machine.addState(new Stop());
        machine.addState(new Tensorflow());
        machine.addState(new RingsCollected());
        machine.addState(new Move(lineX,lineY));
        machine.addState(new Stop());
        machine.addState(new Finish());
        */
        machine.addState(new Start());
        machine.addState(new Move(12,72,1));
        waitForStart();
        machine.states.get(0).Run(machine,this);
    }
    /*
    * Any methods that you use more than once can be defined here.
    * */
}
