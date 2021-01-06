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
    DcMotor Shooter;
    DcMotor Conveyor;
    DcMotor Intake;
    DcMotor Claw;
    ColorRangeSensor[] colors;
    Servo[] servos;
    OdometryGlobalCoordinatePosition odometry;
    DcMotor verticalLeft;
    DcMotor verticalRight;
    DcMotor horizontal;
    double COUNTS_PER_INCH = 307.699557;

    @Override
    public void runOpMode() throws InterruptedException {
        odometry = new OdometryGlobalCoordinatePosition(verticalLeft, verticalRight, horizontal, COUNTS_PER_INCH, 75);
        Thread positionThread = new Thread(odometry);
        odometry.reverseRightEncoder();
        odometry.reverseNormalEncoder();
        positionThread.start();
        machine.addState(States.Start,new Start());
        machine.addState(States.MoveToWobble,new Move(12,72,States.StopMovingToWobble.stateNum));
        waitForStart();
        machine.runState(States.Start);
    }
    /*
    * Any methods that you use more than once can be defined here.
    * */
}
