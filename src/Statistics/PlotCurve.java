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
package Statistics;

import java.util.LinkedList;

/**
 *
 * @author mohamad
 */
public class PlotCurve {

    public PlotCurve(LinkedList<Integer> x, LinkedList<Double> y, String curveName, int takeValueEvery, String symbol, String legend, boolean appendToAnotherFileBesidesItsOwnFile, String otherFileName) {
        boolean addedOCA = false;
        for (OverallCurveAdder oca : Simulator.Simulator.overallCurveAdderList) {
            if ((oca.getCurveName().equals(curveName))) {
//                System.out.println("added new ylist to " + otherFileName+" curve name "+ curveName);
                oca.addYList(y);
                addedOCA = true;
            }
        }
        if (!addedOCA) {
//            System.out.println("added new oca");
            Simulator.Simulator.overallCurveAdderList.add(new OverallCurveAdder(x, y, curveName, takeValueEvery, symbol, legend, appendToAnotherFileBesidesItsOwnFile, otherFileName));
        }
        String xString = "x = [ ";
        String yString = "y" + curveName + " = [";
        double sum = 0;
        int counter = 0;
        for (int i = 0; i < x.size(); i++) {
            sum += y.get(i);
            counter++;
            if (i == 0) {
                xString += x.get(i) + ",";
                yString += (sum / (double) counter) + ",";
                counter = 0;
                sum = 0;
            } else if (i == (x.size() - 1)) {
                xString += x.get(i) + ",";
                yString += (sum / (double) counter) + ",";
                counter = 0;
                sum = 0;
            } else if (i % takeValueEvery == 0) {
                xString += x.get(i) + ",";
                yString += (sum / (double) counter) + ",";
                counter = 0;
                sum = 0;
            }
        }
        xString = xString.substring(0, xString.length() - 1) + "];";
        yString = yString.substring(0, yString.length() - 1) + "];";
        String plotString = "plot(x,y" + curveName + "," + symbol + ")\nhold on\nlegend(\'" + legend + "\')";
        new OurFileWriter(xString + "\n" + yString + "\n" + plotString + "\n", Simulator.Simulator.secondary_path + Simulator.Simulator.path_delimilter + curveName + ".m", false);
        if (appendToAnotherFileBesidesItsOwnFile) {
            new OurFileWriter(xString + "\n" + yString + "\n" + plotString + "\n", Simulator.Simulator.default_path + Simulator.Simulator.path_delimilter + "AVERAGE_CURVES" + Simulator.Simulator.path_delimilter + otherFileName + ".m", true);
        }

    }

}
