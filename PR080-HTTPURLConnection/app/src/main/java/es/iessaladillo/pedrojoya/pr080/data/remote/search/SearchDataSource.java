package es.iessaladillo.pedrojoya.pr080.data.remote.search;

public class SearchDataSource {

    public SearchRequest search(String text) {
        return new SearchRequest(text);
    }

}
