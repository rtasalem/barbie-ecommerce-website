import "../styles/scroll.css";
import premiereData from "../data/premiereData.json";

import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const PremiereScroll = (props) => {
  const api = 'http://localhost:8088/api/v1/items';

  const { items } = props;
  const [captions, setCaptions] = useState([]);

  useEffect(() => {
    // Fetch data from the API and set captions in the state
    fetch(api)
      .then(response => response.json())
      .then(data => {
        const fetchedCaptions = data.map(item => item.name);
        setCaptions(fetchedCaptions);
      })
      .catch(error => {
        console.error("Error fetching data from API:", error);
        setCaptions([]);
      });
  }, [api]);

  return (
    <div>
      <div className="box">
        <p className="heading">Right off the Red Carpet</p>
        <div className="scroll-container">
          <div className="horizontal-media-scroller">
            {premiereData.map((item, idx) => (
              <div key={idx} className="pic">
                <Link className='caption-link' to={`/premiere-item-page/${idx + 1}`}> 
                  <picture className="show-img"><img src={item.src} alt={item.alt} /></picture>
                  <span className="caption">{captions[idx]}</span>
                  <picture className="hover-img"><img src={item.hoverSrc} alt={item.hoverAlt} /></picture>
                </Link>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default PremiereScroll;
