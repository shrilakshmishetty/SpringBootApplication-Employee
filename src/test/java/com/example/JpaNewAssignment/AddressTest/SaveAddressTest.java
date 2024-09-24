package com.example.JpaNewAssignment.AddressTest;

import com.example.JpaNewAssignment.Model.Address;
import com.example.JpaNewAssignment.Repository.AddressRepository;
import com.example.JpaNewAssignment.ServiceImpl.AddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;



@ExtendWith(MockitoExtension.class)
public class SaveAddressTest {
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private AddressService addressService;
    @Test
    public void saveAddressTest(){
        Address address=new Address();
        addressService.saveAddress(address);
        Mockito.verify(addressRepository, Mockito.times(1)).save(address);
    }
}
