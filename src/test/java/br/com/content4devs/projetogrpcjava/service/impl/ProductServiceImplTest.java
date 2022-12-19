package br.com.content4devs.projetogrpcjava.service.impl;

import br.com.content4devs.projetogrpcjava.domain.Product;
import br.com.content4devs.projetogrpcjava.dto.ProductInputDTO;
import br.com.content4devs.projetogrpcjava.dto.ProductOutputDTO;
import br.com.content4devs.projetogrpcjava.exceptions.ProductAlreadyExistsException;
import br.com.content4devs.projetogrpcjava.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("When create product is called with valid data a product is returned")
    public void createProductSuccessTest(){
        Product product = new Product(1L, "product name", 10.0, 10);

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductInputDTO productInputDTO = new ProductInputDTO(
                product.getName(),
                product.getPrice(),
                product.getQuantityInStock()
        );

        ProductOutputDTO productOutputDTO = productService.create(productInputDTO);

        Assertions.assertThat(productOutputDTO)
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    @Test
    @DisplayName("When create product is called with duplicated name, throw ProductAlreadyExists exception")
    public void createProductExceptionTest(){
        Product product = new Product(1L, "product name", 10.0, 10);

        Mockito.when(productRepository.findByNameIgnoreCase(Mockito.any())).thenReturn(Optional.of(product));

        ProductInputDTO productInputDTO = new ProductInputDTO(
                product.getName(),
                product.getPrice(),
                product.getQuantityInStock()
        );

        Assertions.assertThatExceptionOfType(ProductAlreadyExistsException.class)
                .isThrownBy(() -> productService.create(productInputDTO));
    }
}
