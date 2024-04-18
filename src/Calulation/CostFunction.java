/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calulation;

import Networks_Package.NetworkSignalStrength;
import Peers_Package.Peer;

/**
 *
 * @author mohamad
 */
public class CostFunction {

    public static double calculate_cost_function(Peer p, NetworkSignalStrength n) {
//        double globalMinPC = 0, globalMaxRss = 0;
//
//        for (Peer ps : Simulator.Simulator.sp.Peers) {
//
//            if (globalMinPC == 0) {
//                globalMinPC = ps.getMin_available_pc();
//            }
//            if (globalMaxRss == 0) {
//                globalMaxRss = ps.getMax_available_s();
//            }
//            if (p.getMin_available_pc() < globalMinPC) {
//                globalMinPC = ps.getMin_available_pc();
//            }
//            if (p.getMax_available_s() > globalMaxRss) {
//                globalMaxRss = ps.getMax_available_s();
//            }
//        }
        if (p.isNetworkAvailable(n.getNetwork())) {
//            return NetworkCostFunctionSmaller.calculate_Network_cost_function(p, n.getNetwork(), globalMaxRss, globalMinPC)/p.getDatarate();
            double s_ratio = n.getSignalStrength() / p.getMax_available_s();
//        System.out.println("in second" +Simulator.Simulator.sp.current_simulation_time+ " for user "+p.getPeerID()+" the s_ratio equals: "+s_ratio);
//        String s_ratio_string = " " + n.getSignalStrength() + "/" + p.getMax_available_s();
            double mc_ratio = 0;
//        String mc_ratio_string = " (1- " + n.getNetwork().getMc() + "/" + p.getMax_available_mc()+")"; 
            double pc_ratio;
            if (p.getWs() == 0 && p.getWpc() == 1) {
                pc_ratio = (1 / (n.getNetwork().getPc(p.getDatarate())) / (1 / (p.getMin_available_pc())));
            } else {
                pc_ratio = (1 / (n.getNetwork().getPc(p.getDatarate())) / (1 / (p.getMin_available_pc())));
            }
//        String pr_ratio_string = " (1- " + n.getNetwork().getPc() + "/" + p.getMax_available_pc()+")";
            double result = (1 * p.getWs() * s_ratio + 1 * p.getWpc() * pc_ratio);
            result = result / p.getBest_network_rank();
//            System.out.println("sratio: "+ s_ratio+" --- pc ratio: "+pc_ratio);
//        System.out.println("cost function user:" + p.getPeerID() + "to network:" + n.getNetwork().getName() + "is:\n"
//                + " (" + p.getWs() + "*" + s_ratio + " + " + p.getWmc() + "*" + mc_ratio+ " + " + p.getWpc() + "*" + pc_ratio + ")*"+p.getDatarate()+"=" + result);
            return result;
        }

        System.err.println("Error calculating cost function of network that is not available\n"
                + "global optimization assigned unavailable network to a user\n"
                + "");
        System.exit(-1);
        return 0;

    }
}
