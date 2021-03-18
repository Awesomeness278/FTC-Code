package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.concurrent.TimeUnit;

public class PIDcontroller {
    DcMotor motor;
    double integral = 0;
    PIDCoefficients coefficients = new PIDCoefficients(0.00001,0.00000001,0.00001);
    public ElapsedTime PIDTimer = new ElapsedTime();
    double prevPos;
    double newPos;
    double lastError;
    double error = Double.NaN;

    /**@param motor The motor you want to control via PID.
     */
    public PIDcontroller(DcMotor motor){
        this.motor = motor;
    }

    public double run(double targetSpeed){
        if(Double.isNaN(error)){
            setup();
            return 0;
        }else {
            return moveMotor(targetSpeed);
        }
    }

    public void setup(){
        error = calcSpeed();
        PIDTimer.reset();
        lastError = 0;
    }

    double moveMotor(double targetSpeed){
        error = targetSpeed-calcSpeed();
        double changeInError = lastError - error;
        integral += changeInError * PIDTimer.time();
        double derivative = changeInError/PIDTimer.time();
        double P = coefficients.p * error;
        double I = coefficients.i * integral;
        double D = coefficients.d * derivative;
        lastError = error;
        PIDTimer.reset();
        return P+I+D;
    }

    double calcSpeed(){
        prevPos = newPos;
        newPos = motor.getCurrentPosition();
        double speed = (newPos-prevPos)/PIDTimer.time(TimeUnit.MILLISECONDS);
        speed*=10;
        return speed;
    }
}
