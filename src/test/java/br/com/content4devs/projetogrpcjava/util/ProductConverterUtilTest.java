package br.com.content4devs.projetogrpcjava.util;

import br.com.content4devs.projetogrpcjava.domain.Product;
import br.com.content4devs.projetogrpcjava.dto.ProductInputDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductConverterUtilTest {

    @Test
    public void productToProductOutputDtoTest(){
        var product = new Product(1L, "product name", 10.00, 10);
        var productOutputDto = ProductConverterUtil.productToProductOutputDto(product);

        Assertions.assertThat(product)
                .usingRecursiveComparison()
                .isEqualTo(productOutputDto);
    }

    @Test
    public void productInputDtoToProductTest(){
        var productInputDto = new ProductInputDTO( "product name", 10.00, 10);
        var product = ProductConverterUtil.productInputDtoToProduct(productInputDto);

        Assertions.assertThat(productInputDto)
                .usingRecursiveComparison()
                .isEqualTo(product);
    }
}
