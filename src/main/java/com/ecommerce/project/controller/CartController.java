package com.ecommerce.project.controller;

import com.ecommerce.project.model.Cart;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.payload.CartItemDTO;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.service.CartService;
import com.ecommerce.project.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuthUtil authUtil;


    @Autowired
    private CartService cartService;

    @Tag(name = "Cart Controller", description = "APIs to create cart, add product to cart, get cart, get cart by ID, update cart product, delete product from cart")
    @Operation(summary = "Create Or Update Cart", description = "API to create or update cart")
    @PostMapping("/cart/create")
    public ResponseEntity<String> createOrUpdateCart(@RequestBody List<CartItemDTO> cartItems) {
        String response = cartService.createOrUpdateCartWithItems(cartItems);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Tag(name = "Cart Controller", description = "APIs to create cart, add product to cart, get cart, get cart by ID, update cart product, delete product from cart")
    @Operation(summary = "Add Product to Cart", description = "API to add product to cart")
    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(
            @Parameter(description = "ID of product that you wish to add to cart")
            @PathVariable Long productId,
            @PathVariable Integer quantity) {
        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }


    @Tag(name = "Cart Controller", description = "APIs to create cart, add product to cart, get cart, get cart by ID, update cart product, delete product from cart")
    @Operation(summary = "Get Carts", description = "API to get carts")
    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getCarts() {
        List<CartDTO> cartDTOs = cartService.getAllCarts();
        return new ResponseEntity<>(cartDTOs, HttpStatus.FOUND);
    }


    @Tag(name = "Cart Controller", description = "APIs to create cart, add product to cart, get cart, get cart by ID, update cart product, delete product from cart")
    @Operation(summary = "Get Cart By Id", description = "API to get carts by ID")
    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCartById() {
        String emailId = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(emailId);
        Long cartId = cart.getCartId();
        CartDTO cartDTO = cartService.getCart(emailId, cartId);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }


    @Tag(name = "Cart Controller", description = "APIs to create cart, add product to cart, get cart, get cart by ID, update cart product, delete product from cart")
    @Operation(summary = "Update Cart Product", description = "API to update cart product")
    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct(
            @Parameter(description = "Operation that you wish to perform - increase item or decrease item")
            @PathVariable Long productId,
            @PathVariable String operation) {

        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,
                operation.equalsIgnoreCase("delete") ? -1 : 1);

        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }


    @Tag(name = "Cart Controller", description = "APIs to create cart, add product to cart, get cart, get cart by ID, update cart product, delete product from cart")
    @Operation(summary = "Delete Product From Cart", description = "API to delete product from cart")
    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(
            @Parameter(description = "ID of product that you wish to delete")
            @PathVariable Long cartId,
            @PathVariable Long productId) {
        String status = cartService.deleteProductFromCart(cartId, productId);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
