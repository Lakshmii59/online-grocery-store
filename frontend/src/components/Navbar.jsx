import { Link, useLocation, useNavigate } from "react-router-dom"
import { useState, useEffect } from "react"
import "../scss/Navbar.scss"
 
function Navbar() {
  const location = useLocation()
  const navigate = useNavigate()
 
  const [searchTerm, setSearchTerm] = useState("")
 
  const [customer, setCustomer] = useState(
    JSON.parse(localStorage.getItem("customer"))
  )
 
  const [showProfile, setShowProfile] = useState(false)

  useEffect(() => {
    const updateCustomer = () => {
      setCustomer(JSON.parse(localStorage.getItem("customer")))
    }
    window.addEventListener("customerChanged",updateCustomer)
    return () => {
      window.removeEventListener("customerChanged",updateCustomer)
    }
  },[])
 
  const handleLogout = () => {
    localStorage.removeItem("customer")
    window.dispatchEvent(new Event("customerChanged"))
    setCustomer(null)
    setShowProfile(false)
    navigate("/login")
  }
 
  const handleSearch = (e) => {
    if (e.key === "Enter" && searchTerm.trim()) {
      navigate(`/products?search=${encodeURIComponent(searchTerm)}`)
    }
  }
 
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
                  onClick={() => setShowProfile(!showProfile)}
                >
                  👤 Profile
                </button>
 
                {showProfile && (
                  <div className="profile-menu">
 
                    <h3>My Account</h3>
 
                    <p>{customer.customerName}</p>
                    <p>{customer.email}</p>
 
                    <hr />
 
                    <div onClick={() => navigate("/profile")}>
                      My Account
                    </div>
 
                    <div onClick={() => navigate("/cart")}>
                      My Basket
                    </div>
 
                    <div>My Orders</div>
 
                    <div>Contact Us</div>
 
                    <div onClick={handleLogout}>
                      Logout
                    </div>
                  </div>
                )}
 
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
  )
}
 
export default Navbar