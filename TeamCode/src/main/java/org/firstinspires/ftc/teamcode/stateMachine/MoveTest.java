package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.teamcode.Odometry.Odometry;

public class MoveTest extends StateManager {
    double tx;
    double ty;
    double COUNTS_PER_INCH = 307.699557;
    States exit;
    double sp = 0.6;
    public MoveTest(double tx, double ty, States exit){
        this.tx = -tx;
        this.ty = -ty;
        this.exit = exit;
        if(exit==States.Stop){
            this.tx = Double.NaN;
        }
    }
    double startX;
    double startY;
    @Override
    public void Run(StateMachine machine) {
        if(Double.isNaN(this.tx)){
            this.tx = machine.opMode.odometry.returnXCoordinate()/COUNTS_PER_INCH;
        }
        if(machine.opMode.recognition!=null) {
            if (machine.opMode.recognition.getLabel().equals("Quad")) {
                if (machine.opMode.position <= 2) {
                    this.tx += 3;
                } else {
                    this.ty -= 3;
                }
            }
        }
        startX = machine.opMode.odometry.returnXCoordinate()/COUNTS_PER_INCH;
        startY = machine.opMode.odometry.returnYCoordinate()/COUNTS_PER_INCH;
        Autonomous opMode = machine.opMode;
        DcMotor left_back = opMode.left_back;
        DcMotor left_front = opMode.left_front;
        DcMotor right_back = opMode.right_back;
        DcMotor right_front = opMode.right_front;

        double leftFront = 0;
        double leftBack = 0;
        double rightFront = 0;
        double rightBack = 0;

        while(ExitCondition(machine)&&opMode.opModeIsActive()) {
            double x = opMode.odometry.returnXCoordinate() / COUNTS_PER_INCH - tx;
            double y = opMode.odometry.returnYCoordinate() / COUNTS_PER_INCH - ty;
            opMode.telemetry.addData("Orientation",opMode.odometry.returnOrientation());
            opMode.telemetry.addData("Runtime",opMode.getRuntime());
            opMode.telemetry.addData("X",opMode.odometry.returnXCoordinate()/COUNTS_PER_INCH);
            opMode.telemetry.addData("Y",opMode.odometry.returnYCoordinate()/COUNTS_PER_INCH);
            opMode.telemetry.update();
            double radius = 1 / Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            x = x * -radius;
            y = y * -radius;
            leftFront += (y + x)-opMode.odometry.returnOrientation()/20;
            leftBack += (y - x)-opMode.odometry.returnOrientation()/20;
            rightFront += (y - x)+opMode.odometry.returnOrientation()/20;
            rightBack += (y + x)+opMode.odometry.returnOrientation()/20;
            double scalar = Math.max(Math.max(Math.abs(leftFront), Math.abs(leftBack)), Math.max(Math.abs(rightFront), Math.abs(rightBack)));
            if (scalar < 1) scalar = 1;
            left_front.setPower(leftFront / scalar * slowFactor(machine,0.25));
            left_back.setPower(leftBack / scalar * slowFactor(machine,0.25));
            right_front.setPower(rightFront / scalar * slowFactor(machine,0.25));
            right_back.setPower(rightBack / scalar * slowFactor(machine,0.25));
        }
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setPower(0);
        left_front.setPower(0);
        right_back.setPower(0);
        right_front.setPower(0);
        if(!opMode.opModeIsActive()){
            opMode.stop();
        }else {
            Exit(machine);
        }
    }

    public double slowFactor(StateMachine machine, double minSpeed){
        double maxSpeed = sp;
        Autonomous opMode = machine.opMode;
        double speed = sp;
        double distance = machine.opMode.dist(opMode.odometry.returnXCoordinate()/opMode.COUNTS_PER_INCH,opMode.odometry.returnYCoordinate()/opMode.COUNTS_PER_INCH,tx,ty);
        if(distance<12){
            double percentage = 1-distance/12;
            speed = maxSpeed-minSpeed;
            speed*=percentage;
            speed+=minSpeed;
        }
        return speed;
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        Autonomous opMode = machine.opMode;
        return opMode.dist(startX,startY,opMode.odometry.returnXCoordinate()/COUNTS_PER_INCH,opMode.odometry.returnYCoordinate()/COUNTS_PER_INCH)<=opMode.dist(startX,startY,tx,ty);
    }

    @Override
    public void Exit(StateMachine machine) {
        if(exit.stateNum==States.Stop.stateNum) {
            machine.opMode.stop();
        }else{
            machine.runState(exit);
        }
    }
}