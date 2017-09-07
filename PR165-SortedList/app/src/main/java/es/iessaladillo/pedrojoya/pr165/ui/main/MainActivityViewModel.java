package es.iessaladillo.pedrojoya.pr165.ui.main;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import es.iessaladillo.pedrojoya.pr165.data.local.Repository;
import es.iessaladillo.pedrojoya.pr165.data.model.Product;

class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private List<Product> products;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public List<Product> getProducts() {
        if (products == null) {
            products = repository.getProducts();
        }
        return products;
    }

}
