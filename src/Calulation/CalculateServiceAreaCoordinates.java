/*
 * Copyright (C) 2015 mohamad
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
public class CalculateServiceAreaCoordinates {

    public static int calculate_min_y(int radius) {
//case intersection of circles        
//the height of the service area = 2*r*(radical3)/2=r*(redical(3))=r*1.73205
//        double min_y_double = radius * 0.133974;
//        return 1 + (int) min_y_double;
//        //case smaller service area:
        double min_y_double = radius * 0.133974;
//        double maxRandom = 2 * radius - radius * 0.133974 - 1;
        return 1 + (int) min_y_double;

    }

    public static int calculate_max_y(int radius) {
//case intersection of circles        
//        double min_y_double = radius * 0.133974;

//        //case smaller service area:
        double min_y_double = 11 * radius / 20;
        return 2 * radius - (int) min_y_double - 1;
        //case smaller service area:

    }

}
