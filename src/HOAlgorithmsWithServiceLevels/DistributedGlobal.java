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
package HOAlgorithmsWithServiceLevels;

import HandOverAlgorithms.*;
import Calulation.CalculateSignalStrength;
import Calulation.NetworkCostFunction;
import Calulation.NetworkCostFunctionSmaller;
import Knapsack.Item;
import Networks_Package.Network;
import Peers_Package.Peer;
import SubGradient.CentralizedController;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author mohamad
 */
public class DistributedGlobal {

    public static void startHandingOver(List<Peer> Peers, List<Network> Networks, boolean isGreedy, int subgradientIterations) {
//        Collections.shuffle(Peers, Simulator.Simulator.sp.randGenerator);
//        Collections.shuffle(Networks, Simulator.Simulator.sp.randGenerator);
        for (Network n : Networks) {
            n.setUsed_capacity(0);
            n.setConnectedPeers(new LinkedList<Peer>());
            n.setPeersRequestingConnection(new LinkedList<Peer>());
            n.setTotalNumberOfUsersInList(1);
            n.setNumberOfRequestListsReceived(1);
        }
        double globalMinPC = 0, globalMaxRss = 0;

        for (Peer p123 : Peers) {
            p123.setConnected(false);
            p123.setConnectionFailed(false);
            p123.setCurrentConnectedNetwork(0);
            if (globalMinPC == 0) {
                globalMinPC = p123.getMin_available_pc();
            }
            if (globalMaxRss == 0) {
                globalMaxRss = p123.getMax_available_s();
            }
            if (p123.getMin_available_pc() < globalMinPC) {
                globalMinPC = p123.getMin_available_pc();
            }
            if (p123.getMax_available_s() > globalMaxRss) {
                globalMaxRss = p123.getMax_available_s();
            }
        }
        double processingTime = 0;
        long HOEProcessingTimeStart = System.nanoTime();
        List<Peer> peersServiceLevel = new LinkedList<Peer>();
        int SLRange;
        if (Simulator.Simulator.isSL) {
            SLRange = 3;
        } else {
            SLRange = 1;
        }
        for (int ed = 0; ed < SLRange; ed++) {
            peersServiceLevel = new LinkedList<Peer>();
            for (Peer ped : Peers) {
                if (ped.getService_level() == ed) {
                    peersServiceLevel.add(ped);
                }
            }
//            Collections.shuffle(peersServiceLevel, Simulator.Simulator.sp.randGenerator);
//            Collections.shuffle(Networks, Simulator.Simulator.sp.randGenerator);
            double[][] globalCostFunction = new double[peersServiceLevel.size()][Networks.size()];
            for (int i = 0; i < peersServiceLevel.size(); i++) {
                for (int j = 0; j < Networks.size(); j++) {
                    if (peersServiceLevel.get(i).isNetworkAvailable(Networks.get(j))) {
                        globalCostFunction[i][j] = NetworkCostFunctionSmaller.calculate_Network_cost_function(peersServiceLevel.get(i), Networks.get(j), globalMaxRss, globalMinPC);
//                    System.err.println(""+NetworkCostFunctionSmaller.calculate_Network_cost_function(Peers.get(i), Networks.get(j), globalMaxRss, globalMinPC));
                    } else {
                        globalCostFunction[i][j] = 0;
                    }
                }
            }
//        System.out.print("\n[ ");
//        for (int i = 0; i < Peers.size(); i++) {
////            System.out.print("\n{ ");
//            for (int j = 0; j < Networks.size(); j++) {
//                System.out.print(globalCostFunction[i][j] + " ,");
//            }
//            System.out.println("");
//        }
//        System.out.println(" ]");
            double[] networkCapacities = new double[Networks.size()];
            for (int i = 0; i < Networks.size(); i++) {
                networkCapacities[i] = Networks.get(i).getFreeCapacity();
            }
            double[] usersDatarate = new double[peersServiceLevel.size()];
            for (int i = 0; i < peersServiceLevel.size(); i++) {
                usersDatarate[i] = peersServiceLevel.get(i).getDatarate();
            }

//        double[][] solution = GlobalOptimization.GlobalOptimizationSolver.globalOptimizationParser(globalCostFunction, usersDatarate, networkCapacities);
            double[][] solution = null;
            boolean isSubGradient = true;
            double maxLowerBound = 0;
            double[][] maxLBResult = null;

            if (isSubGradient) {
                int totalIterations = 0;
//            for (int x = 0; x < 1; x++) {
//                System.out.println(""+x);
                double[] lambda = new double[peersServiceLevel.size()];
                for (int i = 0; i < peersServiceLevel.size(); i++) {
                    lambda[i] = 1;
                }
                boolean stoppingCriteria = false;

                int MAX_ITERATIONS = subgradientIterations;
                System.out.println("max iterations = " + MAX_ITERATIONS);
                double beta = 2;
                double MIN_BETA = 0.005;
                int previousUpperBoundInt = 0;
                double previousUpperBoundDouble = 0;
                int consecutiveUnchangedUBcounter = 0;
                int MAX_consecutiveUnchangedUBcounter = 20;
                double up = 0;

                while (!stoppingCriteria) {
                    totalIterations++;
                    System.out.println("iteration: " + totalIterations);
                    CentralizedController controller = new CentralizedController(globalCostFunction, peersServiceLevel.size(), networkCapacities, peersServiceLevel, Networks, lambda);
                    if (isGreedy) {
                        solution = controller.getFeasibleSolutionGreedy();
                    } else {
                        solution = controller.getFeasibleSolutionApproximation();
                    }
                    double[][] relaxedResults = controller.getResult();
                    double upperBound = 0;
                    double lowerBound = 0;
                    for (int i = 0; i < peersServiceLevel.size(); i++) {
                        for (int j = 0; j < Networks.size(); j++) {
                            if (relaxedResults[i][j] == 1) {
                                upperBound += globalCostFunction[i][j];
                            }
                        }
                    }
                    System.out.println("ub: " + upperBound);
                    if (previousUpperBoundInt == (int) upperBound) {
                        System.out.println("same upper bound");
                        consecutiveUnchangedUBcounter++;
                        if (consecutiveUnchangedUBcounter == MAX_consecutiveUnchangedUBcounter) {
                            beta = beta / 2;
                            consecutiveUnchangedUBcounter = 0;
                            System.out.println("beta changed due to consecutive upperbounds, beta=" + beta);
                        }
                    } else {
                        consecutiveUnchangedUBcounter = 0;
                    }
                    previousUpperBoundInt = (int) upperBound;
//                System.out.println("pre:"+previousUpperBoundDouble+"\nnow:"+upperBound);
                    previousUpperBoundDouble = upperBound;

                    for (int i = 0; i < peersServiceLevel.size(); i++) {
                        for (int j = 0; j < Networks.size(); j++) {
                            if (solution[i][j] == 1) {
                                lowerBound += globalCostFunction[i][j];

                            }
                        }
                    }
                    if (lowerBound > maxLowerBound) {
                        maxLowerBound = lowerBound;
                        maxLBResult = solution;
                    }
                    System.out.println("lb: " + lowerBound);
                    if (upperBound - lowerBound <= 1) {
                        System.out.println("Optimal Solution found: ub-lb=" + (upperBound - lowerBound));
                        stoppingCriteria = true;
                    } else if (totalIterations == MAX_ITERATIONS) {
                        System.out.println("Total iterations reached: " + MAX_ITERATIONS);
                        stoppingCriteria = true;
                    } else if (beta <= MIN_BETA) {
                        System.out.println("Minimum Beta reached: " + MIN_BETA);
                        stoppingCriteria = true;
                    } else {
                        double globalSubGradientDirection = 0;
                        int[] subgradients = new int[peersServiceLevel.size()];
                        int subGradientDirection = 0;
                        for (int i = 0; i < peersServiceLevel.size(); i++) {
                            int numberOfConnectedNetworks = 0;
                            for (int j = 0; j < Networks.size(); j++) {
                                if (relaxedResults[i][j] == 1) {
                                    numberOfConnectedNetworks++;
                                }
                            }
                            subGradientDirection = numberOfConnectedNetworks - 1;
                            subgradients[i] = subGradientDirection;
                            globalSubGradientDirection += Math.pow(subGradientDirection, 2);
                        }
//                        System.out.println("subgradient: "+subGradientDirection);
                        double theta = beta * (upperBound - lowerBound) / (globalSubGradientDirection);
                        for (int i = 0; i < peersServiceLevel.size(); i++) {
//                            System.out.println("beta: " + beta + "\nub: " + upperBound + "\nlb: " + lowerBound + "\nub-lb: " + (upperBound - lowerBound) + "\nsubgradient: " + subGradientDirection + "\ntheta: " + theta);
//                                System.out.println("lambda before: " + lambda[i]);
                            if (theta * subgradients[i] + lambda[i] > 0) {
                                lambda[i] = lambda[i] + theta * subgradients[i];
                            } else {
                                lambda[i] = 0;
                            }

//                                System.out.println("lambda after: " + lambda[i]);
                        }

                    }
                }
                Simulator.Simulator.stat.DistributedIterationCount.add((double) totalIterations);
            } else {//not subgradient
                double[] lambda = new double[peersServiceLevel.size()];
                for (int i = 0; i < peersServiceLevel.size(); i++) {
                    lambda[i] = 0;
                }
                CentralizedController controller = new CentralizedController(globalCostFunction, peersServiceLevel.size(), networkCapacities, peersServiceLevel, Networks, lambda);
                if (isGreedy) {
                    solution = controller.getFeasibleSolutionGreedy();
                } else {
                    solution = controller.getFeasibleSolutionApproximation();
                }
            }

            solution = maxLBResult;
//            
            //            }
            //            long HOEProcessingTimeEnd = System.nanoTime();
            //            double processing_time = (HOEProcessingTimeEnd - HOEProcessingTimeStart) / (double) 1000000;
            //            System.out.println("Waiting for scan "+processing_time);
            //            Scanner scan = new Scanner(System.in);
            //            String s = scan.next();
            //            double[][] solution = GlobalOptimization.GlobalOptimizationSolverREmoveZeros.globalOptimizationParser(globalCostFunction, usersDatarate, networkCapacities, peersServiceLevel, Networks);
            Peer currentPeer;
            Network currentNetwork;
            for (int i = 0; i < peersServiceLevel.size(); i++) {
                boolean connected = false;
                currentPeer = peersServiceLevel.get(i);
                for (int j = 0; j < Networks.size(); j++) {
                    if (solution[i][j] == 1) {
                        if (globalCostFunction[i][j] == 0) {
                            System.err.println("choosed network but global cost function ==0");
                            System.exit(-1);

                        }
//                    System.out.println("user " + Peers.get(i).getPeerID() + " connected to network: " + Networks.get(j).getName()+"is network available: "+Peers.get(i).isNetworkAvailable(Networks.get(j)));
                        if (connected) {
                            System.err.println("user cost function to the second network he is connected to  " + currentPeer.getNetworkRank(Networks.get(j)) + " value in global cost function " + globalCostFunction[i][j]);
                            System.err.println("User is assigned to a second network that is not available in GlobalOptimizationHandover.java");
                            System.err.println("user connected to 2 networks at the same time");
                            System.exit(-1);
                        } else {

                            currentNetwork = Networks.get(j);
                            if (currentPeer.isNetworkAvailable(currentNetwork)) {
//                            System.err.println("user cost function to the first network he is connected to  " + currentPeer.getNetworkRank(Networks.get(j)) + " value in global cost function " + globalCostFunction[i][j]);

                                connected = true;
                                currentPeer.setConnected(true);
                                currentPeer.setConnectedNetwork(currentNetwork);
                                currentPeer.setConnected_network_rank(currentPeer.getNetworkRank(currentNetwork));
                                currentPeer.setCurrentConnectedNetwork(currentNetwork.getId());
                                currentNetwork.setUsed_capacity(currentNetwork.getUsed_capacity() + currentPeer.getRequestedResources(currentNetwork.getId()));
                                currentNetwork.getConnectedPeers().add(currentPeer);
                            } else {
                                System.err.println("user cost function to the network that is not available " + currentPeer.getNetworkRank(currentNetwork) + " value in global cost function " + globalCostFunction[i][j]);
                                System.err.println("User is assigned to a network that is not available in GlobalOptimizationHandover.java");
                                System.exit(-1);
                            }

                        }
                    }
                }
                if (!connected) {
                    currentPeer.setConnectionFailed(true);
                }
            }
        }
        long HOEProcessingTimeEnd = System.nanoTime();
        processingTime += (HOEProcessingTimeEnd - HOEProcessingTimeStart) / (double) 1000000;
        Simulator.Simulator.stat.processingTime.add(processingTime);
        for (Network nx : Networks) {
//            System.out.println("network utilization" + (double) (nx.getUsed_capacity() / (double) nx.getCapacity()));
            if (nx.getCapacity() < nx.getUsed_capacity()) {

                System.err.println("Error Used capacity > capacity\n "
                        + "Error Used capacity > capacity\n"
                        + "Error Used capacity > capacity\n"
                        + "Error Used capacity > capacity\n"
                        + "Error Used capacity > capacity\n"
                        + "Error Used capacity > capacity\n");
                System.exit(-1);
            }
        }
        for (Peer px : Peers) {
            if (px.isConnected()) {
                if (!px.isNetworkAvailable(px.getConnectedNetwork())) {
                    System.err.println("Error connected to un available network ");
                    System.exit(-1);
                }
            }
        }
/////////////////////////////
    }

}
