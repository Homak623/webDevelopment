package org.example.webdevelopment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webdevelopment.Models.Image;
import org.example.webdevelopment.Models.Product;
import org.example.webdevelopment.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getList() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByTitle(String title) {
        return  productRepository.findByTitle(title);
    }

    public void saveProducts(Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        List<Image> images = new ArrayList<>();

        if (file1.getSize() != 0) {
            Image img1 = toImageEntity(file1);
            img1.setPreviewImage(true);
            images.add(img1);
        }
        if (file2.getSize() != 0) {
            Image img2 = toImageEntity(file2);
            images.add(img2);
        }
        if (file3.getSize() != 0) {
            Image img3 = toImageEntity(file3);
            images.add(img3);
        }

        for (Image image : images) {
            image.setProduct(product);
        }

        product.setImages(images);
        log.info("Saving new Product.Title {}", product.getTitle());
        Product savedProduct = productRepository.save(product);

        if (!images.isEmpty()) {
            savedProduct.setPreviewImageId(images.get(0).getId());
            productRepository.save(savedProduct);
        }
    }

    @Transactional
    public void updateProductFields(Long id, String title, String description, Integer price, String city, String author) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Product not found with id: " + id)
        );

        if (title != null && !title.isEmpty()) {
            product.setTitle(title);
        }
        if (description != null && !description.isEmpty()) {
            product.setDescription(description);
        }
        if (price != null) {
            product.setPrice(price);
        }
        if (city != null && !city.isEmpty()) {
            product.setCity(city);
        }
        if (author != null && !author.isEmpty()) {
            product.setAuthor(author);
        }

        productRepository.save(product);
    }
    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalPath(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductsById(Long id) {
      return  productRepository.findById(id).orElse(null);
    }
}

