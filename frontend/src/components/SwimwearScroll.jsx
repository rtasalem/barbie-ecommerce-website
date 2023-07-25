import "../styles/scroll.css";
import swimwearData from "../data/swimwearData.json";

import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const SwimwearScroll = (props) => {
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
        <p className="heading">Straight to the Beach</p>
        <div className="scroll-container">
          <div className="horizontal-media-scroller">
            {swimwearData.map((item, idx) => (
              <div key={idx} className="pic">
                <Link className='caption-link' to={`/swimwear-item-page/${idx + 13}`}> 
                  <picture className="show-img"><img src={item.src} alt={item.alt} /></picture>
                  <span className="caption">{captions[idx+12]}</span>
                  <picture className="hover-img"><img src={item.hoverSrc} alt={item.hoverAlt} /></picture>
                </Link>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
    )
}

export default SwimwearScroll;