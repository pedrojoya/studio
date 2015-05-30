
package es.iessaladillo.pedrojoya.pr139.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("next_max_tag_id")
    @Expose
    private String nextMaxTagId;
    @SerializedName("deprecation_warning")
    @Expose
    private String deprecationWarning;
    @SerializedName("next_max_id")
    @Expose
    private String nextMaxId;
    @SerializedName("next_min_id")
    @Expose
    private String nextMinId;
    @SerializedName("min_tag_id")
    @Expose
    private String minTagId;
    @SerializedName("next_url")
    @Expose
    private String nextUrl;

    /**
     * 
     * @return
     *     The nextMaxTagId
     */
    public String getNextMaxTagId() {
        return nextMaxTagId;
    }

    /**
     * 
     * @param nextMaxTagId
     *     The next_max_tag_id
     */
    public void setNextMaxTagId(String nextMaxTagId) {
        this.nextMaxTagId = nextMaxTagId;
    }

    /**
     * 
     * @return
     *     The deprecationWarning
     */
    public String getDeprecationWarning() {
        return deprecationWarning;
    }

    /**
     * 
     * @param deprecationWarning
     *     The deprecation_warning
     */
    public void setDeprecationWarning(String deprecationWarning) {
        this.deprecationWarning = deprecationWarning;
    }

    /**
     * 
     * @return
     *     The nextMaxId
     */
    public String getNextMaxId() {
        return nextMaxId;
    }

    /**
     * 
     * @param nextMaxId
     *     The next_max_id
     */
    public void setNextMaxId(String nextMaxId) {
        this.nextMaxId = nextMaxId;
    }

    /**
     * 
     * @return
     *     The nextMinId
     */
    public String getNextMinId() {
        return nextMinId;
    }

    /**
     * 
     * @param nextMinId
     *     The next_min_id
     */
    public void setNextMinId(String nextMinId) {
        this.nextMinId = nextMinId;
    }

    /**
     * 
     * @return
     *     The minTagId
     */
    public String getMinTagId() {
        return minTagId;
    }

    /**
     * 
     * @param minTagId
     *     The min_tag_id
     */
    public void setMinTagId(String minTagId) {
        this.minTagId = minTagId;
    }

    /**
     * 
     * @return
     *     The nextUrl
     */
    public String getNextUrl() {
        return nextUrl;
    }

    /**
     * 
     * @param nextUrl
     *     The next_url
     */
    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

}
