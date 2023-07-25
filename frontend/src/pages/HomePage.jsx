
import PremiereScroll from "../components/PremiereScroll";
import "../styles/homepage.css";
import Carousel from "../components/Carousel";
import SwimwearScroll from "../components/SwimwearScroll";
import KenScroll from "../components/KenScroll";

const HomePage = () => {
    return (
    <div>
        <body className="body">
        <Carousel />

        <PremiereScroll />
        <SwimwearScroll />
        <KenScroll />

        </body>
    </div>
    )
}

export default HomePage;