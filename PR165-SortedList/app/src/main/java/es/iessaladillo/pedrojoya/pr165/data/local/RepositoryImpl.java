package es.iessaladillo.pedrojoya.pr165.data.local;

import java.util.List;

import es.iessaladillo.pedrojoya.pr165.data.model.Product;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final Database database;

    private RepositoryImpl(Database database) {
        this.database = database;
    }

    public synchronized static RepositoryImpl getInstance(Database database) {
        if (instance == null) {
            instance = new RepositoryImpl(database);
        }
        return instance;
    }

    @Override
    public List<Product> getProducts() {
        return database.getProducts();
    }

    @Override
    public void addProduct(Product product) {
        database.addProduct(product);
    }

    @Override
    public void deleteProduct(Product product) {
        database.deleteProduct(product);
    }

    @Override
    public void toggleBoughtState(Product product) {
        database.toogleProductBoughtState(product);
    }

}
