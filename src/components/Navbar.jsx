import { Link, useLocation, useNavigate } from "react-router-dom"
import { useState, useEffect } from "react"
import "../scss/Navbar.scss"
import { getAllCategories } from "../services/categoryService"
 
function Navbar() {
  const location = useLocation()
  const navigate = useNavigate()
  const [categories, setCategories] = useState([])
  const [showCategories, setShowCategories] = useState(false)
  const [searchTerm, setSearchTerm] = useState("")
 
  const [customer, setCustomer] = useState(
    JSON.parse(localStorage.getItem("customer"))
  )
 
  const [showProfile, setShowProfile] = useState(false)
 
  useEffect(() => {
    setCustomer(JSON.parse(localStorage.getItem("customer")))
    getAllCategories().then((response) => {
      setCategories(response.data)
    })
    .catch((error) => {console.error("Error fetching categories",error)
    })
  }, [location])
 
  const handleLogout = () => {
    localStorage.removeItem("customer")
    setCustomer(null)
    setShowProfile(false)
    navigate("/login")
  };
 
  return (
    <nav className="navbar">
 
      <h2>Online Grocery Store</h2>
 
      <div className="search-box">
        <input type="text" placeholder="Search for products..."
        value={searchTerm} onChange={(e) => 
          setSearchTerm(e.target.value)}
          onKeyDown={(e) => {
            if (e.key === "Enter"){
              navigate(`/products?search=${encodeURIComponent(searchTerm)}`)
            }
          }}/>
      </div>
 
      <div className="nav-links">
        <div className="category-dropdown">
          <button className="category-button" 
          onClick={() => setShowCategories(!showCategories)}>Category ▾</button>
          {showCategories && (
            <div className="category-menu">
              {categories.map((category) => (
                <div key={category.categoryId}
                className="category-item" onClick={() => {
                  navigate(`/products?category=${category.categoryId}`)
                  setShowCategories(false)
                }}>
                  {category.categoryName}
                  </div>
              ))}
              </div>
          )}
          </div>

        <Link to="/products">Products</Link>
        <Link to="/cart">Cart</Link>
 
        {customer ? (
          <div className="profile-container">
            <button className="profile-button" onClick={() => setShowProfile(!showProfile)}>👤Profile</button>
 
            {showProfile && (
              <div className="profile-menu">
                <h3>My Account</h3>
                <p>{customer.customerName}</p>
                <p>{customer.email}</p>
                <hr />
                <div onClick={() => navigate("/profile")}>My Account</div>
                <div onClick={() => navigate("/cart")}>My Basket</div>
                <div>My Orders</div>
                <div>Contact Us</div>
                <div onClick={handleLogout}>Logout</div>
              </div>
            )}
          </div>
        ) : (
          <Link to="/login">Login/Sign up</Link>
        )}
      </div>
    </nav>
  )
}
 
export default Navbar