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
public class CostFunctionMinMaxNormalization {

    public static double calculate_cost_function(Peer p, NetworkSignalStrength n) {
        double mc_ratio = 0;
        double s_ratio = 0;
        double pc_ratio = 0;
        if (p.getMax_available_s() == p.getMin_available_s()) {
            s_ratio = 0.001;
        } else {
            s_ratio = 1 - Math.abs(n.getSignalStrength() - p.getMax_available_s()) / (p.getMax_available_s() - p.getMin_available_s());
        }
//         s_ratio = n.getSignalStrength();
//        System.out.println("in second" +Simulator.Simulator.sp.current_simulation_time+ " for user "+p.getPeerID()+" the s_ratio equals: "+s_ratio);
//        String s_ratio_string = " " + n.getSignalStrength() + "/" + p.getMax_available_s();
        if (p.getMin_available_mc() == p.getMax_available_mc()) {
            mc_ratio = 0.001;
        } else {
            mc_ratio = 1 - Math.abs(n.getNetwork().getMc() - p.getMin_available_mc()) / (p.getMax_available_mc() - p.getMin_available_mc());
        }
//        String mc_ratio_string = " (1- " + n.getNetwork().getMc() + "/" + p.getMax_available_mc()+")";
        if (p.getMin_available_pc() == p.getMax_available_pc()) {
            pc_ratio = 0.001;
        } else {
            pc_ratio = 1 - Math.abs(n.getNetwork().getPc(p.getDatarate()) - p.getMin_available_pc()) / (p.getMax_available_pc() - p.getMin_available_pc());
        }

//        String pr_ratio_string = " (1- " + n.getNetwork().getPc() + "/" + p.getMax_available_pc()+")";
        double result = (1 * p.getWs() * s_ratio + 1 * p.getWmc() * mc_ratio + 1 * p.getWpc() * pc_ratio);
//        System.out.println("cost function user:" + p.getPeerID() + "to network:" + n.getNetwork().getName() + "is:\n"
//                + " (" + p.getWs() + "*" + s_ratio + " + " + p.getWmc() + "*" + mc_ratio+ " + " + p.getWpc() + "*" + pc_ratio + ")*"+p.getDatarate()+"=" + result);
        return result;

    }

}
