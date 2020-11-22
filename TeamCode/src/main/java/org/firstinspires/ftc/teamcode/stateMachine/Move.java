package org.firstinspires.ftc.teamcode.stateMachine;

public class Move extends StateMachine{
    float x;
    float y;

    public Move(float x,float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void Run(StateManager manager, Autonomous opMode) {
        int loops = 0;
        while(loops<80){
            loops++;
            opMode.motors[0].setPower(-1);
            opMode.motors[1].setPower(1);
            opMode.motors[2].setPower(-1);
            opMode.motors[3].setPower(1);
        }
        Exit(manager, opMode);
    }

    @Override
    public boolean ExitCondition(Autonomous opMode) {
        return true;
    }

    @Override
    public void Exit(StateManager manager, Autonomous opMode) {
        opMode.telemetry.addData("Moved","Yes");
        opMode.stop();
    }
}