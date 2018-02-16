
package es.iessaldillo.pedrojoya.pr208.data.remote.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeopleResponse {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("results")
    @Expose
    private List<PeopleResponseResult> peopleResponseResults = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public List<PeopleResponseResult> getPeopleResponseResults() {
        return peopleResponseResults;
    }

    public void setPeopleResponseResults(List<PeopleResponseResult> peopleResponseResults) {
        this.peopleResponseResults = peopleResponseResults;
    }

}
