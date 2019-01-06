package es.iessaladillo.pedrojoya.pr080.data.remote.search;

public class SearchDataSourceImpl implements SearchDataSource {

    public SearchRequest search(String text) {
        return new SearchRequest(text);
    }

}
