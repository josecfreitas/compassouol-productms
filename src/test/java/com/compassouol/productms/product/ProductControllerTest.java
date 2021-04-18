package com.compassouol.productms.product;

import com.compassouol.productms.config.errorhandler.ExceptionHandlerD;
import com.compassouol.productms.factory.ProductFactory;
import com.compassouol.productms.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({ProductController.class, ExceptionHandlerD.class})
class ProductControllerTest {

    private final String PRODUCT_NOT_FOUND_MESSAGE = "Not found product message";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product genericProduct;

    @BeforeEach
    public void setUp() {
        genericProduct = ProductFactory.createGenericProduct();
    }

    @Test
    public void testFind_whenExistingId_thanReturnProduct() throws Exception {
        String productId = "id";
        genericProduct.setId(productId);

        when(productService.find(anyString())).thenReturn(genericProduct);

        expect(
                genericProduct,
                mockMvc
                        .perform(get(getUri(productId)).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
        );
    }

    @Test
    public void testFind_whenDoesNotExistsId_thanReturn404() throws Exception {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        String productId = "id";
        genericProduct.setId(productId);

        when(productService.find(anyString())).thenThrow(new ResponseStatusException(notFound, PRODUCT_NOT_FOUND_MESSAGE));

        mockMvc
                .perform(get(getUri(productId)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(notFound.value()))
                .andExpect(jsonPath("status_code", is(notFound.value())))
                .andExpect(jsonPath("message", is(PRODUCT_NOT_FOUND_MESSAGE)));
    }

    @Test
    public void testList_whenThereAreProducts_thanReturnListOfAll() throws Exception {
        when(productService.list()).thenReturn(List.of(genericProduct, genericProduct));

        expect(
                genericProduct,
                mockMvc
                        .perform(get(getUri()).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(2))),
                "$[0]."
        );
    }

    @Test
    public void testList_whenThereAreNotProducts_thanReturnEmpty() throws Exception {
        when(productService.list()).thenReturn(List.of());

        mockMvc
                .perform(get(getUri()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testCreate_whenValidProduct_thanCreates() throws Exception {
        when(productService.create(any())).thenReturn(genericProduct);

        expect(
                genericProduct,
                mockMvc
                        .perform(post(getUri())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(genericProduct))
                        )
                        .andExpect(status().isCreated())
                        .andExpect(header().string("Location", Matchers.endsWith(getUri(genericProduct.getId()))))
        );
    }

    @Test
    public void testCreate_whenInvalidProductName_thanBadRequest() throws Exception {
        genericProduct.setName("");
        mockMvc
                .perform(post(getUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(genericProduct))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code", is(HttpStatus.BAD_REQUEST.value())));
    }

    @Test
    public void testCreate_whenInvalidProductDescription_thanBadRequest() throws Exception {
        genericProduct.setDescription("");
        mockMvc
                .perform(post(getUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(genericProduct))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code", is(HttpStatus.BAD_REQUEST.value())));
    }

    @Test
    public void testCreate_whenInvalidProductPrice_thanBadRequest() throws Exception {
        genericProduct.setPrice(BigDecimal.ZERO);
        mockMvc
                .perform(post(getUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(genericProduct))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code", is(HttpStatus.BAD_REQUEST.value())));
    }

    @Test
    public void testCreate_whenNullProduct_thanBadRequest() throws Exception {
        mockMvc
                .perform(post(getUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new Product()))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code", is(HttpStatus.BAD_REQUEST.value())));
    }

    @Test
    public void testCreate_whenNoBody_thanBadRequest() throws Exception {
        mockMvc
                .perform(post(getUri()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code", is(HttpStatus.BAD_REQUEST.value())));
    }

    @Test
    public void testUpdate_whenValidProduct_thanUpdateData() throws Exception {
        genericProduct.setName("changing name");
        when(productService.update(anyString(), any())).thenReturn(genericProduct);

        expect(
                genericProduct,
                mockMvc
                        .perform(put(getUri(genericProduct.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(genericProduct))
                        )
                        .andExpect(status().isOk())
        );
    }

    @Test
    public void testUpdate_whenInvalidProduct_thanBadRequest() throws Exception {
        genericProduct.setName("");
        mockMvc
                .perform(put(getUri(genericProduct.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(genericProduct))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code", is(HttpStatus.BAD_REQUEST.value())));
    }

    @Test
    public void testUpdate_whenDoesNotExistsId_thanNotFound() throws Exception {
        HttpStatus notFound = HttpStatus.BAD_REQUEST;
        when(productService.update(anyString(), any())).thenThrow(new ResponseStatusException(notFound, PRODUCT_NOT_FOUND_MESSAGE));
        mockMvc
                .perform(put(getUri(genericProduct.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(genericProduct))
                )
                .andExpect(status().is(notFound.value()))
                .andExpect(jsonPath("status_code", is(notFound.value())))
                .andExpect(jsonPath("message", is(PRODUCT_NOT_FOUND_MESSAGE)));
    }

    @Test
    public void testDelete_whenFoundProduct_thanDelete() throws Exception {
        mockMvc
                .perform(delete(getUri(genericProduct.getId())).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete_whenDoesNotFoundProduct_thanNotFound() throws Exception {
        HttpStatus notFound = HttpStatus.BAD_REQUEST;
        doThrow((new ResponseStatusException(notFound, PRODUCT_NOT_FOUND_MESSAGE))).when(productService).delete(anyString());
        mockMvc
                .perform(delete(getUri(genericProduct.getId())).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(notFound.value()));
    }

    private String getUri() {
        return String.format("/products/%s", "");
    }

    private String getUri(String relativePath) {
        return String.format("/products/%s", relativePath);
    }

    private void expect(Product genericProduct, ResultActions resultActions) throws Exception {
        expect(genericProduct, resultActions, "");
    }

    private void expect(Product genericProduct, ResultActions resultActions, String jsonPathBaseExpression) throws Exception {
        resultActions
                .andExpect(jsonPath(jsonPathBaseExpression + "id", is(genericProduct.getId())))
                .andExpect(jsonPath(jsonPathBaseExpression + "name", is(genericProduct.getName())))
                .andExpect(jsonPath(jsonPathBaseExpression + "description", is(genericProduct.getDescription())))
                .andExpect(jsonPath(jsonPathBaseExpression + "price", is(genericProduct.getPrice().doubleValue())));
    }
}
