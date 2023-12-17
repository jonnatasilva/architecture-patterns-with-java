package com.handson.jonnatas.architecturepatternswithjava.e2e;

import com.handson.jonnatas.architecturepatternswithjava.*;
import com.handson.jonnatas.architecturepatternswithjava.adapters.repository.RepositoryFacade;
import com.handson.jonnatas.architecturepatternswithjava.domain.Batch;
import com.handson.jonnatas.architecturepatternswithjava.domain.Quantity;
import com.handson.jonnatas.architecturepatternswithjava.domain.Reference;
import com.handson.jonnatas.architecturepatternswithjava.domain.Sku;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = ArchitecturePatternsWithJavaApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TestApi {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepositoryFacade repository;

    @Test
    void testApiReturnsAllocation() throws Exception {
        var sku = randomSku();
        var otherSku = randomSku("other");
        var earlyBatch = randomBatchRef(1);
        var laterBatch = randomBatchRef(2);
        var otherBatch = randomBatchRef(3);

        addStock(laterBatch, sku, 100, LocalDate.of(2011, Month.JANUARY, 2));
        addStock(earlyBatch, sku, 100, LocalDate.of(2011, Month.JANUARY, 2));
        addStock(otherBatch, otherSku, 100, null);

        var data = "{\"orderId\": \"" + randomOrderId(1) + "\", \"sku\": \"" + sku + "\", \"qty\": 3 }";

        this.mockMvc.perform(post("/allocate")
                        .content(data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.batchRef", is(earlyBatch)));
    }

    @Test
    void testApiReturnsAllocationArePersisted() throws Exception {
        var sku = randomSku();
        var batch1 = randomBatchRef(1);
        var batch2 = randomBatchRef(2);

        var order1 = randomOrderId(1);
        var order2 = randomOrderId(2);

        addStock(batch1, sku, 10, LocalDate.of(2011, Month.JANUARY, 1));
        addStock(batch2, sku, 10, LocalDate.of(2011, Month.JANUARY, 2));

        var line1 = "{\"orderId\": \"" + order1 + "\", \"sku\": \"" + sku + "\", \"qty\": 10 }";
        var line2 = "{\"orderId\": \"" + order2 + "\", \"sku\": \"" + sku + "\", \"qty\": 10 }";

        this.mockMvc.perform(post("/allocate")
                        .content(line1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.batchRef", is(batch1)));

        this.mockMvc.perform(post("/allocate")
                        .content(line2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.batchRef", is(batch2)));
    }

    @Test
    void test400MessageForOutOfStock() throws Exception {
        var sku = randomSku();
        var smallBatch = randomBatchRef(1);
        var largeOrder = randomOrderId(1);

        addStock(smallBatch, sku, 10, LocalDate.of(2011, Month.JANUARY, 1));

        var data = "{\"orderId\": \"" + largeOrder + "\", \"sku\": \"" + sku + "\", \"qty\": 20 }";

        this.mockMvc.perform(post("/allocate")
                        .content(data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Out of stock for sku " + sku)));
    }

    @Test
    void test400ForInvalidSku() throws Exception {
        var unknownSku = randomSku();
        var orderId = randomOrderId(1);

        var data = "{\"orderId\": \"" + orderId + "\", \"sku\": \"" + unknownSku + "\", \"qty\": 20 }";

        this.mockMvc.perform(post("/allocate")
                        .content(data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid sku " + unknownSku)));
    }

    private String randomOrderId(int i) {
        return String.format("orderId-%d", i);
    }

    private void addStock(String ref, String sku, int purchasedQuantity, LocalDate eta) {
        repository.save(new Batch(Reference.of(ref), Sku.of(sku), Quantity.of(purchasedQuantity), eta));
    }

    private String randomBatchRef(int i) {
        return "batch-" + i;
    }

    private String randomSku() {
        return randomSku(null);
    }

    private String randomSku(String sku) {
        if (Objects.isNull(sku)) {
            return String.format("sku-%d", new Random().nextInt());
        } else {
            return String.format("sku-%s-%d", sku, new Random().nextInt(10));
        }
    }
}