import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import "../scss/Navbar.scss";

function Navbar() {
  const navigate = useNavigate();

  const [searchTerm, setSearchTerm] = useState("");

  const [customer, setCustomer] = useState(
    JSON.parse(localStorage.getItem("customer")),
  );

  useEffect(() => {
    const updateCustomer = () => {
      setCustomer(JSON.parse(localStorage.getItem("customer")));
    };
    window.addEventListener("customerChanged", updateCustomer);
    return () => {
      window.removeEventListener("customerChanged", updateCustomer);
    };
  }, []);

  const handleSearch = (e) => {
    if (e.key === "Enter" && searchTerm.trim()) {
      navigate(`/products?search=${encodeURIComponent(searchTerm)}`);
    }
  };

  return (
    <nav className="navbar">
      <Link to="/" className="brand-name">
        <span className="brand-icon">🌿</span>
        <span>
          <strong>FreshNest</strong>
        </span>
      </Link>

      <div className="search-box">
        <span className="search-icon">⌕</span>

        <input
          type="text"
          placeholder="Search for products..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          onKeyDown={handleSearch}
        />
      </div>

      <div className="nav-links">
        {customer?.role === "ADMIN" ? (
          <>
            <Link to="/admin/products">Products</Link>
            <Link to="/admin/categories">Categories</Link>
            <Link to="/admin/inventory">Inventory</Link>
            <Link to="/admin/orders">Orders</Link>

            <button
              className="profile-button admin-profile"
              onClick={() => navigate("/admin")}
            >
              👤 Admin
            </button>
          </>
        ) : (
          <>
            <Link to="/cart" className="cart-link">
              🛒 Cart
            </Link>

            {customer ? (
              <div className="profile-container">
                <button
                  className="profile-button"
                  onClick={() => navigate("/profile")}
                >
                  👤 Profile
                </button>
              </div>
            ) : (
              <Link to="/login" className="login-link">
                Login / Sign up
              </Link>
            )}
          </>
        )}
      </div>
    </nav>
  );
}

export default Navbar;
