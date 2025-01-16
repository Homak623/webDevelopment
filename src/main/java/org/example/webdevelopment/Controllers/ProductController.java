package org.example.webdevelopment.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.webdevelopment.Models.Product;
import org.example.webdevelopment.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public String products(@ModelAttribute Product product, Model model,BindingResult bindingResult)
    {
            if(!bindingResult.hasErrors()) {
                boolean hasFilters = (product.getTitle() != null && !product.getTitle().trim().isEmpty()) ||
                        (product.getDescription() != null && !product.getDescription().trim().isEmpty()) ||
                        (product.getPrice() > 0) ||
                        (product.getCity() != null && !product.getCity().trim().isEmpty()) ||
                        (product.getAuthor() != null && !product.getAuthor().trim().isEmpty());

                if (hasFilters) {
                    List<Product> filteredProducts = productService.getProducts(
                            product.getTitle(),
                            product.getDescription(),
                            product.getPrice(),
                            product.getCity(),
                            product.getAuthor()
                    );
                    model.addAttribute("products", filteredProducts);
                } else {

                    model.addAttribute("products", productService.getList());
                }
            }
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable long id, Model model) {
        Product product = productService.getProductsById(id);
        if (product == null) {
            return "redirect:/?error=ProductNotFound";
        }
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "product-info";
    }

    @PatchMapping("/product/update/{id}")
    public String updateProduct(@PathVariable long id,
                                @Valid Product product,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getFieldErrors());
            model.addAttribute("product", productService.getProductsById(id));
            return "product-info";
        }
        productService.updateProductFields(
                id,
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getCity(),
                product.getAuthor()
        );
        return "redirect:/product/" + id;
    }

    @PostMapping("/product/create")
    public String createProduct(@Valid @ModelAttribute Product product,
                                @RequestParam(name = "file1", required = false) MultipartFile file1,
                                @RequestParam(name = "file2", required = false) MultipartFile file2,
                                @RequestParam(name = "file3", required = false) MultipartFile file3,
                                BindingResult bindingResult,
                                Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getFieldErrors());
            model.addAttribute("product", product);
            return "products";
        }
        productService.saveProducts(product, file1, file2, file3);
        return "redirect:/";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }
}

