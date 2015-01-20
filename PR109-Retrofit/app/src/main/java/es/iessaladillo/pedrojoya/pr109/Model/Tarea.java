package es.iessaladillo.pedrojoya.pr109.Model;

public class Tarea {
    private String concepto;
    private String createdAt;
    private String objectId;
    private String responsable;
    private String updatedAt;

    public Tarea(String concepto, String createdAt, String objectId, String responsable, String updatedAt) {
        this.concepto = concepto;
        this.createdAt = createdAt;
        this.objectId = objectId;
        this.responsable = responsable;
        this.updatedAt = updatedAt;
    }

    public Tarea() {}

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
