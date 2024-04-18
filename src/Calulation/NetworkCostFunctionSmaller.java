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
public class NetworkCostFunctionSmaller {

    public static double calculate_Network_cost_function(Peer p, Network n, double max_s, double min_pc) {
        double s_ratio = CalculateSignalStrength.calculate_signal_strength(p, n) / max_s;
//        System.out.println("in second" +Simulator.Simulator.sp.current_simulation_time+ " for user "+p.getPeerID()+" the s_ratio equals: "+s_ratio);
//        String s_ratio_string = " " + n.getSignalStrength() + "/" + p.getMax_available_s();
//        double mc_ratio = (1 / (n.getMc() * p.getDatarate())) / (1 / min_mc);
//        String mc_ratio_string = " (1- " + n.getNetwork().getMc() + "/" + p.getMax_available_mc()+")";
        double pc_ratio;
        if (p.getWs() == 0 && p.getWpc() == 1) {
            pc_ratio = ((1 / (n.getPc(p.getDatarate()))) / (1 / min_pc));
        } else {
            pc_ratio = ((1 / (n.getPc(p.getDatarate()))) / (1 / min_pc));
        }

//        String pr_ratio_string = " (1- " + n.getNetwork().getPc() + "/" + p.getMax_available_pc()+")";
//        double result = (1 * p.getWs() * s_ratio + 1 * p.getWpc() * pc_ratio + 0.1 * (s_ratio + pc_ratio)) * p.getDatarate();
        double result = (1 * p.getWs() * s_ratio + 1 * p.getWpc() * pc_ratio) * p.getDatarate();
//        double result = (0.1*s_ratio + 0.9*pc_ratio) * p.getDatarate();
//        double result =  p.getDatarate();
//        System.out.println("cost function user:" + p.getPeerID() + "to network:" + n.getNetwork().getName() + "is:\n"
//                + " (" + p.getWs() + "*" + s_ratio + " + " + p.getWmc() + "*" + mc_ratio+ " + " + p.getWpc() + "*" + pc_ratio + ")*"+p.getDatarate()+"=" + result);

//        System.out.println("result: "+result);
        if(result/p.getDatarate()>1){
            System.err.println("result>1 and pc="+n.getPc(p.getDatarate()) +"while min_pc="+min_pc+"\ns="+CalculateSignalStrength.calculate_signal_strength(p, n) +"while max s = "+max_s);
        }
        return result;

    }

}
