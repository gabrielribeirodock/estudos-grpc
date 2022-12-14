package br.com.content4devs.projetogrpcjava.service;

import br.com.content4devs.projetogrpcjava.dto.ProductInputDTO;
import br.com.content4devs.projetogrpcjava.dto.ProductOutputDTO;

import java.util.List;

public interface IProductService {
    ProductInputDTO create(ProductInputDTO inputDTO);
    ProductInputDTO findById(Long id);
    List<ProductOutputDTO> findAll();
    void delete(Long id);
}
