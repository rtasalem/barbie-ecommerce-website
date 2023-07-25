import "../styles/scroll.css";
import kenData from "../data/kenData.json";

import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const KenScroll = (props) => {
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
        <div class="box">
        <p class="heading">Pump up the KENergy</p>
        <div class="scroll-container">
            <div class="horizontal-media-scroller">
            {kenData.map((item, idx) => (
              <div key={idx} className="pic">
                <Link className='caption-link' to={`/ken-item-page/${idx + 25}`}> 
                  <picture className="show-img"><img src={item.src} alt={item.alt} /></picture>
                  <span className="caption">{captions[idx+24]}</span>
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

export default KenScroll;