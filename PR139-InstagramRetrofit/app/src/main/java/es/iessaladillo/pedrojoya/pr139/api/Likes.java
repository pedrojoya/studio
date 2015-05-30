
package es.iessaladillo.pedrojoya.pr139.api;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Likes {

    @Expose
    private Integer count;
    @Expose
    private List<Datum_> data = new ArrayList<Datum_>();

    /**
     * 
     * @return
     *     The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum_> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum_> data) {
        this.data = data;
    }

}
