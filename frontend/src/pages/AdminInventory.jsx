import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  getAllInventory,
  createInventory,
  updateInventory,
  deleteInventory,
} from "../services/inventoryService";
import { getAllProducts } from "../services/productService";
import "../scss/AdminInventory.scss";

function AdminInventory() {
  const navigate = useNavigate();

  const [inventory, setInventory] = useState([]);
  const [products, setProducts] = useState([]);

  const [showForm, setShowForm] = useState(false);
  const [editProductId, setEditProductId] = useState(null);

  const [formData, setFormData] = useState({
    productId: "",
    availableQuantity: "",
    reservedQuantity: 0,
  });

  useEffect(() => {
    loadInventory();
    loadProducts();
  }, []);

  const loadInventory = () => {
    getAllInventory()
      .then((response) => {
        setInventory(response.data);
      })
      .catch((error) => {
        console.error("Error fetching inventory", error);
      });
  };

  const loadProducts = () => {
    getAllProducts()
      .then((response) => {
        setProducts(response.data);
      })
      .catch((error) => {
        console.error("Error fetching products", error);
      });
  };

  const handleAddInventory = () => {
    setEditProductId(null);

    setFormData({
      productId: "",
      availableQuantity: "",
      reservedQuantity: 0,
    });

    setShowForm(true);
  };

  const handleEdit = (item) => {
    setEditProductId(item.productId);

    setFormData({
      productId: item.productId,
      availableQuantity: item.availableQuantity,
      reservedQuantity: item.reservedQuantity,
    });

    setShowForm(true);
  };

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const inventoryData = {
      productId: Number(formData.productId),
      availableQuantity: Number(formData.availableQuantity),
      reservedQuantity: Number(formData.reservedQuantity),
    };

    if (editProductId) {
      updateInventory(editProductId, inventoryData)
        .then(() => {
          alert("Inventory updated successfully");
          setShowForm(false);
          setEditProductId(null);
          loadInventory();
        })
        .catch((error) => {
          console.error("Error updating inventory", error);
        });
    } else {
      createInventory(inventoryData)
        .then(() => {
          alert("Inventory created successfully");
          setShowForm(false);
          loadInventory();
        })
        .catch((error) => {
          console.error("Error creating inventory", error);
        });
    }
  };

  const handleDelete = (productId) => {
    if (!window.confirm("Are you sure you want to delete this inventory?")) {
      return;
    }

    deleteInventory(productId)
      .then(() => {
        alert("Inventory deleted successfully");
        loadInventory();
      })
      .catch((error) => {
        console.error("Error deleting inventory", error);
      });
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditProductId(null);

    setFormData({
      productId: "",
      availableQuantity: "",
      reservedQuantity: 0,
    });
  };

  const getProductName = (productId) => {
    const product = products.find((product) => product.productId === productId);

    return product ? product.productName : `Product ${productId}`;
  };

  return (
    <div className="admin-inventory">
      <div className="admin-inventory-header">
        <div>
          <h1>Inventory Management</h1>
          <p>Manage product stock and inventory levels</p>
        </div>

        <div className="header-actions">
          <button
            type="button"
            className="secondary-button"
            onClick={() => navigate("/admin")}
          >
            Back to Dashboard
          </button>

          <button
            type="button"
            className="primary-button"
            onClick={handleAddInventory}
          >
            + Add Inventory
          </button>
        </div>
      </div>

      {showForm && (
        <div className="inventory-form-card">
          <div className="form-header">
            <h2>{editProductId ? "Edit Inventory" : "Add Inventory"}</h2>
          </div>

          <form onSubmit={handleSubmit}>
            <div className="form-grid">
              <div className="form-group">
                <label>Product</label>

                <select
                  name="productId"
                  value={formData.productId}
                  onChange={handleChange}
                  disabled={editProductId !== null}
                  required
                >
                  <option value="">Select Product</option>

                  {products.map((product) => (
                    <option key={product.productId} value={product.productId}>
                      {product.productName}
                    </option>
                  ))}
                </select>
              </div>

              <div className="form-group">
                <label>Available Quantity</label>

                <input
                  type="number"
                  name="availableQuantity"
                  value={formData.availableQuantity}
                  onChange={handleChange}
                  min="0"
                  required
                />
              </div>

              <div className="form-group">
                <label>Reserved Quantity</label>

                <input
                  type="number"
                  name="reservedQuantity"
                  value={formData.reservedQuantity}
                  readOnly
                />
              </div>
            </div>

            <div className="form-actions">
              <button type="submit" className="primary-button">
                {editProductId ? "Update Inventory" : "Save Inventory"}
              </button>

              <button
                type="button"
                className="cancel-button"
                onClick={handleCancel}
              >
                Cancel
              </button>
            </div>
          </form>
        </div>
      )}

      <div className="inventory-table-card">
        <div className="table-header">
          <div>
            <h2>Inventory</h2>
            <span>{inventory.length} inventory records</span>
          </div>
        </div>

        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Inventory ID</th>
                <th>Product</th>
                <th>Available Quantity</th>
                <th>Reserved Quantity</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>
              {inventory.map((item) => (
                <tr key={item.inventoryId}>
                  <td>{item.inventoryId}</td>

                  <td className="product-name">
                    {getProductName(item.productId)}
                  </td>

                  <td>
                    <span className="quantity available">
                      {item.availableQuantity}
                    </span>
                  </td>

                  <td>
                    <span className="quantity reserved">
                      {item.reservedQuantity}
                    </span>
                  </td>

                  <td>
                    <div className="action-buttons">
                      <button
                        type="button"
                        className="edit-button"
                        onClick={() => handleEdit(item)}
                      >
                        Edit
                      </button>

                      <button
                        type="button"
                        className="delete-button"
                        onClick={() => handleDelete(item.productId)}
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default AdminInventory;
