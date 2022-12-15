package br.com.content4devs.projetogrpcjava.util;

import br.com.content4devs.projetogrpcjava.domain.Product;
import br.com.content4devs.projetogrpcjava.dto.ProductInputDTO;
import br.com.content4devs.projetogrpcjava.dto.ProductOutputDTO;

public class ProductConverterUtil {

    public static ProductOutputDTO productToProductOutputDto(Product product){
        ProductOutputDTO productOutputDTO = new ProductOutputDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantityInStock()
        );

        return productOutputDTO;
    }

    public static Product productInputDtoToProduct(ProductInputDTO productInputDto){
        Product product = new Product(
                null,
                productInputDto.getName(),
                productInputDto.getPrice(),
                productInputDto.getQuantityInStock()
        );

        return product;
    }
}
