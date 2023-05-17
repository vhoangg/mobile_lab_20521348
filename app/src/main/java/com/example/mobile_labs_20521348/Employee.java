package com.example.mobile_labs_20521348;

import androidx.annotation.NonNull;

public class Employee {
    public String name;
    public double gross;
    public double net;

    Employee(String name, double salary){
        this.name = name;
        this.gross = salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGross(double gross) {
        this.gross = gross;
    }

    public void setNet(double net) {
        this.net = net;
    }

    public String getName() {
        return name;
    }

    public double getGross() {
        return gross;
    }

    public double getNet() {
        return net;
    }

    double CalNet (){
        double net;
        double a = this.gross - this.gross*0.105;
        if(a <= 11000000)
            net = a;
        else {
            double tax = (a - 11000000) * 0.05;
            net = a - tax;
        }
        return net;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\nNet salary: " + Math.round(this.net);
    }
}