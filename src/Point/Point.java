/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Point;

import Networks_Package.Network;
import Simulator.SimulationParameters;
import Simulator.Simulator;

/**
 *
 * @author mohamad
 */
public class Point {

    double x, y;
    SimulationParameters sp = Simulator.sp;
    int angle;
    int stop_time;//how much it will stop before continue moving
    int stop_every;//every when it will stop
    int stop_time_counter, stop_every_counter;
    int direction_change_counter;
    int direction_change_every;
    boolean is_stopped;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(int min_x, int min_y, int max_x, int max_y) {
//        do {
        this.x = min_x + (max_x - min_x -1) * sp.randGenerator.nextDouble();
        this.y = min_y + (max_y - min_y -1) * sp.randGenerator.nextDouble();
//        } while (!isWithinRange());
        stop_time = sp.min_stop_time + sp.randGenerator.nextInt(sp.max_stop_time - sp.min_stop_time);
        stop_every = sp.min_stop_every + sp.randGenerator.nextInt(sp.max_stop_every - sp.min_stop_every);
        direction_change_every = sp.min_direction_change + sp.randGenerator.nextInt(sp.max_direction_change - sp.min_direction_change);
        stop_every_counter = 0;
        stop_time_counter = 0;
        is_stopped = false;
        angle = sp.randGenerator.nextInt(360);
    }

    public void movePoint(int speed) {
        direction_change_counter++;
        if (direction_change_counter == direction_change_every) {
            direction_change_counter = 0;
            this.angle = sp.randGenerator.nextInt(360);
        }
        if (is_stopped) {
            if (stop_time_counter == stop_time) {
                stop_time_counter = 0;
                is_stopped = false;
                return;
            } else {
                stop_time_counter++;
                return;
            }
        }
        if (stop_every_counter == stop_every) {
            is_stopped = true;
            stop_every_counter = -1;
        }
        double new_x, new_y;
        boolean skip = true;
        do {
            if (!skip) {
                this.angle = sp.randGenerator.nextInt(360);
            }
            new_x = this.x + speed * Math.cos(angle);
            new_y = this.y + speed * Math.sin(angle);
            skip = false;
        } while (!isWithinRange(new_x, new_y));
        this.x = new_x;
        this.y = new_y;
        stop_every_counter++;
    }


    private boolean isWithinRange() {
        for (Network n : sp.major_Netowrks) {
            if (this.distanceTo(n.getCenter()) < n.getRadius()) {
                return true;
            }
        }
        return false;
    }

    private boolean isWithinRange(double x, double y) {
        if (x > (sp.min_x) && x < (sp.max_x) && y > (sp.min_y) && y < (sp.max_y)) {
            return true;
        } else {
            return false;
        }
//        the below commented code in case no service areas
//        Point p = new Point(x, y);
//        for (Network n : sp.major_Netowrks) {
//            if (p.distanceTo(n.getCenter()) < n.getRadius()) {
//                return true;
//            }
//        }
//        return false;
    }

    public double distanceTo(Point p) {
        return Math.sqrt((this.x - p.getX()) * (this.x - p.getX()) + (this.y - p.getY()) * (this.y - p.getY()));

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "x=" + getX() + "--y=" + getY();
    }
}
