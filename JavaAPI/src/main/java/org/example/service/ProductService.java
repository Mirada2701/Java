package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.product.ProductItemDTO;
import org.example.dto.product.ProductPostDTO;
import org.example.entities.ProductEntity;
import org.example.mapper.ProductMapper;
import org.example.repository.IProductRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private IProductRepository productRepository;
    private FileService fileService;
    private ProductMapper productMapper;

    public List<ProductItemDTO> getAllProducts() {
        return productMapper.toDto(productRepository.findAll());
    }

    public ProductItemDTO getProductById(Integer id) {
        return productMapper.toDto(productRepository.findById(id).orElse(null));
    }

    public ProductEntity createProduct(ProductPostDTO product) {
        var entity = new ProductEntity();
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setAmount(product.getAmount());
        entity.setCreationTime(LocalDateTime.now());
        entity.setCategory(product.getCategory());

        return productRepository.save(entity);
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
        entity.setAmount(product.getAmount());
        entity.setCategory(product.getCategory());

        productRepository.save(entity);
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
