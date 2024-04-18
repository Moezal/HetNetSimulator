/*
 * Copyright (C) 2016 mohamad
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package Calulation;

/**
 *
 * @author mohamad
 */
public class CalculatePathLoss {

    public static double calculatePathLoss(boolean AP, double distance) {
        if (AP) {
            double db = 38.2 + 30 * Math.log10(distance);
            return Math.pow(10, -db / 10);
        } else {
            double db = 34 + 40 * Math.log10(distance);
            return Math.pow(10, -db / 10);
        }

    }

//    public static void main(String[] args) {
//        System.out.println("10: " + calculatePathLoss(false, 10));
//        System.out.println("100: " + calculatePathLoss(false, 100));
//        System.out.println("250: " + calculatePathLoss(false, 250));
//        System.out.println("500: " + calculatePathLoss(false, 500));
//        System.out.println("1000: " + calculatePathLoss(false, 1000));
//        System.out.println("10: " + calculatePathLoss(true, 10));
//        System.out.println("100: " + calculatePathLoss(true, 100));
//        System.out.println("250: " + calculatePathLoss(true, 250));
//        System.out.println("500: " + calculatePathLoss(true, 500));
//        System.out.println("1000: " + calculatePathLoss(true, 1000));
//    }

}
