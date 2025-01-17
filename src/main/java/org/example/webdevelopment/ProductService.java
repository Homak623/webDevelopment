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
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getList() {
        return productRepository.findAll();
    }

    public void saveProducts(Product product, MultipartFile... files) throws IOException {
        List<Image> images = Arrays.stream(files)
                .filter(file -> file != null && file.getSize() > 0)
                .map(file -> {
                    try {
                        return toImageEntity(file);
                    } catch (IOException e) {
                        throw new RuntimeException("Error converting file to Image entity", e);
                    }
                })
                .collect(Collectors.toList());

        if (!images.isEmpty()) {
            images.get(0).setPreviewImage(true);
            images.forEach(image -> image.setProduct(product));
            product.setImages(images);

            Product savedProduct = productRepository.save(product);
            savedProduct.setPreviewImageId(images.get(0).getId());
            productRepository.save(savedProduct);
        } else {
            productRepository.save(product);
        }
    }


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

    public List<Product> getProducts(String title, String description, Integer price, String city, String author) {
        return productRepository.findAll().stream()
                .filter(product -> title == null || title.trim().isEmpty() || product.getTitle().toLowerCase().contains(title.trim().toLowerCase()))
                .filter(product -> description == null || description.trim().isEmpty() || product.getDescription().toLowerCase().contains(description.trim().toLowerCase()))
                .filter(product -> price == null || price <= 0 || product.getPrice() == price)
                .filter(product -> city == null || city.trim().isEmpty() || product.getCity().toLowerCase().contains(city.trim().toLowerCase()))
                .filter(product -> author == null || author.trim().isEmpty() || product.getAuthor().toLowerCase().contains(author.trim().toLowerCase()))
                .collect(Collectors.toList());
    }
}

