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
public class HeuristicApproach {

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
//            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Service Level: "+ed+"  $$$$$$$$$$$$$$$$$$$$$$$");
            peersServiceLevel = new LinkedList<Peer>();
            for (Peer ped : Peers) {
                if (ped.getService_level() == ed) {
                    peersServiceLevel.add(ped);
                }
            }

//            Collections.shuffle(peersServiceLevel, Simulator.Simulator.sp.randGenerator);
//            Collections.shuffle(Networks, Simulator.Simulator.sp.randGenerator);
//            System.out.println("Service Level List Size: "+peersServiceLevel.size());
//            double[][] globalCostFunction = new double[peersServiceLevel.size()][Networks.size()];
            double[][] matrixD = new double[Networks.size()][peersServiceLevel.size()];//For the algorithm
            double[][] matrixC = new double[Networks.size()][peersServiceLevel.size()];//costFunction
            double[][] matrixA = new double[Networks.size()][peersServiceLevel.size()];//weights
            double[] matrixB = new double[Networks.size()];//constraints of network capacity
            double[][] matrixX = new double[Networks.size()][peersServiceLevel.size()];//Variables
            for (int i = 0; i < Networks.size(); i++) {
                matrixB[i] = Networks.get(i).getFreeCapacity();
                for (int j = 0; j < peersServiceLevel.size(); j++) {
                    if (peersServiceLevel.get(j).isNetworkAvailable(Networks.get(i))) {
//                        matrixC[i][j] = NetworkCostFunctionSmaller.calculate_Network_cost_function(peersServiceLevel.get(j), Networks.get(i), globalMaxRss, globalMinPC) / (double) peersServiceLevel.get(i).getDatarate();
                        matrixC[i][j] = NetworkCostFunctionSmaller.calculate_Network_cost_function(peersServiceLevel.get(j), Networks.get(i), globalMaxRss, globalMinPC);
                        matrixA[i][j] = peersServiceLevel.get(j).getDatarate();
                    } else {
                        matrixC[i][j] = 0;
                        matrixA[i][j] = 0;
                    }
                    matrixX[i][j] = 0;
                }
            }
            boolean exitLoop = false;
            while (!exitLoop) {
                for (int i = 0; i < Networks.size(); i++) {
                    for (int j = 0; j < peersServiceLevel.size(); j++) {
                        if (peersServiceLevel.get(j).isNetworkAvailable(Networks.get(i)) && (matrixA[i][j] != 0)) {
//                    matrixC[i][j]=NetworkCostFunctionSmaller.calculate_Network_cost_function(peersServiceLevel.get(i), Networks.get(j), globalMaxRss, globalMinPC) / (double) peersServiceLevel.get(i).getDatarate();
                            matrixD[i][j] = matrixA[i][j] / matrixC[i][j];
                            if (matrixA[i][j] > matrixB[i]) {
                                matrixD[i][j] = 0;
                            }

                        } else {
                            matrixD[i][j] = 0;
                        }
                    }
                }
                double[] minValueInEachColumnInMatrixD = new double[peersServiceLevel.size()];
                int[] rowOftheMinimumValueInMatrixD = new int[peersServiceLevel.size()];
                for (int j = 0; j < peersServiceLevel.size(); j++) {
                    double minValue = 0;
                    int minIndex = -1;
                    for (int i = 0; i < Networks.size(); i++) {
                        if (minIndex != -1 && minValue != 0) {
                            if ((matrixD[i][j] >= 1) && (matrixD[i][j] < minValue)) {
                                minValue = matrixD[i][j];
                                minIndex = i;
                            }
                        } else if ((matrixD[i][j] >= 1)) {
                            minValue = matrixD[i][j];
                            minIndex = i;
                        }
                    }
                    minValueInEachColumnInMatrixD[j] = minValue;
                    rowOftheMinimumValueInMatrixD[j] = minIndex;
                }
                double maxValue = 0;
                int maxIndex = -1;
                int maxRow = -1;
                for (int j = 0; j < peersServiceLevel.size(); j++) {
                    if (rowOftheMinimumValueInMatrixD[j] != -1 && minValueInEachColumnInMatrixD[j] != 0) {
                        if (maxValue != 0 && maxIndex != -1) {
                            if (minValueInEachColumnInMatrixD[j] * matrixC[rowOftheMinimumValueInMatrixD[j]][j] > maxValue) {
                                maxValue = minValueInEachColumnInMatrixD[j] * matrixC[rowOftheMinimumValueInMatrixD[j]][j];
                                maxIndex = j;
                                maxRow = rowOftheMinimumValueInMatrixD[j];
                            }
                        } else {
                            maxValue = minValueInEachColumnInMatrixD[j] * matrixC[rowOftheMinimumValueInMatrixD[j]][j];
                            maxIndex = j;
                            maxRow = rowOftheMinimumValueInMatrixD[j];
                        }
                    }
                }
                if (maxValue != 0 && maxRow != -1 && maxIndex != -1) {
                    matrixX[maxRow][maxIndex] = 1;
                    if (matrixA[maxRow][maxIndex] > matrixB[maxRow]) {
                        System.out.println("here the error");
                    }
                    matrixB[maxRow] = matrixB[maxRow] - matrixA[maxRow][maxIndex];
                    for (int i = 0; i < Networks.size(); i++) {
                        matrixA[i][maxIndex] = 0;
//                        matrixC[i][maxIndex] = 0;
                    }
                } else {
//                   exitLoop = true;
                }
                int counterZeroElements = 0;
                for (int i = 0; i < Networks.size(); i++) {
                    for (int j = 0; j < peersServiceLevel.size(); j++) {
                        if (matrixD[i][j] == 0) {
                            counterZeroElements++;
                        }

                    }
                }
                if (counterZeroElements == peersServiceLevel.size() * Networks.size()) {
                    exitLoop = true;
                }

            }
            for (int j = 0; j < peersServiceLevel.size(); j++) {
                int peerConnected = 0;
                for (int i = 0; i < Networks.size(); i++) {
                    if (matrixX[i][j] == 1) {
                        peerConnected++;
//                        connectPeerToNetwork(peersServiceLevel.get(j), Networks.get(i));
                    }
                }
                if (peerConnected > 1) {
                    System.out.println("1 peer connected to more than one network!!");
                    System.exit(j);
                }
            }

            for (int j = 0; j < peersServiceLevel.size(); j++) {
                boolean peerConnected = false;
                for (int i = 0; i < Networks.size(); i++) {
                    if (matrixX[i][j] == 1) {
                        peerConnected = true;
                        connectPeerToNetwork(peersServiceLevel.get(j), Networks.get(i));
                    }
                }
                if (!peerConnected) {
                    peersServiceLevel.get(j).setConnectionFailed(true);
                }
            }

        }
        long HOEProcessingTimeEnd = System.nanoTime();
        processingTime += (HOEProcessingTimeEnd - HOEProcessingTimeStart) / (double) 1000000;
        Simulator.Simulator.stat.processingTime.add(processingTime);
        for (Network nx : Networks) {
//            System.err.println("Second: "+Simulator.Simulator.sp.current_simulation_time+"-- network utilization "+nx.getName()+"  " + (double) (nx.getUsed_capacity() / (double) nx.getCapacity()));
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

    public static void connectPeerToNetwork(Peer p, Network n) {
        p.setConnectionFailed(false);
        p.setConnected(true);
        p.setConnectedNetwork(n);
        p.setConnected_network_rank(p.getNetworkRank(n));
        p.setCurrentConnectedNetwork(n.getId());
        n.setUsed_capacity(n.getUsed_capacity() + p.getDatarate());
        n.getConnectedPeers().add(p);
    }
}
