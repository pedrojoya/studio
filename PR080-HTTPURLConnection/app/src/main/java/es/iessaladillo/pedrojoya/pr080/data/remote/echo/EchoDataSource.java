package es.iessaladillo.pedrojoya.pr080.data.remote.echo;

public class EchoDataSource {

    public EchoRequest requestEcho(String text) {
        return new EchoRequest(text);
    }

}
