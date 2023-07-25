import { useState, useEffect } from 'react';
import CartAPIComponent from '../components/CartApiComponent';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import premiereData from "../data/premiereData.json";
import "../styles/ItemCard.css";

const PremiereItemCard = () => {
    const { itemId } = useParams();
    const navigate = useNavigate();
    const api = `http://localhost:8088/api/v1/items/premiere-item-page/${itemId}`;

    const [itemName, setItemName] = useState('');
    const [itemDescription, setItemDescription] = useState('');
    const [typeOfItem, setTypeOfItem] = useState('');
    const [itemPrice, setItemPrice] = useState(0);
    const [selectedSize, setSelectedSize] = useState('');
    const [itemQuantity, setItemQuantity] = useState(1);

    const cartAPI = CartAPIComponent();

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

    const createBasketItem = {
        basket: 1,
        item: itemId,
        quantity: itemQuantity,
        size: selectedSize
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8088/api/v1/basket', createBasketItem);
            console.log("Basket item created successfully.");
            console.log("Response:", response.data); 

            cartAPI.addItemsToBasket(response.data); // Replace with cartAPI method to add item
            navigate('/cart');
        } catch (error) {
            console.log("Error creating basket item:", error);
            navigate('/cart');
        }
    };

    return (
        <div className="item-details">
            {premiereData.map((item, idx) => (
                idx === parseInt(itemId) - 1 &&
                <img src={item.bigSrc} alt={item.alt} key={idx} className="item-image" />
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
                                <option  value="">Select size</option>
                                <option value="S">S</option>
                                <option value="M">M</option>
                                <option value="L">L</option>
                                <option value="XL">XL</option>
                            </select>
                        </div>
                    </div>
                    <div className='item-text-content'>
                        <p className="item-heading">Quantity:</p>
                        <input id='quantity-box' type="text" value={itemQuantity} onChange={(e) => setItemQuantity(e.target.value)} required='true'/>
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

export default PremiereItemCard;
