package es.iessaladillo.pedrojoya.pr140.data.remote.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@SuppressWarnings("unused")
@Root(name = "blancos")
public class BlankVotes {
    @Element(name = "porcentaje")
    private String percentage;
    @Element(name = "cantidad")
    private String quantity;

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
