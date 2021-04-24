package org.firstinspires.ftc.teamcode.stateMachine;

public class AutonomousData {
    private static AutonomousData instance;
    private double shootingXPosition = 0;
    private double dodgeRingXPosition = 0;
    private double fourRingXPosition = 0;
    private double oneRingXPosition = 0;
    private double noRingXPosition = 0;
    private double wobbleRotation0 = 0;
    private double wobbleRotation1 = 0;
    private double wobbleRotation4 = 0;
    private double shootingXPosition2 = 0;
    private double shootingXPosition3 = 0;
    private double shootingRotation = 0;
    private double shootingRotation2 = 0;
    private double shootingRotation3 = 0;
    private double wobblePathX0 = 0;
    private double wobblePathX1 = 0;
    private double wobblePathX4 = 0;
    private double lineYPosition0 = 0;
    private double lineYPosition1 = 0;
    private double lineYPosition4 = 0;
    private double waitYPosition = 0;
    private double wobbleYPosition0 = 0;
    private double wobbleYPosition1 = 0;
    private double wobbleYPosition4 = 0;
    private double wobblePathY = 0;
    private double lineXPosition0 = 0;
    private double lineXPosition1 = 0;
    private double lineXPosition4 = 0;

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
                dodgeRingXPosition = 0;
                shootingXPosition = 0;
                oneRingXPosition = 4;
                noRingXPosition = 0;
                fourRingXPosition = 0;
                wobblePathX0 = 0;
                wobblePathX1 = 0;
                wobblePathX4 = 1;
                wobbleRotation0 = -45;
                wobbleRotation1 = 90;
                wobbleRotation4 = -50;
                shootingXPosition2 = 0;
                shootingXPosition3 = 0;
                shootingRotation = 0;
                shootingRotation2 = 0;
                shootingRotation3 = 7;
                lineYPosition0 = 50;
                lineYPosition1 = 72;
                lineYPosition4 = 72;
                lineXPosition0 = 4;
                lineXPosition1 = 0;
                lineXPosition4 = 0;
                waitYPosition = 50;
                wobbleYPosition0 = 52;
                wobbleYPosition1 = 102;
                wobbleYPosition4 = 102;
                wobblePathY = 0;
                break;
            case 2://Inner Blue
                shootingXPosition = 0;//Center
                dodgeRingXPosition = 0;//X position robot goes to to dodge the ring stack
                fourRingXPosition = -20;//The X position of the four ring spot
                oneRingXPosition = 0;//The X position of the one ring spot
                noRingXPosition = -40;//The X position of the no ring spot
                wobblePathX0 = 24;
                wobblePathX1 = 24;
                wobblePathX4 = 24;
                wobbleRotation0 = -160;//The rotation the robot does before dropping the wobble(in degrees)
                wobbleRotation1 = -90;
                wobbleRotation4 = -60;
                shootingXPosition2 = 0;//The right shooting position
                shootingXPosition3 = 0;//The left shooting position
                shootingRotation = 0;//The center shooting rotation
                shootingRotation2 = 0;//The right shooting rotation
                shootingRotation3 = -8;//The left shooting rotation
                lineYPosition0 = 88;
                lineYPosition1 = 80;
                lineYPosition4 = 80;
                waitYPosition = Double.NaN;
                wobbleYPosition0 = 91;
                wobbleYPosition1 = 110;
                wobbleYPosition4 = 115;
                wobblePathY = 90;
                lineXPosition0 = 0;
                lineXPosition1 = 0;
                lineXPosition4 = 0;
                break;
            case 3://Inner Red
                shootingXPosition = 0;//Center
                dodgeRingXPosition = 0;//X position robot goes to to dodge the ring stack
                fourRingXPosition = 20;//The X position of the four ring spot
                oneRingXPosition = 4;//The X position of the one ring spot
                noRingXPosition = 40;//The X position of the no ring spot
                wobblePathX0 = -24;
                wobblePathX1 = -24;
                wobblePathX4 = -24;
                wobbleRotation0 = 160;//The rotation the robot does before dropping the wobble(in degrees)
                wobbleRotation1 = 90;
                wobbleRotation4 = 60;
                shootingXPosition2 = 0;//The right shooting position
                shootingXPosition3 = 0;//The left shooting position
                shootingRotation = 0;//The center shooting rotation
                shootingRotation2 = 0;//The right shooting rotation
                shootingRotation3 = 8;//The left shooting rotation
                lineYPosition0 = 88;
                lineYPosition1 = 80;
                lineYPosition4 = 80;
                waitYPosition = Double.NaN;
                wobbleYPosition0 = 91;
                wobbleYPosition1 = 105;
                wobbleYPosition4 = 115;
                wobblePathY = 90;
                lineXPosition0 = 0;
                lineXPosition1 = 0;
                lineXPosition4 = 0;
                break;
            case 4://Outer Red
                dodgeRingXPosition = 0;
                shootingXPosition = 0;
                oneRingXPosition = -4;
                noRingXPosition = 3;
                fourRingXPosition = 0;
                wobblePathX0 = 0;
                wobblePathX1 = 0;
                wobblePathX4 = -1;
                wobbleRotation0 = 0;
                wobbleRotation1 = -90;
                wobbleRotation4 = 0;
                shootingXPosition2 = 0;
                shootingXPosition3 = 0;
                shootingRotation = 1;
                shootingRotation2 = -12;
                shootingRotation3 = 5;
                lineYPosition0 = 50;
                lineYPosition1 = 72;
                lineYPosition4 = 72;
                waitYPosition = 50;
                wobbleYPosition0 = 60;
                wobbleYPosition1 = 88;
                wobbleYPosition4 = 104;
                wobblePathY = 0;
                lineXPosition0 = 0;
                lineXPosition1 = 0;
                lineXPosition4 = 0;
                break;
            default:
                break;
        }
    }

    public double getShootingXPosition(){
        return shootingXPosition;
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

    public double getWobbleRotation0() { return wobbleRotation0; }

    public double getWobbleRotation1() { return wobbleRotation1; }

    public double getWobbleRotation4() { return wobbleRotation4; }

    public double getShootingXPosition2() { return shootingXPosition2; }

    public double getShootingXPosition3(){ return shootingXPosition3; }

    public double getShootingRotation(){ return shootingRotation; }

    public double getShootingRotation2(){ return shootingRotation2; }

    public double getShootingRotation3(){ return shootingRotation3; }

    public double getWobblePathX0() { return wobblePathX0; }

    public double getWobblePathX1() { return wobblePathX1; }

    public double getWobblePathX4() { return wobblePathX4; }

    public double getLineYPosition0() { return lineYPosition0; }

    public double getLineYPosition1() { return lineYPosition1; }

    public double getLineYPosition4() { return lineYPosition4; }

    public double getWaitYPosition() { return waitYPosition; }

    public double getWobbleYPosition0() { return wobbleYPosition0; }

    public double getWobbleYPosition1() { return wobbleYPosition1; }

    public double getWobbleYPosition4() { return wobbleYPosition4; }

    public double getWobblePathY() { return wobblePathY; }

    public double getLineXPosition0() { return lineXPosition0; }

    public double getLineXPosition1() { return lineXPosition1; }

    public double getLineXPosition4() { return lineXPosition4; }
}
