package es.iessaladillo.pedrojoya.pr165.data.local;

import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr165.data.model.Product;

public class Database {

    private static Database instance;

    private final ArrayList<Product> products;
    private int mCount;

    private Database() {
        products = new ArrayList<>();
        insertInitialData();
    }

    public synchronized static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void insertInitialData() {
        products.add(new Product(++mCount, "Macarrones integrales", 1, "kg"));
        products.add(new Product(++mCount, "Atún en aceite vegetal", 6, "latas"));
        products.add(new Product(++mCount, "Aceite Carbonell", 2, "litros"));
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void deleteProduct(Product product) {
        products.remove(products.indexOf(product));
    }

    public void toogleProductBoughtState(Product product) {
        products.get(products.indexOf(product)).toggleBought();
    }

}
