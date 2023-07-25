import Footer from "./components/Footer";
import Header from "./components/Header";
import Navbar from "./components/Navbar";

import HomePage from "./pages/HomePage";
import PremiereItemPage from "./pages/PremiereItemPage";
import SwimwearItemPage from "./pages/SwimwearItemPage";
import KenItemPage from "./pages/KenItemPage";

import PremierePage from "./pages/PremierePage";
import SwimwearPage from "./pages/SwimwearPage";
import KenPage from "./pages/KenPage";

import CartPage from "./pages/CartPage";
import WishlistPage from "./pages/WishlistPage";
import AccountPage from "./pages/AccountPage";

import { motion } from "framer-motion"
import { useState } from "react";
import { useEffect } from "react";
import { Routes, Route } from 'react-router-dom';
import "./App.css";


function App() {

  const [mousePosition, setMousePosition] = useState({
    x: 0,
    y: 0
  });

  /* event listener to map the mousePositon using x and y coordinates */
  useEffect(() => {
    const mouseMove = e => {
      setMousePosition({
        x: e.clientX,
        y: e.clientY
      })
    }

    window.addEventListener("mousemove", mouseMove);
    return () => {
      window.removeEventListener("mousemove", mouseMove);
    }
  }, []);

  /* defines animation properties for the cusom cursor, and shifts the default to centre */
  const variants = {
    default: {
      x: mousePosition.x - 20,
      y: mousePosition.y - 18,
    }
  }

  /* the motion.div is the custom cursor, which will follow the user's mouse movement based on the varients defined above */
  return (
    <div>
      <motion.div
        className="cursor"
        variants={variants}
        animate="default"
        style={{
          position: "fixed",
          pointerEvents: "none",
          zIndex: 9999,
        }}
      />
      <body className="body">
      <Header />
      <Navbar />

      <Routes>
        <Route path='/' element={<HomePage />} />
        <Route path='/premiere-item-page/:itemId' element={<PremiereItemPage />} />
        <Route path='/swimwear-item-page/:itemId' element={<SwimwearItemPage />} />
        <Route path='/ken-item-page/:itemId' element={<KenItemPage />} />

        <Route path='/premiere' element={<PremierePage />} />
        <Route path='/swimwear' element={<SwimwearPage />} />
        <Route path='/ken' element={<KenPage />} />

        <Route path='/cart' element={<CartPage />} />
        <Route path='/wishlist' element={<WishlistPage />} />
        <Route path='/account' element={<AccountPage />} />
      </Routes>

      <Footer />
      </body>
    </div>
  );
}

export default App;