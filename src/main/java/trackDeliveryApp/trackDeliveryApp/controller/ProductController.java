package trackDeliveryApp.trackDeliveryApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trackDeliveryApp.trackDeliveryApp.dto.ProductDTO;
import trackDeliveryApp.trackDeliveryApp.mapper.ProductMapper;
import trackDeliveryApp.trackDeliveryApp.model.Product;
import trackDeliveryApp.trackDeliveryApp.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{productId}")
    public ProductDTO getProductById(@PathVariable String productId) {
        Product product = productService.getProductById(productId);
        return productMapper.toDTO(product);
    }

    @PostMapping
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productService.createProduct(product);
        return productMapper.toDTO(savedProduct);
    }

    @PutMapping("/{productId}")
    public ProductDTO updateProduct(@Valid @PathVariable String productId, @RequestBody ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product updatedProduct = productService.updateProduct(productId, product);
        return productMapper.toDTO(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
    }
}
