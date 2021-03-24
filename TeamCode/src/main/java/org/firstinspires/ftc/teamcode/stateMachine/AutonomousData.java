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
    private double wobblePathX0 = 0;
    private double wobblePathX1 = 0;
    private double wobblePathX4 = 0;

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
            case 1://Outer Blue
                dodgeRingXPosition = -8;
                lineXPosition  = 12;
                shootingXPosition = 12;
                oneRingXPosition = 32;
                noRingXPosition = 0;
                wobblePathX0 = 44;
                wobblePathX4 = 44;
                wobblePathX1 = 68;
                fourRingXPosition = -2;
                wobbleRotation = -90;
                shootingXPosition2 = 36;
                shootingXPosition3 = -12;
                shootingRotation = -2;
                shootingRotation2 = -15;
                shootingRotation3 = 3;
                break;
            case 2://Inner Blue
                shootingXPosition = -12;
                lineXPosition = -12;
                dodgeRingXPosition = 12;
                fourRingXPosition = -26;
                oneRingXPosition = 0;
                noRingXPosition = -15;
                wobbleRotation = -90;
                wobblePathX0 = 9;
                wobblePathX4 = -2;
                wobblePathX1 = 24;
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
                fourRingXPosition = 46;//The X position of the four ring spot
                oneRingXPosition = 17;//The X position of the one ring spot
                noRingXPosition = 34;//The X position of the no ring spot
                wobblePathX0 = 58;
                wobblePathX1 = 41;
                wobblePathX4 = 70;
                wobbleRotation = 90;//The rotation the robot does before dropping the wobble(in degrees)
                shootingXPosition2 = 34;//The right shooting position
                shootingXPosition3 = -9;//The left shooting position
                shootingRotation = 1;//The center shooting rotation
                shootingRotation2 = -15;//The right shooting rotation
                shootingRotation3 = 5;//The left shooting rotation
                break;
            case 4://Outer Red
                dodgeRingXPosition = 8;
                lineXPosition  = -12;
                shootingXPosition = -16;
                oneRingXPosition = -13;
                noRingXPosition = 18;
                fourRingXPosition = 12;
                wobblePathX0 = 42;
                wobblePathX1 = 11;
                wobblePathX4 = 36;
                wobbleRotation = 90;
                shootingXPosition2 = 10;
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

    public double getWobblePathX0() { return wobblePathX0; }

    public double getWobblePathX1() { return wobblePathX1; }

    public double getWobblePathX4() { return wobblePathX4; }
}
