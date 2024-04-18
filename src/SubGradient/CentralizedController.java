/*
 * Copyright (C) 2017 mohamad
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
package SubGradient;

import Calulation.NetworkCostFunctionSmaller;
import HandOverAlgorithms.NetworkProcessingThread;
import Networks_Package.Network;
import Peers_Package.Peer;
import Peers_Package.ProphitWeightRatio;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class CentralizedController {

    double[][] result = null;
    List<Peer> peersServiceLevel;
    List<Network> Networks;
    double[][] globalCostFunction;
    double[] networkCapacity;
    double[] lambda;
    double[][] originalGlobalFunction;

    public CentralizedController(double[][] globalCostFunction, int numberOfUsers, double[] networkCapacity, List<Peer> peersServiceLevel, List<Network> Networks, double[] lambda) {
        originalGlobalFunction = globalCostFunction;
        this.lambda = lambda;
        this.networkCapacity = networkCapacity;
        this.globalCostFunction = new double[peersServiceLevel.size()][Networks.size()];
        for (int i = 0; i < peersServiceLevel.size(); i++) {
            for (int j = 0; j < Networks.size(); j++) {
                if (globalCostFunction[i][j] == 0) {
                    this.globalCostFunction[i][j] = 0;
                } else {
                    this.globalCostFunction[i][j] = globalCostFunction[i][j] - lambda[i];
                }

            }
        }
//        for (int i = 0; i < peersServiceLevel.size(); i++) {
//            for (int j = 0; j < Networks.size(); j++) {
//                if (globalCostFunction[i][j] != 0) {
//                    this.globalCostFunction[i][j] = globalCostFunction[i][j] + lambda[i];
////                    System.out.println("lambda "+lambda[i]);
//                }
//            }
//        }
        this.peersServiceLevel = peersServiceLevel;
        this.Networks = Networks;
        int numberOfNetworks = networkCapacity.length;
        result = new double[numberOfUsers][numberOfNetworks];
        double[][] userDataRate = new double[numberOfUsers][numberOfNetworks];
        for (int i = 0; i < numberOfUsers; i++) {
            for (int j = 0; j < numberOfNetworks; j++) {
                if (globalCostFunction[i][j] == 0) {
                    userDataRate[i][j] = 10000 + Networks.get(j).getCapacity();
                } else {
                    userDataRate[i][j] = peersServiceLevel.get(i).getRequestedResources(Networks.get(j).getId());
                }
            }
        }
        LinkedList<KnapsackProcessingThread> knapsackThreads = new LinkedList<KnapsackProcessingThread>();
        for (int i = 0; i < numberOfNetworks; i++) {
            KnapsackProcessingThread kpt = new KnapsackProcessingThread(i, this.globalCostFunction, userDataRate, networkCapacity[i], numberOfUsers);
            kpt.start();
            knapsackThreads.add(kpt);
        }
        boolean allThreadsAreAlive = true;
        while (allThreadsAreAlive) {
            boolean allThreadsTerminated = true;
            for (KnapsackProcessingThread kpt : knapsackThreads) {
                allThreadsTerminated = allThreadsTerminated & (kpt.isHasEnded());
            }
            if (allThreadsTerminated) {
                allThreadsAreAlive = false;
            }

        }

        for (KnapsackProcessingThread kpt : knapsackThreads) {
//            System.out.println("kpt length " + kpt.getNetworkInitial());
            for (int i = 0; i < numberOfUsers; i++) {
//                System.out.println("" + kpt.getResult()[i]);
                result[i][kpt.getNetworkInitial()] = kpt.getResult()[i];
//                System.out.print(" "+result[i][kpt.getNetworkInitial()]);
            }
//            System.out.println("\n");
        }

//        double upperBound = 0;
//        for (int i = 0; i < numberOfUsers; i++) {
//            for (int j = 0; j < numberOfNetworks; j++) {
//                if (result[i][j] != 0) {
//                    upperBound += globalCostFunction[i][j];
//                }
//            }
//        }
//        System.out.println("upper bound " + upperBound);
//        getFeasibleSolutionGreedy();
    }

    public double[][] getResult() {
        return result;
    }

    public double[][] getFeasibleSolutionApproximation() {
        DistributedApproximationPerHzAlgorithm dapa = new DistributedApproximationPerHzAlgorithm(peersServiceLevel, Networks, originalGlobalFunction, result, networkCapacity);
        return dapa.getFeasibleApproximation();
    }

    public double[][] getFeasibleSolutionGreedy() {
        List<ProphitWeightRatio> pwr = new LinkedList<ProphitWeightRatio>();
        for (int i = 0; i < peersServiceLevel.size(); i++) {
            for (int j = 0; j < Networks.size(); j++) {
                if (result[i][j] != 0) {
//                    System.out.println("inside if");
                    double requestedResourcesInHz;
                    if (Networks.get(j).isAP()) {
                        requestedResourcesInHz = peersServiceLevel.get(i).getRequestedResources(Networks.get(j).getId()) * Simulator.Simulator.sp.WIFI_BW / Simulator.Simulator.sp.NUM_WIFI_TIME_SLOTS;
                    } else {
                        requestedResourcesInHz = peersServiceLevel.get(i).getRequestedResources(Networks.get(j).getId()) * 180 / (Simulator.Simulator.sp.NUM_LTE_TIME_SLOTS);
                    }

                    ProphitWeightRatio Pnow = new ProphitWeightRatio(originalGlobalFunction[i][j] / requestedResourcesInHz, peersServiceLevel.get(i));
//                        ProphitWeightRatio Pnow = new ProphitWeightRatio(Calulation.CostFunction.calculate_cost_function(peersServiceLevel.get(i), peersServiceLevel.get(i).getNetworkSignalStrength(Networks.get(j).getId()))*peersServiceLevel.get(i).getDatarate(),peersServiceLevel.get(i));
                    Pnow.setNetowrkProfitRatio(Networks.get(j));
                    Pnow.setNetworkInitialForDistributed(j);
                    Pnow.setPeerInitialForDistributed(i);
                    pwr.add(Pnow);
                } else {
//                    System.out.println("not inside if");
                }
            }
        }
        Collections.sort(pwr);
        double[][] feasibleResults = new double[peersServiceLevel.size()][Networks.size()];
        for (int i = 0; i < peersServiceLevel.size(); i++) {
            for (int j = 0; j < Networks.size(); j++) {
                feasibleResults[i][j] = 0;
            }
        }
        List<Peer> connectedPeers = new LinkedList<Peer>();
        double[] networkCapacityVirtual = new double[networkCapacity.length];
        for (int i = 0; i < networkCapacityVirtual.length; i++) {
            networkCapacityVirtual[i] = networkCapacity[i];
        }

        for (ProphitWeightRatio pwrNow : pwr) {
//                System.out.println("pwr ratio: "+ pwrNow.getRatio() + "     pwr rate: "+pwrNow.getPeer().getDatarate());
            Peer pwrPeer = pwrNow.getPeer();

            if (!pwrPeer.isConnected()) {
                Network pwrNetwork = pwrNow.getNetowrkProfitRatio();
                if (networkCapacityVirtual[pwrNow.getNetworkInitialForDistributed()] >= pwrPeer.getRequestedResources(pwrNetwork.getId())) {
                    networkCapacityVirtual[pwrNow.getNetworkInitialForDistributed()] = networkCapacityVirtual[pwrNow.getNetworkInitialForDistributed()] - pwrPeer.getRequestedResources(pwrNetwork.getId());
                    pwrPeer.setConnected(true);
                    connectedPeers.add(pwrPeer);
                    feasibleResults[pwrNow.getPeerInitialForDistributed()][pwrNow.getNetworkInitialForDistributed()] = 1;
                }
            }
        }
        List<ProphitWeightRatio> toBeRemoved = new LinkedList<ProphitWeightRatio>();
        for (ProphitWeightRatio pwr2 : pwr) {
            if (pwr2.getPeer().isConnected()) {
                toBeRemoved.add(pwr2);
            } else if (networkCapacityVirtual[pwr2.getNetworkInitialForDistributed()] < pwr2.getPeer().getRequestedResources(pwr2.getNetowrkProfitRatio().getId())) {
                toBeRemoved.add(pwr2);
            }
        }
        pwr.removeAll(toBeRemoved);
        for (int i = 0; i < peersServiceLevel.size(); i++) {
            for (int j = 0; j < Networks.size(); j++) {
                if (result[i][j] == 0) {
                    double requestedResourcesInHz;
                    if (Networks.get(j).isAP()) {
                        requestedResourcesInHz = peersServiceLevel.get(i).getRequestedResources(Networks.get(j).getId()) * Simulator.Simulator.sp.WIFI_BW / Simulator.Simulator.sp.NUM_WIFI_TIME_SLOTS;
                    } else {
                        requestedResourcesInHz = peersServiceLevel.get(i).getRequestedResources(Networks.get(j).getId()) * 180 / (Simulator.Simulator.sp.NUM_LTE_TIME_SLOTS);
                    }

                    ProphitWeightRatio Pnow = new ProphitWeightRatio(originalGlobalFunction[i][j] / requestedResourcesInHz, peersServiceLevel.get(i));
//                        ProphitWeightRatio Pnow = new ProphitWeightRatio(Calulation.CostFunction.calculate_cost_function(peersServiceLevel.get(i), peersServiceLevel.get(i).getNetworkSignalStrength(Networks.get(j).getId()))*peersServiceLevel.get(i).getDatarate(),peersServiceLevel.get(i));
                    Pnow.setNetowrkProfitRatio(Networks.get(j));
                    Pnow.setNetworkInitialForDistributed(j);
                    Pnow.setPeerInitialForDistributed(i);
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
                if (networkCapacityVirtual[pwrNow.getNetworkInitialForDistributed()] >= pwrPeer.getRequestedResources(pwrNetwork.getId())) {
                    networkCapacityVirtual[pwrNow.getNetworkInitialForDistributed()] = networkCapacityVirtual[pwrNow.getNetworkInitialForDistributed()] - pwrPeer.getRequestedResources(pwrNetwork.getId());
                    pwrPeer.setConnected(true);
                    connectedPeers.add(pwrPeer);
                    feasibleResults[pwrNow.getPeerInitialForDistributed()][pwrNow.getNetworkInitialForDistributed()] = 1;
                }
            }
        }
        for (Peer px : connectedPeers) {
            px.setConnected(false);
        }
//        double feasible = 0;
//        for (int i = 0; i < peersServiceLevel.size(); i++) {
//            for (int j = 0; j < Networks.size(); j++) {
//                if (feasibleResults[i][j] != 0) {
//                    feasible += originalGlobalFunction[i][j];
//                }
//            }
//        }
//        System.out.println("feasible " + feasible);
//        for(int i=0;i<peersServiceLevel.size();i++){
//            for(int j=0;j<Networks.size();j++){
//                System.out.print(" "+feasibleResults[i][j]);
//            }
//            System.out.println("");
//        }
        return feasibleResults;
    }

}
