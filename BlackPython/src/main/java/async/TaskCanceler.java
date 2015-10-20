package async;

import android.os.AsyncTask;

/**
 * Created by SkyRoW on 31.5.2015.
 */
public class TaskCanceler implements Runnable{
    private AsyncTask task;

    public TaskCanceler(AsyncTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        if (task.getStatus() == AsyncTask.Status.RUNNING )
            task.cancel(true);
    }
}
