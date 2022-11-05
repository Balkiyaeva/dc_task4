package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implementation of the remote service.
 */
public class ServiceImpl extends UnicastRemoteObject implements Service {
    private static ArrayList<Integer> numbers = new ArrayList<>();
    private boolean isstart = false;
    private static long start = 0, end = 0;
    private final BlockingQueue<String> queue;
    public ServiceImpl() throws RemoteException {
        super();
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public String pollElem() throws RemoteException {
        if (!isstart) {
            start = System.nanoTime();
        }
        isstart = true;
        return this.queue.poll();
    }

    @Override
    public void addElem(String str) throws RemoteException {
        this.queue.add(str);
        System.out.println("Added: " + str);
    }

    @Override
    public void processElemInQueue(Integer integer) throws RemoteException {
        numbers.add(integer);
        if (!queue.isEmpty()) {
            System.out.println(queue);
            return;
        }
        sumOfAll();
    }

    public static void sumOfAll(){
        int sum = 0;
        for (Integer el: numbers) {
            sum += el;
        }
        System.out.println("The sum of all lines of each file: " + sum);
        end = System.nanoTime();
        long time = end - start;
        System.out.println("Total time is " + time);
    }

}
