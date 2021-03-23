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
    private double shootingRotation = 0;
    private double shootingRotation2 = 0;
    private double shootingRotation3 = 0;

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
                oneRingXPosition = 20;
                noRingXPosition = -12;
                fourRingXPosition = -14;
                wobbleRotation = -35;
                shootingXPosition2 = 36;
                shootingXPosition3 = -12;
                shootingRotation = -2;
                shootingRotation2 = -15;
                shootingRotation3 = 3;
                break;
            case 2:
                shootingXPosition = -12;
                lineXPosition = -12;
                dodgeRingXPosition = 12;
                fourRingXPosition = -38;
                oneRingXPosition = -12;
                noRingXPosition = -27;
                wobbleRotation = -30;
                shootingXPosition2 = 14;
                shootingXPosition3 = -34;
                shootingRotation = -2;
                shootingRotation2 = -15;
                shootingRotation3 = 15;
                break;
            case 3://Inner Red
                shootingXPosition = 6;//Center
                lineXPosition = 12;//Obsolete
                dodgeRingXPosition = -12;//X position robot goes to to dodge the ring stack
                fourRingXPosition = 34;//The X position of the four ring spot
                oneRingXPosition = 5;//The X position of the one ring spot
                noRingXPosition = 22;//The X position of the no ring spot
                wobbleRotation = 5;//The rotation the robot does before dropping the wobble(in degrees)
                shootingXPosition2 = 34;//The right shooting position
                shootingXPosition3 = -9;//The left shooting position
                shootingRotation = 1;//The center shooting rotation
                shootingRotation2 = -15;//The right shooting rotation
                shootingRotation3 = 5;//The left shooting rotation
                break;
            case 4:
                dodgeRingXPosition = 8;
                lineXPosition  = -12;
                shootingXPosition = -16;
                oneRingXPosition = -25;
                noRingXPosition = 6;
                fourRingXPosition = 0;
                wobbleRotation = 7;
                shootingXPosition2 = 15;
                shootingXPosition3 = -34;
                shootingRotation = 1;
                shootingRotation2 = -15;
                shootingRotation3 = 5;
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

    public double getShootingRotation(){ return shootingRotation; }

    public double getShootingRotation2(){ return shootingRotation2; }

    public double getShootingRotation3(){ return shootingRotation3; }
}
