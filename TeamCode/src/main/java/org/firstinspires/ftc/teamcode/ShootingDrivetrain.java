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
    DrivetrainMovement drivetrain = new DrivetrainMovement(gamepad1, gamepad2);
    FlywheelShooter ringShooting = new FlywheelShooter(gamepad1, gamepad2);
    WobbleGrab grabWobble = new WobbleGrab(gamepad1, gamepad2);

    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain.HardwareMap(hardwareMap);
        ringShooting.HardwareMap(hardwareMap);
        grabWobble.HardwareMap(hardwareMap);

        telemetry.addData("Status", "Init Complete");
        telemetry.update();

        waitForStart();
        while(opModeIsActive()){
            try {
                drivetrain.DriveRobot();
                ringShooting.ShootRing();
                grabWobble.ArmLogic();
            }catch(Exception ex){
                StackTraceElement[] stackTrace = ex.getStackTrace();

                String fullTrace = "";

                for (StackTraceElement trace : stackTrace) {
                    String traceInfo
                            = trace.getClassName().concat(".").concat(trace.getMethodName()).concat(":").concat(trace.getLineNumber()+"").concat("(").concat(trace.getFileName()).concat(")\n");

                    fullTrace.concat(traceInfo);
                }
                telemetry.addData("Trace",fullTrace);
                telemetry.update();
            }
        }
    }
}