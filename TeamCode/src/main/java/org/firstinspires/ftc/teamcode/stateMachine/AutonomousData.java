package org.firstinspires.ftc.teamcode.stateMachine;

public class AutonomousData {
    private static AutonomousData instance;
    private double shootingXPosition = 0;
    private double lineXPosition = 0;
    private double dodgeRingXPosition = 0;
    private double fourRingXPosition = 0;
    private double oneRingXPosition = 0;
    private double noRingXPosition = 0;
    private double wobbleRotation = 0;
    private double shootingXPosition2 = 0;
    private double shootingXPosition3 = 0;

    private AutonomousData(){
    }

    public static AutonomousData getInstance(){
        if(instance == null){
            instance = new AutonomousData();
        }
        return  instance;
    }

    public void SetStartingLocation(int location){
        switch(location){
            case 1:
                dodgeRingXPosition = -8;
                lineXPosition  = 12;
                shootingXPosition = 12;
                oneRingXPosition = 12;
                noRingXPosition = -12;
                fourRingXPosition = -12;
                wobbleRotation = -30;
                shootingXPosition2 = 36;
                shootingXPosition3 = -12;
                break;
            case 2:
                shootingXPosition = -10;
                lineXPosition = -12;
                dodgeRingXPosition = 12;
                fourRingXPosition = -36;
                oneRingXPosition = -10;
                noRingXPosition = -26;
                wobbleRotation = -30;
                shootingXPosition2 = 14;
                shootingXPosition3 = -34;
                break;
            case 3:
                shootingXPosition = 10;
                lineXPosition = 12;
                dodgeRingXPosition = -12;
                fourRingXPosition = 36;
                oneRingXPosition = 6;
                noRingXPosition = 26;
                wobbleRotation = 0;
                shootingXPosition2 = 34;
                shootingXPosition3 = -14;
                break;
            case 4:
                dodgeRingXPosition = 8;
                lineXPosition  = -12;
                shootingXPosition = -12;
                oneRingXPosition = -12;
                noRingXPosition = 12;
                fourRingXPosition = 12;
                wobbleRotation = 0;
                shootingXPosition2 = 12;
                shootingXPosition3 = -36;
                break;
            default:
                break;
        }
    }

    public double getShootingXPosition(){
        return shootingXPosition;
    }

    public double getLineXPosition(){
        return lineXPosition;
    }

    public double getDodgeRingXPosition(){
        return dodgeRingXPosition;
    }

    public double getFourRingXPosition(){
        return fourRingXPosition;
    }

    public double getOneRingXPosition(){
        return oneRingXPosition;
    }

    public double getNoRingXPosition(){
        return noRingXPosition;
    }

    public double getWobbleRotation() { return wobbleRotation; }

    public double getShootingXPosition2() { return shootingXPosition2; }

    public double getShootingXPosition3(){ return shootingXPosition3; }
}
