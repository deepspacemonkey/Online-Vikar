package dk.grouptwo.networking;

import dk.grouptwo.model.objects.Employer;
import dk.grouptwo.model.objects.Job;
import dk.grouptwo.model.objects.License;
import dk.grouptwo.model.objects.Worker;
import dk.grouptwo.networking.remote.RemoteClient;
import dk.grouptwo.networking.remote.RemoteServer;
import dk.grouptwo.utility.Encryptor;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

//maybe singleton
public class Connection implements RemoteClient {
    private RemoteServer server;
    private boolean connected;

    public Connection() {
        connected = false;
    }

    public void StartClient(String host, int port) {
        try {
            Registry registry = null;
            try {
                registry = LocateRegistry.getRegistry(host, port);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            UnicastRemoteObject.exportObject(this, 0);
            server = (RemoteServer) registry.lookup("Server");

            server.registerClient(this);
            connected = true;
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public Employer loginEmployer(String CVR, String password)
            throws RemoteException, NoSuchAlgorithmException {
        return server.loginEmployer(CVR, Encryptor.encrypt(password));
    }

    public Worker loginWorker(String CPR, String password)
            throws RemoteException, NoSuchAlgorithmException {
        return server.loginWorker(CPR, Encryptor.encrypt(password));
    }

    public void createEmployerAccount(Employer employer,
                                      String password) throws RemoteException, NoSuchAlgorithmException {
        server.createEmployerAccount(employer, Encryptor.encrypt(password));
    }

    public void createWorkerAccount(Worker worker, String password)
            throws RemoteException, NoSuchAlgorithmException {
        server.createWorkerAccount(worker, Encryptor.encrypt(password));
    }

    public Employer editEmployer(Employer employer, String password)
            throws RemoteException, NoSuchAlgorithmException {
        return server.editEmployer(employer, Encryptor.encrypt(password));
    }

    public Employer editEmployer(Employer employer, String password, String newPassword)
            throws RemoteException, NoSuchAlgorithmException {
        return server.editEmployer(employer, Encryptor.encrypt(password), Encryptor.encrypt(newPassword));
    }

    public Worker editWorker(Worker worker, String password)
            throws RemoteException, NoSuchAlgorithmException {
        return server.editWorker(worker, Encryptor.encrypt(password));
    }

    public Worker editWorker(Worker worker, String password, String newPassword)
            throws RemoteException, NoSuchAlgorithmException {
        return server.editWorker(worker, Encryptor.encrypt(password), Encryptor.encrypt(newPassword));
    }

    public void addJob(Job job) throws RemoteException {
        server.addJob(job, this);
    }

    public void removeJob(Job job) throws RemoteException {
        server.removeJob(job, this);
    }

    public void updateJob(Job job) throws RemoteException {
        server.updateJob(job, this);
    }

    public void applyForAJob(Job job, Worker worker) throws RemoteException {
        server.applyForJob(job, worker);
    }

    public ArrayList<Job> getUpcomingJobs(Worker worker) throws RemoteException {
        return server.getUpcomingJobs(worker);
    }

    public ArrayList<Job> getWorkerJobHistory(Worker worker) throws RemoteException {
        return server.getWorkerJobHistory(worker);
    }

    public ArrayList<Job> getEmployerWorkHistory(Employer employer) throws RemoteException {
        return server.getEmployerWorkHistory(employer);
    }

    //todo
    public void addLicense(License license, Worker worker) throws RemoteException {
        server.addLicense(license, worker);
    }

    public void removeLicense(License license) throws RemoteException {
        server.removeLicense(license);
    }

    public ArrayList<Job> getEmployerJobs(Employer employer) {
        return null;
    }

    public ArrayList<Job> getJobs() {
        return null;
    }

    //REMOTE
    //todo

    @Override
    public void _addJob(Job job) throws RemoteException {
        System.out.println(job);
    }

    @Override
    public void _removeJob(Job job) throws RemoteException {
        System.out.println(job);
    }

    @Override
    public void _updateJob(Job job) throws RemoteException {
        System.out.println(job);
    }
}
