/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calulation;

import Networks_Package.Network;
import Networks_Package.NetworkSignalStrength;
import Peers_Package.Peer;

/**
 *
 * @author mohamad
 */
public class NetworkCostFunctionMaxMinNormalization {

    public static double calculate_Network_cost_function(Peer p, Network n, double max_mc, double max_pc, double max_s, double min_mc, double min_pc, double min_s, double overall_requested_BUs, double overall_mc, double overall_pc, double overall_s) {
        double s_ratio = 1 - Math.abs(CalculateSignalStrength.calculate_signal_strength(p, n) - max_s) / (max_s - min_s);
//        System.out.println("in second" +Simulator.Simulator.sp.current_simulation_time+ " for user "+p.getPeerID()+" the s_ratio equals: "+s_ratio);
//        String s_ratio_string = " " + n.getSignalStrength() + "/" + p.getMax_available_s();
        double mc_ratio = 1 - Math.abs(n.getMc() * p.getDatarate() - min_mc) / (max_mc - min_mc);
//        String mc_ratio_string = " (1- " + n.getNetwork().getMc() + "/" + p.getMax_available_mc()+")";
        double pc_ratio = 1 - Math.abs(n.getPc(p.getDatarate() - min_pc)) / (max_pc - min_pc);
//        String pr_ratio_string = " (1- " + n.getNetwork().getPc() + "/" + p.getMax_available_pc()+")";
        double result = (1 * p.getWs() * s_ratio + 1 * p.getWmc() * mc_ratio + 1 * p.getWpc() * pc_ratio) * p.getDatarate();
//        System.out.println("cost function user:" + p.getPeerID() + "to network:" + n.getNetwork().getName() + "is:\n"
//                + " (" + p.getWs() + "*" + s_ratio + " + " + p.getWmc() + "*" + mc_ratio+ " + " + p.getWpc() + "*" + pc_ratio + ")*"+p.getDatarate()+"=" + result);

//        System.out.println("result: "+result);
        return result;

    }

}
