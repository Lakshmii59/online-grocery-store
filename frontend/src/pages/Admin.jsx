import { useNavigate } from "react-router-dom";
import "../scss/Admin.scss";

function Admin() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("customer");
    window.dispatchEvent(new Event("customerChanged"));
    navigate("/login");
  };

  return (
    <div className="admin">
      <div className="admin-header">
        <h1>Admin Dashboard</h1>
        <p>Welcome back, Admin</p>
      </div>

        <div className="admin-card">
          <div className="card-content">
            <h2>Inventory Management</h2>
            <p>Manage products, categories, stock and inventory</p>
          </div>

          <button type="button" onClick={() => navigate("/admin/inventory")}>
            Manage Inventory
          </button>
        </div>

        <div className="admin-card">
          <div className="card-content">
            <h2>Orders</h2>
            <p>View and manage customer orders</p>
          </div>

          <button type="button" onClick={() => navigate("/admin/orders")}>
            Manage Orders
          </button>
        </div>
      
      <div className="admin-actions">
        <h2>Quick Actions</h2>

        <div className="quick-actions">
          <button type="button" onClick={() => navigate("/admin/inventory?form=product")}>
            + Add Product
          </button>

          <button type="button" onClick={() => navigate("/admin/inventory?form=category")}>
            + Add Category
          </button>

          <button type="button" onClick={() => navigate("/admin/inventory?form=inventory")}>
            + Add Inventory
          </button>
        </div>
      </div>

      <button type="button" className="logout-button" onClick={handleLogout}>
        Logout
      </button>
    </div>
  );
}

export default Admin;
