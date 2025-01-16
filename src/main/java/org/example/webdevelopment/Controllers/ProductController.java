package org.example.webdevelopment.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.webdevelopment.Models.Product;
import org.example.webdevelopment.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title, Model model) {
        if (title != null && !title.isEmpty())
        {
            model.addAttribute("products", productService.getProductsByTitle(title));
        }
        else
        {
            model.addAttribute("products", productService.getList());
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
                                @RequestParam(required = false) String title,
                                @RequestParam(required = false) String description,
                                @RequestParam(required = false) Integer price,
                                @RequestParam(required = false) String city,
                                @RequestParam(required = false) String author) {
        productService.updateProductFields(id, title, description, price, city, author);
        return "redirect:/product/" + id;
    }

    @PostMapping("/product/create")
    public String createProduct(Product product,@RequestParam(name="file1") MultipartFile file1,
                                @RequestParam(name="file2")MultipartFile file2,
                                @RequestParam(name="file3")MultipartFile file3)  throws IOException {
        productService.saveProducts(product,file1,file2,file3);
        return "redirect:/";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }
}

