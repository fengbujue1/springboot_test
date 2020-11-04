package com.zyj.springboot_test.test.java.leetCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomizedCollection {
    private ArrayList<Integer> vals;
    private Random random = new Random();


    public static void main(String[] args) {
        RandomizedCollection randomizedCollection = new RandomizedCollection();
        System.out.println(randomizedCollection.insert(1));
        System.out.println(randomizedCollection.remove(1));
        System.out.println(randomizedCollection.insert(1));
    }
    /** Initialize your data structure here. */
    public RandomizedCollection() {
        this.vals = new ArrayList<>();
    }

    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        if (vals.contains(val)) {
            vals.add(val);
            return false;
        } else {
            vals.add(val);
            return true;
        }
    }

    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        if (vals.contains(val)) {
            int i = vals.indexOf(val);
            vals.remove(i);
            return true;
        } else {
            return false;
        }
    }

    /** Get a random element from the collection. */
    public int getRandom() {
        int bound = vals.size();
        int rand = random.nextInt(bound);
        return vals.get(rand);
    }
}
