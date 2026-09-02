import { useNavigate } from "react-router-dom"
import "../scss/Admin.scss"
 
function Admin() {
  const navigate = useNavigate()
 
  const handleLogout = () => {
    localStorage.removeItem("customer")
    window.dispatchEvent(new Event("customerChanged"))
    navigate("/login")
  }
 
  return (
    <div className="admin">
      <div className="admin-header">
        <h1>Admin Dashboard</h1>
        <p>Welcome back, Admin</p>
      </div>
 
      <div className="admin-cards">
        <div className="admin-card">
          <div className="card-content">
            <h2>Products</h2>
            <p>Manage your FreshNest products</p>
          </div>
 
          <button
            type="button"
            onClick={() => navigate("/admin/products")}
          >
            Manage Products
          </button>
        </div>
 
        <div className="admin-card">
          <div className="card-content">
            <h2>Categories</h2>
            <p>Organize products into categories</p>
          </div>
 
          <button
            type="button"
            onClick={() => navigate("/admin/categories")}
          >
            Manage Categories
          </button>
        </div>
 
        <div className="admin-card">
          <div className="card-content">
            <h2>Inventory</h2>
            <p>Manage product stock and inventory</p>
          </div>
 
          <button
            type="button"
            onClick={() => navigate("/admin/inventory")}
          >
            Manage Inventory
          </button>
        </div>
      </div>
 
      <div className="admin-actions">
        <h2>Quick Actions</h2>
 
        <div className="quick-actions">
          <button
            type="button"
            onClick={() => navigate("/admin/products")}
          >
            + Add Product
          </button>
 
          <button
            type="button"
            onClick={() => navigate("/admin/categories")}
          >
            + Add Category
          </button>
 
          <button
            type="button"
            onClick={() => navigate("/admin/inventory")}
          >
            + Add Inventory
          </button>
        </div>
      </div>
 
      <button
        type="button"
        className="logout-button"
        onClick={handleLogout}
      >
        Logout
      </button>
    </div>
  )
}
 
export default Admin