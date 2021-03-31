package org.firstinspires.ftc.teamcode.stateMachine;

public class Wait extends StateManager {
    public Wait(){}
    @Override
    public void Run(StateMachine machine) {
        while(!ExitCondition(machine)&&machine.opMode.opModeIsActive()){
            machine.updater.addTelemetry();
        }
        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) { return machine.opMode.getRuntime() > 27; }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.MoveToLine);
    }
}