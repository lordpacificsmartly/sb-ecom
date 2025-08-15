package com.ecommerce.project.controller;

import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.service.AddressService;
import com.ecommerce.project.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {


    @Autowired
    private AddressService addressService;

    @Autowired
    AuthUtil authUtil;


    @Tag(name = "Address Controller", description = "APIs to create address, get addresses, get address by ID, get user addresses,update address, delete address")
    @Operation(summary = "Create Address", description = "API to create address")
    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        User user = authUtil.loggedInUser();
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO, user);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }

    @Tag(name = "Address Controller", description = "APIs to create address, get addresses, get address by ID, get user addresses,update address, delete address")
    @Operation(summary = "Get Addresses", description = "API to get addresses")
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddresses(){
        List<AddressDTO> addressList = addressService.getAddresses();
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @Tag(name = "Address Controller", description = "APIs to create address, get addresses, get address by ID, get user addresses,update address, delete address")
    @Operation(summary = "Get Address By Id", description = "API to get address by Id")
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(
            @Parameter(description = "ID of address that you wish to get")
            @PathVariable Long addressId){
        AddressDTO addressDTO = addressService.getAddressesById(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @Tag(name = "Address Controller", description = "APIs to create address, get addresses, get address by ID, get user addresses,update address, delete address")
    @Operation(summary = "Get User Addresses", description = "API to get addresses of a user")
    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getUserAddresses(){
        User user = authUtil.loggedInUser();
        List<AddressDTO> addressList = addressService.getUserAddresses(user);
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @Tag(name = "Address Controller", description = "APIs to create address, get addresses, get address by ID, get user addresses,update address, delete address")
    @Operation(summary = "Update Address", description = "API to update address")
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(
            @Parameter(description = "ID of address that you wish to update")
            @PathVariable Long addressId,
                                                    @RequestBody AddressDTO addressDTO){
        AddressDTO updatedAddress = addressService.updateAddress(addressId, addressDTO);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @Tag(name = "Address Controller", description = "APIs to create address, get addresses, get address by ID, get user addresses,update address, delete address")
    @Operation(summary = "Delete Address", description = "API to delete address")
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(
            @Parameter(description = "ID of address that you wish to delete")
            @PathVariable Long addressId){
        String status = addressService.deleteAddress(addressId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
