package org.example.webdevelopment.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    @NotEmpty(message = "Title shouldn't be empty")
    @Size(min = 3, max = 50, message = "Title should be between 3 and 50")
    private String title;
    @NotEmpty(message = "Description shouldn't be empty")
    @Size(min = 5, max = 1000, message = "Description should be between 5 and 1000")
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "price")
    @Min(value = 0,message = "Price should be greater then 0")
    private int price;
    @Column(name = "city")
    @NotEmpty(message = "City shouldn't be empty")
    @Size(min = 3, max = 20, message = "City should be between 3 and 20")
    private String city;
    @Column(name = "author")
    @NotEmpty(message = "Author shouldn't be empty")
    @Size(min = 3, max = 50, message = "Author should be between 3 and 50")
    private String author;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init(){
        dateOfCreated=LocalDateTime.now();
    }

    public void addImageToProduct(Image image){
        image.setProduct(this);
        images.add(image);
    }
}



