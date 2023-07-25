import "../styles/Header.css";

const Header = () => {
    return (
        <div className="header-container">
            <div className="logo-header">
                <a href="/">
                <img src="../icons/logo.png" alt="homepage" />
                </a>
            </div>
                <div className="menu-items">
                    <a href="/cart">
                    <img src="../icons/cart.png" alt="cart" />
                    </a>
                    <a href="/wishlist">
                    <img src="../icons/wishlist.png" alt="wishlist" />
                    </a>
                    <a href="/account">
                    <img src="../icons/account.png" alt="settings" />
                    </a>
                </div>
        </div>
      );
    }
    
export default Header;






