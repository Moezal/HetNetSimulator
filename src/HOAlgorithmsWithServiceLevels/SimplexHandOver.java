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
public class SimplexHandOver {

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
        for (int ed = 0; ed < 3; ed++) {
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
//            double[][] solution = GlobalOptimization.GlobalOptimizationSolverREmoveZeros.globalOptimizationParser(globalCostFunction, usersDatarate, networkCapacities);
            double[][] solution = GlobalOptimization.SimplexHO.globalOptimizationParser(globalCostFunction, usersDatarate, networkCapacities);
            Peer currentPeer;
            Network currentNetwork;
            List<Integer> unconnectedPeers = new LinkedList<Integer>();
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
                                currentNetwork.setUsed_capacity(currentNetwork.getUsed_capacity() + currentPeer.getDatarate());
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
                    unconnectedPeers.add(i);
                    currentPeer.setConnectionFailed(true);
                }
            }
            double maxX = 0;
            int iMaxX = -1;
            int jMaxX = -1;
            boolean allUnConnectedPeersHaveAllXequalZero = false;
            while (!allUnConnectedPeersHaveAllXequalZero) {
                for (int i : unconnectedPeers) {
                    if (!peersServiceLevel.get(i).isConnected()) {
                        for (int j = 0; j < Networks.size(); j++) {
                            if (peersServiceLevel.get(i).getDatarate() > Networks.get(j).getFreeCapacity()) {
                                solution[i][j] = 0;
                            } else if (solution[i][j] > maxX) {
                                maxX = solution[i][j];
                                iMaxX = i;
                                jMaxX = j;
                            }
                        }
                    }
                }
                if (iMaxX == -1 || jMaxX == -1) {
                    allUnConnectedPeersHaveAllXequalZero = true;
                } else if (maxX != 0) {
                    System.out.println("added maxX: " + maxX);
                    peersServiceLevel.get(iMaxX).setConnectionFailed(false);
                    peersServiceLevel.get(iMaxX).setConnected(true);
                    peersServiceLevel.get(iMaxX).setConnectedNetwork(Networks.get(jMaxX));
                    peersServiceLevel.get(iMaxX).setConnected_network_rank(peersServiceLevel.get(iMaxX).getNetworkRank(Networks.get(jMaxX)));
                    peersServiceLevel.get(iMaxX).setCurrentConnectedNetwork(Networks.get(jMaxX).getId());
                    Networks.get(jMaxX).setUsed_capacity(Networks.get(jMaxX).getUsed_capacity() + peersServiceLevel.get(iMaxX).getDatarate());
                    Networks.get(jMaxX).getConnectedPeers().add(peersServiceLevel.get(iMaxX));
                }
                maxX = 0;
                iMaxX = -1;
                jMaxX = -1;
            }

            //greedy heuristic part
            List<Peer> UnconnectedPeersList = new LinkedList<Peer>();
            for (Peer px : peersServiceLevel) {
                if (!px.isConnected()) {
                    UnconnectedPeersList.add(px);
                }
            }
            List<ProphitWeightRatio> pwr = new LinkedList<ProphitWeightRatio>();
            for (int i = 0; i < peersServiceLevel.size(); i++) {
                for (int j = 0; j < Networks.size(); j++) {
                    if (peersServiceLevel.get(i).isNetworkAvailable(Networks.get(j))) {
                        if (globalCostFunction[i][j] != 0) {
                            ProphitWeightRatio Pnow = new ProphitWeightRatio(globalCostFunction[i][j], peersServiceLevel.get(i));
                            Pnow.setNetowrkProfitRatio(Networks.get(j));
                            pwr.add(Pnow);
                        } else {
                            System.err.println("something wrong");
                        }
                    }

                }
            }
            Collections.sort(pwr);

            for (ProphitWeightRatio pwrNow : pwr) {
//                System.out.println("pwr ratio: "+ pwrNow.getRatio() + "     pwr rate: "+pwrNow.getPeer().getDatarate());
                Peer pwrPeer = pwrNow.getPeer();

                if (!pwrPeer.isConnected()) {
                    Network pwrNetwork = pwrNow.getNetowrkProfitRatio();
                    if (pwrNetwork.getFreeCapacity() >= pwrPeer.getDatarate()) {
                        pwrPeer.setConnectionFailed(false);
                        pwrPeer.setConnected(true);
                        pwrPeer.setConnectedNetwork(pwrNetwork);
                        pwrPeer.setConnected_network_rank(pwrPeer.getNetworkRank(pwrNetwork));
                        pwrPeer.setCurrentConnectedNetwork(pwrNetwork.getId());
                        pwrNetwork.setUsed_capacity(pwrNetwork.getUsed_capacity() + pwrPeer.getDatarate());
                        pwrNetwork.getConnectedPeers().add(pwrPeer);
                    }
                }
            }
            //end greedy heuristic
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
