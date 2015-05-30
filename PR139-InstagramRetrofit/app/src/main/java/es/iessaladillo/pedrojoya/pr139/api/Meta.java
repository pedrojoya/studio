
package es.iessaladillo.pedrojoya.pr139.api;

import com.google.gson.annotations.Expose;

public class Meta {

    @Expose
    private Integer code;

    /**
     * 
     * @return
     *     The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

}
