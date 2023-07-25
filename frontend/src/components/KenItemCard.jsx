import { useState, useEffect } from 'react';
import CartAPIComponent from '../components/CartApiComponent';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import kenData from "../data/kenData.json";
import "../styles/ItemCard.css";

const KenItemCard = () => {
    const { itemId } = useParams();
    const navigate = useNavigate();
    const api = `http://localhost:8088/api/v1/items/ken-item-page/${itemId}`;

    const [itemName, setItemName] = useState('');
    const [itemDescription, setItemDescription] = useState('');
    const [typeOfItem, setTypeOfItem] = useState('');
    const [itemPrice, setItemPrice] = useState(0);
    const [selectedSize, setSelectedSize] = useState('');
    const [itemQuantity, setItemQuantity] = useState(1);

    const cartAPI = CartAPIComponent();

    // Function to fetch item details by ID
    const fetchItemById = async (itemId) => {
        try {
            const response = await axios.get(`http://localhost:8088/api/v1/items/${itemId}`);
            return response.data; // Assuming the item data is returned as JSON
        } catch (error) {
            console.error('Error fetching item:', error);
            return null;
        }
    };

    // Function to fetch basket details by ID
    const fetchBasketById = async (basketId) => {
        try {
            const response = await axios.get(`http://localhost:8088/api/v1/basket/${basketId}`);
            return response.data; // Assuming the basket data is returned as JSON
        } catch (error) {
            console.error('Error fetching basket:', error);
            return null;
        }
    };

    useEffect(() => {
        fetch(api)
            .then(response => response.json())
            .then(data => {
                setItemName(data.name);
                setItemDescription(data.description);
                setItemPrice(data.price);
                setTypeOfItem(data.itemType);
            })
            .catch(error => {
                console.error("Error fetching data from API:", error);
                setItemName('');
                setItemDescription('');
                setItemPrice(0);
                setTypeOfItem('');
            });
    }, [api]);

    const handleSizeChange = (e) => {
        setSelectedSize(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const basketId = 1; // Change the basket ID as needed
        const basket = await fetchBasketById(basketId);
        const item = await fetchItemById(itemId);

        if (!basket || !item) {
            console.error('Error fetching Basket or Item.');
            return;
        }

        const createBasketItem = {
            basket,
            item,
            quantity: itemQuantity,
            size: selectedSize
        };

        try {
            const response = await axios.post('http://localhost:8088/api/v1/basket/addToBasket/1', createBasketItem);
            console.log('Basket item created successfully.');
            console.log('Response:', response.data);

            cartAPI.addItemsToBasket(response.data); // Replace with cartAPI method to add item
            navigate('/cart');
        } catch (error) {
            console.log('Error creating basket item:', error);
            navigate('/cart');
        }
    };

    return (
        <div className="item-details">
            {kenData.map((item, idx) => (
                idx === parseInt(itemId) - 25 &&
                <img src={item.src} alt={item.alt} key={idx} className="item-image" />
            ))}
            <div className="item-content">
                <form onSubmit={handleSubmit} className='add-basket-item-form'>
                    <h1 className="item-name">{itemName}</h1>
                    <div className='item-content-details'>
                        <div className='item-text-content'>
                            <p className="item-heading">Description:</p>
                            <p className="item-text">{itemDescription}</p>
                        </div>
                        <div className='item-text-content'>
                            <p className="item-heading">Type:</p>
                            <p className="item-text">{typeOfItem}</p>
                        </div>
                        <div className='item-text-content'>
                            <p className="item-heading">Price:</p>
                            <p className="item-text">£{itemPrice}</p>
                        </div>
                        <div className='item-text-content'>
                            <div className="item-size">
                                <label htmlFor="size" className='item-heading' id='size-heading'>Size:</label>
                                <select id='size-dropdown' value={selectedSize} onChange={handleSizeChange}>
                                    <option value="">Select size</option>
                                    <option value="S">S</option>
                                    <option value="M">M</option>
                                    <option value="L">L</option>
                                    <option value="XL">XL</option>
                                </select>
                            </div>
                        </div>
                        <div className='item-text-content'>
                            <p className="item-heading">Quantity:</p>
                            <input id='quantity-box' type="text" value={itemQuantity} onChange={(e) => setItemQuantity(e.target.value)} required='true' />
                        </div>
                        <div className="add-to-basket">
                            {/* <button className='submit-button' type="submit" onClick={handleSubmit}>Add to Basket</button> */}
                            <button className='submit-button' type="submit">Add to Basket</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default KenItemCard;
