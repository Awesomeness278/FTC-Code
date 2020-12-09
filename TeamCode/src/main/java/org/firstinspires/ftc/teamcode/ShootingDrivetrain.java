package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.HardwareMapSections.DrivetrainMovement;
import org.firstinspires.ftc.teamcode.HardwareMapSections.FlywheelShooter;
import org.firstinspires.ftc.teamcode.HardwareMapSections.WobbleGrab;

@TeleOp(name = "Shooting + Arm + Drivetrain")
public class ShootingDrivetrain extends LinearOpMode {
    DrivetrainMovement drivetrain;
    FlywheelShooter ringShooting;
    WobbleGrab grabWobble;

    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain.HardwareMap(hardwareMap);
        ringShooting.HardwareMap(hardwareMap);
        grabWobble.HardwareMap(hardwareMap);

        telemetry.addData("Status", "Init Complete");
        telemetry.update();

        waitForStart();
        while(opModeIsActive()){
            drivetrain.DriveRobot();
            ringShooting.ShootRing();
            grabWobble.ArmLogic();
        }
    }
}
