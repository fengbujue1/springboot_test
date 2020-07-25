package com.zyj.springboot_test.test.java.testThread.test_ForkJoinPool;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 使用ForkJoinPool实现归并排序示例代码
 *
 */
public class MergeSortDemo {
	
	public static void main(String[] args) throws Exception {   
        long beginTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        SortTask task = new SortTask(inits);
        ForkJoinTask<int[]> taskResult = pool.submit(task);
        try {
            int[] ints = taskResult.get();
            for (int i = 0; i < 100; i++) {
                System.out.println(ints[i]);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(System.out);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时=" + (endTime - beginTime));      
    }
	
	private static int MAX = 100 * 10000;
	
    private static int inits[] = new int[MAX];
 
    // 进行随机队列初始化
    static {
        Random r = new Random();
        for(int index = 1 ; index <= MAX ; index++) {
            inits[index - 1] = r.nextInt(10000000);
        }
    }
 
    /**
     * 单个排序的子任务
     * 
     */
    static class SortTask extends RecursiveTask<int[]> {
        /** */
		private static final long serialVersionUID = 1L;
		private int source[];
 
        public SortTask(int source[]) {
            this.source = source;
        }
 
        /* (non-Javadoc)
         * @see java.util.concurrent.RecursiveTask#compute()
         */
        @Override
        protected int[] compute() {
            int sourceLen = source.length;
            // 如果条件成立，说明任务中要进行排序的集合还不够小
            if(sourceLen > 2) {
                int midIndex = sourceLen / 2;
                // 拆分成两个子任务
                SortTask task1 = new SortTask(Arrays.copyOf(source, midIndex));
                SortTask task2 = new SortTask(Arrays.copyOfRange(source, midIndex , sourceLen));
                task1.fork();
                task2.fork();
                // 将两个有序的数组，合并成一个有序的数组
                int result1[] = task1.join();
                int result2[] = task2.join();
                int mer[] = joinInts(result1 , result2);
                return mer;
            } 
            // 否则说明集合中只有一个或者两个元素，可以进行这两个元素的比较排序了
            else {
                // 如果条件成立，说明数组中只有一个元素，或者是数组中的元素都已经排列好位置了
                if(sourceLen == 1
                    || source[0] <= source[1]) {
                    return source;
                } else {
                    int targetp[] = new int[sourceLen];
                    targetp[0] = source[1];
                    targetp[1] = source[0];
                    return targetp;
                }
            }
        }
 
        private int[] joinInts(int array1[] , int array2[]) {
        	int destInts[] = new int[array1.length + array2.length];
            int array1Len = array1.length;
            int array2Len = array2.length;
            int destLen = destInts.length;
     
            // 只需要以新的集合destInts的长度为标准，遍历一次即可
            for(int index = 0 , array1Index = 0 , array2Index = 0 ; index < destLen ; index++) {
                int value1 = array1Index >= array1Len ? Integer.MAX_VALUE : array1[array1Index];
                int value2 = array2Index >= array2Len ? Integer.MAX_VALUE : array2[array2Index];
                // 如果条件成立，说明应该取数组array1中的值
                if(value1 < value2) {
                    array1Index++;
                    destInts[index] = value1;
                }
                // 否则取数组array2中的值
                else {
                    array2Index++;
                    destInts[index] = value2;
                }
            }
            return destInts;
        }
    }
}
