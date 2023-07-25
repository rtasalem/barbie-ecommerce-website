import React from 'react';
import { useParams, Link } from 'react-router-dom';
import swimwearData from '../data/swimwearData.json';
import '../styles/ItemGrid.css';

const SwimwearItemGrid = () => {
  // Get the itemId from the URL parameter using the useParams hook
  const { itemId } = useParams();

  // Create a function to get the next 6 items based on the itemId
  const getNextSixItems = () => {
    const index = swimwearData.findIndex((item) => item.id === parseInt(itemId));

    // If the item is not found or it is the last item, start from the beginning
    if (index === -1 || index >= swimwearData.length - 1) {
      return swimwearData.slice(0, 6);
    }

    // If there are enough items after the current one, take the next 6
    if (swimwearData.length - index >= 6) {
      return swimwearData.slice(index + 1, index + 7);
    }

    // If there are not enough items after the current one, wrap around to the beginning
    const remainingItems = 6 - (swimwearData.length - index - 1);
    return [...swimwearData.slice(index + 1), ...swimwearData.slice(0, remainingItems)];
  };

  // Get the next 6 items
  const nextSixItems = getNextSixItems();

  return (
    <div>
      <span className='heading grid'>More like this</span>
      <div className="more-grid-container">
        {nextSixItems.map((item) => (
          <div className="grid-item" key={item.id}>
            <Link className='caption-link' to={`/swimwear-item-page/${item.id}`}>
            <img src={item.src} alt={item.alt} />
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
};

export default SwimwearItemGrid;
