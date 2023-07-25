import React from 'react';
import "../styles/CartPage.css";
import CartAPIComponent from '../components/CartApiComponent';
import { useNavigate } from 'react-router-dom';

const CartPage = () => {

  const cartAPI = CartAPIComponent();
  const navigate = useNavigate();

  // Destructure
  const { cartItems, basketTotal, addItemToBasket, editItemQuantityInBasket, editItemSizeInBasket, calculateTotalPrice, deleteItemFromBasketById, clearBasket } = cartAPI;

  return (
    <div className="container">
      <h1 className="title">Cart</h1>
      <div className='cart-item-card'>
        <div className="cart-functions">

          {/* <button onClick={() => editItemSizeInBasket(1, "M")}>
            Size
          </button> 
          <button onClick={() => editItemQuantityInBasket(123, 3)}>
            Quantity
          </button>

          <button onClick={() => calculateTotalPrice(1)}>
            Total Price
          </button>


          <button onClick={() => deleteItemFromBasketById(123)}>
            Delete
          </button>


          <button onClick={() => clearBasket(1)}>
            Clear Cart with ID 1
          </button> */}
        </div>
      </div>
      <div className="cart-info">
        <CartAPIComponent />
        <div className="checkout">
          <button> Checkout</button>
        </div>
      </div>
    </div>
  );
}

export default CartPage;
