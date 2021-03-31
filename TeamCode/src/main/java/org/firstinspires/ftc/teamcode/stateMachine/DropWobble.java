package org.firstinspires.ftc.teamcode.stateMachine;

public class DropWobble extends StateManager {
    @Override
    public void Run(StateMachine machine) {
        machine.opMode.Arm.setPower(0.15);
        machine.opMode.Claw.setPosition(0);
        machine.opMode.Arm.setTargetPosition(-100);
        double startWait = machine.opMode.getRuntime();
        while(machine.opMode.getRuntime()-startWait<2){
            machine.updater.addTelemetry();
        }
        machine.opMode.Arm.setPower(0);
        machine.opMode.Claw.setPosition(0.5);
        startWait = machine.opMode.getRuntime();
        while(machine.opMode.getRuntime()-startWait<1){
            machine.updater.addTelemetry();
        }
        machine.opMode.Arm.setPower(0.1);
        machine.opMode.Arm.setTargetPosition(100);
        startWait = machine.opMode.getRuntime();
        while(machine.opMode.getRuntime()-startWait<1){
            machine.updater.addTelemetry();
        }
        machine.opMode.Claw.setPosition(0);
        machine.opMode.Arm.setPower(0);
        if(machine.opMode.opModeIsActive()){
            Exit(machine);
        }
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return true;
    }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.StraightenAfterWobble);
    }
}