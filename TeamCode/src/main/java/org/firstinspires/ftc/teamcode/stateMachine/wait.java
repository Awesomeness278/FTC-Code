package org.firstinspires.ftc.teamcode.stateMachine;

public class wait extends StateManager {
    public wait(){}
    @Override
    public void Run(StateMachine machine) {
        double delay = 2;
        while(!ExitCondition(machine)&&machine.opMode.opModeIsActive()){
            machine.opMode.Shooter.setPower(1);
            if(machine.opMode.getRuntime()-delay<machine.opMode.getRuntime()-machine.opMode.getRuntime()%(2*delay)) {
                machine.opMode.Conveyor.setPower(1);
            }else{
                machine.opMode.Conveyor.setPower(0);
            }
        }
        machine.opMode.Shooter.setPower(0);
        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return machine.opMode.getRuntime()>25;
    }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.Move4);
    }
}