package com.sorin.sda.clothingshop.controller;

import com.sorin.sda.clothingshop.model.Product;
import com.sorin.sda.clothingshop.service.CategoryService;
import com.sorin.sda.clothingshop.service.ProductService;
import com.sorin.sda.clothingshop.service.dto.ProductDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/product-list")

    public String showProductListPage(Model model) {
        model.addAttribute("products", productService.getAll());
        return "productList";
    }

    @GetMapping("/add-product")
    public String addProduct(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("product", new ProductDTO());
        return "product-add";
    }

    @PostMapping("/addproduct")
    public String saveProduct(@ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-add";
        }
        productService.save(productDTO);
        return "redirect:/product-list";
    }

    @GetMapping("/edit-product/{id}")
    public String editProduct(@PathVariable("id") Long productId, Model model) {
        ProductDTO productDTO = productService.findProductDTObyId(productId);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("product", productDTO);
        return "product-edit";
    }

    @PostMapping("/editproduct")
    public String editProduct(@ModelAttribute("product") ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-edit";
        }
        productService.update(productDTO);
        return "redirect:/product-list";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable("id") Long productId) {
        productService.delete(productId);
        return "redirect:/product-list";
    }
}
