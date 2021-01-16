package org.firstinspires.ftc.teamcode.stateMachine;

public class MoveToWobble extends StateManager {
    @Override
    public void Run(StateMachine machine) {
        Autonomous opMode = machine.opMode;
        if(opMode.recognition!=null){
            if(opMode.recognition.getLabel()=="Quad"){
                machine.addState(States.Move1,new MoveTest(36,108,States.Move2));
            }else if(opMode.recognition.getLabel()=="Single"){
                machine.addState(States.Move1,new MoveTest(12,84,States.Move2));
            }
        }else{
            machine.addState(States.Move1,new MoveTest(36,60,States.Move2));
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