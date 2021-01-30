package org.firstinspires.ftc.teamcode.stateMachine;

public class DropWobble extends StateManager {
    @Override
    public void Run(StateMachine machine) {
        machine.opMode.Arm.setTargetPosition(-245);
        while(machine.opMode.Arm.getCurrentPosition()!=-245&&machine.opMode.opModeIsActive()){}
        machine.opMode.Claw.setPosition(0.25);
        while(machine.opMode.Claw.getPosition()!=0.25&&machine.opMode.opModeIsActive()){}
        if(!machine.opMode.opModeIsActive()){
            machine.opMode.stop();
        }
        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return true;
    }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.Move3);
    }
}