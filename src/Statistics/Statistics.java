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
package Statistics;

import Calulation.CostFunction;
import Calulation.NetworkCostFunction;
import Calulation.NetworkCostFunctionSmaller;
import Networks_Package.Network;
import Networks_Package.NetworkSignalStrength;
import Peers_Package.Peer;
import java.util.LinkedList;

/**
 *
 * @author mohamad
 */
public class Statistics {

    public LinkedList<Double> averageSatisfactionPerBU, averageSatisfactionPerUser, averageEnergyPerBU, averageHandoverPerUser, averageRssPerUser, averageRssPerBU, averageEnergyConsumedPerUser, averageBUsBlocked, averageUsersBlocked;
//    public LinkedList<Double> averageSatisfactionPerBUWithUnConnectedUsers, averageEnergyPerBUWithUnConnectedUsers, averageHandoverPerUserWithUnConnectedUsers, averageRssPerUserWithUnConnectedUsers, averageRssPerBUWithUnConnectedUsers, averageEnergyConsumedPerUserWithUnConnectedUsers;
    public LinkedList<Double> averageSatisfactionPerBU_SL_0, averageSatisfactionPerServed_BU_SL_0, averageSatisfactionPerUser_SL_0, averageEnergyPerBU_SL_0, averageHandoverPerUser_SL_0, averageRssPerUser_SL_0, averageRssPerBU_SL_0, averageEnergyConsumedPerUser_SL_0, averageBUsBlocked_SL_0, averageUsersBlocked_SL_0;
    public LinkedList<Double> averageSatisfactionPerBU_SL_1, averageSatisfactionPerServed_BU_SL_1, averageSatisfactionPerUser_SL_1, averageEnergyPerBU_SL_1, averageHandoverPerUser_SL_1, averageRssPerUser_SL_1, averageRssPerBU_SL_1, averageEnergyConsumedPerUser_SL_1, averageBUsBlocked_SL_1, averageUsersBlocked_SL_1;
    public LinkedList<Double> averageProfitPerBU_SL_0, averageProfitPerBU_SL_1, averageProfitPerBU_SL_2;
    public LinkedList<Double> averageSatisfactionPerBU_SL_2, averageSatisfactionPerServed_BU_SL_2, averageSatisfactionPerUser_SL_2, averageEnergyPerBU_SL_2, averageHandoverPerUser_SL_2, averageRssPerUser_SL_2, averageRssPerBU_SL_2, averageEnergyConsumedPerUser_SL_2, averageBUsBlocked_SL_2, averageUsersBlocked_SL_2;
    public LinkedList<Integer> numberRejectedUsers, numberOfUsersServed, numberBUsServed, numberBUsRejected, numberOfUsersInTheSystem, numberOfBUsRequested;
    public LinkedList<Integer> numberRejectedUsers_SL_0, numberOfUsersServed_SL_0, numberBUsServed_SL_0, numberBUsRejected_SL_0, numberOfUsersInTheSystem_SL_0, numberOfBUsRequested_SL_0;
    public LinkedList<Integer> numberRejectedUsers_SL_1, numberOfUsersServed_SL_1, numberBUsServed_SL_1, numberBUsRejected_SL_1, numberOfUsersInTheSystem_SL_1, numberOfBUsRequested_SL_1;
    public LinkedList<Integer> numberRejectedUsers_SL_2, numberOfUsersServed_SL_2, numberBUsServed_SL_2, numberBUsRejected_SL_2, numberOfUsersInTheSystem_SL_2, numberOfBUsRequested_SL_2;
    public LinkedList<Double> averageNumberOfUsersInTheConnectionRequestListsPerList, averageNumberOfConnectionRequestListsReceivedPerNetwork, processingTime, DistributedIterationCount, numberOfHandover, numberOfHandover_SL_0, numberOfHandover_SL_1, numberOfHandover_SL_2;

    public LinkedList<Double> average_satisfaction_32,
            average_satisfaction_150,
            average_satisfaction_500,
            average_satisfaction_700,
            average_satisfaction_1000,
            average_satisfaction_87,
            average_satisfaction_55, jainIndexSatisfaction, jainIndexSatisfactionDataRate,
            jainIndexSatisfactionNumberBlocked,
            indexOfDispersionSatisfaction, indexOfDispersionDatarate,
            average_satisfaction_300,
            average_satisfaction_1200,
            average_satisfaction_BU_55,
            average_satisfaction_BU_300,
            average_satisfaction_BU_1200,
            average_satisfaction_BU_32,
            average_satisfaction_BU_150,
            average_satisfaction_BU_500,
            average_satisfaction_BU_700,
            average_satisfaction_BU_1000,
            average_satisfaction_BU_87,
            average_satisfaction_32_SL_0,
            average_satisfaction_150_SL_0,
            average_satisfaction_500_SL_0,
            average_satisfaction_700_SL_0,
            average_satisfaction_1000_SL_0,
            average_satisfaction_87_SL_0,
            average_satisfaction_55_SL_0, jainIndexSatisfaction_SL_0,
            indexOfDispersionSatisfaction_SL_0,
            average_satisfaction_300_SL_0,
            average_satisfaction_1200_SL_0,
            average_satisfaction_32_SL_1,
            average_satisfaction_150_SL_1,
            average_satisfaction_500_SL_1,
            average_satisfaction_700_SL_1,
            average_satisfaction_1000_SL_1,
            average_satisfaction_87_SL_1,
            average_satisfaction_55_SL_1, jainIndexSatisfaction_SL_1,
            indexOfDispersionSatisfaction_SL_1,
            average_satisfaction_300_SL_1,
            average_satisfaction_1200_SL_1,
            average_satisfaction_32_SL_2,
            average_satisfaction_150_SL_2,
            average_satisfaction_500_SL_2,
            average_satisfaction_700_SL_2,
            average_satisfaction_1000_SL_2,
            average_satisfaction_87_SL_2,
            average_satisfaction_55_SL_2, jainIndexSatisfaction_SL_2,
            indexOfDispersionSatisfaction_SL_2,
            average_satisfaction_300_SL_2,
            average_satisfaction_1200_SL_2;

    public LinkedList<Double> percentage_blocked_32,
            percentage_blocked_150,
            percentage_blocked_500,
            percentage_blocked_700,
            percentage_blocked_1000,
            percentage_blocked_87,
            percentage_blocked_55,
            percentage_blocked_300,
            percentage_blocked_1200,
            percentage_blocked_BU_55,
            percentage_blocked_BU_300,
            percentage_blocked_BU_1200,
            percentage_blocked_BU_32,
            percentage_blocked_BU_150,
            percentage_blocked_BU_500,
            percentage_blocked_BU_700,
            percentage_blocked_BU_1000,
            percentage_blocked_BU_87;

    public boolean testIfAllValuesCollected() {
        int simulationTime = Simulator.Simulator.sp.simulation_time;
        if ((averageSatisfactionPerBU.size() == simulationTime) && (averageSatisfactionPerUser.size() == simulationTime)
                && (averageEnergyPerBU.size() == simulationTime) && (averageHandoverPerUser.size() == simulationTime)
                && (averageRssPerUser.size() == simulationTime) && (averageRssPerBU.size() == simulationTime)
                && (averageEnergyConsumedPerUser.size() == simulationTime) && (averageBUsBlocked.size() == simulationTime)
                && (averageUsersBlocked.size() == simulationTime) && (numberRejectedUsers.size() == simulationTime)
                && (numberOfUsersServed.size() == simulationTime) && (numberBUsServed.size() == simulationTime)
                && (numberBUsRejected.size() == simulationTime) && (numberOfUsersInTheSystem.size() == simulationTime)
                && (numberOfBUsRequested.size() == simulationTime)
                && (averageNumberOfUsersInTheConnectionRequestListsPerList.size() == simulationTime)
                && (averageNumberOfConnectionRequestListsReceivedPerNetwork.size() == simulationTime)
                && (numberOfHandover.size() == simulationTime) && (processingTime.size() == simulationTime)) {
            return true;
        }
        return false;
    }

    public Statistics() {
        numberOfUsersInTheSystem = new LinkedList<Integer>();
        numberOfUsersInTheSystem_SL_0 = new LinkedList<Integer>();
        numberOfUsersInTheSystem_SL_1 = new LinkedList<Integer>();
        numberOfUsersInTheSystem_SL_2 = new LinkedList<Integer>();

        numberOfBUsRequested = new LinkedList<Integer>();
        numberOfBUsRequested_SL_0 = new LinkedList<Integer>();
        numberOfBUsRequested_SL_1 = new LinkedList<Integer>();
        numberOfBUsRequested_SL_2 = new LinkedList<Integer>();

        numberRejectedUsers = new LinkedList<Integer>();
        numberRejectedUsers_SL_0 = new LinkedList<Integer>();
        numberRejectedUsers_SL_1 = new LinkedList<Integer>();
        numberRejectedUsers_SL_2 = new LinkedList<Integer>();
        numberOfUsersServed = new LinkedList<Integer>();
        numberOfUsersServed_SL_0 = new LinkedList<Integer>();
        numberOfUsersServed_SL_1 = new LinkedList<Integer>();
        numberOfUsersServed_SL_2 = new LinkedList<Integer>();

        numberBUsServed = new LinkedList<Integer>();
        numberBUsServed_SL_0 = new LinkedList<Integer>();
        numberBUsServed_SL_1 = new LinkedList<Integer>();
        numberBUsServed_SL_2 = new LinkedList<Integer>();

        numberBUsRejected = new LinkedList<Integer>();
        numberBUsRejected_SL_0 = new LinkedList<Integer>();
        numberBUsRejected_SL_1 = new LinkedList<Integer>();
        numberBUsRejected_SL_2 = new LinkedList<Integer>();

        averageSatisfactionPerBU = new LinkedList<Double>();
        averageSatisfactionPerBU_SL_0 = new LinkedList<Double>();
        averageSatisfactionPerBU_SL_1 = new LinkedList<Double>();
        averageSatisfactionPerBU_SL_2 = new LinkedList<Double>();
        averageProfitPerBU_SL_0 = new LinkedList<Double>();
        averageProfitPerBU_SL_1 = new LinkedList<Double>();
        averageProfitPerBU_SL_2 = new LinkedList<Double>();
        averageSatisfactionPerServed_BU_SL_0 = new LinkedList<Double>();
        averageSatisfactionPerServed_BU_SL_1 = new LinkedList<Double>();
        averageSatisfactionPerServed_BU_SL_2 = new LinkedList<Double>();
        averageSatisfactionPerUser = new LinkedList<Double>();
        averageSatisfactionPerUser_SL_0 = new LinkedList<Double>();
        averageSatisfactionPerUser_SL_1 = new LinkedList<Double>();
        averageSatisfactionPerUser_SL_2 = new LinkedList<Double>();

        averageEnergyPerBU = new LinkedList<Double>();
        averageEnergyPerBU_SL_0 = new LinkedList<Double>();
        averageEnergyPerBU_SL_1 = new LinkedList<Double>();
        averageEnergyPerBU_SL_2 = new LinkedList<Double>();

        averageEnergyConsumedPerUser = new LinkedList<Double>();
        averageEnergyConsumedPerUser_SL_0 = new LinkedList<Double>();
        averageEnergyConsumedPerUser_SL_1 = new LinkedList<Double>();
        averageEnergyConsumedPerUser_SL_2 = new LinkedList<Double>();

        averageHandoverPerUser = new LinkedList<Double>();
        averageHandoverPerUser_SL_0 = new LinkedList<Double>();
        averageHandoverPerUser_SL_1 = new LinkedList<Double>();
        averageHandoverPerUser_SL_2 = new LinkedList<Double>();

        averageRssPerBU = new LinkedList<Double>();
        averageRssPerBU_SL_0 = new LinkedList<Double>();
        averageRssPerBU_SL_1 = new LinkedList<Double>();
        averageRssPerBU_SL_2 = new LinkedList<Double>();

        averageRssPerUser = new LinkedList<Double>();
        averageRssPerUser_SL_0 = new LinkedList<Double>();
        averageRssPerUser_SL_1 = new LinkedList<Double>();
        averageRssPerUser_SL_2 = new LinkedList<Double>();

        averageNumberOfUsersInTheConnectionRequestListsPerList = new LinkedList<Double>();
        averageNumberOfConnectionRequestListsReceivedPerNetwork = new LinkedList<Double>();
        averageBUsBlocked = new LinkedList<Double>();
        averageBUsBlocked_SL_0 = new LinkedList<Double>();
        averageBUsBlocked_SL_1 = new LinkedList<Double>();
        averageBUsBlocked_SL_2 = new LinkedList<Double>();

        averageUsersBlocked = new LinkedList<Double>();
        averageUsersBlocked_SL_0 = new LinkedList<Double>();
        averageUsersBlocked_SL_1 = new LinkedList<Double>();
        averageUsersBlocked_SL_2 = new LinkedList<Double>();

        numberOfHandover = new LinkedList<Double>();
        numberOfHandover_SL_0 = new LinkedList<Double>();
        numberOfHandover_SL_1 = new LinkedList<Double>();
        numberOfHandover_SL_2 = new LinkedList<Double>();

        processingTime = new LinkedList<Double>();
        DistributedIterationCount = new LinkedList<Double>();
        average_satisfaction_1000 = new LinkedList<Double>();
        average_satisfaction_1200 = new LinkedList<Double>();
        average_satisfaction_150 = new LinkedList<Double>();
        average_satisfaction_300 = new LinkedList<Double>();
        average_satisfaction_32 = new LinkedList<Double>();
        average_satisfaction_500 = new LinkedList<Double>();
        average_satisfaction_55 = new LinkedList<Double>();
        jainIndexSatisfaction = new LinkedList<Double>();
        jainIndexSatisfactionDataRate = new LinkedList<Double>();
        jainIndexSatisfactionNumberBlocked = new LinkedList<Double>();
        indexOfDispersionDatarate = new LinkedList<Double>();
        indexOfDispersionSatisfaction = new LinkedList<Double>();
        average_satisfaction_700 = new LinkedList<Double>();
        average_satisfaction_87 = new LinkedList<Double>();
        average_satisfaction_BU_1000 = new LinkedList<Double>();
        average_satisfaction_BU_1200 = new LinkedList<Double>();
        average_satisfaction_BU_150 = new LinkedList<Double>();
        average_satisfaction_BU_300 = new LinkedList<Double>();
        average_satisfaction_BU_32 = new LinkedList<Double>();
        average_satisfaction_BU_500 = new LinkedList<Double>();
        average_satisfaction_BU_55 = new LinkedList<Double>();
        average_satisfaction_BU_700 = new LinkedList<Double>();
        average_satisfaction_BU_87 = new LinkedList<Double>();
        percentage_blocked_32 = new LinkedList<Double>();
        percentage_blocked_150 = new LinkedList<Double>();
        percentage_blocked_500 = new LinkedList<Double>();
        percentage_blocked_700 = new LinkedList<Double>();
        percentage_blocked_1000 = new LinkedList<Double>();
        percentage_blocked_87 = new LinkedList<Double>();
        percentage_blocked_55 = new LinkedList<Double>();
        percentage_blocked_300 = new LinkedList<Double>();
        percentage_blocked_1200 = new LinkedList<Double>();
        percentage_blocked_BU_55 = new LinkedList<Double>();
        percentage_blocked_BU_300 = new LinkedList<Double>();
        percentage_blocked_BU_1200 = new LinkedList<Double>();
        percentage_blocked_BU_32 = new LinkedList<Double>();
        percentage_blocked_BU_150 = new LinkedList<Double>();
        percentage_blocked_BU_500 = new LinkedList<Double>();
        percentage_blocked_BU_700 = new LinkedList<Double>();
        percentage_blocked_BU_1000 = new LinkedList<Double>();
        percentage_blocked_BU_87 = new LinkedList<Double>();

        average_satisfaction_32_SL_0 = new LinkedList<Double>();
        average_satisfaction_150_SL_0 = new LinkedList<Double>();
        average_satisfaction_500_SL_0 = new LinkedList<Double>();
        average_satisfaction_700_SL_0 = new LinkedList<Double>();
        average_satisfaction_1000_SL_0 = new LinkedList<Double>();
        average_satisfaction_87_SL_0 = new LinkedList<Double>();
        average_satisfaction_55_SL_0 = new LinkedList<Double>();
        jainIndexSatisfaction_SL_0 = new LinkedList<Double>();
        indexOfDispersionSatisfaction_SL_0 = new LinkedList<Double>();
        average_satisfaction_300_SL_0 = new LinkedList<Double>();
        average_satisfaction_1200_SL_0 = new LinkedList<Double>();
        average_satisfaction_32_SL_1 = new LinkedList<Double>();
        average_satisfaction_150_SL_1 = new LinkedList<Double>();
        average_satisfaction_500_SL_1 = new LinkedList<Double>();
        average_satisfaction_700_SL_1 = new LinkedList<Double>();
        average_satisfaction_1000_SL_1 = new LinkedList<Double>();
        average_satisfaction_87_SL_1 = new LinkedList<Double>();
        average_satisfaction_55_SL_1 = new LinkedList<Double>();
        jainIndexSatisfaction_SL_1 = new LinkedList<Double>();
        indexOfDispersionSatisfaction_SL_1 = new LinkedList<Double>();
        average_satisfaction_300_SL_1 = new LinkedList<Double>();
        average_satisfaction_1200_SL_1 = new LinkedList<Double>();
        average_satisfaction_32_SL_2 = new LinkedList<Double>();
        average_satisfaction_150_SL_2 = new LinkedList<Double>();
        average_satisfaction_500_SL_2 = new LinkedList<Double>();
        average_satisfaction_700_SL_2 = new LinkedList<Double>();
        average_satisfaction_1000_SL_2 = new LinkedList<Double>();
        average_satisfaction_87_SL_2 = new LinkedList<Double>();
        average_satisfaction_55_SL_2 = new LinkedList<Double>();
        jainIndexSatisfaction_SL_2 = new LinkedList<Double>();
        indexOfDispersionSatisfaction_SL_2 = new LinkedList<Double>();
        average_satisfaction_300_SL_2 = new LinkedList<Double>();
        average_satisfaction_1200_SL_2 = new LinkedList<Double>();
//        averageSatisfactionPerBUWithUnConnectedUsers = new LinkedList<Double>();
//        averageEnergyPerBUWithUnConnectedUsers = new LinkedList<Double>();
//        averageEnergyConsumedPerUserWithUnConnectedUsers = new LinkedList<Double>();
//        averageHandoverPerUserWithUnConnectedUsers = new LinkedList<Double>();
//        averageRssPerBUWithUnConnectedUsers = new LinkedList<Double>();
//        averageRssPerUserWithUnConnectedUsers = new LinkedList<Double>();
    }

    public void updateStatisticsValues() {

        int number_served_users_for_this_second = 0, number_rejected_users_for_this_second = 0;
        int number_served_users_for_this_second_SL_0 = 0, number_rejected_users_for_this_second_SL_0 = 0;
        int number_served_users_for_this_second_SL_1 = 0, number_rejected_users_for_this_second_SL_1 = 0;
        int number_served_users_for_this_second_SL_2 = 0, number_rejected_users_for_this_second_SL_2 = 0;
        double ap_32 = 0, ap_500 = 0, ap_1000 = 0, ap_87 = 0, ap_150 = 0, ap_700 = 0, ap_55 = 0, ap_300 = 0, ap_1200 = 0;
        double bs_32 = 0, bs_500 = 0, bs_1000 = 0, bs_87 = 0, bs_150 = 0, bs_700 = 0, bs_55 = 0, bs_300 = 0, bs_1200 = 0;
        double rejected_32 = 0, rejected_500 = 0, rejected_1000 = 0, rejected_87 = 0, rejected_150 = 0, rejected_700 = 0, rejected_55 = 0, rejected_300 = 0, rejected_1200 = 0;
        double totalSatisfactionForThisSecond = 0, totalSatisfactionForThisSecondMultipliedByBUs = 0, totalRssForThisSecond = 0, totalPowerConsumedForThisSecond = 0;
        int totalServedBUsForThisSecond = 0, totalRejectedBUsForThisSecond = 0, totalRequestedBUsForThisSecond = 0;
        int totalNumberOfHandoversForThisSecond = 0;

        double totalSatisfactionForThisSecond_SL_0 = 0, totalSatisfactionForThisSecondMultipliedByBUs_SL_0 = 0, totalRssForThisSecondTimeBU_SL_0 = 0, totalRssForThisSecond_SL_0 = 0, totalPowerConsumedForThisSecond_SL_0 = 0;
        double totalProfitForThisSecondMultipliedByBUs_SL_0 = 0, totalProfitForThisSecondMultipliedByBUs_SL_1 = 0, totalProfitForThisSecondMultipliedByBUs_SL_2 = 0;
        int totalServedBUsForThisSecond_SL_0 = 0, totalRejectedBUsForThisSecond_SL_0 = 0, totalRequestedBUsForThisSecond_SL_0 = 0;
        int totalNumberOfHandoversForThisSecond_SL_0 = 0;

        double totalSatisfactionForThisSecond_SL_1 = 0, totalSatisfactionForThisSecondMultipliedByBUs_SL_1 = 0, totalRssForThisSecondTimeBU_SL_1 = 0, totalRssForThisSecond_SL_1 = 0, totalPowerConsumedForThisSecond_SL_1 = 0;
        int totalServedBUsForThisSecond_SL_1 = 0, totalRejectedBUsForThisSecond_SL_1 = 0, totalRequestedBUsForThisSecond_SL_1 = 0;
        int totalNumberOfHandoversForThisSecond_SL_1 = 0;

        double totalSatisfactionForThisSecond_SL_2 = 0, totalSatisfactionForThisSecondMultipliedByBUs_SL_2 = 0, totalRssForThisSecondTimeBU_SL_2 = 0, totalRssForThisSecond_SL_2 = 0, totalPowerConsumedForThisSecond_SL_2 = 0;
        int totalServedBUsForThisSecond_SL_2 = 0, totalRejectedBUsForThisSecond_SL_2 = 0, totalRequestedBUsForThisSecond_SL_2 = 0;
        int totalNumberOfHandoversForThisSecond_SL_2 = 0;

        int totalNumberOfUsersInRequestConnectionList = 0;
        int totalNumberOfConnectionRequestListsReceived = 0;
        int totalNumberUsers_SL_0 = 0, totalNumberUsers_SL_1 = 0, totalNumberUsers_SL_2 = 0;
        for (Peer prf : Simulator.Simulator.sp.Peers) {
            if (prf.getService_level() == 0) {
                totalNumberUsers_SL_0++;
            } else if (prf.getService_level() == 1) {
                totalNumberUsers_SL_1++;
            } else if (prf.getService_level() == 2) {
                totalNumberUsers_SL_2++;
            }
        }
        numberOfUsersInTheSystem.add(Simulator.Simulator.sp.Peers.size());
        numberOfUsersInTheSystem_SL_0.add(totalNumberUsers_SL_0);
        numberOfUsersInTheSystem_SL_1.add(totalNumberUsers_SL_1);
        numberOfUsersInTheSystem_SL_2.add(totalNumberUsers_SL_2);
        double value_average_satisfaction_1000 = 0,
                value_average_satisfaction_1200 = 0,
                value_average_satisfaction_150 = 0,
                value_average_satisfaction_300 = 0,
                value_average_satisfaction_32 = 0,
                value_average_satisfaction_500 = 0,
                value_average_satisfaction_55 = 0,
                value_average_satisfaction_700 = 0,
                value_average_satisfaction_87 = 0;
        double value_average_satisfaction_1000_SL_0 = 0,
                value_average_satisfaction_1200_SL_0 = 0,
                value_average_satisfaction_150_SL_0 = 0,
                value_average_satisfaction_300_SL_0 = 0,
                value_average_satisfaction_32_SL_0 = 0,
                value_average_satisfaction_500_SL_0 = 0,
                value_average_satisfaction_55_SL_0 = 0,
                value_average_satisfaction_700_SL_0 = 0,
                value_average_satisfaction_87_SL_0 = 0;
        double value_average_satisfaction_1000_SL_1 = 0,
                value_average_satisfaction_1200_SL_1 = 0,
                value_average_satisfaction_150_SL_1 = 0,
                value_average_satisfaction_300_SL_1 = 0,
                value_average_satisfaction_32_SL_1 = 0,
                value_average_satisfaction_500_SL_1 = 0,
                value_average_satisfaction_55_SL_1 = 0,
                value_average_satisfaction_700_SL_1 = 0,
                value_average_satisfaction_87_SL_1 = 0;
        double value_average_satisfaction_1000_SL_2 = 0,
                value_average_satisfaction_1200_SL_2 = 0,
                value_average_satisfaction_150_SL_2 = 0,
                value_average_satisfaction_300_SL_2 = 0,
                value_average_satisfaction_32_SL_2 = 0,
                value_average_satisfaction_500_SL_2 = 0,
                value_average_satisfaction_55_SL_2 = 0,
                value_average_satisfaction_700_SL_2 = 0,
                value_average_satisfaction_87_SL_2 = 0;
//                average_satisfaction_BU_1000 = 0,
//                average_satisfaction_BU_1200 = 0,
//                average_satisfaction_BU_150 = 0,
//                average_satisfaction_BU_300 = 0,
//                average_satisfaction_BU_32 = 0,
//                average_satisfaction_BU_500 = 0,
//                average_satisfaction_BU_55 = 0,
//                average_satisfaction_BU_700 = 0,
//                average_satisfaction_BU_87 = 0;
        double number_blocked_1000 = 0,
                number_blocked_1200 = 0,
                number_blocked_150 = 0,
                number_blocked_300 = 0,
                number_blocked_32 = 0,
                number_blocked_500 = 0,
                number_blocked_55 = 0,
                number_blocked_700 = 0,
                number_blocked_87 = 0;
        double datarate_blocked_1000 = 0,
                datarate_blocked_1200 = 0,
                datarate_blocked_150 = 0,
                datarate_blocked_300 = 0,
                datarate_blocked_32 = 0,
                datarate_blocked_500 = 0,
                datarate_blocked_55 = 0,
                datarate_blocked_700 = 0,
                datarate_blocked_87 = 0;
//                number_blocked_BU_1000 = 0,
//                number_blocked_BU_1200 = 0,
//                number_blocked_BU_150 = 0,
//                number_blocked_BU_300 = 0,
//                number_blocked_BU_32 = 0,
//                number_blocked_BU_500 = 0,
//                number_blocked_BU_55 = 0,
//                number_blocked_BU_700 = 0,
//                number_blocked_BU_87 = 0;

        double number_all_1000 = 0,
                number_all_1200 = 0,
                number_all_150 = 0,
                number_all_300 = 0,
                number_all_32 = 0,
                number_all_500 = 0,
                number_all_55 = 0,
                number_all_700 = 0,
                number_all_87 = 0;
        double datarate_all_1000 = 0,
                datarate_all_1200 = 0,
                datarate_all_150 = 0,
                datarate_all_300 = 0,
                datarate_all_32 = 0,
                datarate_all_500 = 0,
                datarate_all_55 = 0,
                datarate_all_700 = 0,
                datarate_all_87 = 0;
        double datarate_all_1000_SL_0 = 0,
                datarate_all_1200_SL_0 = 0,
                datarate_all_150_SL_0 = 0,
                datarate_all_300_SL_0 = 0,
                datarate_all_32_SL_0 = 0,
                datarate_all_500_SL_0 = 0,
                datarate_all_55_SL_0 = 0,
                datarate_all_700_SL_0 = 0,
                datarate_all_87_SL_0 = 0;
        double datarate_all_1000_SL_1 = 0,
                datarate_all_1200_SL_1 = 0,
                datarate_all_150_SL_1 = 0,
                datarate_all_300_SL_1 = 0,
                datarate_all_32_SL_1 = 0,
                datarate_all_500_SL_1 = 0,
                datarate_all_55_SL_1 = 0,
                datarate_all_700_SL_1 = 0,
                datarate_all_87_SL_1 = 0;
        double datarate_all_1000_SL_2 = 0,
                datarate_all_1200_SL_2 = 0,
                datarate_all_150_SL_2 = 0,
                datarate_all_300_SL_2 = 0,
                datarate_all_32_SL_2 = 0,
                datarate_all_500_SL_2 = 0,
                datarate_all_55_SL_2 = 0,
                datarate_all_700_SL_2 = 0,
                datarate_all_87_SL_2 = 0;
//                number_all_BU_1000 = 0,
//                number_all_BU_1200 = 0,
//                number_all_BU_150 = 0,
//                number_all_BU_300 = 0,
//                number_all_BU_32 = 0,
//                number_all_BU_500 = 0,
//                number_all_BU_55 = 0,
//                number_all_BU_700 = 0,
//                number_all_BU_87 = 0;
        for (Peer p : Simulator.Simulator.sp.Peers) {
            totalRequestedBUsForThisSecond += p.getDatarate();
            boolean sl1 = false, sl2 = false, sl0 = false;
            if (p.getService_level() == 0) {
                sl0 = true;
                totalRequestedBUsForThisSecond_SL_0 += p.getDatarate();
            } else if (p.getService_level() == 1) {
                sl1 = true;
                totalRequestedBUsForThisSecond_SL_1 += p.getDatarate();
            } else if (p.getService_level() == 2) {
                sl2 = true;
                totalRequestedBUsForThisSecond_SL_2 += p.getDatarate();
            }
            if (p.getDatarate() == 32) {
                datarate_all_32 += 32;
                number_all_32++;
                if (sl0) {
                    datarate_all_32_SL_0 += 32;
                } else if (sl1) {
                    datarate_all_32_SL_1 += 32;
                } else if (sl2) {
                    datarate_all_32_SL_2 += 32;
                }
            } else if (p.getDatarate() == 500) {
                datarate_all_500 += 500;
                number_all_500++;
                if (sl0) {
                    datarate_all_500_SL_0 += 500;
                } else if (sl1) {
                    datarate_all_500_SL_1 += 500;
                } else if (sl2) {
                    datarate_all_500_SL_2 += 500;
                }
            } else if (p.getDatarate() == 1000) {
                datarate_all_1000 += 1000;
                number_all_1000++;
                if (sl0) {
                    datarate_all_1000_SL_0 += 1000;
                } else if (sl1) {
                    datarate_all_1000_SL_1 += 1000;
                } else if (sl2) {
                    datarate_all_1000_SL_2 += 1000;
                }
            } else if (p.getDatarate() == 87) {
                datarate_all_87 += 87;
                number_all_87++;
                if (sl0) {
                    datarate_all_87_SL_0 += 87;
                } else if (sl1) {
                    datarate_all_87_SL_1 += 87;
                } else if (sl2) {
                    datarate_all_87_SL_2 += 87;
                }
            } else if (p.getDatarate() == 150) {
                datarate_all_150 += 150;
                number_all_150++;
                if (sl0) {
                    datarate_all_150_SL_0 += 150;
                } else if (sl1) {
                    datarate_all_150_SL_1 += 150;
                } else if (sl2) {
                    datarate_all_150_SL_2 += 150;
                }
            } else if (p.getDatarate() == 700) {
                datarate_all_700 += 700;
                number_all_700++;
                if (sl0) {
                    datarate_all_700_SL_0 += 700;
                } else if (sl1) {
                    datarate_all_700_SL_1 += 700;
                } else if (sl2) {
                    datarate_all_700_SL_2 += 700;
                }
            } else if (p.getDatarate() == 55) {
                datarate_all_55 += 55;
                number_all_55++;
                if (sl0) {
                    datarate_all_55_SL_0 += 55;
                } else if (sl1) {
                    datarate_all_55_SL_1 += 55;
                } else if (sl2) {
                    datarate_all_55_SL_2 += 55;
                }
            } else if (p.getDatarate() == 300) {
                datarate_all_300 += 300;
                number_all_300++;
                if (sl0) {
                    datarate_all_300_SL_0 += 300;
                } else if (sl1) {
                    datarate_all_300_SL_1 += 300;
                } else if (sl2) {
                    datarate_all_300_SL_2 += 300;
                }
            } else if (p.getDatarate() == 1200) {
                datarate_all_1200 += 1200;
                number_all_1200++;
                if (sl0) {
                    datarate_all_1200_SL_0 += 1200;
                } else if (sl1) {
                    datarate_all_1200_SL_1 += 1200;
                } else if (sl2) {
                    datarate_all_1200_SL_2 += 1200;
                }
            }
            if (p.isConnected()) {
                if (p.getDatarate() == 32) {
                    if (sl0) {
                        value_average_satisfaction_32_SL_0 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl1) {
                        value_average_satisfaction_32_SL_1 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl2) {
                        value_average_satisfaction_32_SL_2 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    }
                    value_average_satisfaction_32 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                } else if (p.getDatarate() == 500) {
                    if (sl0) {
                        value_average_satisfaction_500_SL_0 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl1) {
                        value_average_satisfaction_500_SL_1 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl2) {
                        value_average_satisfaction_500_SL_2 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    }
                    value_average_satisfaction_500 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                } else if (p.getDatarate() == 1000) {
                    if (sl0) {
                        value_average_satisfaction_1000_SL_0 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl1) {
                        value_average_satisfaction_1000_SL_1 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl2) {
                        value_average_satisfaction_1000_SL_2 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    }
                    value_average_satisfaction_1000 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                } else if (p.getDatarate() == 87) {
                    if (sl0) {
                        value_average_satisfaction_87_SL_0 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl1) {
                        value_average_satisfaction_87_SL_1 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl2) {
                        value_average_satisfaction_87_SL_2 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    }
                    value_average_satisfaction_87 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                } else if (p.getDatarate() == 150) {
                    if (sl0) {
                        value_average_satisfaction_150_SL_0 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl1) {
                        value_average_satisfaction_150_SL_1 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl2) {
                        value_average_satisfaction_150_SL_2 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    }
                    value_average_satisfaction_150 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                } else if (p.getDatarate() == 700) {
                    if (sl0) {
                        value_average_satisfaction_700_SL_0 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl1) {
                        value_average_satisfaction_700_SL_1 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl2) {
                        value_average_satisfaction_700_SL_2 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    }
                    value_average_satisfaction_700 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                } else if (p.getDatarate() == 55) {
                    if (sl0) {
                        value_average_satisfaction_55_SL_0 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl1) {
                        value_average_satisfaction_55_SL_1 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl2) {
                        value_average_satisfaction_55_SL_2 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    }
                    value_average_satisfaction_55 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                } else if (p.getDatarate() == 300) {
                    if (sl0) {
                        value_average_satisfaction_300_SL_0 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl1) {
                        value_average_satisfaction_300_SL_1 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl2) {
                        value_average_satisfaction_300_SL_2 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    }
                    value_average_satisfaction_300 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                } else if (p.getDatarate() == 1200) {
                    if (sl0) {
                        value_average_satisfaction_1200_SL_0 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl1) {
                        value_average_satisfaction_1200_SL_1 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    } else if (sl2) {
                        value_average_satisfaction_1200_SL_2 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                    }
                    value_average_satisfaction_1200 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork())) * p.getDatarate();
                }
                if (p.getPreviousConnectedNetwork() != p.getCurrentConnectedNetwork()) {
                    if (p.getPreviousConnectedNetwork() != -1) {
                        totalNumberOfHandoversForThisSecond++;
                        if (p.getService_level() == 0) {
                            totalNumberOfHandoversForThisSecond_SL_0++;
                        } else if (p.getService_level() == 1) {
                            totalNumberOfHandoversForThisSecond_SL_1++;
                        } else if (p.getService_level() == 2) {
                            totalNumberOfHandoversForThisSecond_SL_2++;
                        }
                    }
                }
                double globalMinPCSL0 = 0, globalMaxRssSL0 = 0;
                for (Peer pmaxmain : Simulator.Simulator.sp.Peers) {
//                    if (pmaxmain.getService_level() == 0) {
                    if (globalMinPCSL0 == 0) {
                        globalMinPCSL0 = pmaxmain.getMin_available_pc();
                    }
                    if (globalMaxRssSL0 == 0) {
                        globalMaxRssSL0 = pmaxmain.getMax_available_s();
                    }
                    if (p.getMin_available_pc() < globalMinPCSL0) {
                        globalMinPCSL0 = pmaxmain.getMin_available_pc();
                    }
                    if (p.getMax_available_s() > globalMaxRssSL0) {
                        globalMaxRssSL0 = pmaxmain.getMax_available_s();
                    }
//                    }
                }
//                double globalMinPCSL0 = 0, globalMaxRssSL0 = 0;
//                for (Peer pmaxmain : Simulator.Simulator.sp.Peers) {
//                    if (pmaxmain.getService_level() == 0) {
//                        if (globalMinPCSL0 == 0) {
//                            globalMinPCSL0 = pmaxmain.getMin_available_pc();
//                        }
//                        if (globalMaxRssSL0 == 0) {
//                            globalMaxRssSL0 = pmaxmain.getMax_available_s();
//                        }
//                        if (p.getMin_available_pc() < globalMinPCSL0) {
//                            globalMinPCSL0 = pmaxmain.getMin_available_pc();
//                        }
//                        if (p.getMax_available_s() > globalMaxRssSL0) {
//                            globalMaxRssSL0 = pmaxmain.getMax_available_s();
//                        }
//                    }
//                }
//                double globalMinPCSL1 = 0, globalMaxRssSL1 = 0;
//                for (Peer pmaxmain : Simulator.Simulator.sp.Peers) {
//                    if (pmaxmain.getService_level() == 1) {
//                        if (globalMinPCSL1 == 0) {
//                            globalMinPCSL1 = pmaxmain.getMin_available_pc();
//                        }
//                        if (globalMaxRssSL1 == 0) {
//                            globalMaxRssSL1 = pmaxmain.getMax_available_s();
//                        }
//                        if (p.getMin_available_pc() < globalMinPCSL1) {
//                            globalMinPCSL1 = pmaxmain.getMin_available_pc();
//                        }
//                        if (p.getMax_available_s() > globalMaxRssSL1) {
//                            globalMaxRssSL1 = pmaxmain.getMax_available_s();
//                        }
//                    }
//                }
//                double globalMinPCSL2 = 0, globalMaxRssSL2 = 0;
//                for (Peer pmaxmain : Simulator.Simulator.sp.Peers) {
//                    if (pmaxmain.getService_level() == 2) {
//                        if (globalMinPCSL2 == 0) {
//                            globalMinPCSL2 = pmaxmain.getMin_available_pc();
//                        }
//                        if (globalMaxRssSL2 == 0) {
//                            globalMaxRssSL2 = pmaxmain.getMax_available_s();
//                        }
//                        if (p.getMin_available_pc() < globalMinPCSL2) {
//                            globalMinPCSL2 = pmaxmain.getMin_available_pc();
//                        }
//                        if (p.getMax_available_s() > globalMaxRssSL2) {
//                            globalMaxRssSL2 = pmaxmain.getMax_available_s();
//                        }
//                    }
//                }
                if (p.getService_level() == 0) {
                    number_served_users_for_this_second_SL_0++;
                    totalServedBUsForThisSecond_SL_0 += p.getDatarate();
                    totalPowerConsumedForThisSecond_SL_0 += p.getConnectedNetwork().getPc(p.getDatarate());
                    totalRssForThisSecond_SL_0 += Calulation.CalculateSignalStrength.calculate_signal_strength(p, p.getConnectedNetwork());
                    totalRssForThisSecondTimeBU_SL_0 += Calulation.CalculateSignalStrength.calculate_signal_strength(p, p.getConnectedNetwork()) * p.getDatarate();
                    totalSatisfactionForThisSecond_SL_0 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork()));
//                    totalSatisfactionForThisSecond_SL_0 += (NetworkCostFunctionSmaller.calculate_Network_cost_function(p, p.getConnectedNetwork(), globalMaxRssSL0, globalMinPCSL0)) / p.getDatarate();
//                    totalSatisfactionForThisSecondMultipliedByBUs_SL_0 += (p.getDatarate()) * CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork()));
                    totalSatisfactionForThisSecondMultipliedByBUs_SL_0 += ((double) p.getDatarate() * CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getConnectedNetwork().getId())));
                    totalProfitForThisSecondMultipliedByBUs_SL_0 += (NetworkCostFunctionSmaller.calculate_Network_cost_function(p, p.getConnectedNetwork(), globalMaxRssSL0, globalMinPCSL0));
                } else if (p.getService_level() == 1) {
                    number_served_users_for_this_second_SL_1++;
                    totalServedBUsForThisSecond_SL_1 += p.getDatarate();
                    totalPowerConsumedForThisSecond_SL_1 += p.getConnectedNetwork().getPc(p.getDatarate());
                    totalRssForThisSecond_SL_1 += Calulation.CalculateSignalStrength.calculate_signal_strength(p, p.getConnectedNetwork());;
                    totalRssForThisSecondTimeBU_SL_1 += Calulation.CalculateSignalStrength.calculate_signal_strength(p, p.getConnectedNetwork()) * p.getDatarate();
                    totalSatisfactionForThisSecond_SL_1 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork()));

//                    totalSatisfactionForThisSecondMultipliedByBUs_SL_1 += (p.getDatarate()) * CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork()));
                    totalSatisfactionForThisSecondMultipliedByBUs_SL_1 += ((double) p.getDatarate() * CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getConnectedNetwork().getId())));
                    totalProfitForThisSecondMultipliedByBUs_SL_1 += (NetworkCostFunctionSmaller.calculate_Network_cost_function(p, p.getConnectedNetwork(), globalMaxRssSL0, globalMinPCSL0));
                } else if (p.getService_level() == 2) {
                    number_served_users_for_this_second_SL_2++;
                    totalServedBUsForThisSecond_SL_2 += p.getDatarate();
                    totalPowerConsumedForThisSecond_SL_2 += p.getConnectedNetwork().getPc(p.getDatarate());
                    totalRssForThisSecond_SL_2 += Calulation.CalculateSignalStrength.calculate_signal_strength(p, p.getConnectedNetwork());;
                    totalRssForThisSecondTimeBU_SL_2 += Calulation.CalculateSignalStrength.calculate_signal_strength(p, p.getConnectedNetwork()) * p.getDatarate();
                    totalSatisfactionForThisSecond_SL_2 += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork()));
//                    totalSatisfactionForThisSecondMultipliedByBUs_SL_2 += (p.getDatarate()) * CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork()));
                    totalSatisfactionForThisSecondMultipliedByBUs_SL_2 += ((double) p.getDatarate() * CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getConnectedNetwork().getId())));
                    totalProfitForThisSecondMultipliedByBUs_SL_2 += (NetworkCostFunctionSmaller.calculate_Network_cost_function(p, p.getConnectedNetwork(), globalMaxRssSL0, globalMinPCSL0));
                }
                number_served_users_for_this_second++;
                totalServedBUsForThisSecond += p.getDatarate();
                totalPowerConsumedForThisSecond += p.getConnectedNetwork().getPc(p.getDatarate());
                totalRssForThisSecond += Calulation.CalculateSignalStrength.calculate_signal_strength(p, p.getConnectedNetwork());;
                totalSatisfactionForThisSecond += CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork()));
                totalSatisfactionForThisSecondMultipliedByBUs += ((double) p.getDatarate()) * CostFunction.calculate_cost_function(p, p.getNetworkSignalStrength(p.getCurrentConnectedNetwork()));

            } else {
                if (p.getPreviousConnectedNetwork() != p.getCurrentConnectedNetwork()) {
                    if (p.getPreviousConnectedNetwork() != -1) {
                        totalNumberOfHandoversForThisSecond++;
                        if (p.getService_level() == 0) {
                            totalNumberOfHandoversForThisSecond_SL_0++;
                        } else if (p.getService_level() == 1) {
                            totalNumberOfHandoversForThisSecond_SL_1++;
                        } else if (p.getService_level() == 2) {
                            totalNumberOfHandoversForThisSecond_SL_2++;
                        }
                    }
                }
                if (p.getService_level() == 0) {
                    number_rejected_users_for_this_second_SL_0++;
                    totalRejectedBUsForThisSecond_SL_0 += p.getDatarate();
                } else if (p.getService_level() == 1) {
                    number_rejected_users_for_this_second_SL_1++;
                    totalRejectedBUsForThisSecond_SL_1 += p.getDatarate();
                } else if (p.getService_level() == 2) {
                    number_rejected_users_for_this_second_SL_2++;
                    totalRejectedBUsForThisSecond_SL_2 += p.getDatarate();
                }
                number_rejected_users_for_this_second++;
                totalRejectedBUsForThisSecond += p.getDatarate();
                if (p.getDatarate() == 32) {
                    number_blocked_32++;
                    datarate_blocked_32 += p.getDatarate();
                } else if (p.getDatarate() == 500) {
                    number_blocked_500++;
                    datarate_blocked_500 += p.getDatarate();
                } else if (p.getDatarate() == 1000) {
                    number_blocked_1000++;
                    datarate_blocked_1000 += p.getDatarate();
                } else if (p.getDatarate() == 87) {
                    number_blocked_87++;
                    datarate_blocked_87 += p.getDatarate();
                } else if (p.getDatarate() == 150) {
                    number_blocked_150++;
                    datarate_blocked_150 += p.getDatarate();
                } else if (p.getDatarate() == 700) {
                    number_blocked_700++;
                    datarate_blocked_700 += p.getDatarate();
                } else if (p.getDatarate() == 55) {
                    number_blocked_55++;
                    datarate_blocked_55 += p.getDatarate();
                } else if (p.getDatarate() == 300) {
                    number_blocked_300++;
                    datarate_blocked_300 += p.getDatarate();
                } else if (p.getDatarate() == 1200) {
                    number_blocked_1200++;
                    datarate_blocked_1200 += p.getDatarate();
                }

            }
        }
        for (Network n : Simulator.Simulator.sp.Netowrks) {
            totalNumberOfConnectionRequestListsReceived += n.getNumberOfRequestListsReceived();
//            System.out.println("number connection requests received: " + n.getNumberOfRequestListsReceived());
//            System.out.println("average number of users in the list is: " + (double) n.getTotalNumberOfUsersInList() / (double) n.getNumberOfRequestListsReceived());
            totalNumberOfUsersInRequestConnectionList += n.getTotalNumberOfUsersInList();
            n.setNumberOfRequestListsReceived(0);
            n.setTotalNumberOfUsersInList(0);
            if (n.isAP()) {
                for (Peer p : n.getConnectedPeers()) {
                    if (p.getDatarate() == 32) {
                        ap_32++;
                    }
                    if (p.getDatarate() == 500) {
                        ap_500++;
                    }
                    if (p.getDatarate() == 1000) {
                        ap_1000++;
                    }
                    if (p.getDatarate() == 87) {
                        ap_87++;
                    }
                    if (p.getDatarate() == 150) {
                        ap_150++;
                    }
                    if (p.getDatarate() == 700) {
                        ap_700++;
                    }
                    if (p.getDatarate() == 55) {
                        ap_55++;
                    }
                    if (p.getDatarate() == 300) {
                        ap_300++;
                    }
                    if (p.getDatarate() == 1200) {
                        ap_1200++;
                    }

                }

            } else {
                for (Peer p : n.getConnectedPeers()) {
                    if (p.getDatarate() == 32) {
                        ap_32++;
                    }
                    if (p.getDatarate() == 500) {
                        bs_500++;
                    }
                    if (p.getDatarate() == 1000) {
                        bs_1000++;
                    }
                    if (p.getDatarate() == 87) {
                        bs_87++;
                    }
                    if (p.getDatarate() == 150) {
                        bs_150++;
                    }
                    if (p.getDatarate() == 700) {
                        bs_700++;
                    }
                    if (p.getDatarate() == 55) {
                        bs_55++;
                    }
                    if (p.getDatarate() == 300) {
                        bs_300++;
                    }
                    if (p.getDatarate() == 1200) {
                        bs_1200++;
                    }

                }
            }
        }

        if (number_served_users_for_this_second == 0) {
            number_served_users_for_this_second = 1;
        }
        if (number_served_users_for_this_second_SL_0 == 0) {
            number_served_users_for_this_second_SL_0 = 1;
        }
        if (number_served_users_for_this_second_SL_1 == 0) {
            number_served_users_for_this_second_SL_1 = 1;
        }
        if (number_served_users_for_this_second_SL_2 == 0) {
            number_served_users_for_this_second_SL_2 = 1;
        }
//        System.out.println("unconnected users " + number_rejected_users_for_this_second);

        numberOfHandover.add((double) totalNumberOfHandoversForThisSecond);
        averageBUsBlocked.add(((double) totalRejectedBUsForThisSecond / (double) totalRequestedBUsForThisSecond) * 100.0);
        averageUsersBlocked.add(((double) number_rejected_users_for_this_second / (double) Simulator.Simulator.sp.Peers.size()) * 100.0);
//        System.out.println(""+(number_rejected_users_for_this_second+"+"+number_served_users_for_this_second)+"/"+Simulator.Simulator.sp.Peers.size());
//        System.out.println(((double) number_rejected_users_for_this_second / (double) Simulator.Simulator.sp.Peers.size()));
        averageNumberOfUsersInTheConnectionRequestListsPerList.add((double) totalNumberOfUsersInRequestConnectionList / (double) totalNumberOfConnectionRequestListsReceived);
        averageNumberOfConnectionRequestListsReceivedPerNetwork.add((double) totalNumberOfConnectionRequestListsReceived / (double) Simulator.Simulator.sp.Netowrks.size());
        numberOfBUsRequested.add(totalRequestedBUsForThisSecond);
        numberRejectedUsers.add(number_rejected_users_for_this_second);
        numberOfUsersServed.add(number_served_users_for_this_second);
        numberBUsServed.add(totalServedBUsForThisSecond);
        numberBUsRejected.add(totalRejectedBUsForThisSecond);
        averageSatisfactionPerUser.add((double) totalSatisfactionForThisSecond / (double) Simulator.Simulator.sp.Peers.size());
        averageSatisfactionPerBU.add((double) totalSatisfactionForThisSecondMultipliedByBUs / (double) totalRequestedBUsForThisSecond);
        averageEnergyPerBU.add((double) totalPowerConsumedForThisSecond / (double) totalServedBUsForThisSecond);
        averageEnergyConsumedPerUser.add((double) totalPowerConsumedForThisSecond / (double) number_served_users_for_this_second);
        averageHandoverPerUser.add((double) totalNumberOfHandoversForThisSecond / (double) number_served_users_for_this_second);
//        System.out.println("number of handovers in this second: " + totalNumberOfHandoversForThisSecond);
        averageRssPerBU.add((double) totalRssForThisSecond / (double) totalRequestedBUsForThisSecond);
        averageRssPerUser.add((double) totalRssForThisSecond / (double) Simulator.Simulator.sp.Peers.size());

        numberOfHandover_SL_0.add((double) totalNumberOfHandoversForThisSecond_SL_0);
        averageBUsBlocked_SL_0.add(((double) totalRejectedBUsForThisSecond_SL_0 / (double) totalRequestedBUsForThisSecond_SL_0) * 100.0);
        averageUsersBlocked_SL_0.add(((double) number_rejected_users_for_this_second_SL_0 / (double) totalNumberUsers_SL_0) * 100.0);
//        System.out.println(""+(number_rejected_users_for_this_second+"+"+number_served_users_for_this_second)+"/"+Simulator.Simulator.sp.Peers.size());
//        System.out.println(((double) number_rejected_users_for_this_second / (double) Simulator.Simulator.sp.Peers.size()));
        numberOfBUsRequested_SL_0.add(totalRequestedBUsForThisSecond_SL_0);
        numberRejectedUsers_SL_0.add(number_rejected_users_for_this_second_SL_0);
        numberOfUsersServed_SL_0.add(number_served_users_for_this_second_SL_0);
        numberBUsServed_SL_0.add(totalServedBUsForThisSecond_SL_0);
        numberBUsRejected_SL_0.add(totalRejectedBUsForThisSecond_SL_0);
        averageSatisfactionPerUser_SL_0.add(totalSatisfactionForThisSecond_SL_0 / totalNumberUsers_SL_0);
        averageSatisfactionPerBU_SL_0.add(totalSatisfactionForThisSecondMultipliedByBUs_SL_0 / totalRequestedBUsForThisSecond_SL_0);
        averageProfitPerBU_SL_0.add(totalProfitForThisSecondMultipliedByBUs_SL_0 / totalRequestedBUsForThisSecond_SL_0);
        averageSatisfactionPerServed_BU_SL_0.add(totalSatisfactionForThisSecondMultipliedByBUs_SL_0 / totalServedBUsForThisSecond_SL_0);
        averageEnergyPerBU_SL_0.add(totalPowerConsumedForThisSecond_SL_0 / totalServedBUsForThisSecond_SL_0);
        averageEnergyConsumedPerUser_SL_0.add(totalPowerConsumedForThisSecond_SL_0 / number_served_users_for_this_second_SL_0);
        averageHandoverPerUser_SL_0.add((double) totalNumberOfHandoversForThisSecond_SL_0 / number_served_users_for_this_second_SL_0);
//        System.out.println("number of handovers in this second: " + totalNumberOfHandoversForThisSecond);
        averageRssPerBU_SL_0.add(totalRssForThisSecondTimeBU_SL_0 / totalServedBUsForThisSecond_SL_0);
        averageRssPerUser_SL_0.add(totalRssForThisSecond_SL_0 / totalNumberUsers_SL_0);

        numberOfHandover_SL_1.add((double) totalNumberOfHandoversForThisSecond_SL_1);
        averageBUsBlocked_SL_1.add(((double) totalRejectedBUsForThisSecond_SL_1 / (double) totalRequestedBUsForThisSecond_SL_1) * 100.0);
        averageUsersBlocked_SL_1.add(((double) number_rejected_users_for_this_second_SL_1 / (double) totalNumberUsers_SL_1) * 100.0);
//        System.out.println(""+(number_rejected_users_for_this_second+"+"+number_served_users_for_this_second)+"/"+Simulator.Simulator.sp.Peers.size());
//        System.out.println(((double) number_rejected_users_for_this_second / (double) Simulator.Simulator.sp.Peers.size()));
        numberOfBUsRequested_SL_1.add(totalRequestedBUsForThisSecond_SL_1);
        numberRejectedUsers_SL_1.add(number_rejected_users_for_this_second_SL_1);
        numberOfUsersServed_SL_1.add(number_served_users_for_this_second_SL_1);
        numberBUsServed_SL_1.add(totalServedBUsForThisSecond_SL_1);
        numberBUsRejected_SL_1.add(totalRejectedBUsForThisSecond_SL_1);
        averageSatisfactionPerUser_SL_1.add(totalSatisfactionForThisSecond_SL_1 / totalNumberUsers_SL_1);
        averageSatisfactionPerBU_SL_1.add(totalSatisfactionForThisSecondMultipliedByBUs_SL_1 / totalRequestedBUsForThisSecond_SL_1);
        averageProfitPerBU_SL_1.add(totalProfitForThisSecondMultipliedByBUs_SL_1 / totalRequestedBUsForThisSecond_SL_1);
        averageSatisfactionPerServed_BU_SL_1.add(totalSatisfactionForThisSecondMultipliedByBUs_SL_1 / totalServedBUsForThisSecond_SL_1);
        averageEnergyPerBU_SL_1.add(totalPowerConsumedForThisSecond_SL_1 / totalServedBUsForThisSecond_SL_1);
        averageEnergyConsumedPerUser_SL_1.add(totalPowerConsumedForThisSecond_SL_1 / number_served_users_for_this_second_SL_1);
        averageHandoverPerUser_SL_1.add((double) totalNumberOfHandoversForThisSecond_SL_1 / number_served_users_for_this_second_SL_1);
//        System.out.println("number of handovers in this second: " + totalNumberOfHandoversForThisSecond);
        averageRssPerBU_SL_1.add(totalRssForThisSecondTimeBU_SL_1 / totalServedBUsForThisSecond_SL_1);
        averageRssPerUser_SL_1.add(totalRssForThisSecond_SL_1 / totalNumberUsers_SL_1);

        numberOfHandover_SL_2.add((double) totalNumberOfHandoversForThisSecond_SL_2);
        averageBUsBlocked_SL_2.add(((double) totalRejectedBUsForThisSecond_SL_2 / (double) totalRequestedBUsForThisSecond_SL_2) * 100.0);
        averageUsersBlocked_SL_2.add(((double) number_rejected_users_for_this_second_SL_2 / (double) totalNumberUsers_SL_2) * 100.0);
//        System.out.println(""+(number_rejected_users_for_this_second+"+"+number_served_users_for_this_second)+"/"+Simulator.Simulator.sp.Peers.size());
//        System.out.println(((double) number_rejected_users_for_this_second / (double) Simulator.Simulator.sp.Peers.size()));
        numberOfBUsRequested_SL_2.add(totalRequestedBUsForThisSecond_SL_2);
        numberRejectedUsers_SL_2.add(number_rejected_users_for_this_second_SL_2);
        numberOfUsersServed_SL_2.add(number_served_users_for_this_second_SL_2);
        numberBUsServed_SL_2.add(totalServedBUsForThisSecond_SL_2);
        numberBUsRejected_SL_2.add(totalRejectedBUsForThisSecond_SL_2);
        averageSatisfactionPerUser_SL_2.add((double) totalSatisfactionForThisSecond_SL_2 / (double) totalNumberUsers_SL_2);
        averageSatisfactionPerBU_SL_2.add((double) totalSatisfactionForThisSecondMultipliedByBUs_SL_2 / (double) totalRequestedBUsForThisSecond_SL_2);
        averageProfitPerBU_SL_2.add(totalProfitForThisSecondMultipliedByBUs_SL_2 / totalRequestedBUsForThisSecond_SL_2);
        averageSatisfactionPerServed_BU_SL_2.add((double) totalSatisfactionForThisSecondMultipliedByBUs_SL_2 / (double) totalServedBUsForThisSecond_SL_2);
        averageEnergyPerBU_SL_2.add((double) totalPowerConsumedForThisSecond_SL_2 / (double) totalServedBUsForThisSecond_SL_2);
        averageEnergyConsumedPerUser_SL_2.add((double) totalPowerConsumedForThisSecond_SL_2 / (double) number_served_users_for_this_second_SL_2);
        averageHandoverPerUser_SL_2.add((double) totalNumberOfHandoversForThisSecond_SL_2 / (double) number_served_users_for_this_second_SL_2);
//        System.out.println("number of handovers in this second: " + totalNumberOfHandoversForThisSecond);
        averageRssPerBU_SL_2.add((double) totalRssForThisSecondTimeBU_SL_2 / (double) totalServedBUsForThisSecond_SL_2);
        averageRssPerUser_SL_2.add((double) totalRssForThisSecond_SL_2 / (double) totalNumberUsers_SL_2);

//        averageSatisfactionPerBUWithUnConnectedUsers.add(totalSatisfactionForThisSecondMultipliedByBUs/(totalServedBUsForThisSecond+totalRejectedBUsForThisSecond));
//        averageEnergyPerBUWithUnConnectedUsers.add(totalPowerConsumedForThisSecond/(totalServedBUsForThisSecond+totalRejectedBUsForThisSecond));
//        averageEnergyConsumedPerUserWithUnConnectedUsers.add(totalPowerConsumedForThisSecond/(number_rejected_users_for_this_second+number_served_users_for_this_second));
//        averageHandoverPerUserWithUnConnectedUsers.add();
//        averageRssPerBUWithUnConnectedUsers.add();
//        averageRssPerUserWithUnConnectedUsers.add();
        average_satisfaction_55.add((double) value_average_satisfaction_55 / (double) datarate_all_55);
        average_satisfaction_1000.add((double) value_average_satisfaction_1000 / (double) datarate_all_1000);
        average_satisfaction_1200.add((double) value_average_satisfaction_1200 / (double) datarate_all_1200);
        average_satisfaction_150.add((double) value_average_satisfaction_150 / (double) datarate_all_150);
        average_satisfaction_300.add((double) value_average_satisfaction_300 / (double) datarate_all_300);
        average_satisfaction_32.add((double) value_average_satisfaction_32 / (double) datarate_all_32);
        average_satisfaction_500.add((double) value_average_satisfaction_500 / (double) datarate_all_500);
        average_satisfaction_87.add((double) value_average_satisfaction_87 / (double) datarate_all_87);
        average_satisfaction_700.add((double) value_average_satisfaction_700 / (double) datarate_all_700);

        average_satisfaction_55_SL_0.add((double) value_average_satisfaction_55_SL_0 / (double) datarate_all_55_SL_0);
        average_satisfaction_1000_SL_0.add((double) value_average_satisfaction_1000_SL_0 / (double) datarate_all_1000_SL_0);
        average_satisfaction_1200_SL_0.add((double) value_average_satisfaction_1200_SL_0 / (double) datarate_all_1200_SL_0);
        average_satisfaction_150_SL_0.add((double) value_average_satisfaction_150_SL_0 / (double) datarate_all_150_SL_0);
        average_satisfaction_300_SL_0.add((double) value_average_satisfaction_300_SL_0 / (double) datarate_all_300_SL_0);
        average_satisfaction_32_SL_0.add((double) value_average_satisfaction_32_SL_0 / (double) datarate_all_32_SL_0);
        average_satisfaction_500_SL_0.add((double) value_average_satisfaction_500_SL_0 / (double) datarate_all_500_SL_0);
        average_satisfaction_87_SL_0.add((double) value_average_satisfaction_87_SL_0 / (double) datarate_all_87_SL_0);
        average_satisfaction_700_SL_0.add((double) value_average_satisfaction_700_SL_0 / (double) datarate_all_700_SL_0);

        average_satisfaction_55_SL_1.add((double) value_average_satisfaction_55_SL_1 / (double) datarate_all_55_SL_1);
        average_satisfaction_1000_SL_1.add((double) value_average_satisfaction_1000_SL_1 / (double) datarate_all_1000_SL_1);
        average_satisfaction_1200_SL_1.add((double) value_average_satisfaction_1200_SL_1 / (double) datarate_all_1200_SL_1);
        average_satisfaction_150_SL_1.add((double) value_average_satisfaction_150_SL_1 / (double) datarate_all_150_SL_1);
        average_satisfaction_300_SL_1.add((double) value_average_satisfaction_300_SL_1 / (double) datarate_all_300_SL_1);
        average_satisfaction_32_SL_1.add((double) value_average_satisfaction_32_SL_1 / (double) datarate_all_32_SL_1);
        average_satisfaction_500_SL_1.add((double) value_average_satisfaction_500_SL_1 / (double) datarate_all_500_SL_1);
        average_satisfaction_87_SL_1.add((double) value_average_satisfaction_87_SL_1 / (double) datarate_all_87_SL_1);
        average_satisfaction_700_SL_1.add((double) value_average_satisfaction_700_SL_1 / (double) datarate_all_700_SL_1);

        average_satisfaction_55_SL_2.add((double) value_average_satisfaction_55_SL_2 / (double) datarate_all_55_SL_2);
        average_satisfaction_1000_SL_2.add((double) value_average_satisfaction_1000_SL_2 / (double) datarate_all_1000_SL_2);
        average_satisfaction_1200_SL_2.add((double) value_average_satisfaction_1200_SL_2 / (double) datarate_all_1200_SL_2);
        average_satisfaction_150_SL_2.add((double) value_average_satisfaction_150_SL_2 / (double) datarate_all_150_SL_2);
        average_satisfaction_300_SL_2.add((double) value_average_satisfaction_300_SL_2 / (double) datarate_all_300_SL_2);
        average_satisfaction_32_SL_2.add((double) value_average_satisfaction_32_SL_2 / (double) datarate_all_32_SL_2);
        average_satisfaction_500_SL_2.add((double) value_average_satisfaction_500_SL_2 / (double) datarate_all_500_SL_2);
        average_satisfaction_87_SL_2.add((double) value_average_satisfaction_87_SL_2 / (double) datarate_all_87_SL_2);
        average_satisfaction_700_SL_2.add((double) value_average_satisfaction_700_SL_2 / (double) datarate_all_700_SL_2);

//        int i = Simulator.Simulator.sp.current_smiulation_time-1;
        LinkedList<Double> sats = new LinkedList<Double>();
        LinkedList<Double> sats_SL_0 = new LinkedList<Double>();
        LinkedList<Double> sats_SL_1 = new LinkedList<Double>();
        LinkedList<Double> sats_SL_2 = new LinkedList<Double>();
        if (datarate_all_32 != 0) {
            if (average_satisfaction_32.getLast() != null) {
                sats.add(average_satisfaction_32.getLast());
            }
            if (datarate_all_32_SL_0 != 0) {
                if (average_satisfaction_32_SL_0.getLast() != null) {
                    sats_SL_0.add(average_satisfaction_32_SL_0.getLast());
                }
            }
            if (datarate_all_32_SL_1 != 0) {
                if (average_satisfaction_32_SL_1.getLast() != null) {
                    sats_SL_1.add(average_satisfaction_32_SL_1.getLast());
                }
            }
            if (datarate_all_32_SL_2 != 0) {
                if (average_satisfaction_32_SL_2.getLast() != null) {
                    sats_SL_2.add(average_satisfaction_32_SL_2.getLast());
                }
            }
        }
        if (datarate_all_55 != 0) {
            if (average_satisfaction_55.getLast() != null) {
                sats.add(average_satisfaction_55.getLast());
            }
            if (datarate_all_55_SL_0 != 0) {
                if (average_satisfaction_55_SL_0.getLast() != null) {
                    sats_SL_0.add(average_satisfaction_55_SL_0.getLast());
                }
            }
            if (datarate_all_55_SL_1 != 0) {
                if (average_satisfaction_55_SL_1.getLast() != null) {
                    sats_SL_1.add(average_satisfaction_55_SL_1.getLast());
                }
            }
            if (datarate_all_55_SL_2 != 0) {
                if (average_satisfaction_55_SL_2.getLast() != null) {
                    sats_SL_2.add(average_satisfaction_55_SL_2.getLast());
                }
            }
        }
        if (datarate_all_87 != 0) {
            if (average_satisfaction_87.getLast() != null) {
                sats.add(average_satisfaction_87.getLast());
            }
            if (datarate_all_87_SL_0 != 0) {
                if (average_satisfaction_87_SL_0.getLast() != null) {
                    sats_SL_0.add(average_satisfaction_87_SL_0.getLast());
                }
            }
            if (datarate_all_87_SL_1 != 0) {
                if (average_satisfaction_87_SL_1.getLast() != null) {
                    sats_SL_1.add(average_satisfaction_87_SL_1.getLast());
                }
            }
            if (datarate_all_87_SL_2 != 0) {
                if (average_satisfaction_87_SL_2.getLast() != null) {
                    sats_SL_2.add(average_satisfaction_87_SL_2.getLast());
                }
            }
        }
        if (datarate_all_150 != 0) {
            if (average_satisfaction_150.getLast() != null) {
                sats.add(average_satisfaction_150.getLast());
            }
            if (datarate_all_150_SL_0 != 0) {
                if (average_satisfaction_150_SL_0.getLast() != null) {
                    sats_SL_0.add(average_satisfaction_150_SL_0.getLast());
                }
            }
            if (datarate_all_150_SL_1 != 0) {
                if (average_satisfaction_150_SL_1.getLast() != null) {
                    sats_SL_1.add(average_satisfaction_150_SL_1.getLast());
                }
            }
            if (datarate_all_150_SL_2 != 0) {
                if (average_satisfaction_150_SL_2.getLast() != null) {
                    sats_SL_2.add(average_satisfaction_150_SL_2.getLast());
                }
            }
        }
        if (datarate_all_300 != 0) {
            if (average_satisfaction_300.getLast() != null) {
                sats.add(average_satisfaction_300.getLast());
            }
            if (datarate_all_300_SL_0 != 0) {
                if (average_satisfaction_300_SL_0.getLast() != null) {
                    sats_SL_0.add(average_satisfaction_300_SL_0.getLast());
                }
            }
            if (datarate_all_300_SL_1 != 0) {
                if (average_satisfaction_300_SL_1.getLast() != null) {
                    sats_SL_1.add(average_satisfaction_300_SL_1.getLast());
                }
            }
            if (datarate_all_300_SL_2 != 0) {
                if (average_satisfaction_300_SL_2.getLast() != null) {
                    sats_SL_2.add(average_satisfaction_300_SL_2.getLast());
                }
            }
        }
        if (datarate_all_700 != 0) {
            if (average_satisfaction_700.getLast() != null) {
                sats.add(average_satisfaction_700.getLast());
            }
            if (datarate_all_700_SL_0 != 0) {
                if (average_satisfaction_700_SL_0.getLast() != null) {
                    sats_SL_0.add(average_satisfaction_700_SL_0.getLast());
                }
            }
            if (datarate_all_700_SL_1 != 0) {
                if (average_satisfaction_700_SL_1.getLast() != null) {
                    sats_SL_1.add(average_satisfaction_700_SL_1.getLast());
                }
            }
            if (datarate_all_700_SL_2 != 0) {
                if (average_satisfaction_700_SL_2.getLast() != null) {
                    sats_SL_2.add(average_satisfaction_700_SL_2.getLast());
                }
            }
        }
        if (datarate_all_1000 != 0) {
            if (average_satisfaction_1000.getLast() != null) {
                sats.add(average_satisfaction_1000.getLast());
            }
            if (datarate_all_1000_SL_0 != 0) {
                if (average_satisfaction_1000_SL_0.getLast() != null) {
                    sats_SL_0.add(average_satisfaction_1000_SL_0.getLast());
                }
            }
            if (datarate_all_1000_SL_1 != 0) {
                if (average_satisfaction_1000_SL_1.getLast() != null) {
                    sats_SL_1.add(average_satisfaction_1000_SL_1.getLast());
                }
            }
            if (datarate_all_1000_SL_2 != 0) {
                if (average_satisfaction_1000_SL_2.getLast() != null) {
                    sats_SL_2.add(average_satisfaction_1000_SL_2.getLast());
                }
            }
        }
        if (datarate_all_1200 != 0) {
            if (average_satisfaction_1200.getLast() != null) {
                sats.add(average_satisfaction_1200.getLast());
            }
            if (datarate_all_1200_SL_0 != 0) {
                if (average_satisfaction_1200_SL_0.getLast() != null) {
                    sats_SL_0.add(average_satisfaction_1200_SL_0.getLast());
                }
            }
            if (datarate_all_1200_SL_1 != 0) {
                if (average_satisfaction_1200_SL_1.getLast() != null) {
                    sats_SL_1.add(average_satisfaction_1200_SL_1.getLast());
                }
            }
            if (datarate_all_1200_SL_2 != 0) {
                if (average_satisfaction_1200_SL_2.getLast() != null) {
                    sats_SL_2.add(average_satisfaction_1200_SL_2.getLast());
                }
            }
        }
        if (datarate_all_500 != 0) {
            if (average_satisfaction_500.getLast() != null) {
                sats.add(average_satisfaction_500.getLast());
            }
            if (datarate_all_500_SL_0 != 0) {
                if (average_satisfaction_500_SL_0.getLast() != null) {
                    sats_SL_0.add(average_satisfaction_500_SL_0.getLast());
                }
            }
            if (datarate_all_500_SL_1 != 0) {
                if (average_satisfaction_500_SL_1.getLast() != null) {
                    sats_SL_1.add(average_satisfaction_500_SL_1.getLast());
                }
            }
            if (datarate_all_500_SL_2 != 0) {
                if (average_satisfaction_500_SL_2.getLast() != null) {
                    sats_SL_2.add(average_satisfaction_500_SL_2.getLast());
                }
            }
        }
        double num = 0;
        double denuminatorJainSatisfaction = 0;
        for (int i = 0; i < sats.size(); i++) {
            num += sats.get(i);
            denuminatorJainSatisfaction += Math.pow(sats.get(i), 2);
        }
        double meanSatisfaction = num / sats.size();
        double varianceSatisfaction = 0;

        for (int i = 0; i < sats.size(); i++) {
            varianceSatisfaction += Math.pow(sats.get(i) - meanSatisfaction, 2);
        }
        varianceSatisfaction = varianceSatisfaction / sats.size();
        double numeratorJainSatisfaction = Math.pow(num, 2);
        denuminatorJainSatisfaction = denuminatorJainSatisfaction * sats.size();
        jainIndexSatisfaction.add(numeratorJainSatisfaction / denuminatorJainSatisfaction);
        indexOfDispersionSatisfaction.add(varianceSatisfaction / meanSatisfaction);

        double num_SL_0 = 0;
        double denuminatorJainSatisfaction_SL_0 = 0;
        for (int i = 0; i < sats_SL_0.size(); i++) {
            num_SL_0 += sats_SL_0.get(i);
            denuminatorJainSatisfaction_SL_0 += Math.pow(sats_SL_0.get(i), 2);
        }
        double meanSatisfaction_SL_0 = num_SL_0 / sats_SL_0.size();
        double varianceSatisfaction_SL_0 = 0;

        for (int i = 0; i < sats_SL_0.size(); i++) {
            varianceSatisfaction_SL_0 += Math.pow(sats_SL_0.get(i) - meanSatisfaction_SL_0, 2);
        }
        varianceSatisfaction_SL_0 = varianceSatisfaction_SL_0 / sats_SL_0.size();
        double numeratorJainSatisfaction_SL_0 = Math.pow(num_SL_0, 2);
        denuminatorJainSatisfaction_SL_0 = denuminatorJainSatisfaction_SL_0 * sats_SL_0.size();
        jainIndexSatisfaction_SL_0.add(numeratorJainSatisfaction_SL_0 / denuminatorJainSatisfaction_SL_0);
        indexOfDispersionSatisfaction_SL_0.add(varianceSatisfaction_SL_0 / meanSatisfaction_SL_0);
        double num_SL_1 = 0;
        double denuminatorJainSatisfaction_SL_1 = 0;
        for (int i = 0; i < sats_SL_1.size(); i++) {
            num_SL_1 += sats_SL_1.get(i);
            denuminatorJainSatisfaction_SL_1 += Math.pow(sats_SL_1.get(i), 2);
        }
        double meanSatisfaction_SL_1 = num_SL_1 / sats_SL_1.size();
        double varianceSatisfaction_SL_1 = 0;

        for (int i = 0; i < sats_SL_1.size(); i++) {
            varianceSatisfaction_SL_1 += Math.pow(sats_SL_1.get(i) - meanSatisfaction_SL_1, 2);
        }
        varianceSatisfaction_SL_1 = varianceSatisfaction_SL_1 / sats_SL_1.size();
        double numeratorJainSatisfaction_SL_1 = Math.pow(num_SL_1, 2);
        denuminatorJainSatisfaction_SL_1 = denuminatorJainSatisfaction_SL_1 * sats_SL_1.size();
        jainIndexSatisfaction_SL_1.add(numeratorJainSatisfaction_SL_1 / denuminatorJainSatisfaction_SL_1);
        indexOfDispersionSatisfaction_SL_1.add(varianceSatisfaction_SL_1 / meanSatisfaction_SL_1);
        double num_SL_2 = 0;
        double denuminatorJainSatisfaction_SL_2 = 0;
        for (int i = 0; i < sats_SL_2.size(); i++) {
            num_SL_2 += sats_SL_2.get(i);
            denuminatorJainSatisfaction_SL_2 += Math.pow(sats_SL_2.get(i), 2);
        }
        double meanSatisfaction_SL_2 = num_SL_2 / sats_SL_2.size();
        double varianceSatisfaction_SL_2 = 0;

        for (int i = 0; i < sats_SL_2.size(); i++) {
            varianceSatisfaction_SL_2 += Math.pow(sats_SL_2.get(i) - meanSatisfaction_SL_2, 2);
        }
        varianceSatisfaction_SL_2 = varianceSatisfaction_SL_2 / sats_SL_2.size();
        double numeratorJainSatisfaction_SL_2 = Math.pow(num_SL_2, 2);
        denuminatorJainSatisfaction_SL_2 = denuminatorJainSatisfaction_SL_2 * sats_SL_2.size();
        jainIndexSatisfaction_SL_2.add(numeratorJainSatisfaction_SL_2 / denuminatorJainSatisfaction_SL_2);
        indexOfDispersionSatisfaction_SL_2.add(varianceSatisfaction_SL_2 / meanSatisfaction_SL_2);

        percentage_blocked_55.add((double) 100 * number_blocked_55 / (double) number_all_55);
        percentage_blocked_1000.add((double) 100 * number_blocked_1000 / (double) number_all_1000);
        percentage_blocked_1200.add((double) 100 * number_blocked_1200 / (double) number_all_1200);
        percentage_blocked_150.add((double) 100 * number_blocked_150 / (double) number_all_150);
        percentage_blocked_300.add((double) 100 * number_blocked_300 / (double) number_all_300);
        percentage_blocked_32.add((double) 100 * number_blocked_32 / (double) number_all_32);
        percentage_blocked_500.add((double) 100 * number_blocked_500 / (double) number_all_500);
        percentage_blocked_87.add((double) 100 * number_blocked_87 / (double) number_all_87);
        percentage_blocked_700.add((double) 100 * number_blocked_700 / (double) number_all_700);

        LinkedList<Double> satsRateBlock = new LinkedList<Double>();
        LinkedList<Double> satsNumberBlock = new LinkedList<Double>();
        if (datarate_all_32 != 0) {
            if (datarate_blocked_32 != 0) {
                satsNumberBlock.add(percentage_blocked_32.getLast());
                satsRateBlock.add((double) datarate_blocked_32 / (double) datarate_all_32);
            } else {
                satsNumberBlock.add(0.0);
                satsRateBlock.add(0.0);
            }
//            System.err.println("ad" + (double) datarate_blocked_32 / (double) datarate_all_32);
        }
        if (datarate_all_55 != 0) {
            satsNumberBlock.add(percentage_blocked_55.getLast());
            if (datarate_blocked_55 != 0) {
                satsRateBlock.add((double) datarate_blocked_55 / (double) datarate_all_55);
            } else {
                satsNumberBlock.add(0.0);
                satsRateBlock.add(0.0);
            }
        }
        if (datarate_all_87 != 0) {
            if (datarate_blocked_87 != 0) {
                satsNumberBlock.add(percentage_blocked_87.getLast());
                satsRateBlock.add((double) datarate_blocked_87 / (double) datarate_all_87);
            } else {
                satsNumberBlock.add(0.0);
                satsRateBlock.add(0.0);
            }
        }
        if (datarate_all_150 != 0) {
            if (datarate_blocked_150 != 0) {
                satsNumberBlock.add(percentage_blocked_150.getLast());
                satsRateBlock.add((double) datarate_blocked_150 / (double) datarate_all_150);
            } else {
                satsNumberBlock.add(0.0);
                satsRateBlock.add(0.0);
            }
        }
        if (datarate_all_300 != 0) {
            if (datarate_blocked_300 != 0) {
                satsNumberBlock.add(percentage_blocked_300.getLast());
                satsRateBlock.add((double) datarate_blocked_300 / (double) datarate_all_300);
            } else {
                satsNumberBlock.add(0.0);
                satsRateBlock.add(0.0);
            }
        }
        if (datarate_all_700 != 0) {
            if (datarate_blocked_700 != 0) {
                satsNumberBlock.add(percentage_blocked_700.getLast());
                satsRateBlock.add((double) datarate_blocked_700 / (double) datarate_all_700);
            } else {
                satsNumberBlock.add(0.0);
                satsRateBlock.add(0.0);
            }
        }
        if (datarate_all_1000 != 0) {
            if (datarate_blocked_1000 != 0) {
                satsNumberBlock.add(percentage_blocked_1000.getLast());
                satsRateBlock.add((double) datarate_blocked_1000 / (double) datarate_all_1000);
            } else {
                satsNumberBlock.add(0.0);
                satsRateBlock.add(0.0);
            }
        }
        if (datarate_all_1200 != 0) {
            if (datarate_blocked_1200 != 0) {
                satsNumberBlock.add(percentage_blocked_1200.getLast());
                satsRateBlock.add((double) datarate_blocked_1200 / (double) datarate_all_1200);
            } else {
                satsNumberBlock.add(0.0);
                satsRateBlock.add(0.0);
            }
        }
        if (datarate_all_500 != 0) {
            if (datarate_blocked_500 != 0) {
                satsNumberBlock.add(percentage_blocked_500.getLast());
                satsRateBlock.add((double) datarate_blocked_500 / (double) datarate_all_500);
            } else {
                satsNumberBlock.add(0.0);
                satsRateBlock.add(0.0);
            }
        }
        double numR = 0;
        double denumR = 0;
        for (int i = 0; i < satsRateBlock.size(); i++) {
            numR += satsRateBlock.get(i);
            denumR += Math.pow(satsRateBlock.get(i), 2);
//            System.err.println("denum+="+sats.get(i));
        }
        double meanR = Math.pow(numR, 2);
//        for (int i = 0; i < sats.size(); i++) {
//            denum += Math.pow(mean - sats.get(i), 2);
//        }
        denumR = denumR * satsRateBlock.size();
        if (denumR == numR) {
            jainIndexSatisfactionDataRate.add(1.0);
        } else {
            jainIndexSatisfactionDataRate.add(meanR / denumR);
        }

        double numblocked = 0;
        double denumblocked = 0;
        for (int i = 0; i < satsNumberBlock.size(); i++) {
            numblocked += satsNumberBlock.get(i);
            denumblocked += Math.pow(satsNumberBlock.get(i), 2);
//            System.err.println("denum+="+sats.get(i));
        }
        double meanBlocked = Math.pow(numblocked, 2);
//        for (int i = 0; i < sats.size(); i++) {
//            denum += Math.pow(mean - sats.get(i), 2);
//        }
        denumblocked = denumblocked * satsNumberBlock.size();
        if (denumblocked == numblocked) {
            jainIndexSatisfactionNumberBlocked.add(1.0);
        } else {
            jainIndexSatisfactionNumberBlocked.add(meanBlocked / denumblocked);
        }

        if (number_rejected_users_for_this_second == 0) {
            number_rejected_users_for_this_second = 1;
        }
    }

    public double getOverallAveragePowerConsumptionPerUser() {
        long result = 0;
        for (double d : averageEnergyConsumedPerUser) {
            result += d;
        }
        return result / (double) averageEnergyConsumedPerUser.size();
    }

    public double getOverallAverageRssPerUser() {
        double result = 0;
        for (double d : averageRssPerUser) {
            result += d;
        }
        return result / (double) averageRssPerUser.size();
    }

    public double getOverallAverageSatisfactionPerUser() {
        double result = 0;
        for (double d : averageSatisfactionPerUser) {
            result += d;
        }
        return result / (double) averageSatisfactionPerUser.size();
    }

    public double getOverallAveragePowerConsumptionPerBU() {
        long result = 0;
        for (double d : averageEnergyPerBU) {
            result += d;
        }
        return result / (double) averageEnergyPerBU.size();
    }

    public double getOverallAverageRssPerBU() {
        double result = 0;
        for (double d : averageRssPerBU) {
            result += d;
        }
        return result / (double) averageRssPerBU.size();
    }

    public double getOverallAverageSatisfactionPerBU() {
        double result = 0;
        for (double d : averageSatisfactionPerBU) {
            result += d;
        }
        return (result / (double) averageSatisfactionPerBU.size());
    }

//    public long getTotalNumberRejectedUsers() {
//        long result = 0;
//        for (int n : numberRejectedUsers) {
//            result += n;
//        }
//        return result;
//    }
//    public long getTotalNumberOfUsersInTheSystem() {
//        long result = 0;
//        for (int n : numberOfUsersInTheSystem) {
//            result += n;
//        }
//        return result;
//    }
//    public long getTotalNumberServedUsers() {
//        long result = 0;
//        for (int n : numberOfUsersServed) {
//            result += n;
//        }
//        return result;
//    }
    public double getAverageUserBlockingProbability() {
        double result = 0;
        for (double d : averageUsersBlocked) {
            result += d;
        }
//        System.out.println("average user blocked .size() "+averageUsersBlocked.size());
        return (result / (double) averageUsersBlocked.size());
    }

//    public long getTotalNumberRejectedBUs() {
//        long result = 0;
//        for (int n : numberBUsRejected) {
//            result += n;
//        }
//        return result;
//    }
//    public long getTotalNumberServedBUs() {
//        long result = 0;
//        for (int n : numberBUsServed) {
//            result += n;
//        }
//        return result;
//    }
//    public long getTotalNumberRequestedBUs() {
//        long result = 0;
//        for (int n : numberOfBUsRequested) {
//            result += n;
//        }
//        return result;
//    }
    public double getAverageBUBlockingProbability() {
        double result = 0;
        for (double d : averageBUsBlocked) {
            result += d;
        }
        return (result / (double) averageBUsBlocked.size());
    }

    public double getAverageNumberOfHandoversPerUserDuringTheWholeSimulation() {
        double result = 0;
        for (double d : averageHandoverPerUser) {
            result += d;
        }
        return result;
    }

    public double getAverageNumberOfUsersInTheConnectionRequestListsPerList() {
        long result = 0;
        for (double d : averageNumberOfUsersInTheConnectionRequestListsPerList) {
            result += d;
        }
        return result / (double) averageNumberOfUsersInTheConnectionRequestListsPerList.size();

    }

    public double getAverageNumberOfConnectionRequestListsReceivedPerNetwork() {
        long result = 0;
        for (double d : averageNumberOfConnectionRequestListsReceivedPerNetwork) {
            result += d;
        }
        return result / (double) averageNumberOfConnectionRequestListsReceivedPerNetwork.size();
    }

    public double getAverageProcessingTime() {
        long result = 0;
        for (double d : processingTime) {
            result += d;
        }
        return result / (double) processingTime.size();
    }

    public double getAverageDistributedIterationCount() {
        long result = 0;
        for (double d : DistributedIterationCount) {
            result += d;
        }
        return result / (double) DistributedIterationCount.size();
    }

    public void plotAverageSatisfactionPerUser(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerUser, curveName, takeValueEvery, symbol, legend, true, "AverageSatisfactionPerUser");
    }

    public void plotAverageSatisfactionPerBU(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerBU, curveName, takeValueEvery, symbol, legend, true, "AverageSatisfactionPerBU");
    }

    public void plotAveragePowerConsumptionPerUser(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, averageEnergyConsumedPerUser, curveName, takeValueEvery, symbol, legend, true, "AveragePowerConsumptionPerUser");
    }

    public void plotAveragePowerConsumptionPerBU(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, averageEnergyPerBU, curveName, takeValueEvery, symbol, legend, true, "AveragePowerConsumptionPerBU");
    }

    public void plotAverageRssPerUser(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, averageRssPerUser, curveName, takeValueEvery, symbol, legend, true, "AverageRssPerUser");
    }

    public void plotAverageRssPerBU(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, averageRssPerBU, curveName, takeValueEvery, symbol, legend, true, "AverageRssPerBU");
    }

    public void plotNetworkProcessingTime(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, processingTime, curveName, takeValueEvery, symbol, legend, true, "ProcessingTime");
    }

    public void plotDistributedIterationCount(String curveName, int takeValueEvery, String symbol, String legend) {
        if (DistributedIterationCount.size() > 0) {
            new PlotCurve(numberOfUsersInTheSystem, DistributedIterationCount, curveName, takeValueEvery, symbol, legend, true, "DistributedIterationCount");
        }
    }

    public void plotRequestedDatarate(String curveName, int takeValueEvery, String symbol, String legend) {
        LinkedList<Double> requestedDatarateDouble = new LinkedList<Double>();
        for (Integer i : numberOfBUsRequested) {
            requestedDatarateDouble.add((double) i);
        }
        new PlotCurve(numberOfUsersInTheSystem, requestedDatarateDouble, curveName, takeValueEvery, symbol, legend, true, "TotalRequestedDatarate");
    }

    public void plotNumberOfHandover(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, numberOfHandover, curveName, takeValueEvery, symbol, legend, true, "NumberOfHnadover");
    }

    public void plotAverageHandoverPerUser(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, averageHandoverPerUser, curveName, takeValueEvery, symbol, legend, true, "AverageHandoverPerUser");
    }

    public void plotAverageUserBlockingProbability(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, averageUsersBlocked, curveName, takeValueEvery, symbol, legend, true, "AverageUserBlocking");
    }

    public void plotAverageBUBlockingProbability(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, averageBUsBlocked, curveName, takeValueEvery, symbol, legend, true, "AverageBUBlocking");
    }

    public void plotAverageSatisfaction_55(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, average_satisfaction_55, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "AverageSatisfaction55");
    }

    public void plotAverageJainSatisfaction(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, jainIndexSatisfaction, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "AverageJainIndexSatisfaction");
    }

    public void plotAverageJainSatisfaction_SL_0(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, jainIndexSatisfaction_SL_0, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "AverageJainIndexSatisfaction_SL_0");
    }

    public void plotAverageJainSatisfaction_SL_1(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, jainIndexSatisfaction_SL_1, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "AverageJainIndexSatisfaction_SL_1");
    }

    public void plotAverageJainSatisfaction_SL_2(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, jainIndexSatisfaction_SL_2, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "AverageJainIndexSatisfaction_SL_2");
    }

    public void plotAverageIndexOfDispersionSatisfaction(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, indexOfDispersionSatisfaction, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "AverageIndexOfDisresionSatisfaction");
    }

    public void plotAverageIndexOfDispersionSatisfaction_SL_0(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, indexOfDispersionSatisfaction_SL_0, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "AverageIndexOfDisresionSatisfaction_SL_0");
    }

    public void plotAverageIndexOfDispersionSatisfaction_SL_1(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, indexOfDispersionSatisfaction_SL_1, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "AverageIndexOfDisresionSatisfaction_SL_1");
    }

    public void plotAverageIndexOfDispersionSatisfaction_SL_2(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, indexOfDispersionSatisfaction_SL_2, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "AverageIndexOfDisresionSatisfaction_SL_2");
    }

    public void plotAveragejainIndexSatisfactionDataRate(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, jainIndexSatisfactionDataRate, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "AveragejainIndexSatisfactionDataRate");
    }

    public void plotAveragejainIndexSatisfactionNumberBlocked(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, jainIndexSatisfactionNumberBlocked, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "AveragejainIndexSatisfactionNumberBlocked");
    }

    public void plotAverageSatisfaction_1000(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, average_satisfaction_1000, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "AverageSatisfaction1000");
    }

    public void plotAverageSatisfaction_1200(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, average_satisfaction_1200, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "AverageSatisfaction1200");
    }

    public void plotAverageSatisfaction_150(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, average_satisfaction_150, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "AverageSatisfaction150");
    }

    public void plotAverageSatisfaction_300(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, average_satisfaction_300, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "AverageSatisfaction300");
    }

    public void plotAverageSatisfaction_32(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, average_satisfaction_32, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "AverageSatisfaction32");
    }

    public void plotAverageSatisfaction_500(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, average_satisfaction_500, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "AverageSatisfaction500");
    }

    public void plotAverageSatisfaction_700(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, average_satisfaction_700, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "AverageSatisfaction700");
    }

    public void plotAverageSatisfaction_87(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, average_satisfaction_87, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "AverageSatisfaction87");
    }

    public void plotPercentageBlocked_55(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, percentage_blocked_55, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "PercentageBlocked55");
    }

    public void plotPercentageBlocked_1000(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, percentage_blocked_1000, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "PercentageBlocked1000");
    }

    public void plotPercentageBlocked_1200(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, percentage_blocked_1200, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "PercentageBlocked1200");
    }

    public void plotPercentageBlocked_150(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, percentage_blocked_150, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "PercentageBlocked150");
    }

    public void plotPercentageBlocked_300(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, percentage_blocked_300, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "PercentageBlocked300");
    }

    public void plotPercentageBlocked_32(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, percentage_blocked_32, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "PercentageBlocked32");
    }

    public void plotPercentageBlocked_500(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, percentage_blocked_500, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "PercentageBlocked500");
    }

    public void plotPercentageBlocked_700(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, percentage_blocked_700, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "PercentageBlocked700");
    }

    public void plotPercentageBlocked_87(String curveName, int takeValueEvery, String symbol, String legend) {
        new PlotCurve(numberOfUsersInTheSystem, percentage_blocked_1000, curveName, takeValueEvery, symbol, legend, true, Simulator.Simulator.path_delimilter + "DATARATES_STATISTICS" + Simulator.Simulator.path_delimilter + "PercentageBlocked87");
    }

    public void SL_plotAverageSatisfactionPerUser(String curveName, int takeValueEvery, String symbol, String legend) {
//        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerUser, curveName, takeValueEvery, symbol, legend, true, "AverageSatisfactionPerUser");
        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerUser_SL_0, curveName + "_SL_0", takeValueEvery, symbol, legend + "_SL_0", true, "AverageSatisfactionPerUser");
        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerUser_SL_1, curveName + "_SL_1", takeValueEvery, symbol, legend + "_SL_1", true, "AverageSatisfactionPerUser");
        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerUser_SL_2, curveName + "_SL_2", takeValueEvery, symbol, legend + "_SL_2", true, "AverageSatisfactionPerUser");

    }

    public void SL_plotAverageSatisfactionPerBU(String curveName, int takeValueEvery, String symbol, String legend) {
//        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerBU, curveName, takeValueEvery, symbol, legend, true, "AverageSatisfactionPerBU");
        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerBU_SL_0, curveName + "_SL_0", takeValueEvery, symbol, legend + "_SL_0", true, "AverageSatisfactionPerBU");
        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerBU_SL_1, curveName + "_SL_1", takeValueEvery, symbol, legend + "_SL_1", true, "AverageSatisfactionPerBU");
        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerBU_SL_2, curveName + "_SL_2", takeValueEvery, symbol, legend + "_SL_2", true, "AverageSatisfactionPerBU");

    }

    public void SL_plotAverageProfitPerBU(String curveName, int takeValueEvery, String symbol, String legend) {
//        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerBU, curveName, takeValueEvery, symbol, legend, true, "AverageSatisfactionPerBU");
        new PlotCurve(numberOfUsersInTheSystem, averageProfitPerBU_SL_0, curveName + "_SL_0", takeValueEvery, symbol, legend + "_SL_0", true, "AverageProfitPerBU");
        new PlotCurve(numberOfUsersInTheSystem, averageProfitPerBU_SL_1, curveName + "_SL_1", takeValueEvery, symbol, legend + "_SL_1", true, "AverageProfitPerBU");
        new PlotCurve(numberOfUsersInTheSystem, averageProfitPerBU_SL_2, curveName + "_SL_2", takeValueEvery, symbol, legend + "_SL_2", true, "AverageProfitPerBU");

    }

    public void SL_plotAverageSatisfactionPerServedBU(String curveName, int takeValueEvery, String symbol, String legend) {
//        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerBU, curveName, takeValueEvery, symbol, legend, true, "AverageSatisfactionPerBU");
        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerServed_BU_SL_0, curveName + "_SL_0", takeValueEvery, symbol, legend + "_SL_0", true, "AverageSatisfactionPerServedBU");
        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerServed_BU_SL_1, curveName + "_SL_1", takeValueEvery, symbol, legend + "_SL_1", true, "AverageSatisfactionPerServedBU");
        new PlotCurve(numberOfUsersInTheSystem, averageSatisfactionPerServed_BU_SL_2, curveName + "_SL_2", takeValueEvery, symbol, legend + "_SL_2", true, "AverageSatisfactionPerServedBU");

    }

    public void SL_plotAveragePowerConsumptionPerUser(String curveName, int takeValueEvery, String symbol, String legend) {
//        new PlotCurve(numberOfUsersInTheSystem, averageEnergyConsumedPerUser, curveName, takeValueEvery, symbol, legend, true, "AveragePowerConsumptionPerUser");
        new PlotCurve(numberOfUsersInTheSystem, averageEnergyConsumedPerUser_SL_0, curveName + "_SL_0", takeValueEvery, symbol, legend + "_SL_0", true, "AveragePowerConsumptionPerUser");
        new PlotCurve(numberOfUsersInTheSystem, averageEnergyConsumedPerUser_SL_1, curveName + "_SL_1", takeValueEvery, symbol, legend + "_SL_1", true, "AveragePowerConsumptionPerUser");
        new PlotCurve(numberOfUsersInTheSystem, averageEnergyConsumedPerUser_SL_2, curveName + "_SL_2", takeValueEvery, symbol, legend + "_SL_2", true, "AveragePowerConsumptionPerUser");

    }

    public void SL_plotAveragePowerConsumptionPerBU(String curveName, int takeValueEvery, String symbol, String legend) {
//        new PlotCurve(numberOfUsersInTheSystem, averageEnergyPerBU, curveName, takeValueEvery, symbol, legend, true, "AveragePowerConsumptionPerBU");
        new PlotCurve(numberOfUsersInTheSystem, averageEnergyPerBU_SL_0, curveName + "_SL_0", takeValueEvery, symbol, legend + "_SL_0", true, "AveragePowerConsumptionPerBU");
        new PlotCurve(numberOfUsersInTheSystem, averageEnergyPerBU_SL_1, curveName + "_SL_1", takeValueEvery, symbol, legend + "_SL_1", true, "AveragePowerConsumptionPerBU");
        new PlotCurve(numberOfUsersInTheSystem, averageEnergyPerBU_SL_2, curveName + "_SL_2", takeValueEvery, symbol, legend + "_SL_2", true, "AveragePowerConsumptionPerBU");

    }

    public void SL_plotAverageRssPerUser(String curveName, int takeValueEvery, String symbol, String legend) {
//        new PlotCurve(numberOfUsersInTheSystem, averageRssPerUser, curveName, takeValueEvery, symbol, legend, true, "AverageRssPerUser");
        new PlotCurve(numberOfUsersInTheSystem, averageRssPerUser_SL_0, curveName + "_SL_0", takeValueEvery, symbol, legend + "_SL_0", true, "AverageRssPerUser");
        new PlotCurve(numberOfUsersInTheSystem, averageRssPerUser_SL_1, curveName + "_SL_1", takeValueEvery, symbol, legend + "_SL_1", true, "AverageRssPerUser");
        new PlotCurve(numberOfUsersInTheSystem, averageRssPerUser_SL_2, curveName + "_SL_2", takeValueEvery, symbol, legend + "_SL_2", true, "AverageRssPerUser");

    }

    public void SL_plotAverageRssPerBU(String curveName, int takeValueEvery, String symbol, String legend) {
//        new PlotCurve(numberOfUsersInTheSystem, averageRssPerBU, curveName, takeValueEvery, symbol, legend, true, "AverageRssPerBU");
        new PlotCurve(numberOfUsersInTheSystem, averageRssPerBU_SL_0, curveName + "_SL_0", takeValueEvery, symbol, legend + "_SL_0", true, "AverageRssPerBU");
        new PlotCurve(numberOfUsersInTheSystem, averageRssPerBU_SL_1, curveName + "_SL_1", takeValueEvery, symbol, legend + "_SL_1", true, "AverageRssPerBU");
        new PlotCurve(numberOfUsersInTheSystem, averageRssPerBU_SL_2, curveName + "_SL_2", takeValueEvery, symbol, legend + "_SL_2", true, "AverageRssPerBU");

    }

    public void SL_plotNumberOfHandover(String curveName, int takeValueEvery, String symbol, String legend) {
//        new PlotCurve(numberOfUsersInTheSystem, numberOfHandover, curveName, takeValueEvery, symbol, legend, true, "NumberOfHnadover");
        new PlotCurve(numberOfUsersInTheSystem, numberOfHandover_SL_0, curveName + "_SL_0", takeValueEvery, symbol, legend + "_SL_0", true, "NumberOfHnadover");
        new PlotCurve(numberOfUsersInTheSystem, numberOfHandover_SL_1, curveName + "_SL_1", takeValueEvery, symbol, legend + "_SL_1", true, "NumberOfHnadover");
        new PlotCurve(numberOfUsersInTheSystem, numberOfHandover_SL_2, curveName + "_SL_2", takeValueEvery, symbol, legend + "_SL_2", true, "NumberOfHnadover");

    }

    public void SL_plotAverageHandoverPerUser(String curveName, int takeValueEvery, String symbol, String legend) {
//        new PlotCurve(numberOfUsersInTheSystem, averageHandoverPerUser, curveName, takeValueEvery, symbol, legend, true, "AverageHandoverPerUser");
        new PlotCurve(numberOfUsersInTheSystem, averageHandoverPerUser_SL_0, curveName + "_SL_0", takeValueEvery, symbol, legend + "_SL_0", true, "AverageHandoverPerUser");
        new PlotCurve(numberOfUsersInTheSystem, averageHandoverPerUser_SL_1, curveName + "_SL_1", takeValueEvery, symbol, legend + "_SL_1", true, "AverageHandoverPerUser");
        new PlotCurve(numberOfUsersInTheSystem, averageHandoverPerUser_SL_2, curveName + "_SL_2", takeValueEvery, symbol, legend + "_SL_2", true, "AverageHandoverPerUser");

    }

    public void SL_plotAverageUserBlockingProbability(String curveName, int takeValueEvery, String symbol, String legend) {
//        new PlotCurve(numberOfUsersInTheSystem, averageUsersBlocked, curveName, takeValueEvery, symbol, legend, true, "AverageUserBlocking");
        new PlotCurve(numberOfUsersInTheSystem, averageUsersBlocked_SL_0, curveName + "_SL_0", takeValueEvery, symbol, legend + "_SL_0", true, "AverageUserBlocking");
        new PlotCurve(numberOfUsersInTheSystem, averageUsersBlocked_SL_1, curveName + "_SL_1", takeValueEvery, symbol, legend + "_SL_1", true, "AverageUserBlocking");
        new PlotCurve(numberOfUsersInTheSystem, averageUsersBlocked_SL_2, curveName + "_SL_2", takeValueEvery, symbol, legend + "_SL_2", true, "AverageUserBlocking");

    }

    public void SL_plotAverageBUBlockingProbability(String curveName, int takeValueEvery, String symbol, String legend) {
//        new PlotCurve(numberOfUsersInTheSystem, averageBUsBlocked, curveName, takeValueEvery, symbol, legend, true, "AverageBUBlocking");
        new PlotCurve(numberOfUsersInTheSystem, averageBUsBlocked_SL_0, curveName + "_SL_0", takeValueEvery, symbol, legend + "_SL_0", true, "AverageBUBlocking");
        new PlotCurve(numberOfUsersInTheSystem, averageBUsBlocked_SL_1, curveName + "_SL_1", takeValueEvery, symbol, legend + "_SL_1", true, "AverageBUBlocking");
        new PlotCurve(numberOfUsersInTheSystem, averageBUsBlocked_SL_2, curveName + "_SL_2", takeValueEvery, symbol, legend + "_SL_2", true, "AverageBUBlocking");

    }

    public void SL_plotAverageJainSatisfaction(String curveName, int takeValueEvery, String symbol, String legend) {
//        new PlotCurve(numberOfUsersInTheSystem, averageBUsBlocked, curveName, takeValueEvery, symbol, legend, true, "AverageBUBlocking");
        new PlotCurve(numberOfUsersInTheSystem, jainIndexSatisfaction_SL_0, curveName + "_SL_0", takeValueEvery, symbol, legend + "_SL_0", true, "jainIndexSatisfaction");
        new PlotCurve(numberOfUsersInTheSystem, jainIndexSatisfaction_SL_1, curveName + "_SL_1", takeValueEvery, symbol, legend + "_SL_1", true, "jainIndexSatisfaction");
        new PlotCurve(numberOfUsersInTheSystem, jainIndexSatisfaction_SL_2, curveName + "_SL_2", takeValueEvery, symbol, legend + "_SL_2", true, "jainIndexSatisfaction");

    }

    public void SL_plotAverageDispersionSatisfaction(String curveName, int takeValueEvery, String symbol, String legend) {
//        new PlotCurve(numberOfUsersInTheSystem, averageBUsBlocked, curveName, takeValueEvery, symbol, legend, true, "AverageBUBlocking");
        new PlotCurve(numberOfUsersInTheSystem, indexOfDispersionSatisfaction_SL_0, curveName + "_SL_0", takeValueEvery, symbol, legend + "_SL_0", true, "indexOfDispersionSatisfaction");
        new PlotCurve(numberOfUsersInTheSystem, indexOfDispersionSatisfaction_SL_1, curveName + "_SL_1", takeValueEvery, symbol, legend + "_SL_1", true, "indexOfDispersionSatisfaction");
        new PlotCurve(numberOfUsersInTheSystem, indexOfDispersionSatisfaction_SL_2, curveName + "_SL_2", takeValueEvery, symbol, legend + "_SL_2", true, "indexOfDispersionSatisfaction");

    }

//     public void plotAverageNumberOfHandoversPerUser(){
//        new PlotCurve(numberOfUsersInTheSystem, , "average_Energy_per_user", 1);
//    }
}
