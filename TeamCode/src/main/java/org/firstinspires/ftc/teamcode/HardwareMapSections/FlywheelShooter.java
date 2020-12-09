package org.firstinspires.ftc.teamcode.HardwareMapSections;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class FlywheelShooter {
    DcMotor shooter, intake, conveyor;
    String shootName = "Shooter", convName = "Conveyor", intakeName = "Intake";

    double flywheelPower = 0.8;
    boolean dPadUpPressed = false;
    boolean dPadDownPressed = false;
    boolean rightTriggerPressed = false;
    int flywheelSwitch = 0;

    public void ShootRing(){
        if(gamepad2.dpad_up){
            if(!dPadUpPressed) {
                flywheelPower += 0.005;
            }
            dPadUpPressed = true;
        } else {
            dPadUpPressed = false;
        }

        if(gamepad2.dpad_down){
            if(!dPadDownPressed) {
                flywheelPower -= 0.005;
            }
            dPadDownPressed = true;
        } else {
            dPadDownPressed = false;
        }

        if(gamepad2.right_trigger > 0.05){
            if(!rightTriggerPressed) {
                flywheelSwitch = (flywheelSwitch + 1) % 2;
            }
            rightTriggerPressed = true;
        } else {
            rightTriggerPressed = false;
        }
        shooter.setPower(-(flywheelSwitch*flywheelPower));

        //Intake and Conveyor
        if(gamepad2.a){
            intake.setPower(1);
        } else {
            intake.setPower(0);
        }
        if(gamepad2.b){
            conveyor.setPower(0.7);
        } else {
            conveyor.setPower(0);
        }
        telemetry.addData("Flywheel Power: ",flywheelPower);
    }
    public void HardwareMap(HardwareMap hardwareMap){
        shooter = hardwareMap.dcMotor.get(shootName);
        conveyor = hardwareMap.dcMotor.get(convName);
        intake = hardwareMap.dcMotor.get(intakeName);

    }
}
