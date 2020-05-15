package dk.grouptwo.networking.remote;

import dk.grouptwo.model.objects.Job;

import java.rmi.Remote;

public interface RemoteWorkerClient extends Remote {
    public void addJob(Job job);

    public void moveToUpcoming(Job job);

    public void moveToHistory(Job job);
}
