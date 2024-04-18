/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

import Networks_Package.Network;
import Networks_Package.ServiceArea;
import Peers_Package.Peer;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mohamad
 */
public class SimulationParameters {

    public int simulation_time;//in seconds
    public int current_simulation_time;
    public double roMinusOne;//ro-1=lamda/mue = arrival rate/ departure rate
    public double ro_remainder;
    public List<Network> Netowrks;
    public List<Peer> Peers;
    public Random randGenerator;
    public int seed;
    public int max_x, max_y, min_x, min_y;
    public List<Network> major_Netowrks;
    public List<ServiceArea> service_areas;
    public int min_stop_time, max_stop_time;
    public int min_stop_every, max_stop_every;
    public int min_direction_change, max_direction_change;
    public int slow_moving_min_speed, slow_moving_max_speed;
    public int fast_moving_min_speed, fast_moving_max_speed;
    public int SLOW_SPEED = 1, STABLE = 0, FAST_SPEED = 2;
    public int NUM_LTE_RBs,NUM_LTE_TIME_SLOTS,NUM_WIFI_TIME_SLOTS,WIFI_BW, LTE_BW;
    public double LTE_TX_POWER_per_RB,WIFI_TX_POWER;
    public double LTE_Noise, WIFI_Noise;
    public PrintWriter report_writer;
    public int user_speed, user_BU;
//    public int initial_number_of_users;
    public double ws, wmc, wpc;
    public int peerID;
    public int networkID;
    public int Service_area_id;
    public int[] voice_BU, video_BU, download_bu;
//    public int min_max_diff;
    public int BS_BU, BS_money_cost, BS_radius;
    public int number_service_area, number_of_AP_in_service_area, AP_radius, AP_BU, AP_money_cost;
    public int number_users_in_the_service_area;
    public int AP_PC, BS_PC;
    public double max_s;
    public int max_mc, max_pc, pc_variation, mc_variation;
    public double max_mc_in_system,max_s_in_system;
    public int[] all_data_rates;
    public double alpha_d_wifi, alpha_d_lte, beta_wifi, beta_lte;
    public String algortihmUsed;
    public int datarateCounter;

    public void initializeVariables() {
        datarateCounter=0;
        max_mc_in_system = 0;
        current_simulation_time = 1;
        peerID = 1;
        networkID = 0;
        Service_area_id = 0;
        major_Netowrks = new LinkedList<Network>();
        Netowrks = new LinkedList<Network>();
        Peers = new LinkedList<Peer>();
        service_areas = new LinkedList<ServiceArea>();
        randGenerator = new Random();
        randGenerator.setSeed(seed);
        System.out.println("seed: "+seed);
        ro_remainder = 0;
        user_speed = 0;
        user_BU = 1;
//        try {
////            report_writer = new PrintWriter("C:\\Users\\mohamad\\Desktop\\Reports\\report.txt", "UTF-8");
////            mobility_writer = new PrintWriter("C:\\Users\\mohamad\\Desktop\\Reports\\mobility.txt", "UTF-8");
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(SimulationParameters.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(SimulationParameters.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
