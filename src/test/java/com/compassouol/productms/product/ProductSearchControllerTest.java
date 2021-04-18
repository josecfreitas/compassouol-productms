package com.compassouol.productms.product;

import com.compassouol.productms.factory.ProductFactory;
import com.compassouol.productms.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private List<Product> productList;

    @BeforeEach
    public void setUp() {
        Product productA = new Product();
        productA.setName("Test Product A");
        productA.setDescription("Test Description Product A");
        productA.setPrice(new BigDecimal("10.5"));

        Product productB = ProductFactory.createGenericProduct();
        productB.setName("Test Product B");
        productB.setDescription("Test Description Product B");
        productB.setPrice(new BigDecimal("146"));

        Product productC = ProductFactory.createGenericProduct();
        productC.setName("Test Product C");
        productC.setDescription("Test Description Product C");
        productC.setPrice(new BigDecimal("1053.33"));

        productList = List.of(productA, productB, productC);

        productRepository.saveAll(productList);
    }

    @AfterEach
    public void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    public void testList_whenWithoutParameters_thanReturnAllProducts() throws Exception {
        mockMvc
                .perform(get(getUri()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testList_whenWithQParameterForName_thanReturnFilteredProducts() throws Exception {
        mockMvc
                .perform(get(getUri()).contentType(MediaType.APPLICATION_JSON).param("q", "prod A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testList_whenWithQParameterForDescription_thanReturnFilteredProducts() throws Exception {
        mockMvc
                .perform(get(getUri()).contentType(MediaType.APPLICATION_JSON).param("q", "descript A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testList_whenWithMinPriceParameter_thanReturnFilteredProducts() throws Exception {
        mockMvc
                .perform(get(getUri()).contentType(MediaType.APPLICATION_JSON).param("min_price", "100.555555"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testList_whenWithMaxPriceParameter_thanReturnFilteredProducts() throws Exception {
        mockMvc
                .perform(get(getUri()).contentType(MediaType.APPLICATION_JSON).param("max_price", "100.555555"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testList_whenFilterWithoutResults_thanReturnEmptyList() throws Exception {
        mockMvc
                .perform(get(getUri()).contentType(MediaType.APPLICATION_JSON).param("max_price", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testList_whenAllFilters_thanReturnEmptyList() throws Exception {
        mockMvc
                .perform(
                        get(getUri()).contentType(MediaType.APPLICATION_JSON)
                                .param("q", "prod B")
                                .param("min_price", "146")
                                .param("max_price", "1000")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(productList.get(1).getName())));
    }

    private String getUri() {
        return "/products/search";
    }
}
