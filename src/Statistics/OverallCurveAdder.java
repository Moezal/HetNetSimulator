/*
 * Copyright (C) 2015 mzalghou
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
 * @author mzalghou
 */
public class OverallCurveAdder {

    LinkedList<Integer> x;
    LinkedList<Double> y;
    String curveName;
    int takeValueEvery;
    String symbol;
    String legend;
    boolean appendToAnotherFileBesidesItsOwnFile;
    String otherFileName;
    int counter;

    public OverallCurveAdder(LinkedList<Integer> x, LinkedList<Double> y, String curveName, int takeValueEvery, String symbol, String legend, boolean appendToAnotherFileBesidesItsOwnFile, String otherFileName) {
        this.x = x;
        this.y = y;
        this.curveName = curveName;
        this.takeValueEvery = takeValueEvery;
        this.symbol = symbol;
        this.legend = legend;
        this.appendToAnotherFileBesidesItsOwnFile = appendToAnotherFileBesidesItsOwnFile;
        this.otherFileName = otherFileName;
        this.counter = 1;
    }

    public void addYList(LinkedList<Double> yList) {
        for (int i = 0; i < y.size(); i++) {
//            System.out.print(y.get(i)+"+"+yList.get(i)+"=");
            y.set(i, y.get(i) + yList.get(i));
//            System.out.println(""+y.get(i));

        }
        counter++;
    }

    public void adjustYList() {
        for (int i = 0; i < y.size(); i++) {
            y.set(i, y.get(i) / ((double) counter));
        }
//        System.out.println("averaging " + counter + " curves");
    }

    public void PlotCurve() {
        otherFileName = "OVERALL_STATISTICS" + Simulator.Simulator.path_delimilter + "OVERALL_" + otherFileName;
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
            new OurFileWriter(xString + "\n" + yString + "\n" + plotString + "\n", Simulator.Simulator.default_path + Simulator.Simulator.path_delimilter + otherFileName + ".m", true);
        }

    }

    public LinkedList<Integer> getX() {
        return x;
    }

    public void setX(LinkedList<Integer> x) {
        this.x = x;
    }

    public LinkedList<Double> getY() {
        return y;
    }

    public void setY(LinkedList<Double> y) {
        this.y = y;
    }

    public String getCurveName() {
        return curveName;
    }

    public void setCurveName(String curveName) {
        this.curveName = curveName;
    }

    public int getTakeValueEvery() {
        return takeValueEvery;
    }

    public void setTakeValueEvery(int takeValueEvery) {
        this.takeValueEvery = takeValueEvery;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public boolean isAppendToAnotherFileBesidesItsOwnFile() {
        return appendToAnotherFileBesidesItsOwnFile;
    }

    public void setAppendToAnotherFileBesidesItsOwnFile(boolean appendToAnotherFileBesidesItsOwnFile) {
        this.appendToAnotherFileBesidesItsOwnFile = appendToAnotherFileBesidesItsOwnFile;
    }

    public String getOtherFileName() {
        return otherFileName;
    }

    public void setOtherFileName(String otherFileName) {
        this.otherFileName = otherFileName;
    }

}
