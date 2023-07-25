import React from 'react';
import { Link } from 'react-router-dom';
import premiereData from '../data/premiereData.json';
import '../styles/ItemGrid.css';


const PremiereCollection = () => {
  return (
    <div>
      <div className="the-grid-container">
        {premiereData.map((item) => (
          <div className="grid-item-collection" key={item.id}>
            <Link className='caption-link' to={`/premiere-item-page/${item.id}`}>
            <img src={item.bigSrc} alt={item.alt} />
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
};
export default PremiereCollection;