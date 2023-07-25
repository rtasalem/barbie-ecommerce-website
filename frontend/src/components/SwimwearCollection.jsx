import React from 'react';
import { useParams, Link } from 'react-router-dom';
import swimwearData from '../data/swimwearData.json';
import '../styles/ItemGrid.css';


const SwimwearCollection = () => {
    return (
        <div>
        <div className="the-grid-container">
          {swimwearData.map((item) => (
            <div className="grid-item-collection" key={item.id}>
              <Link className='caption-link' to={`/swimwear-item-page/${item.id}`}>
              <img src={item.src} alt={item.alt} />
              </Link>
            </div>
          ))}
        </div>
      </div>
    )
}
export default SwimwearCollection;