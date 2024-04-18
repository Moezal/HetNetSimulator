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
package HandOverAlgorithms;

import Networks_Package.Network;
import Peers_Package.Peer;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class ResultPlotSatisfaction {

    List<Integer> x;
    List<Double> y;

    public ResultPlotSatisfaction(List<Integer> x, List<Double> y) {
        this.x = x;
        this.y = y;
        Simulator.Simulator.sp.report_writer.println(this);
    }

    @Override
    public String toString() {
        String x_result="x=[";
        for(int i: x){
            x_result+=i+",";
        }
        x_result=x_result.substring(0, x_result.toCharArray().length-1);
        x_result+="];";
        System.out.println(x_result);
        String y_result="y=[";
        for(double i: y){
            y_result+=i+",";
        }
        y_result=y_result.substring(0, y_result.toCharArray().length-1);
        y_result+="];";
        String plot = "plot(x,y,'g')";
        return x_result+"\n"+y_result+"\n"+plot;
    }


}
