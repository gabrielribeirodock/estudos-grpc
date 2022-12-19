package br.com.content4devs.projetogrpcjava.resources;

import br.com.content4devs.ProductRequest;
import br.com.content4devs.ProductResponse;
import br.com.content4devs.ProductServiceGrpc;
import br.com.content4devs.projetogrpcjava.dto.ProductInputDTO;
import br.com.content4devs.projetogrpcjava.dto.ProductOutputDTO;
import br.com.content4devs.projetogrpcjava.service.IProductService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class ProductResource extends ProductServiceGrpc.ProductServiceImplBase {

    @Autowired
    private IProductService productService;

    @Override
    public void create(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        ProductInputDTO productInputDTO = new ProductInputDTO(
                request.getName(),
                request.getPrice(),
                request.getQuantityInStock()
        );

        ProductOutputDTO productOutputDTO = productService.create(productInputDTO);

        ProductResponse response = ProductResponse.newBuilder()
                .setId(productOutputDTO.getId())
                .setName(productOutputDTO.getName())
                .setPrice(productOutputDTO.getPrice())
                .setQuantityInStock(productOutputDTO.getQuantityInStock())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}