/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

import Calulation.CalculateServiceAreaCoordinates;
import Calulation.ShuffleLinkedList;
import HOAlgorithmsWithServiceLevels.GreedyAlgorithmSolver222;
import HOAlgorithmsWithServiceLevels.GreedyAlgorithmSolver2221;
import HOAlgorithmsWithServiceLevels.GreedyAlgorithmSolverTotalProfit;
import HOAlgorithmsWithServiceLevels.GreedyPerHzAlgorithmSolver;
import HandOverAlgorithms.CostFunctionAtTheEndOfIteration;
import HandOverAlgorithms.CostFunctionHandover;
import HandOverAlgorithms.EstimateNetworksUtilizationAndRequests;
import HandOverAlgorithms.GlobalOptimizationHandover;
import HandOverAlgorithms.MaxProfitFist;
import HandOverAlgorithms.OptimizationAtTheEndOfTheIteration;
import HandOverAlgorithms.OptimizationMultiThreaded;
import HandOverAlgorithms.OptimizationMultiThreaded_AllUsersConnected;
import HandOverAlgorithms.OptimizedCostFunctionHandover;
import Networks_Package.Network;
import Networks_Package.NetworkSignalStrength;
import Networks_Package.ServiceArea;
import Peers_Package.Peer;
import Point.Point;
import Statistics.OurFileWriter;
import Statistics.OverallCurveAdder;
import Statistics.Statistics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mohamad
 */
public class Simulator {

    /**
     * @param args the command line arguments
     */
    public static boolean isSL;
    public static int setvice_level;
    public static SimulationParameters sp;
    public static String default_path = "C:\\Simulation_Reports";
//    public static String default_path = "/home-reseau/mzalghou/Bureau/Simulation_Reports";
//    public static String default_path = "/home/mohamad/Simulation_Reports";
    public static String path_delimilter = "\\";
//    public static String path_delimilter = "/";
    public static String secondary_path;
    public static Statistics stat;
    public static int seed;
    public static LinkedList<OverallCurveAdder> overallCurveAdderList;
    public static boolean isGlobalMinMax, isMultipliedByDataRate;
    public static int SubGradientIterations;

    public static void main(String[] args) {
        overallCurveAdderList = new LinkedList<OverallCurveAdder>();
        Date d = new Date();
        default_path += path_delimilter + new SimpleDateFormat("dd_MM_yy____HH_mm").format(Calendar.getInstance().getTime());

//        {plot(x,y1,'-ro',x,y2,'-.b')
//        legend('sin(x)','cos(x)')}
//        plot(x,y,'-.or') plots y versus x using a dash-dot line (-.), places circular markers (o) at the data points, and colors both line and marker red (r)
//        '-'Solid line (default)     '--'Dashed line     ':'Dotted line     '-.'Dash-dot line
//        '+'Plus sign     'o'Circle   '*'Asterisk     '.'Point    'x'Cross    'square' or 's'Square   'diamond' or 'd'Diamond     '^''>''<''v'pointing triangle
//        r Red   g Green     b Blue      c Cyan      m Magenta     y Yellow      k   Black       w White
//      algo,Wpc,Ws,initialNumberUsersPerServiceArea,Mue,simulationTime,plotPointEvery,numberServiceAreas,numberAccessPointsInServiceArea,symbol,legend, stable_users
//        SimulateUsingAlgorithm("GlobalOptimization", 0.5, 0.5, 80, 0.004, 10000, 1000, 1, 3, "\'-*r\'", "Global", false);
//        SimulateUsingAlgorithm("GlobalOptimization", 0.0, 1.0, 80, 0.004, 10000, 1000, 1, 3, "\'->r\'", "Global-Rss", false);
//        SimulateUsingAlgorithm("GlobalOptimization", 1.0, 0.0, 80, 0.004, 10000, 1000, 1, 3, "\'-or\'", "Global-PC", false);
//        SimulateUsingAlgorithm("Optimization", 1.0, 0.0, 80, 0.004, 10000, 1000, 1, 3, "\'-ok\'", "Opt-PC", false);
//        SimulateUsingAlgorithm("CostFunction", 1.0, 0.0, 80, 0.004, 10000, 1000, 1, 3, "\':ob\'", "CF-PC", false);
//        SimulateUsingAlgorithm("Optimization", 0.5, 0.5, 80, 0.004, 10000, 1000, 1, 3, "\'-*k\'", "Opt", false);
//        SimulateUsingAlgorithm("CostFunction", 0.5, 0.5, 80, 0.004, 10000, 1000, 1, 3, "\':*b\'", "CF", false);
//        SimulateUsingAlgorithm("Optimization", 0.0, 1.0, 80, 0.004, 10000, 1000, 1, 3, "\'->k\'", "Opt-Rss", false);
//        SimulateUsingAlgorithm("CostFunction", 0.0, 1.0, 80, 0.004, 10000, 1000, 1, 3, "\':>b\'", "CF-Rss", false);
        seed = 527;
        isSL = false;
        boolean stableSimulation = false;
        int TotalSimulationTime, numAPinSA;
        double numUsersPerSec = 5;
//        int initNumUsers = 1;
        int initNumUsers = 1;
        int plotPointEveryNum = 1;
        if (isSL) {
            TotalSimulationTime = 16;
            numAPinSA = 3;
            numUsersPerSec = 9;
            initNumUsers = 3;
            plotPointEveryNum = 1;
        } else {
            TotalSimulationTime = 21;
            numAPinSA = 2;
        }
        SubGradientIterations = 600;

//        SimulateUsingAlgorithm("EstimateUtilization", 0.5, 0.5, 80, 0.004, 10000, 1000, 1, 3, "\'-*r\'", "Global", false);
        for (int iteration = 0; iteration < 10000; iteration++) {
//            SimulateUsingAlgorithm("EstimateUtilization", 0.5, 0.5, 70, 5, 15, 1, 1, 3, "\'-*r\'", "Global", true);
//            SimulateUsingAlgorithm("GlobalOptimization", 0.5, 0.5, 80, 4, 11, 1, 1, 3, "\'-*r\'", "Global", true);
//            SimulateUsingAlgorithm("GlobalOptimization", 1.0, 0.0, 80, 4, 11, 1, 1, 3, "\'-or\'", "Global-PC", true);

//            SimulateUsingAlgorithm("DistributedSubGradientGreedy", 0.4, 0.6, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\'-om\'", "DISTSubGreedy_", stableSimulation, true, true);
//            SimulateUsingAlgorithm("DistributedGreedy", 0.4, 0.6, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\'-om\'", "DISTGreedy_", stableSimulation, true, true);
//////            SimulateUsingAlgorithm("GlobalOptimization", 0.4, 0.6, 1, 1, 60, 5, 1, 1,  "\'->k\'", "OPT", stableSimulation, true, true);
//            SimulateUsingAlgorithm("Simplex2", 0.4, 0.6, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\'->m\'", "SIM2", stableSimulation, true, true);
//            SimulateUsingAlgorithm("Approximation", 0.4, 0.6, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\'->b\'", "aprx", stableSimulation, true, true);
            SimulateUsingAlgorithm("GlobalOptimization", 0.2, 0.8, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\'->k\'", "OPT", stableSimulation, true, true);
            SimulateUsingAlgorithm("GlobalOptimization", 0, 1, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\'->k\'", "OPT-RSS", stableSimulation, true, true);
            SimulateUsingAlgorithm("GlobalOptimization", 1, 0, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\'->k\'", "OPT-PC", stableSimulation, true, true);
//////            SimulateUsingAlgorithm("GlobalOptimizationFullprofit", 0.4, 0.6, 1, 1, 100, 5, 1, 1, "\'->k\'", "OPT-full", stableSimulation, true, true);
//            SimulateUsingAlgorithm("GreedyNoFair", 0.4, 0.6, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\'-xg\'", "GRD-nofair", stableSimulation, true, true);
//            SimulateUsingAlgorithm("Greedy", 0.4, 0.6, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\'-xg\'", "GRD", stableSimulation, true, true);
//////            SimulateUsingAlgorithm("SimplexPerHz", 0.4, 0.6, initNumUsers, numUsersPerSec, TotalSimulationTime, 5, 1, numSPinSA, "\'->m\'", "SIM2-Hz", stableSimulation, true, true);
//            SimulateUsingAlgorithm("SimplexAgain", 0.4, 0.6, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\'->m\'", "SIM-Again", stableSimulation, true, true);
//            SimulateUsingAlgorithm("ApproximationPerHz", 0.4, 0.6, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\'->b\'", "aprx-Hz", stableSimulation, true, true);
//            SimulateUsingAlgorithm("GreedyPerHz", 0.4, 0.6, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\'-xr\'", "GRD-Hz", stableSimulation, true, true);
////            SimulateUsingAlgorithm("CostFunction", 0.4, 0.6, 1, 1, 100, 5, 1, 1, "\':*k\'", "CF", stableSimulation, true, true);
            SimulateUsingAlgorithm("ProfitFunction", 0.5, 0.5, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\':*k\'", "PF", stableSimulation, true, true);
            SimulateUsingAlgorithm("ProfitFunction", 0, 1, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\':*k\'", "PF-RSS", stableSimulation, true, true);
            SimulateUsingAlgorithm("ProfitFunction", 1, 0, initNumUsers, numUsersPerSec, TotalSimulationTime, plotPointEveryNum, 1, numAPinSA, "\':*k\'", "PF-PC", stableSimulation, true, true);

//            SimulateUsingAlgorithm("Simplex", 0.4, 0.6, 95, 1, 41, 5, 1, 3, "\'->k\'", "SIM", true, true, true);
//            SimulateUsingAlgorithm("SimplexAgain", 0.4, 0.6, 95, 1, 41, 5, 1, 3, "\'->b\'", "SIMA", true, true, true);
//            SimulateUsingAlgorithm("SimplexDual", 0.4, 0.6, 95, 1, 41, 5, 1, 3, "\'->k\'", "dual", true, true, true);
//            SimulateUsingAlgorithm("ApproximationWithoutModification", 0.4, 0.6, 95, 1, 41, 5, 1, 3, "\'->k\'", "aprx2", true, true, true);
//            SimulateUsingAlgorithm("HeuristicApproach", 0.4, 0.6, 95, 1, 41, 5, 1, 3, "\':*m\'", "her", true, true, true);
//            SimulateUsingAlgorithm("GreedyTotProfit", 0.5, 0.5, 100, 4, 11, 1, 1, 3, "\'-ok\'", "GRDTot", true, true, true);
//            SimulateUsingAlgorithm("Optimization", 1.0, 0.0, 50, 4, 11, 1, 1, 3, "\'-ok\'", "Opt-PC-global-multiply", true, true, true);
//            SimulateUsingAlgorithm("Optimization", 0.5, 0.5, 50, 4, 11, 1, 1, 3, "\'-*k\'", "Opt-global-multiply", true, true, true);
//            SimulateUsingAlgorithm("Optimization", 0.0, 1.0, 50, 4, 11, 1, 1, 3, "\'->k\'", "Opt-Rss-global-multiply", true, true, true);
//            SimulateUsingAlgorithm("Optimization", 1.0, 0.0, 50, 4, 11, 1, 1, 3, "\'-og\'", "Opt-PC-global-Notmultiply", true, true, false);
//            SimulateUsingAlgorithm("Optimization", 0.5, 0.5, 50, 4, 11, 1, 1, 3, "\'-*g\'", "Opt-global-Notmultiply", true, true, false);
//            SimulateUsingAlgorithm("Optimization", 0.0, 1.0, 50, 4, 11, 1, 1, 3, "\'->g\'", "Opt-Rss-global-Notmultiply", true, true, false);
//            SimulateUsingAlgorithm("Optimization", 1.0, 0.0, 80, 4, 11, 1, 1, 3, "\'-or\'", "Opt-PC-Notglobal-multiply", true, false, true);
//            SimulateUsingAlgorithm("Optimization", 0.5, 0.5, 80, 4, 11, 1, 1, 3, "\'-*r\'", "Opt-Notglobal-multiply", true, false, true);
//            SimulateUsingAlgorithm("Optimization", 0.0, 1.0, 80, 4, 11, 1, 1, 3, "\'->r\'", "Opt-Rss-Notglobal-multiply", true, false, true);
//            SimulateUsingAlgorithm("Optimization", 1.0, 0.0, 50, 4, 11, 1, 1, 3, "\'-om\'", "Opt-PC-Notglobal-Notmultiply", true, false, false);
//            SimulateUsingAlgorithm("Optimization", 0.5, 0.5, 50, 4, 11, 1, 1, 3, "\'-*m\'", "Opt-Notglobal-Notmultiply", true, false, false);
//            SimulateUsingAlgorithm("Optimization", 0.0, 1.0, 50, 4, 11, 1, 1, 3, "\'->m\'", "Opt-Rss-Notglobal-Notmultiply", true, false, false);
//            SimulateUsingAlgorithm("CostFunction", 1.0, 0.0, 50, 4, 11, 1, 1, 3, "\':ob\'", "CF-PC", true, true, true);
//            SimulateUsingAlgorithm("CostFunction", 0.4, 0.6, 95, 1, 41, 5, 1, 3, "\':*b\'", "PF", true, true, true);
//            SimulateUsingAlgorithm("CostFunction", 1, 0, 100, 4, 11, 1, 1, 3, "\':*k\'", "Wi-Fi", true, true, true);
//            SimulateUsingAlgorithm("CostFunction", 0.0, 1.0, 50, 4, 11, 1, 1, 3, "\':>b\'", "Rss", true, true, true);
            seed = sp.randGenerator.nextInt(100000);
            System.out.println("Iteration number " + iteration);
        }
        System.out.println("number of curves" + overallCurveAdderList.size());
        for (OverallCurveAdder oca : overallCurveAdderList) {
            oca.adjustYList();
            oca.PlotCurve();
        }
    }

    private static void SimulateUsingAlgorithm(String algorithm, double powerConsumptionWeight, double RssWeight, int initialNumberOfUsersPerServiceArea, double Mue, int simulationTime, int plotPointEvery, int numberServiceAreas, int numberAccessPointsInServiceArea, String symbol, String legend, boolean stable_simulation, boolean globalMaxMin, boolean multiplyDataRate) {
        System.out.println("" + algorithm);

        isGlobalMinMax = globalMaxMin;
        isMultipliedByDataRate = multiplyDataRate;
        String additionnal = "";
        if (isGlobalMinMax) {
            additionnal += "t";
        } else {
            additionnal += "f";
        }
        if (isMultipliedByDataRate) {
            additionnal += "t";
        } else {
            additionnal += "f";
        }
        String algorithm_used = additionnal + algorithm + "_" + powerConsumptionWeight + "_" + RssWeight + "_" + initialNumberOfUsersPerServiceArea + "_" + Mue + "_" + simulationTime + "_" + numberServiceAreas + "_" + numberAccessPointsInServiceArea;
        algorithm_used = algorithm_used.replace(".", "p");
        stat = new Statistics();
        setDefaultPath(algorithm_used);
        setSimulationParam();
        sp.wpc = powerConsumptionWeight;
        sp.ws = RssWeight;
        sp.number_users_in_the_service_area = initialNumberOfUsersPerServiceArea;
        sp.roMinusOne = Mue;
        sp.simulation_time = simulationTime;
        sp.number_service_area = numberServiceAreas;
        sp.number_of_AP_in_service_area = numberAccessPointsInServiceArea;
        setvice_level = sp.randGenerator.nextInt(3);
//        System.out.println(""+setvice_level);
        createNetworksAndUsers();
        String allNetworksString = "";
        for (Network n : sp.Netowrks) {
            allNetworksString += "\n" + n.getName() + " radius: " + n.getRadius() + " center: " + n.getCenter().getX() + "---" + n.getCenter().getY() + " number bu " + n.getCapacity() + " --- is AP" + n.isAP();
        }
//        System.out.println("created");
        new OurFileWriter(allNetworksString, secondary_path + path_delimilter + "allNetworksInfo.txt", false);
        startSimulation(algorithm, algorithm_used, symbol, legend, plotPointEvery, stable_simulation);

    }

    private static void setDefaultPath(String algorithm_used) {
        Date d = new Date();
        secondary_path = default_path + path_delimilter + algorithm_used + path_delimilter;
        System.out.println(secondary_path);
        File path = new File(secondary_path);
        if (!path.exists()) {
            if (!path.mkdirs()) {
                System.err.println("Cannot create path of folders");
                System.exit(0);
            }
        }

    }
    static int number_of_users_added = 0;

    private static void startSimulation(String algorithm, String algorithmParameters, String symbol, String legend, int PlotPointEvery, boolean stable_simulation) {

        int currentServiceAreaToAddUsers = 0;

        for (sp.current_simulation_time = 1; sp.current_simulation_time <= sp.simulation_time; sp.current_simulation_time++) {
//            Collections.shuffle(sp.Peers, sp.randGenerator);
//            Collections.shuffle(sp.Netowrks, sp.randGenerator);
//            System.err.println("simulation time: " + sp.current_simulation_time);
            boolean x = false;
            if (sp.current_simulation_time != 1) {
                if (sp.roMinusOne >= 1) {
                    for (int j = 0; j < (int) sp.roMinusOne; j++) {
//                    for (ServiceArea sva : sp.service_areas) {
                        ServiceArea sva = sp.service_areas.get(currentServiceAreaToAddUsers);
                        addPeer(sva.getMin_x(), sva.getMin_y(), sva.getMax_x(), sva.getMax_y());
                        x = true;
//                    System.out.println("added " + (int) sp.roMinusOne + " to sva " + sp.service_areas.get(currentServiceAreaToAddUsers).getName());
//                    }
                    }

                }
                sp.ro_remainder += sp.roMinusOne - ((int) sp.roMinusOne);
                if (sp.ro_remainder >= 1) {
//                System.out.println("inside here");
//                for (int j = 0; j < (int) sp.ro_remainder; j++) {
//                    for (ServiceArea sva : sp.service_areas) {
                    ServiceArea sva = sp.service_areas.get(currentServiceAreaToAddUsers);
                    addPeer(sva.getMin_x(), sva.getMin_y(), sva.getMax_x(), sva.getMax_y());
                    x = true;
//                System.out.println("added " + 1 + " to sva " + sp.service_areas.get(currentServiceAreaToAddUsers).getName());
//                    }
//                }
                    sp.ro_remainder = sp.ro_remainder - ((int) sp.ro_remainder);
                }
                if (x) {
                    currentServiceAreaToAddUsers++;
                }
//            System.out.println("sva size "+ sp.service_areas.size());
                if (currentServiceAreaToAddUsers == sp.service_areas.size()) {
                    currentServiceAreaToAddUsers = 0;
                }
            }
//            System.out.println("peers b4 shuffling: ");
//            for(int ghr = 0 ; ghr<sp.Peers.size();ghr++){
//                System.out.println(" ID: "+sp.Peers.get(ghr).getPeerID()+ " datarate: "+sp.Peers.get(ghr).getDatarate() );
//            }
//            Collections.shuffle(sp.Peers, sp.randGenerator);
            //            Collections.shuffle(sp.Netowrks, sp.randGenerator);
//            System.out.println("peers after shuffling: ");
//            for(int ghr = 0 ; ghr<sp.Peers.size();ghr++){
//                System.out.println(" ID: "+sp.Peers.get(ghr).getPeerID()+ " datarate: "+sp.Peers.get(ghr).getDatarate() );
//            }
//            Collections.shuffle(sp.Netowrks, sp.randGenerator);
//            ShuffleLinkedList.ShuffleLinkedList(sp.Peers);
            for (Peer p : sp.Peers) {

                if (p.isConnected()) {
                    p.setPreviousConnectedNetwork(p.getCurrentConnectedNetwork());
                } else {
                    p.setPreviousConnectedNetwork(-1);
                }
                if (!stable_simulation) {
                    p.movePeer();
                }

                p.testNetworks();
            }
            for (Peer p : sp.Peers) {
                p.rankNetworks();
            }

            if (algorithm.equalsIgnoreCase("CostFunction")) {
                HOAlgorithmsWithServiceLevels.CostFunctionAtTheEndOfIteration.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("ProfitFunction")) {
                HOAlgorithmsWithServiceLevels.profitFunctionBased.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("Optimization")) {
                OptimizationMultiThreaded_AllUsersConnected.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("GlobalOptimization")) {
                HOAlgorithmsWithServiceLevels.GlobalOptimizationHandover.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("GlobalOptimizationFullprofit")) {
                HOAlgorithmsWithServiceLevels.GlobalOptimizationHandoverFullProfit.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("Greedy")) {
                GreedyAlgorithmSolver222.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("GreedyNoFair")) {
                GreedyAlgorithmSolver2221.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("GreedyPerHz")) {
                GreedyPerHzAlgorithmSolver.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("GreedyTotProfit")) {
                GreedyAlgorithmSolverTotalProfit.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("EstimateUtilization")) {
                EstimateNetworksUtilizationAndRequests.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("Simplex")) {
                HOAlgorithmsWithServiceLevels.SimplexHandOver.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("Simplex2")) {
                HOAlgorithmsWithServiceLevels.SimplexHandOver2.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("SimplexPerHz")) {
                HOAlgorithmsWithServiceLevels.SimplexPerHzHandOver.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("SimplexDual")) {
                HOAlgorithmsWithServiceLevels.SimplexDualHandover.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("Approximation")) {
                HOAlgorithmsWithServiceLevels.ApproximationAlgorithm.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("ApproximationPerHz")) {
                HOAlgorithmsWithServiceLevels.ApproximationPerHzAlgorithm.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("ApproximationWithoutModification")) {
                HOAlgorithmsWithServiceLevels.ApproximationAlgorithmWithoutModification.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("HeuristicApproach")) {
                HOAlgorithmsWithServiceLevels.HeuristicApproach.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.equalsIgnoreCase("SimplexAgain")) {
                HOAlgorithmsWithServiceLevels.SimplexHandOverAgain.startHandingOver(sp.Peers, sp.Netowrks);
            } else if (algorithm.contains("DistributedSubGradientGreedy")) {
                HOAlgorithmsWithServiceLevels.DistributedGlobal.startHandingOver(sp.Peers, sp.Netowrks, true, SubGradientIterations);
            } else if (algorithm.contains("DistributedGreedy")) {
                HOAlgorithmsWithServiceLevels.DistributedGlobal.startHandingOver(sp.Peers, sp.Netowrks, true, 1);
            } else if (algorithm.equalsIgnoreCase("DistributedSubGradientApproximation")) {
                HOAlgorithmsWithServiceLevels.DistributedGlobal.startHandingOver(sp.Peers, sp.Netowrks, false, SubGradientIterations);
            } else {
                System.err.println("The name of the handover algorithm is unkown");
                System.exit(-3);
            }

//            OptimizationAtTheEndOfTheIteration.startHandingOver(sp.Peers, sp.Netowrks);
//            MaxProfitFist.startHandingOver(sp.Peers, sp.Netowrks);
//            CostFunctionHandover.startHandingOver(sp.Peers, sp.Netowrks);
//            AlteredOptimizedCostFunctionHandover.startHandingOver(sp.Peers, sp.Netowrks);
//            OptimizedCostFunctionHandover.startHandingOver(sp.Peers, sp.Netowrks);
//            MinimumWieghtFirst.startHandingOver(sp.Peers, sp.Netowrks);
//            MaxProfitFist.startHandingOver(sp.Peers, sp.Netowrks);
//            TryingOwnImplementation.startHandingOver(sp.Peers, sp.Netowrks);
//            SubOptimalCostFunction.startHandingOver(sp.Peers, sp.Netowrks);
//            SubOptimizationAtTheEndOfTheIteration.startHandingOver(sp.Peers, sp.Netowrks);
//            AllConditionsOptimizationAtTheEndOfTheIteration.startHandingOver(sp.Peers, sp.Netowrks);
            for (Peer p : sp.Peers) {
                p.movementDone();
                if (!p.isConnected()) {
//                    System.err.println("Unconnected users!!!!demanding data rate: "+p.getDatarate()+" has available networks:");
                    for (NetworkSignalStrength n : p.getAvailableNetowrks()) {
//                        System.err.println(n.getNetwork().getName()+" which has utilization: "+n.getNetwork().getUsed_capacity()+"/"+n.getNetwork().getCapacity());
                        if (n.getNetwork().getUsed_capacity() <= (n.getNetwork().getCapacity() - p.getRequestedResources(n.getNetwork().getId()))) {
                            System.err.println("network: " + n.getNetwork().getName() + " is not full and the user request: " + p.getRequestedResources(n.getNetwork().getId()) + " and the network has free: " + n.getNetwork().getFreeCapacity());
                        }
                    }
//                    System.err.println("***********************************");
                }
            }
            stat.updateStatisticsValues();
            for (Network n : sp.Netowrks) {
                n.getUtilization().add(100 * (1.0 * n.getUsed_capacity() / n.getCapacity()));
//                n.getRequested_BUs_list().add(n.getTotal_requested_BUs());
//                System.out.println("utilization of network "+n.getName()+" is :"+n.getUsed_capacity()+"/"+n.getCapacity());
            }
            if (sp.current_simulation_time % PlotPointEvery == 0) {
                System.err.println("**************Simulation second: " + sp.current_simulation_time + "*********************");
            }
//            updatePeerMovementWriter();

//            System.err.println("Simulation second: " + sp.current_simulation_time);
        }
        if (!stat.testIfAllValuesCollected()) {
            System.err.println("errrrrrrorrrr");
            System.exit(-1);
        }
//        sp.report_writer.flush();
//        closeWriters();
        String networkUtilizationString = "";
        for (Network n : sp.Netowrks) {
            networkUtilizationString += "\n" + "for network: " + n.getName() + " the avarage number of utilization is: ";
            double sum = 0;
            for (int i = 0; i < n.getUtilization().size(); i++) {
                sum += n.getUtilization().get(i);
            }
            sum = sum / sp.simulation_time;
            networkUtilizationString += " " + sum;
        }
        new OurFileWriter(networkUtilizationString, secondary_path + path_delimilter + "NetworkUtilization.txt", false);

        String overallValues = "\noverall average power consumed per user: " + stat.getOverallAveragePowerConsumptionPerUser();
        overallValues += "\noverall average Rss per User: " + stat.getOverallAverageRssPerUser();
        overallValues += "\noverall average satisfaction per user: " + stat.getOverallAverageSatisfactionPerUser();
        overallValues += "\noverall average power consumed per BU: " + stat.getOverallAveragePowerConsumptionPerBU();
        overallValues += "\noverall average Rss per BU: " + stat.getOverallAverageRssPerBU();
        overallValues += "\noverall average satisfaction per BU: " + stat.getOverallAverageSatisfactionPerBU();
//        overallValues += "\ntotal number of rejected users: " + stat.getTotalNumberRejectedUsers();
        overallValues += "\naverage number of handover per user during the whole simulation is: " + stat.getAverageNumberOfHandoversPerUserDuringTheWholeSimulation();
        overallValues += "\naverage USER blocking probability: " + stat.getAverageUserBlockingProbability();
        overallValues += "\naverage BU blocking probability: " + stat.getAverageBUBlockingProbability();
        overallValues += "\naverage number of connection request list received per network: " + stat.getAverageNumberOfConnectionRequestListsReceivedPerNetwork();
        overallValues += "\naverage number of users in the connection list: " + stat.getAverageNumberOfUsersInTheConnectionRequestListsPerList();
        overallValues += "\nnumber of users at the end of simulation: " + sp.Peers.size();
        overallValues += "\naverage processing time: " + stat.getAverageProcessingTime();
//        overallValues += "\ntotal rejected BUs: " + stat.getTotalNumberRejectedBUs();
//        overallValues += "\ntotal requested BUs: " + stat.getTotalNumberRequestedBUs();
        new OurFileWriter(overallValues, secondary_path + path_delimilter + "overallValues.txt", false);
//        stat.plotAverageSatisfactionPerUser("averageSatisfactionPerUser_" + algorithmParameters, PlotPointEvery, symbol, legend);
//        stat.plotAverageSatisfactionPerBU("averageSatisfactionPerBU_" + algorithmParameters, PlotPointEvery, symbol, legend);
//        stat.plotAveragePowerConsumptionPerUser("averagePowerConsumptionPerUser_" + algorithmParameters, PlotPointEvery, symbol, legend);
//        stat.plotAveragePowerConsumptionPerBU("averagePowerConsumptionPerBU_" + algorithmParameters, PlotPointEvery, symbol, legend);
//        stat.plotAverageRssPerUser("averageRssPerUser_" + algorithmParameters, PlotPointEvery, symbol, legend);
//        stat.plotAverageRssPerBU("averageRssPerBU_" + algorithmParameters, PlotPointEvery, symbol, legend);
//        stat.plotAverageHandoverPerUser("averageHOperUser_" + algorithmParameters, PlotPointEvery, symbol, legend);
//        stat.plotNetworkProcessingTime("ProcessingTime_" + algorithmParameters, PlotPointEvery, symbol, legend);
//        stat.plotNumberOfHandover("numberOfHO_" + algorithmParameters, PlotPointEvery, symbol, legend);
//        stat.plotAverageBUBlockingProbability("BUBlockingProbability_" + algorithmParameters, PlotPointEvery, symbol, legend);
//        stat.plotAverageUserBlockingProbability("UserBlockingProbability_" + algorithmParameters, PlotPointEvery, symbol, legend);

        stat.SL_plotAverageSatisfactionPerUser("SL_averageSatisfactionPerUser_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.SL_plotAverageSatisfactionPerBU("SL_averageSatisfactionPerBU_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.SL_plotAverageProfitPerBU("SL_averageProfitPerBU_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.SL_plotAverageSatisfactionPerServedBU("SL_averageSatisfactionPerServedBU_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.SL_plotAveragePowerConsumptionPerUser("SL_averagePowerConsumptionPerUser_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.SL_plotAveragePowerConsumptionPerBU("SL_averagePowerConsumptionPerBU_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.SL_plotAverageRssPerUser("SL_averageRssPerUser_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.SL_plotAverageRssPerBU("SL_averageRssPerBU_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.SL_plotAverageHandoverPerUser("SL_averageHOperUser_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotRequestedDatarate("TotalRequestedDatarate_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.SL_plotNumberOfHandover("SL_numberOfHO_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.SL_plotAverageBUBlockingProbability("SL_BUBlockingProbability_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.SL_plotAverageUserBlockingProbability("SL_UserBlockingProbability_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.SL_plotAverageJainSatisfaction("SL_JainSatisfaction" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.SL_plotAverageDispersionSatisfaction("SL_DispersionSatisfaction" + algorithmParameters, PlotPointEvery, symbol, legend);

        stat.plotNetworkProcessingTime("ProcessingTime_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotDistributedIterationCount("DistributedIterationCount_" + algorithmParameters, PlotPointEvery, symbol, legend);

        stat.plotAverageSatisfaction_1000("AverageSatisfaction_1000_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotAverageSatisfaction_1200("AverageSatisfaction_1200_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotAverageSatisfaction_55("AverageSatisfaction_55_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotAverageSatisfaction_87("AverageSatisfaction_87_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotAverageSatisfaction_500("AverageSatisfaction_500_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotAverageSatisfaction_150("AverageSatisfaction_150_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotAverageSatisfaction_300("AverageSatisfaction_300_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotAverageSatisfaction_32("AverageSatisfaction_32_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotAverageSatisfaction_700("AverageSatisfaction_700_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotAveragejainIndexSatisfactionDataRate("AverageJainSatisfactionDataRate_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotAveragejainIndexSatisfactionNumberBlocked("AverageJainSatisfactionNumberBlocked_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotAverageJainSatisfaction("AverageJainSatisfaction_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotAverageIndexOfDispersionSatisfaction("AverageIndexOfDispersionSatisfaction_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotPercentageBlocked_1000("PercentageBlocked_1000_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotPercentageBlocked_1200("PercentageBlocked_1200_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotPercentageBlocked_55("PercentageBlocked_55_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotPercentageBlocked_87("PercentageBlocked_87_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotPercentageBlocked_500("PercentageBlocked_500_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotPercentageBlocked_150("PercentageBlocked_150_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotPercentageBlocked_300("PercentageBlocked_300_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotPercentageBlocked_32("PercentageBlocked_32_" + algorithmParameters, PlotPointEvery, symbol, legend);
        stat.plotPercentageBlocked_700("PercentageBlocked_700_" + algorithmParameters, PlotPointEvery, symbol, legend);
    }

    private static void updatePeerMovementWriter() {
        String xString = "x" + sp.current_simulation_time + " = [";
        String yString = "y" + sp.current_simulation_time + "  = [";
        String idString = "a" + sp.current_simulation_time + "  = [";
        for (Peer p : sp.Peers) {
            xString += " " + p.getLocation().getX();
            yString += " " + p.getLocation().getY();
            idString += " " + p.getPeerID();
        }
        xString += "]; ";
        yString += "]; ";
        idString += "]'; b" + sp.current_simulation_time + "  = num2str(a" + sp.current_simulation_time + " ); c" + sp.current_simulation_time + "  = cellstr(b" + sp.current_simulation_time + " );  text(x" + sp.current_simulation_time + ", y" + sp.current_simulation_time + " , c" + sp.current_simulation_time + " ); ";
        new OurFileWriter(xString + yString + idString + "\n" + "drawnow", secondary_path + path_delimilter + "usersMovement.txt", false);
    }

    private static void createUsers() {
        //creating random peers
        for (ServiceArea sva : sp.service_areas) {
//            System.out.println(sva.getName()+": min_x "+sva.getMin_x()+"---min_y "+sva.getMin_y()+"---max_x "+sva.getMax_x()+"---max_y "+sva.getMax_y());
            for (int i = 0; i < sp.number_users_in_the_service_area; i++) {
                addPeer(sva.getMin_x(), sva.getMin_y(), sva.getMax_x(), sva.getMax_y());
            }
        }
    }

    private static void addPeer(int min_x, int min_y, int max_x, int max_y) {
        sp.Peers.add(new Peer(sp.user_BU, sp.ws, sp.wmc, sp.wpc, sp.user_speed, new Point(min_x, min_y, max_x, max_y)));
        if (sp.user_speed == 2) {
            sp.user_speed = 0;
        } else {
            sp.user_speed++;
        }
        if (sp.user_BU == 3) {
            sp.user_BU = 1;
        } else {
            sp.user_BU++;
        }
    }

    private static void createNetworksAndUsers() {
        //setting major networks
        //number of major networks=number_service_area +1
        int number_major_networks = sp.number_service_area + 1;
        for (int i = 1; i <= number_major_networks; i++) {
            if (i == 1 || i == number_major_networks) {
                sp.major_Netowrks.add(new Network(sp.BS_BU,
                        sp.BS_money_cost, sp.BS_PC, sp.BS_radius, 100,
                        new Point(sp.BS_radius * i, sp.BS_radius), "BS_" + i, false));
                continue;
            }
            sp.major_Netowrks.add(new Network(sp.BS_BU,
                    sp.BS_money_cost, sp.BS_PC, sp.BS_radius, 100,
                    new Point(sp.BS_radius * i, sp.BS_radius), "BS_" + i, false));

        }

        //CREATE SERVICE AREAS
//        int min_y = sp.BS_radius / 5 + Calulation.CalculateServiceAreaCoordinates.calculate_min_y(sp.BS_radius);
        int min_y = Calulation.CalculateServiceAreaCoordinates.calculate_min_y(sp.BS_radius);
        int max_y = min_y + 3 * sp.BS_radius / 10;
        sp.min_y = min_y;
        sp.max_y = max_y;
        sp.min_x = sp.BS_radius;
        sp.max_x = (1 + sp.number_service_area) * sp.BS_radius;
        for (int i = 0; i < sp.number_service_area; i++) {
            sp.service_areas.add(new ServiceArea(sp.BS_radius * (1 + i), min_y, max_y, sp.BS_radius, "Service_Area_" + (i + 1)));
        }
        //CREATE WIFI NETWORKS IN THE SERVICE AREAS
        createWifiNetworks();
        sp.Netowrks.addAll(sp.major_Netowrks);
        createUsers();
        plot();
    }

    private static void createWifiNetworks() {
        int number_wifi_networks = sp.number_service_area * sp.number_of_AP_in_service_area + 1;//+1 because first and last networks are assumed to have half bandwidth
        int length_of_service_area = sp.max_x - sp.min_x;
        int width_of_service_area = sp.max_y - sp.min_y;
        int fixed_y_center = sp.min_y + width_of_service_area / 2;
        for (int i = 0; i < number_wifi_networks; i++) {
            if (i == 0 || i == (number_wifi_networks - 1)) {
                sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(sp.min_x + i * length_of_service_area / (number_wifi_networks - 1), fixed_y_center), "AP_" + (1 + i), true));
                continue;
            }

            sp.Netowrks.add(new Network(sp.AP_BU, sp.AP_money_cost, sp.AP_PC, sp.AP_radius, 100, new Point(sp.min_x + i * length_of_service_area / (number_wifi_networks - 1), fixed_y_center), "AP_" + (1 + i), true));

        }

    }

    private static void plot() {
        PrintWriter network_users_plot = null;
        try {
            network_users_plot = new PrintWriter(secondary_path + path_delimilter + "network_users_plot.m");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Simulator.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        for (Network n : sp.Netowrks) {
            network_users_plot.println("plot_circle(" + n.getCenter().getX() + "," + n.getCenter().getY() + "," + n.getRadius() + ")");
        }
        int height = sp.max_y - sp.min_y;
        for (ServiceArea sva : sp.service_areas) {
            network_users_plot.println("rectangle('Position',[" + sva.getMin_x() + " " + sva.getMin_y() + " " + sp.BS_radius + " " + height + "])");
        }
        String xString = "x = [";
        String yString = "y = [";
        String idString = "a = [";
        for (Peer p : sp.Peers) {
            xString += " " + p.getLocation().getX();
            yString += " " + p.getLocation().getY();
            idString += " " + p.getPeerID();
        }
        xString += "]; ";
        yString += "]; ";
        idString += "]';  plot(x, y, '*'); ";
        network_users_plot.println(xString + yString + idString);
        network_users_plot.flush();
        network_users_plot.close();
    }

    private static void closeWriters() {
        sp.report_writer.flush();
        sp.report_writer.close();
    }

    private static void setSimulationParam() {
        //setting simulation parameters
        sp = new SimulationParameters();
        sp.simulation_time = 10000;
        sp.seed = seed;
        sp.alpha_d_lte = 0.05197;//mW/Mbps
        sp.alpha_d_wifi = 0.13701;//mW/Mbps
        sp.beta_lte = 1288.04;//mW
        sp.beta_wifi = 132.86;//mW
        sp.initializeVariables();
        sp.fast_moving_max_speed = 30;
        sp.fast_moving_min_speed = 15;
        sp.slow_moving_max_speed = 10;
        sp.slow_moving_min_speed = 1;
        sp.min_direction_change = 20;
        sp.max_direction_change = 100;
        sp.min_stop_time = 5;
        sp.max_stop_time = 10;
        sp.min_stop_every = 20;
        sp.max_stop_every = 50;

//        sp.BS_BU = 30000;
//        sp.AP_BU = 10000;
        sp.BS_money_cost = 150;

        sp.AP_money_cost = 10;

//        sp.number_of_AP_in_service_area = 3;
        sp.voice_BU = new int[]{87, 32, 56};
        sp.video_BU = new int[]{300, 500, 1200};
        sp.download_bu = new int[]{150, 700, 1000};
        sp.all_data_rates = new int[]{32, 150, 500, 700, 1000, 87, 55, 300, 1200};
//        sp.all_data_rates = new int[]{4, 15, 50, 70, 100, 9, 6, 30, 120};//note that in Network/getPC() we have multiplied the datarate by 10

//        sp.AP_PC = 30;
//        sp.BS_PC = 150;
        sp.pc_variation = 1;
        sp.mc_variation = 1;
        if (!isSL) {
            sp.NUM_LTE_RBs = 80;
            sp.NUM_LTE_TIME_SLOTS = 1000;
            sp.NUM_WIFI_TIME_SLOTS = 10000;
            sp.LTE_BW = 180 * sp.NUM_LTE_RBs;
            sp.WIFI_BW = 2000;
            sp.LTE_TX_POWER_per_RB = 0.4;
            sp.WIFI_TX_POWER = 0.2;
            sp.BS_BU = sp.NUM_LTE_RBs * sp.NUM_LTE_TIME_SLOTS;
            sp.AP_BU = sp.NUM_WIFI_TIME_SLOTS;
            sp.LTE_Noise = -141.45;//db
            sp.WIFI_Noise = -120;//db
            sp.AP_radius = 110;
            sp.BS_radius = 500;
        } else {
            sp.NUM_LTE_RBs = 20;
            sp.NUM_LTE_TIME_SLOTS = 1000;
            sp.NUM_WIFI_TIME_SLOTS = 10000;
            sp.WIFI_BW = 1500;
            sp.LTE_TX_POWER_per_RB = 0.4;
            sp.WIFI_TX_POWER = 0.2;
            sp.BS_BU = sp.NUM_LTE_RBs * sp.NUM_LTE_TIME_SLOTS;
            sp.AP_BU = sp.NUM_WIFI_TIME_SLOTS;
            sp.LTE_Noise = -141.45;//db
            sp.WIFI_Noise = -120;//db
            sp.AP_radius = 150;
            sp.BS_radius = 400;
        }
    }
}
