package com.fdmgroup.api.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.api.exception.ResourceNotFoundException;
import com.fdmgroup.api.model.Basket;
import com.fdmgroup.api.model.BasketItem;
import com.fdmgroup.api.model.Item;
import com.fdmgroup.api.model.User;
import com.fdmgroup.api.repository.BasketItemRepository;
import com.fdmgroup.api.repository.BasketRepository;
import com.fdmgroup.api.service.BasketService;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @InjectMocks
    private BasketService basketService;

    @Mock
    private BasketRepository basketRepo;

    @Mock
    private BasketItemRepository basketItemRepo;

    @Test
    public void testGetAllItemsInBasket() {
        List<Basket> basketList = new ArrayList<>();
        when(basketRepo.findAll()).thenReturn(basketList);

        List<Basket> result = basketService.getAllItemsInBasket();
        assertEquals(basketList, result);
    }

    @Test
    public void testGetBasket_BasketExists() {
        int basketId = 1;
        Basket basket = new Basket();
        when(basketRepo.findById(basketId)).thenReturn(Optional.of(basket));

        Basket result = basketService.getBasket(basketId);
        assertEquals(basket, result);
    }

    @Test
    public void testGetBasket_BasketNotExists() {
        int basketId = 100;
        when(basketRepo.findById(basketId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> basketService.getBasket(basketId));
    }

    @Test
    public void testGetBasketByUser_UserExists() {
        User user = new User();
        user.setUserId(1L);
        Basket basket = new Basket();
        when(basketRepo.findByUser(user)).thenReturn(Optional.of(basket));

        Basket result = basketService.getBasketByUser(user);
        assertEquals(basket, result);
    }

    @Test
    public void testGetBasketByUser_UserNotExists() {
        User user = new User();
        user.setUserId(1L);
        when(basketRepo.findByUser(user)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> basketService.getBasketByUser(user));
    }
    
    @Test
    public void testAddItemToBasket_ItemNotInBasket() {
        int basketId = 1;
        Basket basket = new Basket();
        BasketItem basketItem = new BasketItem(basket, new Item(), 1, "S");

        when(basketRepo.findById(basketId)).thenReturn(Optional.of(basket));
        when(basketItemRepo.findByBasketAndItem(any(Basket.class), any(Item.class))).thenReturn(null);
        doReturn(new BasketItem()).when(basketItemRepo).save(any(BasketItem.class));

        Basket result = basketService.addItemToBasket(basketId, basketItem);

        verify(basketRepo).findById(basketId);
        verify(basketItemRepo).findByBasketAndItem(any(Basket.class), any(Item.class));
        verify(basketItemRepo).save(any(BasketItem.class));

        assertEquals(basketId, result.getBasketId());
        assertEquals(1, result.getBasketItems().size());
    }

    @Test
    public void testAddItemToBasket_ItemInBasket() {
        int basketId = 1;
        Basket basket = new Basket();
        BasketItem basketItem = new BasketItem();
        basketItem.setQuantity(1);
        when(basketRepo.findById(basketId)).thenReturn(Optional.of(basket));
        when(basketItemRepo.findByBasketAndItem(basket, basketItem.getItem())).thenReturn(basketItem);
        when(basketItemRepo.save(basketItem)).thenReturn(basketItem);

        Basket result = basketService.addItemToBasket(basketId, basketItem);
        assertEquals(basket, result);
    }

    @Test
    public void testEditItemQuantityInBasket_ItemExists() {
        int basketItemId = 1;
        int newQuantity = 5;
        BasketItem basketItem = new BasketItem();
        when(basketItemRepo.findById(basketItemId)).thenReturn(Optional.of(basketItem));
        when(basketItemRepo.save(basketItem)).thenReturn(basketItem);

        basketService.editItemQuantityInBasket(basketItemId, newQuantity);
        assertEquals(newQuantity, basketItem.getQuantity());
    }

    @Test
    public void testEditItemQuantityInBasket_ItemNotExists() {
        int basketItemId = 100;
        int newQuantity = 5;
        when(basketItemRepo.findById(basketItemId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> basketService.editItemQuantityInBasket(basketItemId, newQuantity));
    }

    @Test
    public void testEditItemQuantityInBasket_QuantityLessThanOne() {
        int basketItemId = 1;
        int newQuantity = 0;

        Basket basket = new Basket();
        basket.setBasketId(1);
        Item item = new Item();
        BasketItem basketItem = new BasketItem(basket, item, 2, "M");
        
        when(basketItemRepo.findById(basketItemId)).thenReturn(Optional.of(basketItem));
        when(basketRepo.save(any(Basket.class))).thenReturn(basket);

        basketService.editItemQuantityInBasket(basketItemId, newQuantity);

        verify(basketItemRepo, times(1)).delete(basketItem);
        verify(basketRepo, times(1)).save(basket);
    }

    @Test
    public void testEditItemSizeInBasket_ItemExists() {
        int basketItemId = 1;
        String newSize = "XL";
        BasketItem basketItem = new BasketItem();
        Item item = new Item();
        basketItem.setItem(item);
        when(basketItemRepo.findById(basketItemId)).thenReturn(Optional.of(basketItem));
        when(basketItemRepo.save(basketItem)).thenReturn(basketItem);

        basketService.editItemSizeInBasket(basketItemId, newSize);
        assertEquals(newSize, item.getSize());
    }

    @Test
    public void testEditItemSizeInBasket_ItemNotExists() {
        int basketItemId = 100;
        String newSize = "XL";
        when(basketItemRepo.findById(basketItemId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> basketService.editItemSizeInBasket(basketItemId, newSize));
    }
    
    @Test
    public void testCalculateTotalPrice() {
        int basketId = 1;
        Basket basket = new Basket();
        List<BasketItem> basketItems = new ArrayList<>();
        basketItems.add(new BasketItem(basket, new Item("Item1", "Description", "Type", "Size", 10.0), 2, "M"));
        basketItems.add(new BasketItem(basket, new Item("Item2", "Description", "Type", "Size", 5.0), 3, "S"));
        basket.setBasketItems(basketItems);
        when(basketRepo.findById(basketId)).thenReturn(Optional.of(basket));

        double totalPrice = basketService.calculateTotalPrice(basketId);

        verify(basketRepo).findById(basketId);
        assertEquals(35.0, totalPrice);
    }
    
    @Test
    public void testDeleteItemFromBasketById_ItemExists() {
        int basketItemId = 1;
        BasketItem basketItem = new BasketItem();
        Basket basket = new Basket();
        basket.getBasketItems().add(basketItem);

        when(basketItemRepo.findById(basketItemId)).thenReturn(Optional.of(basketItem));
        basketService.deleteItemFromBasketById(basketItemId);
        verify(basketItemRepo, times(1)).delete(basketItem);
        verify(basketRepo, times(1)).save(basket);
    }

    @Test
    public void testDeleteItemFromBasketById_ItemNotExists() {
        int basketItemId = 100;
        when(basketItemRepo.findById(basketItemId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> basketService.deleteItemFromBasketById(basketItemId));
        verify(basketItemRepo, never()).delete(any(BasketItem.class));
        verify(basketRepo, never()).save(any(Basket.class));
    }
    
    @Test
    public void testClearBasket() {
        int basketId = 1;
        Basket basket = new Basket();
        List<BasketItem> basketItems = new ArrayList<>();
        basketItems.add(new BasketItem(basket, new Item("Item1", "Description", "Type", "Size", 10.0), 2, "M"));
        basketItems.add(new BasketItem(basket, new Item("Item2", "Description", "Type", "Size", 5.0), 3, "S"));
        basket.setBasketItems(basketItems);
        when(basketRepo.findById(basketId)).thenReturn(Optional.of(basket));

        basketService.clearBasket(basketId);
        verify(basketRepo).findById(basketId);
        assertTrue(basket.getBasketItems().isEmpty());
    }

    @Test
    public void testClearBasket_BasketNotExists() {
        int basketId = 100;
        when(basketRepo.findById(basketId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> basketService.clearBasket(basketId));
    }
}
