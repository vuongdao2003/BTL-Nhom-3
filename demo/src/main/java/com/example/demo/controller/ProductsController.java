package com.example.demo.controller;

import com.example.demo.dto.ProductsDTO;
import com.example.demo.payload.ResponData;
import com.example.demo.service.imp.FileServiceimp;
import com.example.demo.service.imp.ProductsServiceimp;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private FileServiceimp fileService;
    @Autowired
    ProductsServiceimp productsServiceimp;
    @PostMapping("/create")
    public ResponseEntity<?> createProducts(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productName") String productName,
            @RequestParam("category") Integer category,
            @RequestParam("price") Double price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("supplierID") Integer supplierID

    ) {
        ResponData responData = new ResponData();
        if (file == null || file.isEmpty() || productName == null || productName.trim().isEmpty()) {
            responData.setMessage("Không có dữ liệu ");
            return new ResponseEntity<>(responData, HttpStatus.BAD_REQUEST);
        }
        boolean isSucces = productsServiceimp.insertProduct(file, productName, category, price, quantity, supplierID);
        responData.setData(isSucces);
        responData.setMessage("Thêm sản phẩm thành công");
        return new ResponseEntity<>(responData, HttpStatus.CREATED);
    }
    @GetMapping("/getALL")
    public ResponseEntity<?> getProducts() {
        ResponData responData = new ResponData();
        responData.setData(productsServiceimp.getProducts());
        return new ResponseEntity<>(responData, HttpStatus.OK);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getProduct(@PathVariable String category) {
        ResponData responData = new ResponData();
        responData.setData(productsServiceimp.getProductsByCategoryT(category));
        return new ResponseEntity<>(responData, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<?> getProductByName(@RequestParam(required = false) String name,
                                              @RequestParam(required = false) String category) {
        ResponData responData = new ResponData();
        responData.setData(productsServiceimp.searchProducts(name, category));
        return new ResponseEntity<>(responData, HttpStatus.OK);

    }
    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        System.out.println("Download file: " + filename);
        Resource file = fileService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody ProductsDTO productsDTO) {
        productsDTO.setId(id);
        ResponData responData = new ResponData();
        boolean isSucces = productsServiceimp.updateProduct(productsDTO);
        if(!isSucces) {
            responData.setMessage("Không tìm thấy sản phẩm");
            return new ResponseEntity<>(responData, HttpStatus.NOT_FOUND);
        }
        responData.setMessage("Cập nhật thành công");
        responData.setData(isSucces);
        return new ResponseEntity<>(responData, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        ResponData responData = new ResponData();
        boolean isSucces = productsServiceimp.deleteProduct(id);
        if(!isSucces) {
            responData.setMessage("Không tìm thấy sản phẩm");
            return new ResponseEntity<>(responData, HttpStatus.NOT_FOUND);
        }
        responData.setMessage("Xóa sản phẩm thành công");
        responData.setData(isSucces);
        return new ResponseEntity<>(responData, HttpStatus.OK);
    }
}
