import { useNavigate } from "react-router-dom";

function Admin(){
    const navigate = useNavigate()

      const handleLogout = () => {
    localStorage.removeItem("customer")
    window.dispatchEvent(new Event("customerChanged"))
    navigate("/login")
  }

  return(
    <div>
        <h1>Admin Dashboard</h1>
        <h2>Welcome, Admin</h2>
        <div>
            <button type="button" onClick={() => navigate("/admin/products")}>Manage Products</button>
            <button type="button" onClick={() => navigate("/admin/categories")}>Manage Categories</button>
            <button type="button" onClick={() => navigate("/admin/inventory")}>Manage Inventories</button>
            <button type="button" onClick={handleLogout}>Logout</button>
        </div>
    </div>
  )
}

export default Admin