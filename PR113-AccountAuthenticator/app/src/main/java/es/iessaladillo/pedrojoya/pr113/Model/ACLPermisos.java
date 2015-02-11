package es.iessaladillo.pedrojoya.pr113.Model;

/**
 * Created by Pedro on 22/01/2015.
 */
public class ACLPermisos {
    boolean read;
    boolean write;

    public ACLPermisos(boolean read, boolean write) {
        this.read = read;
        this.write = write;
    }
}
