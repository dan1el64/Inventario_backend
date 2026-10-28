package com.example.inventario.controller;

import com.example.inventario.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductApiTest {
    @Autowired MockMvc mvc;
    @Autowired ProductRepository products;

    @Test void rejectsInvalidProductWithoutPersisting() throws Exception {
        long before = products.count();
        mvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\" \",\"category\":\"Demo\",\"price\":-1,\"stock\":-2}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.success").value(false));
        assertEquals(before, products.count());
    }

    @Test void requiresPriceAndStock() throws Exception {
        mvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Sensor\",\"category\":\"Demo\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test void rejectsMalformedJsonAsClientError() throws Exception {
        mvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content("{"))
                .andExpect(status().isBadRequest());
    }

    @Test void supportsCreateReadUpdateDeleteContract() throws Exception {
        String created = mvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":9999,\"name\":\" Demo Sensor \",\"category\":\"Lab\",\"price\":12.5,\"stock\":3}"))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.data.name").value("Demo Sensor"))
                .andReturn().getResponse().getContentAsString();
        long id = new com.fasterxml.jackson.databind.ObjectMapper().readTree(created).path("data").path("id").asLong();
        assertNotEquals(9999, id);
        mvc.perform(get("/api/v1/products/" + id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.stock").value(3));
        mvc.perform(put("/api/v1/products/" + id).contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Sensor\",\"category\":\"Lab\",\"price\":15,\"stock\":0}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.stock").value(0));
        mvc.perform(delete("/api/v1/products/" + id)).andExpect(status().isOk());
        mvc.perform(get("/api/v1/products/" + id)).andExpect(status().isNotFound());
    }

    @Test void invalidUpdateLeavesExistingProductUnchanged() throws Exception {
        var existing = products.save(new com.example.inventario.model.Product("Original", "Lab", 5.0, 2));
        mvc.perform(put("/api/v1/products/" + existing.getId()).contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Changed\",\"category\":\"Lab\",\"price\":0,\"stock\":2}"))
                .andExpect(status().isBadRequest());
        assertEquals("Original", products.findById(existing.getId()).orElseThrow().getName());
    }
}
