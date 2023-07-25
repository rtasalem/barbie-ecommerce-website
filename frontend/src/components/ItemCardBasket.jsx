import React from 'react';

const ItemCardBasket = (props) => {
    const { quantity, basketItem } = props;
    const { name, description, itemType, size, price } = basketItem || {};

    return (
        <div className='card'>
            {props.basketItem && (
                <>
                    <p><span>Name: </span>{name}</p>
                    <p><span>Description: </span>{description}</p>
                    <p><span>Item Type: </span>{itemType}</p>
                    <p><span>Quantity: </span>{quantity}</p>
                    <p>
                        <span>Size: </span>
                        <select
                            value={size}
                            onChange={(e) => props.onSizeChange(props.basketItem.id, e.target.value)}
                        >
                            <option value="XS">XS</option>
                            <option value="S">S</option>
                            <option value="M">M</option>
                            <option value="L">L</option>
                            <option value="XL">XL</option>
                        </select>
                    </p>
                    <p><span>Price: </span>${price}</p>
                </>
            )}
        </div>
    );
}

export default ItemCardBasket;
