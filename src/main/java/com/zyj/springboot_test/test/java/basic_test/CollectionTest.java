package com.zyj.springboot_test.test.java.basic_test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CollectionTest {
    public static class LastDayDonationBoxReward {
        public String playerId;
        public List<Integer> rewardItems;

        public LastDayDonationBoxReward() {
            this.rewardItems = new ArrayList<>();
        }

        public LastDayDonationBoxReward(String playerId) {
            this.rewardItems = new ArrayList<>();
            this.playerId = playerId;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((playerId == null) ? 0 : playerId.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
//            LastDayDonationBoxReward obj1 = (LastDayDonationBoxReward) obj;
//            if (this.playerId.equals(obj1.playerId)) {
//                return true;
//            }
//            return false;

//            return super.equals(obj);

            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            LastDayDonationBoxReward other = (LastDayDonationBoxReward) obj;
            if (playerId == null) {
                if (other.playerId != null)
                    return false;
            } else if (!playerId.equals(other.playerId))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "LastDayDonationBoxReward{" +
                    "playerId='" + playerId + '\'' +
                    ", rewardItems=" + rewardItems +
                    '}';
        }

    }
    public static void main(String[] args) {
        HashSet<LastDayDonationBoxReward> lastDayDonationBoxRewards = new HashSet<>();
        LastDayDonationBoxReward lastDayDonationBoxReward1 = new LastDayDonationBoxReward("asbs");
        LastDayDonationBoxReward lastDayDonationBoxReward3 = new LastDayDonationBoxReward("asd1");
        LastDayDonationBoxReward lastDayDonationBoxReward2 = new LastDayDonationBoxReward("asa");
        LastDayDonationBoxReward lastDayDonationBoxReward4 = new LastDayDonationBoxReward("asa");
        lastDayDonationBoxRewards.add(lastDayDonationBoxReward1);
        lastDayDonationBoxRewards.add(lastDayDonationBoxReward2);
        lastDayDonationBoxRewards.add(lastDayDonationBoxReward3);
        lastDayDonationBoxRewards.add(lastDayDonationBoxReward4);

        for (LastDayDonationBoxReward lastDayDonationBoxReward : lastDayDonationBoxRewards) {
            System.out.println(lastDayDonationBoxReward);
        }

    }

}
