package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.product.ProductItemDTO;
import org.example.dto.product.ProductPostDTO;
import org.example.entities.CategoryEntity;
import org.example.entities.ProductEntity;
import org.example.entities.ProductImageEntity;
import org.example.mapper.ProductMapper;
import org.example.repository.IProductImageRepository;
import org.example.repository.IProductRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private IProductRepository productRepository;
    private FileService fileService;
    private IProductImageRepository productImageRepository;
    private ProductMapper productMapper;

    public List<ProductItemDTO> getAllProducts() {
        var list = productRepository.findAll();
        return productMapper.toDto(list);
    }

    public ProductItemDTO getProductById(Integer id) {
        return productMapper.toDto(productRepository.findById(id).orElse(null));
    }

    public ProductEntity createProduct(ProductPostDTO product) {
        var entity = new ProductEntity();
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setCreationTime(LocalDateTime.now());
        var cat = new CategoryEntity();
        cat.setId(product.getCategoryId());
        entity.setCategory(cat);

        productRepository.save(entity);

        int priority = 1;
        for (var img : product.getImages()) {
            var imageName = fileService.load(img);
            var img1 = new ProductImageEntity();
            img1.setPriority(priority);
            img1.setName(imageName);
            img1.setProduct(entity);
            productImageRepository.save(img1);
            priority++;
        }
        return entity;
    }

    public boolean updateProduct(Integer id, ProductPostDTO product) {
        var res = productRepository.findById(id);
        if (res.isEmpty()) {
            return false;
        }
        var entity = res.get();
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        var cat = new CategoryEntity();
        cat.setId(product.getCategoryId());
        entity.setCategory(cat);
        productRepository.save(entity);

        var newImageFiles = product.getImages();
        var oldImages = entity.getImages();
        var newImagesMap = new HashMap<String, Integer>();

        // Створюємо мапу для нових зображень
        int priority = 1;
        for (var file : newImageFiles) {
            if (file != null && !file.isEmpty()) {
                var imageName = file.getOriginalFilename();
                if ("old-image".equals(file.getContentType())) {
                    newImagesMap.put(imageName, priority++);
                }
            }
        }

        // Видаляємо старі зображення, яких немає в новому списку
        for (var productImage : oldImages) {
            if (!newImagesMap.containsKey(productImage.getName())) {
                fileService.remove(productImage.getName());
                productImageRepository.delete(productImage);
            }
        }

        // Оновлюємо пріоритет для збережених зображень
        for (var productImage : oldImages) {
            if (newImagesMap.containsKey(productImage.getName())) {
                productImage.setPriority(newImagesMap.get(productImage.getName()));
                productImageRepository.save(productImage);
            }
        }

        // Додаємо нові зображення
        for (var file : newImageFiles) {
            if (file != null && !file.isEmpty() && !"old-image".equals(file.getContentType())) {
                var imageName = fileService.load(file);
                var img = new ProductImageEntity();
                img.setPriority(priority++);
                img.setName(imageName);
                img.setProduct(entity);
                productImageRepository.save(img);
            }
        }

        return true;
    }

    public boolean deleteProduct(Integer id) {
        var res = productRepository.findById(id);
        if (res.isEmpty()) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }
}