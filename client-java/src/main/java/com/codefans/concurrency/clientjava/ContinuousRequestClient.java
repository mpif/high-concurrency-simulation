package com.codefans.concurrency.clientjava;

import com.codefans.concurrency.clientcommon.ContinuousRequestTask;
import com.codefans.concurrency.clientcommon.HttpAsynClientRequest;

import java.io.*;
import java.util.Date;
import java.util.Scanner;

/**
 * @author: ShengzhiCai
 * @date: 2018-11-27 16:57
 */
public class ContinuousRequestClient {

    private static final String START = "start";
    private static final String STOP = "stop";
    private static final String SHUTDOWN = "shutdown";

    private boolean running;

    public static void main(String[] args) {
        ContinuousRequestClient requestClient = new ContinuousRequestClient();
        requestClient.startup();
    }

    public void startup() {

        Scanner sc = null;

        try {

            System.out.println("waiting command: start|stop");

            running = true;
            String command = "";

            while (running) {

                sc = new Scanner(System.in);
                if(sc.hasNextLine()) {
                    command = sc.nextLine();
                    if (START.equalsIgnoreCase(command)) {
                        new Thread(new ContinuousRequestTask()).start();
                    } else if (STOP.equalsIgnoreCase(command)) {
                        ContinuousRequestTask.stopTask();
                    } else if (SHUTDOWN.equalsIgnoreCase(command)) {
                        running = false;
                        ContinuousRequestTask.shutdown();
                    } else {
                        System.out.println("unkown command:" + command);
                    }
                }

            }

            System.out.println("end!");
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(sc);
        }


    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void close(InputStream is) {
        try {
            if(is != null) {
                is.close();
                is = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(BufferedReader reader) {
        try {
            if(reader != null) {
                reader.close();
                reader = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(Scanner sc) {
        if(sc != null) {
            sc.close();
            sc = null;
        }
    }

}
