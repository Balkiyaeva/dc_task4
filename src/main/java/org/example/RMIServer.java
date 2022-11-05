package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class RMIServer {
    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
        int port = 8080;
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        try {
            System.setProperty(RMI_HOSTNAME, hostName);

            // Create service for RMI
            Service service = new ServiceImpl();

            AtomicInteger countLines = new AtomicInteger();
            Path path = Paths.get("/Users/amira/Downloads/task_1");
            Files.walk(path).forEach(path1 -> {
                File file = path1.toFile();
                if (file.isFile()) {
                    try {
                        System.out.println(path1);
                        service.addElem(path1.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });

            String serviceName = "Service";

            System.out.println("Initializing " + serviceName);

            Registry registry = LocateRegistry.createRegistry(port);
            // Make service available for RMI
            registry.rebind(serviceName, service);

            System.out.println("Start " + serviceName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}