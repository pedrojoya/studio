package es.iessaladillo.pedrojoya.pr109.Model;

import org.json.JSONObject;

public class TareaACL {
    private String concepto;
    private String responsable;
    private ACLField ACL;

    public TareaACL(String concepto, String responsable, ACLField ACL) {
        this.concepto = concepto;
        this.responsable = responsable;
        this.ACL = ACL;
    }

    public TareaACL() {
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public ACLField getACL() {
        return ACL;
    }

    public void setACL(ACLField ACL) {
        this.ACL = ACL;
    }

}
