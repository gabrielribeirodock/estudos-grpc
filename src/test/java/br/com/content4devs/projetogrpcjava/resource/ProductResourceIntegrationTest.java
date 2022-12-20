package br.com.content4devs.projetogrpcjava.resource;

import br.com.content4devs.ProductRequest;
import br.com.content4devs.ProductResponse;
import br.com.content4devs.ProductServiceGrpc;
import br.com.content4devs.RequestById;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext
public class ProductResourceIntegrationTest {

    @GrpcClient("inProcess")
    private ProductServiceGrpc.ProductServiceBlockingStub serviceBlockingStub;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void setUp(){
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("When valid data is provided a product is created")
    public void createProductSuccessTest(){
        ProductRequest productRequest = ProductRequest.newBuilder()
                        .setName("product name")
                        .setPrice(10.00)
                        .setQuantityInStock(100).build();

        ProductResponse productResponse = serviceBlockingStub.create(productRequest);

        Assertions.assertThat(productRequest)
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "price", "quantity_in_stock")
                .isEqualTo(productResponse);
    }

    @Test
    @DisplayName("When create is called with duplicated name, throw ProductAlreadyExistsException")
    public void createProductAlreadyExistsExceptionTest(){
        ProductRequest productRequest = ProductRequest.newBuilder()
                .setName("Product A")
                .setPrice(10.00)
                .setQuantityInStock(100)
                .build();

        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.create(productRequest))
                .withMessage("ALREADY_EXISTS: Produto Product A já cadastrado no sistema.");

    }

    @Test
    @DisplayName("When findById method is called with valid id a product is returned")
    public void findByIdSuccessTest(){
        RequestById request = RequestById.newBuilder().setId(1L).build();

        ProductResponse productResponse = serviceBlockingStub.findById(request);

        Assertions.assertThat(productResponse.getId()).isEqualTo(request.getId());
    }

    @Test
    @DisplayName("When findById is called with invalid id throws ProductNotFoundException")
    public void findByIdProductNotFoundExceptionTest(){
        RequestById request = RequestById.newBuilder().setId(100L).build();

        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.findById(request))
                .withMessage("NOT_FOUND: Produto com ID 100 não encontrado.");
    }

    @Test
    @DisplayName("When delete is called with valid id should not throw Exception")
    public void deleteSuccessTest(){
        RequestById request = RequestById.newBuilder().setId(1L).build();

        Assertions.assertThatNoException().isThrownBy(() -> serviceBlockingStub.delete(request));
    }

    @Test
    @DisplayName("When delete is called with invalid id throws ProductNotFoundException")
    public void deleteProductNotFoundExceptionTest(){
        RequestById request = RequestById.newBuilder().setId(100L).build();

        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.delete(request))
                .withMessage("NOT_FOUND: Produto com ID 100 não encontrado.");
    }
}
