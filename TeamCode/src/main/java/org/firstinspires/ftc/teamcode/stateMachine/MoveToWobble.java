package org.firstinspires.ftc.teamcode.stateMachine;

public class MoveToWobble extends StateManager {
    public MoveToWobble(){}
    @Override
    public void Run(StateMachine machine) {
        Autonomous opMode = machine.opMode;
        machine.addState(States.Move1,new MoveTest(-12,36,States.Move2));
        if(opMode.recognition!=null){
            if(opMode.recognition.getLabel().equals("Quad")){
                machine.addState(States.Move2,new MoveTest(36,108,States.Move3));
            }else if(opMode.recognition.getLabel().equals("Single")){
                machine.addState(States.Move2,new MoveTest(12,84,States.Move3));
            }
        }else{
            machine.addState(States.Move2,new MoveTest(36,60,States.Move3));
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