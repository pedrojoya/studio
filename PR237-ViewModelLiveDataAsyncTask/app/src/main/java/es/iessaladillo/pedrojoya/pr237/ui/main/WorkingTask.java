package es.iessaladillo.pedrojoya.pr237.ui.main;

import es.iessaladillo.pedrojoya.pr237.base.AsyncLiveTask;
import es.iessaladillo.pedrojoya.pr237.base.Event;
import es.iessaladillo.pedrojoya.pr237.base.Resource;

class WorkingTask extends AsyncLiveTask<Resource<Event<String>>> {

    private final Integer steps;

    WorkingTask(Integer steps) {
        this.steps = steps;
    }

    @Override
    protected void doAsync() {
        postValue(Resource.loading(0));
        for (int i = 0; i < steps && !isCancelled(); i++) {
            try {
                work();
            } catch (InterruptedException e) {
                return;
            }
            postValue(Resource.loading(i + 1));
        }
        if (!isCancelled()) {
            postValue(Resource.success(new Event<>("Task completed successfully")));
        }
    }

    @Override
    protected void doOnCancelled() {
        postValue(Resource.error(new Exception("Task cancelled by user")));
    }

    private void work() throws InterruptedException {
        Thread.sleep(1000);
    }

}
