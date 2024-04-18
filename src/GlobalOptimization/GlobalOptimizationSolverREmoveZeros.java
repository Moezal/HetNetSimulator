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
package GlobalOptimization;

import Networks_Package.Network;
import Peers_Package.Peer;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import scpsolver.constraints.LinearBiggerThanEqualsConstraint;
import scpsolver.constraints.LinearSmallerThanEqualsConstraint;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LinearProgram;

/**
 *
 * @author mohamad
 */
public class GlobalOptimizationSolverREmoveZeros {

    public static double[][] globalOptimizationParser(double[][] globalCostFunction, double[] usersDataRate, double[] networkCapacity,List<Peer> peersServiceLevel,List<Network> Networks) {
        int numberOfNetworks = networkCapacity.length;
        int numberOfUsers = usersDataRate.length;
//        System.err.println("number of networks = "+numberOfNetworks + " number of users = "+numberOfUsers);
        double equationArray[] = new double[numberOfUsers * numberOfNetworks];
        //fill equation array
        for (int i = 0; i < numberOfUsers; i++) {
            for (int j = 0; j < numberOfNetworks; j++) {
                equationArray[i * numberOfNetworks + j] = globalCostFunction[i][j];
            }
        }
        LinkedList<Double> equationArrayWithoutZeros = new LinkedList<Double>();
        LinkedList<Integer> ZeroIndeces = new LinkedList<Integer>();
        for (int i = 0; i < numberOfUsers * numberOfNetworks; i++) {
            if (equationArray[i] == 0) {
                ZeroIndeces.add(i);
            } else {
                equationArrayWithoutZeros.add(equationArray[i]);
            }
        }
        double[] equationArrayWithoutZerosArray = new double[equationArrayWithoutZeros.size()];
        for(int i=0;i<equationArrayWithoutZeros.size();i++){
            equationArrayWithoutZerosArray[i]=equationArrayWithoutZeros.get(i);
        }

        LinkedList<double[]> usersConstraint = new LinkedList<double[]>();
        double[] oneUserConstraint = new double[numberOfUsers * numberOfNetworks];
        for (int i = 0; i < numberOfUsers; i++) {
            oneUserConstraint = new double[numberOfUsers * numberOfNetworks];
//            for (int z = 0; z < oneUserConstraint.length; z++) {
//                oneUserConstraint[z] = 1;
//            }
            for (int j = i * numberOfNetworks; j < (i * numberOfNetworks + numberOfNetworks); j++) {
                if (globalCostFunction[i][j - i * numberOfNetworks] == 0) {
                    oneUserConstraint[j] = 0;//@9/11/2015 " it should be =0
                } else {
                    oneUserConstraint[j] = 1;
                }

            }
            usersConstraint.add(DoubleEliminateElementsAtIndeces(oneUserConstraint, ZeroIndeces));
        }

//        System.out.println("number of constraints: " + usersConstraint.size());
//        for (int i = 0; i < usersConstraint.size(); i++) {
//            System.out.print("{ ");
//            for (int j = 0; j < usersConstraint.get(i).length; j++) {
//                System.out.print(usersConstraint.get(i)[j] + " ,");
//            }
//            System.out.println(" }");
//
//        }
        LinkedList<double[]> NetworksConstraint = new LinkedList<double[]>();
        double[] oneNetworkConstraint = new double[numberOfNetworks * numberOfUsers];
//        System.err.println("printing"+ Simulator.Simulator.sp.current_simulation_time);
        
        for (int i = 0; i < numberOfNetworks; i++) {
            oneNetworkConstraint = new double[numberOfNetworks * numberOfUsers];
            int counter = 0;
            for (int j = i; j < numberOfUsers * numberOfNetworks; j += numberOfNetworks) {
                if (equationArray[j] == 0) {
                    oneNetworkConstraint[j] = usersDataRate[counter] + 1000000;//@9/11/2015 " it should be +100000
                } else {
                    oneNetworkConstraint[j] = peersServiceLevel.get(counter).getRequestedResources(Networks.get(i).getId());
//                oneNetworkConstraint[j]= usersDataRate[counter];
                }

                counter++;
            }
//            System.err.println("");
//            for(int x=0;x<oneNetworkConstraint.length;x++){
//                System.err.print(" "+oneNetworkConstraint[x]);
//            }
            NetworksConstraint.add(DoubleEliminateElementsAtIndeces(oneNetworkConstraint, ZeroIndeces));

        }
//        for (int i = 0; i < NetworksConstraint.size(); i++) {
//            System.out.print("{ ");
//            for (int j = 0; j < NetworksConstraint.get(i).length; j++) {
//                System.out.print(NetworksConstraint.get(i)[j] + " ,");
//            }
//            System.out.println(" }");
//
//        }
        LinearProgram lp = new LinearProgram(equationArrayWithoutZerosArray);
        
        int constraintCounter = 0;
        for (double[] d : usersConstraint) {
//            System.out.print("\n constraint: {");
//            for (int i = 0; i < d.length; i++) {
//                System.out.print(d[i] + " ,");
//            }
//            System.out.println(" }");
            lp.addConstraint(new LinearSmallerThanEqualsConstraint(d, 1.0, "constraint" + constraintCounter++));
//            lp.addConstraint(new LinearBiggerThanEqualsConstraint(d, 0.0, "constraint" + constraintCounter++));
        }

        int numberOfNetworksCounter = 0;
        for (double[] d : NetworksConstraint) {
            lp.addConstraint(new LinearSmallerThanEqualsConstraint(d, networkCapacity[numberOfNetworksCounter++], "constraint" + constraintCounter++));
        }
//        System.out.println("lp.getdimension" + lp.getDimension());
//        double []lowerBound=new double[equationArrayWithoutZerosArray.length];
//        double []UpperBound=new double[equationArrayWithoutZerosArray.length];
        for (int i = 0; i < equationArrayWithoutZerosArray.length; i++) {
            lp.setBinary(i);
//            lp.setContinous(i);
//            lowerBound[i]=0;
//            UpperBound[i]=3;
        }
        
//        lp.set
//        lp.setLowerbound(lowerBound);
//        lp.setUpperbound(UpperBound);
        lp.setMinProblem(false);

//        LinearProgramSolver solver = SolverFactory.getSolver(""+lpsolve.LpSolve.NODE_GREEDYMODE);
        System.err.println("lp name " + lp.getName());

        LinearProgramSolver solver = SolverFactory.newDefault();//GLPK or LPSOLVE
//        solver.setTimeconstraint(10);
//        System.err.println("getProbName() " + solver.getProblemName());
        System.err.println("library names " + solver.getLibraryNames()[0]);
//        System.err.println("library names "+solver.getLibraryNames()[1]);
//        System.out.println(lp.convertToCPLEX());
//        solver = SolverFactory.getSolver()
//        lpsolve.LpSolve.
//        solver.setTimeconstraint(100);
        double[] sol = solver.solve(lp);
        sol=DoubleAddZeroElementsAtIndeces(sol, ZeroIndeces, equationArray);
        System.err.println("solver name " + solver.getName());;
//        for (int i = 0; i < sol.length; i++) {
//            System.out.print(" " + sol[i]);
//        }
//        System.out.println("");
        double[][] solutionDoubleArray = new double[numberOfUsers][numberOfNetworks];
        int i = 0, j = 0;
        for (int k = 0; k < sol.length; k++) {
            if ((k % numberOfNetworks) == 0 && (k != 0)) {
                i++;
                j = 0;
            }
            solutionDoubleArray[i][j++] = sol[k];
        }
//        for (i = 0; i < numberOfUsers; i++) {
//            System.out.print("\n{ ");
//            for (j = 0; j < numberOfNetworks; j++) {
//                System.out.print(solutionDoubleArray[i][j] + " ,");
//            }
//            System.out.println(" }");
//        }
        return solutionDoubleArray;

    }

    public static double[] DoubleEliminateElementsAtIndeces(double[] array, LinkedList<Integer> indecesList) {
        double[] result = new double[array.length - indecesList.size()];
        int j = 0;
        for (int i = 0; i < array.length; i++) { 
            if (!indecesList.contains(i)) {
                result[j++] = array[i];
            }
        }
        return result;
    }

    public static  int[] IntegerEliminateElementsAtIndeces(int[] array, LinkedList<Integer> indecesList) {
        int[] result = new int[array.length - indecesList.size()];
        int j = 0;
        for (int i = 0; i < array.length; i++) {  
            if (!indecesList.contains(i)) {
                result[j++] = array[i];
            }
        }
        return result;
    }
    public  static double[] DoubleAddZeroElementsAtIndeces(double[] array, LinkedList<Integer> indecesList, double[] equationArrayOriginal) {
       
        double[] result = new double[equationArrayOriginal.length];
       
        int j=0;
        for (int i = 0; i < equationArrayOriginal.length; i++) {
            if (equationArrayOriginal[i]==0) {
                result[i]=0;
            }else{
                result[i]=array[j++];
            }
        }
//         System.out.println("gotValues");
        return result;
    }
}
