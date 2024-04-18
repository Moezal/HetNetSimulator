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
public class CostFunctionLogNormalization {

    public static double calculate_cost_function(Peer p, NetworkSignalStrength n) {
        double s_ratio = n.getSignalStrength();
//        System.out.println("in second" +Simulator.Simulator.sp.current_simulation_time+ " for user "+p.getPeerID()+" the s_ratio equals: "+s_ratio);
//        String s_ratio_string = " " + n.getSignalStrength() + "/" + p.getMax_available_s();
        double mc_ratio = 1 - (Math.log10(n.getNetwork().getMc()) * p.getDatarate() / Math.log10(p.getMax_available_mc()));
//        String mc_ratio_string = " (1- " + n.getNetwork().getMc() + "/" + p.getMax_available_mc()+")";
        double pc_ratio = 1 - (Math.log10(n.getNetwork().getPc(p.getDatarate())) / Math.log10(p.getMax_available_pc()));
//        String pr_ratio_string = " (1- " + n.getNetwork().getPc() + "/" + p.getMax_available_pc()+")";
        double result = (1 * p.getWs() * s_ratio + 1 * p.getWmc() * mc_ratio + 1 * p.getWpc() * pc_ratio);
//        System.out.println("cost function user:" + p.getPeerID() + "to network:" + n.getNetwork().getName() + "is:\n"
//                + " (" + p.getWs() + "*" + s_ratio + " + " + p.getWmc() + "*" + mc_ratio+ " + " + p.getWpc() + "*" + pc_ratio + ")*"+p.getDatarate()+"=" + result);
        return result;

    }

}
