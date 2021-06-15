package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

public class Drivetrain {
    DcMotor fl,fr,bl,br;
    public Drivetrain(String flName, String frName, String blName, String brName){
        this.fl = hardwareMap.get(DcMotor.class,flName);
        this.fr = hardwareMap.get(DcMotor.class,frName);
        this.bl = hardwareMap.get(DcMotor.class,blName);
        this.br = hardwareMap.get(DcMotor.class,brName);
    }
    public void SetPower(double p1,double p2,double p3,double p4){
        this.fl.setPower(p1);
        this.fr.setPower(p2);
        this.bl.setPower(p3);
        this.br.setPower(p4);
    }
    public void SetModes(DcMotor.RunMode modeFL,DcMotor.RunMode modeFR,DcMotor.RunMode modeBL,DcMotor.RunMode modeBR){
        this.fl.setMode(modeFL);
        this.fr.setMode(modeFR);
        this.bl.setMode(modeBL);
        this.br.setMode(modeBR);
    }
    public void Stop(){
        this.fl.setPower(0);
        this.fr.setPower(0);
        this.bl.setPower(0);
        this.br.setPower(0);
    }
    public void SetZeroPowerBehaviour(DcMotor.ZeroPowerBehavior behavior){
        this.fl.setZeroPowerBehavior(behavior);
        this.fr.setZeroPowerBehavior(behavior);
        this.bl.setZeroPowerBehavior(behavior);
        this.br.setZeroPowerBehavior(behavior);
    }
    public void MecannumDrive(float x, float y, float moveSpeed, float steerSpeed, float speed){
        double leftFront = (-y + x)*moveSpeed - x*-steerSpeed;
        double leftBack = (-y - x)*moveSpeed - x*-steerSpeed;
        double rightFront = (-y - x)*moveSpeed + x*-steerSpeed;
        double rightBack = (-y + x)*moveSpeed + x*-steerSpeed;
        double scalar = Math.max(Math.max(Math.abs(leftFront), Math.abs(leftBack)), Math.max(Math.abs(rightFront), Math.abs(rightBack)));
        if (scalar < 1) scalar = 1;
        this.fl.setPower(leftFront / scalar * speed);
        this.bl.setPower(leftBack / scalar * speed);
        this.fr.setPower(rightFront / scalar * speed);
        this.br.setPower(rightBack / scalar * speed);
    }
    public void Drive(float forwardSpeed, float turnSpeed, float moveSpeed){
        float leftPower = forwardSpeed+turnSpeed;
        float rightPower = forwardSpeed-turnSpeed;
        leftPower *= moveSpeed;
        rightPower *= moveSpeed;
        this.fl.setPower(leftPower);
        this.fr.setPower(rightPower);
        this.bl.setPower(leftPower);
        this.br.setPower(rightPower);
    }
}
