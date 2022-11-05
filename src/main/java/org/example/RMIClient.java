package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.util.Scanner;

public class RMIClient {
    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8080;
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        String SERVICE_PATH = "//" + hostName + ":" + port + "/Service";

        try {
            System.setProperty(RMI_HOSTNAME, hostName);
            Service service = (Service) Naming.lookup(SERVICE_PATH);

            String str = service.pollElem();

            int num = countLines(str);
            if (str == null) {
                System.out.println("Received none!");
            } else {
                System.out.println("Received: " + num);
                service.processElemInQueue(num);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static int countLines(String str) {
        Path path = Paths.get(str);
        File file = path.toFile();
        int countLines = 0;
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            assert sc != null;
            if (!sc.hasNextLine()) break;
            else {
                countLines++;
                sc.nextLine();
            }
        }
        return countLines;
    }


}
