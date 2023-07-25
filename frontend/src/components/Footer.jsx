import React, { useState } from 'react';
import "../styles/Footer.css";

const Footer = () => {
    const [isHovered, setIsHovered] = useState(false);

    const handleHover = () => {
        setIsHovered(true);
    };

    const handleMouseLeave = () => {
        setIsHovered(false);
    };

    return (
        <footer className="footer">
            {!isHovered ? (
                <>
                    <div className="icons">
                        <a href="https://www.instagram.com/"><img src="../icons/instagram.png" alt="Instagram Logo" /></a>
                        <a href="https://twitter.com/"><img src="../icons/twitter.png" alt="Twitter Logo" /></a>
                        <a href="https://www.facebook.com/"><img src="../icons/facebook.png" alt="Facebook Logo" /></a>
                        <a href="https://www.tiktok.com/"><img src="../icons/tiktok.png" alt="TikTok Logo" /></a>
                    </div>
                    <div className="logo">
                        <img src="../icons/logo.png" alt="Barbie Logo" />
                    </div>
                    <div className="creator" onMouseEnter={handleHover}>
                        <p>Created By</p>
                    </div>
                </>
            ) : (
                <div className="names-strip" onMouseLeave={handleMouseLeave}>
                    <ul className="names-list">
                        <li>Laila Al-Eissa</li>
                        <li>Andreea-Daniela Baciu</li>
                        <li>Nadeen Bayley</li>
                        <li>Viktorija Blumberga</li>
                        <li>Maryam Islam</li>
                        <li>Kuljeet Panesar</li>
                        <li>Rana Salem</li>
                    </ul>
                </div>
            )}
        </footer>
    );
};

export default Footer;


