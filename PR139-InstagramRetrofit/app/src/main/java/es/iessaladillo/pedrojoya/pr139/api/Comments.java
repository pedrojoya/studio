
package es.iessaladillo.pedrojoya.pr139.api;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Comments {

    @Expose
    private Integer count;
    @Expose
    private List<Object> data = new ArrayList<>();

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
    public List<Object> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Object> data) {
        this.data = data;
    }

}
