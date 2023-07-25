import { useState, useEffect } from 'react';
import axios from 'axios';
import ItemCardBasket from '../components/ItemCardBasket';
import { useParams } from 'react-router-dom';

const CartApiComponent = () => {
    const routeParams = useParams();
    const apiUrl = `http://localhost:8088/api/v1/basket/1`;
    const [user, setUser] = useState({});
    const [basketItems, setBasketItems] = useState([]);
    const [total, setTotal] = useState({});
    const [cartItems, setCartItems] = useState([]);

    const getAllItemsInBasket = () => {
        axios.get(apiUrl)
            .then(response => {
                console.log(response.data);
                setBasketItems(response.data.basketItems);
                setTotal(response.data.basketTotal);
                setUser(response.data.user);
            })
            .catch(error => console.log('Error fetching cart items:', error));
    }

    const addItemsToBasket = (item) => {
        const itemIndex = cartItems.findIndex((cartItem) => cartItem.item === item.item);

        if (itemIndex === -1) {
            // If the item is not already in the cart, add it as a new item
            setCartItems((prevCartItems) => [...prevCartItems, item]);
        } else {
            // If the item is already in the cart, update its quantity and size
            const updatedCartItems = [...cartItems];
            updatedCartItems[itemIndex].quantity += item.quantity;
            updatedCartItems[itemIndex].size = item.size;
            setCartItems(updatedCartItems);
        }
    };

    useEffect(() => {
        getAllItemsInBasket();
    }, []);

    const editItemSizeInBasket = (basketItemId, newSize) => {

        axios.put(`${apiUrl}/editSize/${basketItemId}`, { newSize })
            .then(response => {

                getAllItemsInBasket();
            })
            .catch(error => console.log('Error editing item size in basket:', error));
    };

    const deleteItemFromBasketById = (basketItemId) => {
        axios.delete(`${apiUrl}/${basketItemId}`)
            .then(response => {
                getAllItemsInBasket();
            })
            .catch(error => console.log('Error deleting item from basket:', error));
    };

    const editItemQuantityInBasket = (basketItemId, newQuantity) => {
        axios.put(`${apiUrl}/editQuantity/${basketItemId}?newQuantity=${newQuantity}`)
            .then(response => {
                getAllItemsInBasket();
            })
            .catch(error => console.log('Error editing item quantity in basket:', error));
    };

    const clearBasket = (basketId) => {
        axios.delete(`${apiUrl}/${basketId}`)
            .then(response => {
                getAllItemsInBasket();
            })
            .catch(error => console.log('Error clearing basket:', error));
    };



    return (
        <div className='basket-items-grid'>
            {
                basketItems?.map(basketItem =>
                    <ItemCardBasket
                        key={basketItem.id}
                        quantity={basketItem.quantity}
                        basketItem={basketItem.item}
                        onSizeChange={editItemSizeInBasket}
                    />
                )
            }
        </div>
    );
}



export default CartApiComponent;

