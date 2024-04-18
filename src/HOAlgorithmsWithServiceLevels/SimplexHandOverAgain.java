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
import Peers_Package.ProphitWeightRatio;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class SimplexHandOverAgain {

    public static void startHandingOver(List<Peer> Peers, List<Network> Networks) {
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
        List<Peer> peersServiceLevelMain = new LinkedList<Peer>();
//        System.out.println("ne iteration");
        for (int ed = 0; ed < 3; ed++) {
            peersServiceLevelMain = new LinkedList<Peer>();
            peersServiceLevel = new LinkedList<Peer>();
            for (Peer ped : Peers) {
                if (ped.getService_level() == ed) {
                    peersServiceLevelMain.add(ped);
                }
            }
            peersServiceLevel.addAll(peersServiceLevelMain);
            List<Peer> peersToRemove = new LinkedList<Peer>();

            while (peersServiceLevel.size() != 0) {

//            Collections.shuffle(peersServiceLevel, Simulator.Simulator.sp.randGenerator);
//            Collections.shuffle(Networks, Simulator.Simulator.sp.randGenerator);
                double[][] globalCostFunction = new double[peersServiceLevel.size()][Networks.size()];
                for (int i = 0; i < peersServiceLevel.size(); i++) {
                    int unAvailableNetworksCounter = 0;
                    for (int j = 0; j < Networks.size(); j++) {
                        if (peersServiceLevel.get(i).getRequestedResources(Networks.get(j).getId()) <= Networks.get(j).getFreeCapacity()) {
                            if (peersServiceLevel.get(i).isNetworkAvailable(Networks.get(j))) {
                                globalCostFunction[i][j] = NetworkCostFunctionSmaller.calculate_Network_cost_function(peersServiceLevel.get(i), Networks.get(j), globalMaxRss, globalMinPC);
//                    System.err.println(""+NetworkCostFunctionSmaller.calculate_Network_cost_function(Peers.get(i), Networks.get(j), globalMaxRss, globalMinPC));
                            } else {
                                unAvailableNetworksCounter++;
                                globalCostFunction[i][j] = 0;
                            }
                        } else {
                            unAvailableNetworksCounter++;
                            globalCostFunction[i][j] = 0;
                        }
                    }
                    if (unAvailableNetworksCounter == Networks.size()) {
                        peersToRemove.add(peersServiceLevel.get(i));
                        peersServiceLevel.get(i).setConnectionFailed(true);
                    }
                }
//                if (peersToRemove.size() == peersServiceLevel.size()) {
//                    peersToRemove = new LinkedList<Peer>();
//                    peersServiceLevel = new LinkedList<Peer>();
//                    break;
//                }
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
//            double[][] solution = GlobalOptimization.GlobalOptimizationSolverREmoveZeros.globalOptimizationParser(globalCostFunction, usersDatarate, networkCapacities);
                double[][] solution = GlobalOptimization.SimplexHO2.globalOptimizationParser(globalCostFunction, usersDatarate, networkCapacities, peersServiceLevel, Networks);
                Peer currentPeer;
                Network currentNetwork;
                for (int i = 0; i < peersServiceLevel.size(); i++) {
                    currentPeer = peersServiceLevel.get(i);
                    for (int j = 0; j < Networks.size(); j++) {
                        if (solution[i][j] == 1) {
                            if (globalCostFunction[i][j] == 0) {
                                System.err.println("choosed network but global cost function ==0");
                                System.exit(-1);

                            }
//                    System.out.println("user " + Peers.get(i).getPeerID() + " connected to network: " + Networks.get(j).getName()+"is network available: "+Peers.get(i).isNetworkAvailable(Networks.get(j)));
                            if (currentPeer.isConnected()) {
                                System.err.println("Current j is:" + j + " solution column:");
                                for (int y = 0; y < Networks.size(); y++) {
                                    System.err.print(" -" + solution[i][y]);
                                }
                                System.err.println("");
                                System.err.println("user cost function to the second network he is connected to  " + currentPeer.getNetworkRank(Networks.get(j)) + " value in global cost function " + globalCostFunction[i][j]);
                                System.err.println("User is assigned to a second network that is not available in GlobalOptimizationHandover.java");
                                System.err.println("user connected to 2 networks at the same time");
                                System.exit(-1);
                            } else {

                                currentNetwork = Networks.get(j);
                                if (currentPeer.isNetworkAvailable(currentNetwork)) {
//                            System.err.println("user cost function to the first network he is connected to  " + currentPeer.getNetworkRank(Networks.get(j)) + " value in global cost function " + globalCostFunction[i][j]);
                                    currentPeer.setConnected(true);
                                    currentPeer.setConnectedNetwork(currentNetwork);
                                    currentPeer.setConnected_network_rank(currentPeer.getNetworkRank(currentNetwork));
                                    currentPeer.setCurrentConnectedNetwork(currentNetwork.getId());
                                    currentNetwork.setUsed_capacity(currentNetwork.getUsed_capacity() + currentPeer.getRequestedResources(currentNetwork.getId()));
                                    currentNetwork.getConnectedPeers().add(currentPeer);
                                    peersToRemove.add(currentPeer);
                                } else {
                                    System.err.println("user cost function to the network that is not available " + currentPeer.getNetworkRank(currentNetwork) + " value in global cost function " + globalCostFunction[i][j]);
                                    System.err.println("User is assigned to a network that is not available in GlobalOptimizationHandover.java");
                                    System.exit(-1);
                                }

                            }
                        }
                    }
                }
                List<Peer> peersToBeAdded = new LinkedList<Peer>();

                for (Peer pEnd : peersServiceLevel) {
                    boolean toBeRemoved = false;
                    for (Peer pToBeRemoved : peersToRemove) {
                        if (pEnd.getPeerID() == pToBeRemoved.getPeerID()) {
                            toBeRemoved = true;
                            break;
                        }
                    }
                    if (!toBeRemoved) {
                        peersToBeAdded.add(pEnd);
                    }
                }
                peersToRemove = new LinkedList<Peer>();
                peersServiceLevel = new LinkedList<Peer>();
                peersServiceLevel.addAll(peersToBeAdded);
//                System.out.println("simplexEndWhile" + peersServiceLevel.size());

            }
//            System.out.println("After while");
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
