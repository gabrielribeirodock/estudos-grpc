package br.com.content4devs.projetogrpcjava.service.impl;

import br.com.content4devs.projetogrpcjava.dto.ProductInputDTO;
import br.com.content4devs.projetogrpcjava.dto.ProductOutputDTO;
import br.com.content4devs.projetogrpcjava.exceptions.ProductAlreadyExistsException;
import br.com.content4devs.projetogrpcjava.repository.ProductRepository;
import br.com.content4devs.projetogrpcjava.service.IProductService;
import br.com.content4devs.projetogrpcjava.util.ProductConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductOutputDTO create(ProductInputDTO inputDTO) {
        checkDuplicity(inputDTO.getName());
        var product = ProductConverterUtil.productInputDtoToProduct(inputDTO);
        var productCreated = productRepository.save(product);
        return ProductConverterUtil.productToProductOutputDto(productCreated);
    }

    @Override
    public ProductInputDTO findById(Long id) {
        return null;
    }

    @Override
    public List<ProductOutputDTO> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    private void checkDuplicity(String name){
        productRepository.findByNameIgnoreCase(name)
                .ifPresent(e -> {
                    throw new ProductAlreadyExistsException(name);
                });
    }
}
