package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDcontroller {
    DcMotor motor;
    double integral = 0;
    PIDCoefficients coefficients = new PIDCoefficients(0,0,0);
    ElapsedTime PIDTimer = new ElapsedTime();
    double prevPos;
    double newPos;
    double lastError;
    double error = Double.NaN;

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
        error = calcSpeed()-targetSpeed;
        double changInError = lastError - error;
        integral += changInError * PIDTimer.time();
        double derivative = changInError/PIDTimer.time();
        double P = coefficients.p * error;
        double I = coefficients.i * integral;
        double D = coefficients.d * derivative;
        error = lastError;
        PIDTimer.reset();
        return P+I+D;
    }

    double calcSpeed(){
        prevPos = newPos;
        newPos = motor.getCurrentPosition();
        double speed = (newPos-prevPos)/PIDTimer.time();
        return speed;
    }
}
