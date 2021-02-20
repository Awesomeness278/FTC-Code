package org.firstinspires.ftc.teamcode.stateMachine;

public class MoveToWobble extends StateManager {
    public MoveToWobble(){

    }
    @Override
    public void Run(StateMachine machine) {
        Autonomous opMode = machine.opMode;

        machine.addState(States.Move1, new OldMoveCode(AutonomousData.getInstance().getDodgeRingXPosition(), 36, States.Move2,false));
        if (opMode.recognition != null) {
            if (opMode.recognition.getLabel().equals("Quad")) {
                machine.addState(States.Move2, new MoveTest(AutonomousData.getInstance().getFourRingXPosition(), 102, States.Rotate));
            } else if (opMode.recognition.getLabel().equals("Single")) {
                machine.addState(States.Move2, new OldMoveCode(AutonomousData.getInstance().getOneRingXPosition(), 78, States.Rotate,false));
            }
        } else {
            machine.addState(States.Move2, new OldMoveCode(AutonomousData.getInstance().getNoRingXPosition(), 56, States.Rotate,false));
        }

        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return true;
    }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.Move1);
    }
}