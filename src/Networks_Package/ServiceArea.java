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
package Networks_Package;

import Point.Point;

/**
 *
 * @author mohamad
 */
public class ServiceArea {

    int min_x, min_y, max_x, max_y;
    String name;
    int id;
    Simulator.SimulationParameters sp = Simulator.Simulator.sp;

    public ServiceArea(int starting_x, int min_y, int max_y, int radius, String name) {
        this.min_x = starting_x;
        this.max_x = starting_x + radius;
        this.min_y = min_y;
        this.max_y = max_y;
        this.name = name;
        this.id = Simulator.Simulator.sp.Service_area_id++;

    }

    public void createWiFiNetworks(int serviceAreaIndex) {
        int AP_min_x, AP_min_y, AP_max_x, AP_max_y;
        AP_min_x = this.min_x;
        AP_min_y = this.min_y;
        AP_max_x = this.max_x;
        AP_max_y = this.max_y;
        int length_of_service_area = AP_max_x - AP_min_x;
        int width_of_service_area = AP_max_y - AP_min_y;
        int fixed_y_center =AP_min_y+ width_of_service_area / 2;
        int counter = 1;
        for (int i = 0; i < sp.number_of_AP_in_service_area; i++) {
            if(serviceAreaIndex==0&&i==0){
              sp.Netowrks.add(new Network(sp.AP_BU/2, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_min_x + i * length_of_service_area / (sp.number_of_AP_in_service_area - 1), fixed_y_center), "AP_" + ((counter++) + serviceAreaIndex * sp.number_of_AP_in_service_area), true));  
              continue;
            }
            if(serviceAreaIndex==(sp.number_service_area-1)&&(i==(sp.number_of_AP_in_service_area-1))){
                sp.Netowrks.add(new Network(sp.AP_BU/2, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_min_x + i * length_of_service_area / (sp.number_of_AP_in_service_area - 1), fixed_y_center), "AP_" + ((counter++) + serviceAreaIndex * sp.number_of_AP_in_service_area), true));
                continue;
            }
            if(i==0){
                continue;
            }
            sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_min_x + i * length_of_service_area / (sp.number_of_AP_in_service_area - 1), fixed_y_center), "AP_" + ((counter++) + serviceAreaIndex * sp.number_of_AP_in_service_area), true));
           
        }

//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_min_x, AP_min_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_min_x, AP_max_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_max_x, AP_min_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_max_x, AP_max_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
////        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point((((AP_min_x + AP_max_x) / 2) - AP_min_x) / 2 + AP_min_x, AP_min_y + (-AP_min_y + AP_max_y) / 2), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
////        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_max_x - (AP_max_x - ((AP_min_x + AP_max_x) / 2)) / 2, AP_min_y + (-AP_min_y + AP_max_y) / 2), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point((AP_min_x + AP_max_x) / 2, AP_min_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point((AP_min_x + AP_max_x) / 2, AP_max_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_min_x, AP_min_y + (-AP_min_y + AP_max_y) / 2), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_max_x, AP_min_y + (-AP_min_y + AP_max_y) / 2), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point((AP_max_x+AP_min_x)/2, AP_min_y + (-AP_min_y + AP_max_y) / 2), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_min_x, AP_min_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_min_x + 2 * sp.AP_radius, AP_max_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_min_x + 4 * sp.AP_radius, AP_min_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_min_x + 6 * sp.AP_radius, AP_max_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_min_x + 8 * sp.AP_radius, AP_min_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_min_x + 10 * sp.AP_radius, AP_max_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_max_x, AP_min_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_min_x, AP_min_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_min_x+2*sp.AP_radius, AP_max_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(AP_max_x+4*sp.AP_radius, AP_min_y), "AP_" + ((counter++) + serviceAreaIndex * 12), true));
//        for (int i = counter; i <= sp.number_of_AP_in_service_area; i++) {
//            int x = sp.randGenerator.nextInt((AP_max_x - AP_min_x)) + AP_min_x;
//            int y = sp.randGenerator.nextInt((AP_max_y - AP_min_y)) + AP_min_y;
//            sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(x, y), "AP_" + (i + serviceAreaIndex * 12), true));
//        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMin_x() {
        return min_x;
    }

    public int getMin_y() {
        return min_y;
    }

    public int getMax_x() {
        return max_x;
    }

    public int getMax_y() {
        return max_y;
    }

}
