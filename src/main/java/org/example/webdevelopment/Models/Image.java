package org.example.webdevelopment.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private  Long id;
    @Column(name="name")
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30")
    private String name;
    @Column(name="originalPath")
    @NotEmpty(message = "Path shouldn't be empty")
    @Size(min = 2, max = 50, message = "Path should be between 2 and 50")
    private  String originalPath;
    @Column(name="size")
    @Min(value = 0, message = "Size should be greater then 0")
    private Long size;
    @Column(name="content_type")
    @NotEmpty(message = "ContentType shouldn't be empty")
    private  String contentType;
    @Column(name="isPreviewImage")
    private  boolean isPreviewImage;
    @Lob
    private byte[] bytes;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    private Product product;

}
