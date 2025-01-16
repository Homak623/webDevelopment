package org.example.webdevelopment.repositories;

import org.example.webdevelopment.Models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long>
{
}
