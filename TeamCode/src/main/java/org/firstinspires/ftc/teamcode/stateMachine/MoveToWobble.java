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
                machine.addState(States.WobblePathMove,new MoveTest(AutonomousData.getInstance().getWobblePathX4(),AutonomousData.getInstance().getWobbleYPosition4(),States.MoveToWobblePosition));
                machine.addState(States.MoveToWobblePosition,new MoveTest(AutonomousData.getInstance().getFourRingXPosition(),AutonomousData.getInstance().getWobbleYPosition4(),States.RotateToWobble));
                machine.addState(States.RotateToWobble, new Rotate(AutonomousData.getInstance().getWobbleRotation4(),States.DropWobble));
                machine.addState(States.MoveToLine, new MoveTest(0,AutonomousData.getInstance().getLineYPosition4(),States.Stop));
                machine.addState(States.StraightenAfterWobble,new Rotate(0,States.MoveToLine));
            } else if (opMode.recognition.getLabel().equals("Single")) {
                machine.addState(States.WobblePathMove,new MoveTest(AutonomousData.getInstance().getWobblePathX1(),AutonomousData.getInstance().getWobbleYPosition1(),States.MoveToWobblePosition));
                machine.addState(States.MoveToWobblePosition, new MoveTest(AutonomousData.getInstance().getOneRingXPosition(), AutonomousData.getInstance().getWobbleYPosition1(), States.RotateToWobble));
                machine.addState(States.RotateToWobble, new Rotate(AutonomousData.getInstance().getWobbleRotation1(),States.DropWobble));
                machine.addState(States.MoveToLine, new MoveTest(0,AutonomousData.getInstance().getLineYPosition1(),States.Stop));
                machine.addState(States.StraightenAfterWobble,new Rotate(0,States.MoveToLine));
            }
        } else {
            if(Double.isNaN(AutonomousData.getInstance().getWaitYPosition())){
                machine.addState(States.WobblePathMove,new MoveTest(AutonomousData.getInstance().getWobblePathX0(),AutonomousData.getInstance().getWobbleYPosition0(),States.MoveToWobblePosition));
                machine.addState(States.MoveToWobblePosition, new MoveTest(AutonomousData.getInstance().getOneRingXPosition(), AutonomousData.getInstance().getWobbleYPosition0(), States.RotateToWobble));
                machine.addState(States.RotateToWobble, new Rotate(AutonomousData.getInstance().getWobbleRotation0(),States.DropWobble));
                machine.addState(States.StraightenAfterWobble,new Park());
            }else {
                machine.addState(States.WobblePathMove, new MoveTest(AutonomousData.getInstance().getWobblePathX0(), AutonomousData.getInstance().getWobbleYPosition0(), States.MoveToWobblePosition));
                machine.addState(States.MoveToWobblePosition, new MoveTest(AutonomousData.getInstance().getNoRingXPosition(), AutonomousData.getInstance().getWobbleYPosition0(), States.RotateToWobble));
                machine.addState(States.RotateToWobble, new Rotate(AutonomousData.getInstance().getWobbleRotation0(), States.DropWobble));
                machine.addState(States.MoveToLine, new MoveTest(0, AutonomousData.getInstance().getLineYPosition0(), States.Park));
                machine.addState(States.StraightenAfterWobble, new Rotate(0, States.MoveToWaitPosition));
                machine.addState(States.MoveToWaitPosition, new MoveTest(0, AutonomousData.getInstance().getWaitYPosition(), States.Wait));
                machine.addState(States.Wait, new Wait());
            }
        }

        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return true;
    }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.WobblePathMove);
    }
}