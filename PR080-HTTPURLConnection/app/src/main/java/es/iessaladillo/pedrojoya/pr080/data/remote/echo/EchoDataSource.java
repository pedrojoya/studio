package es.iessaladillo.pedrojoya.pr080.data.remote.echo;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr080.base.Resource;

public interface EchoDataSource {

    LiveData<Resource<String>> requestEcho(String text, String tag);

    void cancel(String tag);

}
