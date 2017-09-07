package es.iessaladillo.pedrojoya.pr165.data.local;

import java.util.List;

import es.iessaladillo.pedrojoya.pr165.data.model.Product;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Product> getProducts();
    void addProduct(Product product);
    void deleteProduct(Product product);
    void toggleBoughtState(Product product);

}
