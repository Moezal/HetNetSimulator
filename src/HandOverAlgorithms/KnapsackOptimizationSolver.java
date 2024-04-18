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

import GlobalOptimization.*;
import java.util.LinkedList;
import scpsolver.constraints.LinearSmallerThanEqualsConstraint;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LinearProgram;

/**
 *
 * @author mohamad
 */
public class KnapsackOptimizationSolver {

    public static double[] KnapsackOptimizationParser(double[] globalCostFunction, double[] usersDataRate, double networkCapacity) {
//        int numberOfNetworks = networkCapacity.length;
//        int numberOfUsers = usersDataRate.length;
//        double equationArray[] = new double[numberOfUsers];
        //fill equation array
//        for (int i = 0; i < numberOfUsers; i++) {
//            equationArray[i] = globalCostFunction[i];
//        }
        
//        LinkedList<double[]> usersConstraint = new LinkedList<double[]>();
//        double[] oneUserConstraint = new double[numberOfUsers * numberOfNetworks];
//        for (int i = 0; i < numberOfUsers; i++) {
//            oneUserConstraint = new double[numberOfUsers * numberOfNetworks];
//            for (int j = i * numberOfNetworks; j < (i * numberOfNetworks + numberOfNetworks); j++) {
//                if (globalCostFunction[i][j] == 0) {
//                    oneUserConstraint[i * numberOfNetworks + j] = 0;
//                } else {
//                oneUserConstraint[j] = 1;
//                }

//            }
//            usersConstraint.add(oneUserConstraint);
//        }
//        System.out.println("number of constraints: " + usersConstraint.size());
//        for (int i = 0; i < usersConstraint.size(); i++) {
//            System.out.print("{ ");
//            for (int j = 0; j < usersConstraint.get(i).length; j++) {
//                System.out.print(usersConstraint.get(i)[j] + " ,");
//            }
//            System.out.println(" }");
//
//        }
//        LinkedList<double[]> NetworksConstraint = new LinkedList<double[]>();
//        double[] oneNetworkConstraint = new double[numberOfUsers];
////        for (int i = 0; i < numberOfNetworks; i++) {
//        oneNetworkConstraint = new double[numberOfUsers];
//        int counter = 0;
//        for (int j = 0; j < numberOfUsers; j++) {
//            oneNetworkConstraint[j] = usersDataRate[counter++];
//        }
//        NetworksConstraint.add(oneNetworkConstraint);

//        }
//        for (int i = 0; i < NetworksConstraint.size(); i++) {
//            System.out.print("{ ");
//            for (int j = 0; j < NetworksConstraint.get(i).length; j++) {
//                System.out.print(NetworksConstraint.get(i)[j] + " ,");
//            }
//            System.out.println(" }");
//
//        }
        LinearProgram lp = new LinearProgram(globalCostFunction);
//        System.out.print("\nequation array: { ");
//        for(int i=0;i<globalCostFunction.length;i++){
//            System.out.print(globalCostFunction[i]+", ");
//        }
//        System.out.println(" }");
//        System.out.print("\nconstraint: { ");
//        for(int i=0;i<usersDataRate.length;i++){
//            System.out.print(usersDataRate[i]+", ");
//        }
//        System.out.println(" }");
//        System.out.println("network capacity "+networkCapacity);
        
        int constraintCounter = 0;
//        for (double[] d : usersConstraint) {
//            System.out.print("\n constraint: {");
//            for (int i = 0; i < d.length; i++) {
//                System.out.print(d[i] + " ,");
//            }
//            System.out.println(" }");
        lp.addConstraint(new LinearSmallerThanEqualsConstraint(usersDataRate, networkCapacity, "constraint" + constraintCounter++));
        
//        }

//        int numberOfNetworksCounter = 0;
//        for (double[] d : NetworksConstraint) {
//            lp.addConstraint(new LinearSmallerThanEqualsConstraint(d, networkCapacity[numberOfNetworksCounter++], "constraint" + constraintCounter++));
//        }
//        System.out.println("lp.getdimension" + lp.getDimension());
        for (int i = 0; i < usersDataRate.length; i++) {
            lp.setBinary(i);
        }
        lp.setMinProblem(false);

//        LinearProgramSolver solver = SolverFactory.getSolver(""+lpsolve.LpSolve.NODE_GREEDYMODE);
//        System.err.println("lp name " + lp.getName());
        LinearProgramSolver solver = SolverFactory.newDefault();//GLPK or LPSOLVE
//        solver.setTimeconstraint(10);
//        System.err.println("getProbName() " + solver.getProblemName());
//        System.err.println("library names " + solver.getLibraryNames()[0]);
//        System.err.println("library names "+solver.getLibraryNames()[1]);
//        System.out.println(lp.convertToCPLEX());
//        solver = SolverFactory.getSolver()
//        lpsolve.LpSolve.
//        solver.setTimeconstraint(100);
        double[] sol = solver.solve(lp);
//        System.err.println("solver name " + solver.getName());;
//        for (int i = 0; i < sol.length; i++) {
//            System.out.println("" + sol[i]);
//        }
//        double[][] solutionDoubleArray = new double[numberOfUsers][numberOfNetworks];
//        int i = 0, j = 0;
//        for (int k = 0; k < sol.length; k++) {
//            if ((k % numberOfNetworks) == 0 && (k != 0)) {
//                i++;
//                j = 0;
//            }
//            solutionDoubleArray[i][j++] = sol[k];
//        }
//        for (i = 0; i < numberOfUsers; i++) {
//            System.out.print("\n{ ");
//            for (j = 0; j < numberOfNetworks; j++) {
//                System.out.print(solutionDoubleArray[i][j] + " ,");
//            }
//            System.out.println(" }");
//        }
        return sol;

    }
}
