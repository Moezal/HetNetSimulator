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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import scpsolver.constraints.LinearBiggerThanEqualsConstraint;
import scpsolver.constraints.LinearSmallerThanEqualsConstraint;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LinearProgram;

/**
 *
 * @author mohamad
 */
public class SimplexHODual {

    public static double[][] globalOptimizationParser(double[][] globalCostFunction, double[] usersDataRate, double[] networkCapacity) {
        int numberOfNetworks = networkCapacity.length;
        int numberOfUsers = usersDataRate.length;
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
        for (int i = 0; i < equationArrayWithoutZeros.size(); i++) {
            equationArrayWithoutZerosArray[i] = equationArrayWithoutZeros.get(i);
        }

        LinkedList<double[]> usersConstraint = new LinkedList<double[]>();
        LinkedList<double[]> usersConstraint2 = new LinkedList<double[]>();
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
        double[] oneUserConstraint2 = new double[numberOfUsers * numberOfNetworks];
        for (int i = 0; i < numberOfUsers * numberOfNetworks; i++) {
            oneUserConstraint2 = new double[numberOfUsers * numberOfNetworks];
            for (int z = 0; z < oneUserConstraint2.length; z++) {
                if (z == i) {
                    oneUserConstraint2[z] = 1;
                } else {
                    oneUserConstraint2[z] = 0;
                }
            }

            usersConstraint2.add(DoubleEliminateElementsAtIndeces(oneUserConstraint2, ZeroIndeces));
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
        for (int i = 0; i < numberOfNetworks; i++) {
            oneNetworkConstraint = new double[numberOfNetworks * numberOfUsers];
            int counter = 0;
            for (int j = i; j < numberOfUsers * numberOfNetworks; j += numberOfNetworks) {
                if (equationArray[j] == 0) {
                    oneNetworkConstraint[j] = usersDataRate[counter] + 1000000;//@9/11/2015 " it should be +100000
                } else {
                    oneNetworkConstraint[j] = usersDataRate[counter];
                }

                counter++;
            }
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
//        LinearProgram lp = new LinearProgram(equationArrayWithoutZerosArray);

        Collection<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
        LinkedList<double[]> constraintsTranspose = new LinkedList<double[]>();
        double[][] constraintsDoubleArray = new double[usersConstraint.size() + NetworksConstraint.size()][usersConstraint.get(1).length];
        for (int i = 0; i < usersConstraint.size(); i++) {
            for (int j = 0; j < usersConstraint.get(1).length; j++) {
                constraintsDoubleArray[i][j] = usersConstraint.get(i)[j];
            }
        }
        for (int i = usersConstraint.size(); i < usersConstraint.size() + NetworksConstraint.size(); i++) {
            for (int j = 0; j < usersConstraint.get(1).length; j++) {
                constraintsDoubleArray[i][j] = NetworksConstraint.get(i - usersConstraint.size())[j];
            }
        }
        double[][] constraintsTransposeDoubleArray = new double[usersConstraint.get(1).length][usersConstraint.size() + NetworksConstraint.size()];
        for (int i = 0; i < usersConstraint.get(1).length; i++) {
            for (int j = 0; j < usersConstraint.size() + NetworksConstraint.size(); j++) {
                constraintsTransposeDoubleArray[i][j] = constraintsDoubleArray[j][i];
            }
        }
        LinkedList<double[]> newConstraintsTranspose = new LinkedList<double[]>();
        double[] newOneConstraintTranspose = new double[usersConstraint.size() + NetworksConstraint.size()];
        for(int i=0;i<usersConstraint.get(1).length;i++){
            newOneConstraintTranspose = new double[usersConstraint.size() + NetworksConstraint.size()];
            for(int j=0;j<usersConstraint.size() + NetworksConstraint.size();j++){
                newOneConstraintTranspose[j]=constraintsTransposeDoubleArray[i][j];
            }
            newConstraintsTranspose.add(newOneConstraintTranspose);
        }
        int constraintCounter = 0;
//        double[] oneConstraintTranspose = new double[usersConstraint.size() + NetworksConstraint.size()];
//        for (int i = 0; i < usersConstraint.get(2).length; i++) {
//            oneConstraintTranspose = new double[usersConstraint.size() + NetworksConstraint.size()];
//            for (int j = 0; j < usersConstraint.size(); j++) {
//                oneConstraintTranspose[j] = usersConstraint.get(j)[i];
//            }
//            for (int j = usersConstraint.size(); j < NetworksConstraint.size() + usersConstraint.size(); j++) {
//                oneConstraintTranspose[j] = NetworksConstraint.get(j - usersConstraint.size())[i];
//            }
//            constraintsTranspose.add(oneConstraintTranspose);
//        }
//        for (int i = 0; i < constraintsTranspose.size(); i++) {
//            constraints.add(new LinearConstraint(constraintsTranspose.get(i), Relationship.GEQ, equationArrayWithoutZerosArray[i]));
//        }
          for (int i = 0; i < newConstraintsTranspose.size(); i++) {
            constraints.add(new LinearConstraint(newConstraintsTranspose.get(i), Relationship.GEQ, equationArrayWithoutZerosArray[i]));
        }
        double[] newEquationArray = new double[NetworksConstraint.size() + usersConstraint.size()];
        for (int i = 0; i < usersConstraint.size(); i++) {
            newEquationArray[i] = 1;
        }
        int numberOfNetworksCounter = 0;
        for (int i = usersConstraint.size(); i < usersConstraint.size() + NetworksConstraint.size(); i++) {
            newEquationArray[i] = networkCapacity[numberOfNetworksCounter++];
        }
        LinearObjectiveFunction f = new LinearObjectiveFunction(newEquationArray, 0);
        SimplexSolver solver = new SimplexSolver();
        PointValuePair solution = solver.optimize(new MaxIter(1000), f, new LinearConstraintSet(constraints),
                GoalType.MINIMIZE, new NonNegativeConstraint(true));
        double[] sol = solution.getPoint();
        for (int i = 0; i < sol.length; i++) {
            System.out.println("" + sol[i]);
        }
        double[] result = new double[equationArray.length];
        int solCounter = 0;
        int networkCounter = 0;
        for (int i = 0; i < equationArray.length; i++) {
            if ((i % numberOfNetworks == 0) && (i != 0)) {
                solCounter++;
                networkCounter = 0;
            }
            if (equationArray[i] == 0) {
                result[i] = -1000;
            } else {
                result[i] = equationArray[i] - sol[solCounter];
                result[i] = result[i] - sol[sol.length - numberOfNetworks + networkCounter];// subtract the multiplier related to the network
            }
            networkCounter++;
        }
        double[] finalResult = new double[result.length];
        for (int i = 0; i < result.length; i += numberOfNetworks) {
            double sum = 0;
            for (int j = 0; j < numberOfNetworks; j++) {
                if (result[i + j] > 0.00000001) {
                    sum += result[i + j];
                }
            }
            for (int j = 0; j < numberOfNetworks; j++) {
                if (result[i + j] < 0.00000001) {
                    finalResult[i + j] = 0;
                } else {
                    finalResult[i + j] = result[i + j] / sum;
                }
            }
        }
        double[][] solutionDoubleArray = new double[numberOfUsers][numberOfNetworks];
        int i = 0, j = 0;
        for (int k = 0; k < finalResult.length; k++) {
            if ((k % numberOfNetworks) == 0 && (k != 0)) {
                i++;
                j = 0;
            }
            solutionDoubleArray[i][j++] = finalResult[k];
        }
//        for (i = 0; i < numberOfUsers; i++) {
//            System.out.print("\n{ ");
//            for (j = 0; j < numberOfNetworks; j++) {
//                System.out.print(solutionDoubleArray[i][j] + " ,");
//            }
//            System.out.println(" }");
//        }
        return solutionDoubleArray;
//        solCounter = 0;
//        int userDatarateCounter = 0;
//        System.out.println("for user " + solCounter + " and has requested datarate: " + usersDataRate[userDatarateCounter++]);
//        for (int i = 0; i < result.length; i++) {
//            if ((i % numberOfNetworks == 0) && (i != 0)) {
//                solCounter++;
//                System.out.println("for user " + solCounter + " and has requested datarate: " + usersDataRate[userDatarateCounter++]);
//
//            }
//            System.out.println("" + finalResult[i]);
//        }
//        try {
//            Thread.sleep(10000);
////        double[] Xsolution = new double[sol.length];
////        for(int i=0;i<sol.length;i++){
////            System.out.println(""+sol[i]);
////        }
////        System.out.println("lp.getdimension" + lp.getDimension());
////        double []lowerBound=new double[equationArrayWithoutZerosArray.length];
////        double []UpperBound=new double[equationArrayWithoutZerosArray.length];
////        for (int i = 0; i < equationArrayWithoutZerosArray.length; i++) {
//////            lp.setBinary(i);
////            lp.setContinous(i);
//////            lowerBound[i]=0;
//////            UpperBound[i]=3;
////        }
//        } catch (InterruptedException ex) {
//            Logger.getLogger(SimplexHODual.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
////        lp.set
////        lp.setLowerbound(lowerBound);
////        lp.setUpperbound(UpperBound);
////        lp.setMinProblem(false);
////        LinearProgramSolver solver = SolverFactory.getSolver(""+lpsolve.LpSolve.NODE_GREEDYMODE);
////        System.err.println("lp name " + lp.getName());
////        LinearProgramSolver solver = SolverFactory.newDefault();//GLPK or LPSOLVE
////        solver.setTimeconstraint(10);
////        System.err.println("getProbName() " + solver.getProblemName());
////        System.err.println("library names " + solver.getLibraryNames()[0]);
////        System.err.println("library names "+solver.getLibraryNames()[1]);
////        System.out.println(lp.convertToCPLEX());
////        solver = SolverFactory.getSolver()
////        lpsolve.LpSolve.
////        solver.setTimeconstraint(100);
////        double[] sol = solver.solve(lp);
//        double[][] A_Matrix = new double[usersConstraint.size() + NetworksConstraint.size()][usersConstraint.get(2).length];
//        for (int i = 0; i < usersConstraint.size(); i++) {
//            A_Matrix[i] = usersConstraint.get(i);
//        }
//        for (int i = usersConstraint.size(); i < NetworksConstraint.size() + usersConstraint.size(); i++) {
//            A_Matrix[i] = NetworksConstraint.get(i - usersConstraint.size());
//        }
////        for(int i=0;i<usersConstraint.size() + NetworksConstraint.size();i++){
////            System.out.print("i="+i+" : ");
////            for(int j=0;j<usersConstraint.get(2).length;j++){
////                System.out.print(" "+A_Matrix[i][j]);
////            }
////            System.out.println("");
////        }
//        double[] lambdaTA = new double[usersConstraint.get(2).length];
//        double sum = 0;
//        for (int i = 0; i < usersConstraint.get(2).length; i++) {
//            sum = 0;
//            for (int j = 0; j < usersConstraint.size() + NetworksConstraint.size(); j++) {
////                System.out.println("i="+i+" j="+j+" total variables="+usersConstraint.size() * NetworksConstraint.size());
//                sum += sol[j] * A_Matrix[j][i];
//            }
//            lambdaTA[i] = sum;
//        }
//        double[] c_minus_lambdaTA = new double[usersConstraint.get(2).length];
//
//        for (int i = 0; i < usersConstraint.get(2).length; i++) {
//            c_minus_lambdaTA[i] = equationArrayWithoutZerosArray[i] - lambdaTA[i];
//        }
////        for(int i=0;i<c_minus_lambdaTA.length;i++){
////        
////            System.out.println(""+c_minus_lambdaTA[i]);
////        }
//        RealMatrix coefficients = new Array2DRowRealMatrix(c_minus_lambdaTA);
//        DecompositionSolver solver2 = new LUDecomposition(coefficients).getSolver();
//        double[] zeroArray = new double[usersConstraint.get(2).length];
//        for (int i = 0; i < usersConstraint.get(2).length; i++) {
//            zeroArray[i] = 0;
//        }
//        RealVector constants = new ArrayRealVector(zeroArray, false);
//        RealVector solution2 = solver2.solve(constants);
//        for (int i = 0; i < solution2.getDimension(); i++) {
//            System.out.println("" + solution2.getEntry(i));
//        }
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(SimplexHODual.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        for (int i = 0; i < sol.length; i++) {
////            System.out.println("sol_"+i+" = "+sol[i]);
//            if (sol[i] > 0.99999) {
//                sol[i] = 1;
//            } else if (sol[i] < 0.00001) {
//                sol[i] = 0;
//            }
//        }
//
//        sol = DoubleAddZeroElementsAtIndeces(sol, ZeroIndeces, equationArray);
////        System.err.println("solver name " + solver.getName());;
////        for (int i = 0; i < sol.length; i++) {
////            System.out.print(" " + sol[i]);
////        }
////        System.out.println("");
//        double[][] solutionDoubleArray = new double[numberOfUsers][numberOfNetworks];
//        int i = 0, j = 0;
//        for (int k = 0; k < sol.length; k++) {
//            if ((k % numberOfNetworks) == 0 && (k != 0)) {
//                i++;
//                j = 0;
//            }
//            solutionDoubleArray[i][j++] = sol[k];
//        }
////        for (i = 0; i < numberOfUsers; i++) {
////            System.out.print("\n{ ");
////            for (j = 0; j < numberOfNetworks; j++) {
////                System.out.print(solutionDoubleArray[i][j] + " ,");
////            }
////            System.out.println(" }");
////        }
//        return solutionDoubleArray;

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

    public static int[] IntegerEliminateElementsAtIndeces(int[] array, LinkedList<Integer> indecesList) {
        int[] result = new int[array.length - indecesList.size()];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (!indecesList.contains(i)) {
                result[j++] = array[i];
            }
        }
        return result;
    }

    public static double[] DoubleAddZeroElementsAtIndeces(double[] array, LinkedList<Integer> indecesList, double[] equationArrayOriginal) {
        double[] result = new double[equationArrayOriginal.length];
        int j = 0;
        for (int i = 0; i < equationArrayOriginal.length; i++) {
            if (equationArrayOriginal[i] == 0) {
                result[i] = 0;
            } else {
                result[i] = array[j++];
            }
        }
        return result;
    }
}
