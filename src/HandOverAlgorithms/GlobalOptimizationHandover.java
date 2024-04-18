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

import Calulation.CalculateSignalStrength;
import Calulation.NetworkCostFunction;
import Calulation.NetworkCostFunctionSmaller;
import Knapsack.Item;
import Networks_Package.Network;
import Peers_Package.Peer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class GlobalOptimizationHandover {

    public static void startHandingOver(List<Peer> Peers, List<Network> Networks) {
        for (Network n : Networks) {
            n.setUsed_capacity(0);
            n.setConnectedPeers(new LinkedList<Peer>());
            n.setPeersRequestingConnection(new LinkedList<Peer>());
            n.setTotalNumberOfUsersInList(1);
            n.setNumberOfRequestListsReceived(1);
        }
        double globalMinPC = 0, globalMaxRss = 0;
        for (Peer p : Peers) {
            p.setConnected(false);
            p.setConnectionFailed(false);
            p.setCurrentConnectedNetwork(0);
            if (globalMinPC == 0) {
                globalMinPC = p.getMin_available_pc();
            }
            if (globalMaxRss == 0) {
                globalMaxRss = p.getMax_available_s();
            }
            if (p.getMin_available_pc() < globalMinPC) {
                globalMinPC = p.getMin_available_pc();
            }
            if (p.getMax_available_s() > globalMaxRss) {
                globalMaxRss = p.getMax_available_s();
            }
        }
        double processingTime = 0;
        long HOEProcessingTimeStart = System.nanoTime();
        double[][] globalCostFunction = new double[Peers.size()][Networks.size()];
        for (int i = 0; i < Peers.size(); i++) {
            for (int j = 0; j < Networks.size(); j++) {
                if (Peers.get(i).isNetworkAvailable(Networks.get(j))) {
                    globalCostFunction[i][j] = NetworkCostFunctionSmaller.calculate_Network_cost_function(Peers.get(i), Networks.get(j), globalMaxRss, globalMinPC);
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
            networkCapacities[i] = Networks.get(i).getCapacity();
        }
        double[] usersDatarate = new double[Peers.size()];
        for (int i = 0; i < Peers.size(); i++) {
            usersDatarate[i] = Peers.get(i).getDatarate();
        }

//        double[][] solution = GlobalOptimization.GlobalOptimizationSolver.globalOptimizationParser(globalCostFunction, usersDatarate, networkCapacities);
        double[][] solution = GlobalOptimization.GlobalOptimizationSolverREmoveZeros.globalOptimizationParser(globalCostFunction, usersDatarate, networkCapacities, Peers, Networks);
        Peer currentPeer;
        Network currentNetwork;
        for (int i = 0; i < Peers.size(); i++) {
            boolean connected = false;
            currentPeer = Peers.get(i);
            for (int j = 0; j < Networks.size(); j++) {
                if (solution[i][j] == 1) {
                    if(globalCostFunction[i][j]==0){
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
                currentPeer.setConnectionFailed(true);
            }
        }
        long HOEProcessingTimeEnd = System.nanoTime();
        processingTime += (HOEProcessingTimeEnd - HOEProcessingTimeStart) / (double) 1000000;
        Simulator.Simulator.stat.processingTime.add(processingTime);
        for (Network nx : Networks) {
            System.out.println("network utilization" + (double) (nx.getUsed_capacity() / (double) nx.getCapacity()));
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