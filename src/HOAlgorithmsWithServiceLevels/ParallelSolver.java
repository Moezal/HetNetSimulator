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
public class ParallelSolver {

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
//            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Service Level: "+ed+"  $$$$$$$$$$$$$$$$$$$$$$$");
            peersServiceLevel = new LinkedList<Peer>();
            for (Peer ped : Peers) {
                if (ped.getService_level() == ed) {
                    peersServiceLevel.add(ped);
                }
            }
            double globalMinPC = 0, globalMaxRss = 0;

            for (Peer p123 : peersServiceLevel) {
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
//            Collections.shuffle(peersServiceLevel, Simulator.Simulator.sp.randGenerator);
//            Collections.shuffle(Networks, Simulator.Simulator.sp.randGenerator);
//            System.out.println("Service Level List Size: "+peersServiceLevel.size());
//            double[][] globalCostFunction = new double[peersServiceLevel.size()][Networks.size()];
            List<ProphitWeightRatio> pwr = new LinkedList<ProphitWeightRatio>();
            for (int i = 0; i < peersServiceLevel.size(); i++) {
                for (int j = 0; j < Networks.size(); j++) {
                    if (peersServiceLevel.get(i).isNetworkAvailable(Networks.get(j))) {
                        double requestedResourcesInHz;
                        if (Networks.get(j).isAP()) {
                            requestedResourcesInHz = peersServiceLevel.get(i).getRequestedResources(Networks.get(j).getId()) * Simulator.Simulator.sp.WIFI_BW / Simulator.Simulator.sp.NUM_WIFI_TIME_SLOTS;
                        } else {
                            requestedResourcesInHz = peersServiceLevel.get(i).getRequestedResources(Networks.get(j).getId()) * 180 / (Simulator.Simulator.sp.NUM_LTE_TIME_SLOTS);
                        }

                        ProphitWeightRatio Pnow = new ProphitWeightRatio(NetworkCostFunctionSmaller.calculate_Network_cost_function(peersServiceLevel.get(i), Networks.get(j), globalMaxRss, globalMinPC) / requestedResourcesInHz, peersServiceLevel.get(i));
//                        ProphitWeightRatio Pnow = new ProphitWeightRatio(Calulation.CostFunction.calculate_cost_function(peersServiceLevel.get(i), peersServiceLevel.get(i).getNetworkSignalStrength(Networks.get(j).getId()))*peersServiceLevel.get(i).getDatarate(),peersServiceLevel.get(i));
                        Pnow.setNetowrkProfitRatio(Networks.get(j));
                        pwr.add(Pnow);
                    }
                }
            }
            Collections.sort(pwr);

            for (ProphitWeightRatio pwrNow : pwr) {
//                System.out.println("pwr ratio: "+ pwrNow.getRatio() + "     pwr rate: "+pwrNow.getPeer().getDatarate());
                Peer pwrPeer = pwrNow.getPeer();

                if (!pwrPeer.isConnected()) {
                    Network pwrNetwork = pwrNow.getNetowrkProfitRatio();
                    if (pwrNetwork.getFreeCapacity() >= pwrPeer.getRequestedResources(pwrNetwork.getId())) {
                        pwrPeer.setConnectionFailed(false);
                        pwrPeer.setConnected(true);
                        pwrPeer.setConnectedNetwork(pwrNetwork);
                        pwrPeer.setConnected_network_rank(pwrPeer.getNetworkRank(pwrNetwork));
                        pwrPeer.setCurrentConnectedNetwork(pwrNetwork.getId());
                        pwrNetwork.setUsed_capacity(pwrNetwork.getUsed_capacity() + pwrPeer.getRequestedResources(pwrNetwork.getId()));
                        pwrNetwork.getConnectedPeers().add(pwrPeer);
                    }
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

}
