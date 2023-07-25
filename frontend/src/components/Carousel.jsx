import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "../styles/carousel.css"
import {IoIosArrowBack, IoIosArrowForward} from "react-icons/io";

const Carousel = () => {

    const [slide, setSlide] = useState(0);

    const nextSlide =() => {
        setSlide(slide === data.length - 1 ? 0 : slide + 1);
    }

    const prevSlide = () => {
        setSlide(slide === 0 ? data.length - 1 : slide - 1);
    }

    const data = [
        {
          src: "../images/hero_image.jpg",
          alt: "image 1 for carousel",
          text: "Get the Look",
          link: "/premiere",
        },
        {
          src: "../images/hero_image2.jpg",
          alt: "image 2 for carousel",
          text: "Take a Swim!",
          link: "/swimwear",
        },
        {
          src: "../images/hero_image3.jpg",
          alt: "image 3 for carousel",
          text: "Feel the KENergy",
          link: "/ken",
        },
      ];

  return(
    <div className="carousel">
        <IoIosArrowBack className="arrow arrow-left" onClick={prevSlide} />
        {data.map((item, idx) => (
        <div
          key={idx}
          className={slide === idx ? "slide" : "slide slide-hidden"}
        >
          <img src={item.src} alt={item.alt} className="slide-image" />
          <Link to={item.link} className="banner-button">
            {item.text}
          </Link>
        </div>
      ))}
       <IoIosArrowForward className="arrow arrow-right" onClick={nextSlide}/>
       <span className="indicators">
            {data.map((_, idx) => {
            return( 
            <button key={idx} 
                onClick={() => setSlide(idx)} 
                className={slide === idx ? "indicator" : "indicator indicator-inactive"}>
            </button>);
        })}
        </span>
    </div>
  );
};

export default Carousel;
