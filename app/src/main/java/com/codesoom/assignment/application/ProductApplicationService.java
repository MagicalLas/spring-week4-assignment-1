package com.codesoom.assignment.application;

import com.codesoom.assignment.controller.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductId;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * product에 대한 use case들을 담당합니다.
 */
@Service
public class ProductApplicationService {
    ProductRepository productRepository;

    /**
     * @param productRepository product를 저장하고 가져오는데 필요한 repositroy
     */
    public ProductApplicationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 생성된 모든 product들을 반환합니다.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * product를 생성합니다.
     *
     * @param name     생성될 product의 이름
     * @param maker    생성될 product의 제조회사
     * @param price    생성될 product의 가격
     * @param imageURL 생성될 product의 이미지 주소
     * @return 생성된 product을 반환합니다.
     */
    public Product createProduct(String name, String maker, String price, String imageURL) {
        ProductId id = productRepository.nextId();
        Product newProduct = new Product(id, name, maker, price, imageURL);
        productRepository.save(newProduct);
        return newProduct;
    }

    /**
     * product를 삭제합니다.
     *
     * @param id 삭제할 product의 id
     */
    public void deleteProduct(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.find(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException(id);
        }
        productRepository.remove(product.get());
    }

    /**
     * 생성된 product를 가져옵니다.
     *
     * @param id 가져오려는 product의 id
     * @return 생성된 product을 반환합니다. 만약 product를 가져올 수 없으면 null을 반환합니다.
     */
    public Optional<Product> getProduct(Long id) {
        return productRepository.find(id);
    }

    public void updateProduct(Long id, String newName, String newMaker, String newPrice, String newImageURL) throws ProductNotFoundException {
        Optional<Product> product = productRepository.find(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException(id);
        }
        product.get().updateInformation(newName, newMaker, newPrice, newImageURL);
    }
}
