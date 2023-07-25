import React from 'react';
import { Link } from 'react-router-dom';
import kenData from '../data/kenData.json';
import '../styles/ItemGrid.css';


const KenCollection = () => {

    return (
        <div>
        <div className="the-grid-container">
          {kenData.map((item) => (
            <div className="grid-item-collection" key={item.id}>
              <Link className='caption-link' to={`/ken-item-page/${item.id}`}>
              <img src={item.src} alt={item.alt} />
              </Link>
            </div>
          ))}
        </div>
      </div>
    )
}
export default KenCollection;