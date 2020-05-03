package dk.grouptwo.networking;

import dk.grouptwo.model.objects.Employer;
import dk.grouptwo.model.objects.Job;
import dk.grouptwo.model.objects.Worker;
import dk.grouptwo.networking.remote.RemoteClient;
import dk.grouptwo.networking.remote.RemoteServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements RemoteServer{
    private ArrayList<RemoteClient> clients;
    private Worker worker;
    private Employer employer;

    public Server() throws RemoteException {
        UnicastRemoteObject.exportObject(this,0);
        //TODO sorry had to comment this out
        /*Naming.rebind("Job", this);*/
      /*  clients = new ArrayList<RemoteClient>();
        worker = null;
        employer = null;*/
    }

    public static String getIP() {
        try {
            URL aws = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    aws.openStream()));

            String ip = in.readLine();
            return ip;
        } catch (IOException e) {
            e.printStackTrace();
            return "Could not get IP";
        }
    }

    public void start() throws RemoteException, MalformedURLException {
        System.out.printf("Server IP: %s\n", "localhost"/*getIP()*/);
        UnicastRemoteObject.exportObject(this, 0);
        Naming.rebind("Server", this); //permission error
    }

    @Override
    public void registerClient(RemoteClient clientToRegister) throws RemoteException {
        for (RemoteClient client : clients) {
            if(client.equals(employer)) {
                assert clientToRegister instanceof Employer;
                client.createEmployerAccount((Employer) clientToRegister); // password seems redundant
            }
            else if (client.equals(worker)){
                assert clientToRegister instanceof Worker;
                client.createWorkerAccount((Worker)clientToRegister);
            }
        }
    }

    @Override
    public void addJob(Job job) throws RemoteException {
        try {
           RemoteClient remoteClient = (RemoteClient) Naming.lookup("rmi://" + job + ":1099/Job");
            System.out.println(job + " added");
           clients.add(remoteClient);
            for (RemoteClient client : clients) {
                client.updateJob(job);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeJob(Job job) throws RemoteException {
        try {
            for (RemoteClient client : clients) {
                client.removeJob(job);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}