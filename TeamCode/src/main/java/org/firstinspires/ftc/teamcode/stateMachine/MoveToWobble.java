package org.firstinspires.ftc.teamcode.stateMachine;

public class MoveToWobble extends StateManager {
    public MoveToWobble(){

    }
    @Override
    public void Run(StateMachine machine) {
        machine.updater.addTelemetry();
        Autonomous opMode = machine.opMode;

        if (opMode.recognition != null) {
            if (opMode.recognition.getLabel().equals("Quad")) {
                machine.addState(States.Move5,new MoveTest(AutonomousData.getInstance().getWobblePathX4(),116,States.Move2));
                machine.addState(States.Move2,new MoveTest(AutonomousData.getInstance().getFourRingXPosition(),122,States.Rotate));
            } else if (opMode.recognition.getLabel().equals("Single")) {
                machine.addState(States.Move5,new MoveTest(AutonomousData.getInstance().getWobblePathX1(),96,States.Move2));
                machine.addState(States.Move2, new MoveTest(AutonomousData.getInstance().getOneRingXPosition(), 102, States.Rotate));
            }
        } else {
            machine.addState(States.Move5,new MoveTest(AutonomousData.getInstance().getWobblePathX0(),74,States.Move2));
            machine.addState(States.Move2, new MoveTest(AutonomousData.getInstance().getNoRingXPosition(), 80, States.Rotate));
        }

        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return true;
    }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.Move5);
    }
}