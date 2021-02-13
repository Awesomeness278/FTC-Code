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
                wobbleRotation = -45;
                break;
            case 2:
                shootingXPosition = -8;
                lineXPosition = -12;
                dodgeRingXPosition = 12;
                fourRingXPosition = -36;
                oneRingXPosition = -12;
                noRingXPosition = -32;
                wobbleRotation = -45;
                break;
            case 3:
                shootingXPosition = 8;
                lineXPosition = 12;
                dodgeRingXPosition = -12;
                fourRingXPosition = 36;
                oneRingXPosition = 12;
                noRingXPosition = 32;
                wobbleRotation = 0;
                break;
            case 4:
                dodgeRingXPosition = 8;
                lineXPosition  = -12;
                shootingXPosition = -12;
                oneRingXPosition = -12;
                noRingXPosition = 12;
                fourRingXPosition = 12;
                wobbleRotation = 0;
                break;
            default:
                int a = 0;
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
}
