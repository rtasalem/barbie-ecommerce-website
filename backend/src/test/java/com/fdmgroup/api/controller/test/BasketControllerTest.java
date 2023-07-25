package com.fdmgroup.api.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fdmgroup.api.controller.BasketController;
import com.fdmgroup.api.model.Basket;
import com.fdmgroup.api.model.BasketItem;
import com.fdmgroup.api.model.Item;
import com.fdmgroup.api.model.User;
import com.fdmgroup.api.service.BasketService;

@SpringBootTest
@AutoConfigureMockMvc
public class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired 
    BasketController basketController;

    @MockBean
    private BasketService mockBasketService;

    private Basket mockBasket;
    private BasketItem mockItem1;
    private BasketItem mockItem2;
    private BasketItem mockItem3;
    private User mockUser;

    @BeforeEach
    void setup() {
    	Item item1 = new Item("item1", "Description1", "Type1", "Size1", 9.99);
    	Item item2 = new Item("item2", "Description2", "Type2", "Size2", 14.99);
    	Item item3 = new Item("item3", "Description3", "Type3", "Size3", 6.49);
    	
        mockItem1 = new BasketItem(mockBasket, item1, 1, "S");
        mockItem2 = new BasketItem(mockBasket, item2, 2, "M");
        mockItem3 = new BasketItem(mockBasket, item3, 3, "L");

        mockUser = new User(1L, "John", "Doe", "john.doe@example.com", "password123", "123 Main St");

        List<BasketItem> basketItems = Arrays.asList(mockItem1, mockItem2, mockItem3);
        double basketTotal = 24.47;
        mockBasket = new Basket(basketItems, basketTotal, mockUser);
    }
    
    @Test
    void testGetAllItemsInBasket() {
        List<Basket> expectedBasketList = Arrays.asList(mockBasket);
        when(mockBasketService.getAllItemsInBasket()).thenReturn(expectedBasketList);

        ResponseEntity<List<Basket>> response = basketController.getAllItemsInBasket();

        verify(mockBasketService, times(1)).getAllItemsInBasket();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBasketList, response.getBody());
    }

    @Test
    void testGetBasket() {
        int basketId = 1;
        when(mockBasketService.getBasket(basketId)).thenReturn(mockBasket);

        ResponseEntity<Basket> response = basketController.getBasket(basketId);

        verify(mockBasketService, times(1)).getBasket(basketId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBasket, response.getBody());
    }

    @Test
    void testGetBasketByUser() {
        Long userId = 123L;
        User user = new User();
        user.setUserId(userId);

        Basket expectedBasket = new Basket();
        when(mockBasketService.getBasketByUser(user)).thenReturn(expectedBasket);

        ResponseEntity<Basket> response = basketController.getBasketByUser(userId);

        verify(mockBasketService, times(1)).getBasketByUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBasket, response.getBody());
    }
    
    @Test
    void testAddItemToBasket() throws Exception {
        int basketId = 1;
        Item newItem = new Item("New Item", "Description", "Type", "Size", 9.99);
        BasketItem newBasketItem = new BasketItem(mockBasket, newItem, 3, "L");

        Basket updatedBasket = new Basket(Arrays.asList(mockItem1, mockItem2, mockItem3, newBasketItem), 34.46, mockUser);

        when(mockBasketService.addItemToBasket(eq(basketId), any(BasketItem.class))).thenReturn(updatedBasket);

        mockMvc.perform(post("/api/v1/basket/addToBasket/" + basketId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"New Item\",\"description\":\"Description\",\"itemType\":\"Type\",\"size\":\"Size\",\"price\":9.99}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.basketTotal").value(34.46));

        verify(mockBasketService, times(1)).addItemToBasket(eq(basketId), any(BasketItem.class));
    }

    @Test
    void testEditItemQuantityInBasket() throws Exception {
        int basketItemId = 1;
        int newQuantity = 5;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/basket/editQuantity/" + basketItemId)
                .param("newQuantity", String.valueOf(newQuantity)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockBasketService, times(1)).editItemQuantityInBasket(basketItemId, newQuantity);
    }
    
    @Test
    void testEditItemSizeInBasket() throws Exception {
        int basketItemId = 1;
        String newSize = "New Size";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/basket/editSize/" + basketItemId)
                .param("newSize", newSize))
                .andExpect(status().isOk());

        verify(mockBasketService, times(1)).editItemSizeInBasket(eq(basketItemId), eq(newSize));
    }
    
    @Test
    void testCalculateTotalPrice() throws Exception {
        int basketId = 1;
        double totalPrice = 50.0;

        when(mockBasketService.calculateTotalPrice(eq(basketId))).thenReturn(totalPrice);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/basket/totalPrice/" + basketId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(totalPrice));

        verify(mockBasketService, times(1)).calculateTotalPrice(eq(basketId));
    }
    
    @Test
    void testDeleteItemFromBasketById() {
        int basketItemId = 1;

        ResponseEntity<Void> responseEntity = basketController.deleteItemFromBasketById(basketItemId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(mockBasketService, times(1)).deleteItemFromBasketById(eq(basketItemId));
    }
    
    @Test
    void testClearBasket() {
        int basketId = 1;

        ResponseEntity<Void> responseEntity = basketController.clearBasket(basketId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(mockBasketService, times(1)).clearBasket(eq(basketId));
    }
}