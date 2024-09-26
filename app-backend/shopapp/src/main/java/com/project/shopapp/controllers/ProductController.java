package com.project.shopapp.controllers;

import com.github.javafaker.Faker;
import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.ProductListResponse;
import com.project.shopapp.responses.ProductResponse;
import com.project.shopapp.services.ProductService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping("")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDTO productDTO,
                                        //@RequestPart("file") MultipartFile file,
                                        BindingResult result
    ){
        try{
//            "name": "Đ",
//                    "price": 1000000,
//                    "thumbnail": "",
//                    "description": "Đây là đồ điện tử",
//                    "category_id": 1
            if (result.hasErrors()) {
                List<String> errorsMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorsMessage);
            }
            Product newProduct = productService.createProduct(productDTO);
            return ResponseEntity.ok().body("Post product successfully: " + newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "/uploads/{id}" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable("id") Long productId, @ModelAttribute("files") List<MultipartFile> files) throws Exception {
        //List<MultipartFile> files = productDTO.getFiles();
        Product existingProduct = productService.getProductById(productId);
        files = files == null ? new ArrayList<>() : files;
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.getSize() == 0) continue;
            if(file.getSize() > 10*1024*1024) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body("File too large!");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("File must be an image");
            }
            String filename = storeFile(file);
            ProductImage productImage = productService
                    .createProductImage(productId,
                            ProductImageDTO.builder()
                                    .imageUrls(filename)
                                    .build());
            //save to database
            productImages.add(productImage);
        }
        return ResponseEntity.ok().body(productImages);
    }
    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        Path uploadDir = Paths.get("uploads");
        if(!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @PostMapping("/generateFakeProducts")
    public ResponseEntity<?> generateFakeProducts() {
        Faker faker = new Faker();
        for (int i = 0; i < 7000; i++) {
            String productName = faker.commerce().productName();
            if(productService.existByName(productName)) continue;
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .thumbnail("")
                    .description(faker.lorem().sentence())
                    .price((float)faker.number().numberBetween(10000, 100000000))
                    .categoryId((long)faker.number().numberBetween(2, 5))
                    .build();
            try {
                Product fakeProduct = productService.createProduct(productDTO);
            } catch (DataNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.ok().body("Create fake products");
    }

    @GetMapping("")
    public ResponseEntity<ProductListResponse> getProducts(@RequestParam ("page") int page, @RequestParam("limit") int limit ) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<ProductResponse> products = productService.getAllProducts(pageRequest);
        List<ProductResponse> productList = products.getContent();
        ProductListResponse productListResponse = ProductListResponse
                .builder()
                .products(productList)
                .totalPage(products.getTotalPages())
                .build();
        return ResponseEntity.ok().body(productListResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        try {
            Product existingProduct = productService.getProductById(id);
            return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductById(@PathVariable("id") long id, @RequestBody ProductDTO productDTO) {
        try {
            productService.updateProduct(id, productDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Update product with id = " + id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable("id") long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Delete product with id = " + id);
    }
}
