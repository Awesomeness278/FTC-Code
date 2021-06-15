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
    public void Drive(double p1,double p2,double p3,double p4){
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
}
