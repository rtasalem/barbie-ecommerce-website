import React from 'react';
import { useParams, Link } from 'react-router-dom';
import kenData from '../data/kenData.json';
import '../styles/ItemGrid.css';

const KenItemGrid = () => {
  // Get the itemId from the URL parameter using the useParams hook
  const { itemId } = useParams();

  // Create a function to get the next 6 items based on the itemId
  const getNextSixItems = () => {
    const index = kenData.findIndex((item) => item.id === parseInt(itemId));

    // If the item is not found or it is the last item, start from the beginning
    if (index === -1 || index >= kenData.length - 1) {
      return kenData.slice(0, 6);
    }

    // If there are enough items after the current one, take the next 6
    if (kenData.length - index >= 6) {
      return kenData.slice(index + 1, index + 7);
    }

    // If there are not enough items after the current one, wrap around to the beginning
    const remainingItems = 6 - (kenData.length - index - 1);
    return [...kenData.slice(index + 1), ...kenData.slice(0, remainingItems)];
  };

  // Get the next 6 items
  const nextSixItems = getNextSixItems();

  return (
    <div>
      <span className='heading grid'>More like this</span>
      <div className="more-grid-container">
        {nextSixItems.map((item) => (
          <div className="grid-item" key={item.id}>
            <Link className='caption-link' to={`/ken-item-page/${item.id}`}>
            <img src={item.src} alt={item.alt} />
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
};

export default KenItemGrid;
