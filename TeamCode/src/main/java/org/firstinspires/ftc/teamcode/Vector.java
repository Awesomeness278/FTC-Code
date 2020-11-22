package org.firstinspires.ftc.teamcode;

public class Vector{
    protected float x;
    protected float y;
    protected float z;

    public Vector(float x,float y,float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector(float x, float y){
        this(x, y, 0);
    }

    public Vector(float x) {
        this(x, 0);
    }

    public float getX()
    {
        return this.x;
    }
}