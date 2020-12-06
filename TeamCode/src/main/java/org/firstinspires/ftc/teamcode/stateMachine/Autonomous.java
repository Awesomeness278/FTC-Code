package org.firstinspires.ftc.teamcode.stateMachine;


import com.qualcomm.hardware.lynx.LynxEmbeddedIMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
//        manager.addState(new Start());
////        manager.addState(new Move());
////        waitForStart();
////        manager.states.get(0).Run(manager,this);
////        while(opModeIsActive()){
////            telemetry.update();
    //    }
    }
    /*
    * Any methods that you use more than once can be defined here.
    * */
}
