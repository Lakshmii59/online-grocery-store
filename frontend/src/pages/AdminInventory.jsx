import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import {
    getAllInventory,
    createInventory,
    updateInventory,
    deleteInventory
} from "../services/inventoryService"
 
function AdminInventory() {
 
    const navigate = useNavigate()
 
    const [inventory, setInventory] = useState([])
    const [showForm, setShowForm] = useState(false)
    const [editProductId, setEditProductId] = useState(null)
 
    const [formData, setFormData] = useState({
        productId: "",
        availableQuantity: "",
        reservedQuantity: ""
    })
 
    useEffect(() => {
        loadInventory()
    }, [])
 
    const loadInventory = () => {
        getAllInventory()
            .then((response) => {
                setInventory(response.data)
            })
            .catch((error) => {
                console.error("Error fetching inventory", error)
            })
    }
 
    const handleAddInventory = () => {
        setEditProductId(null)
        setFormData({
            productId: "",
            availableQuantity: "",
            reservedQuantity: ""
        })
        setShowForm(true)
    }
 
    const handleEdit = (item) => {
        setEditProductId(item.productId)
        setFormData({
            productId: item.productId,
            availableQuantity: item.availableQuantity,
            reservedQuantity: item.reservedQuantity
        })
        setShowForm(true)
    }
 
    const handleChange = (event) => {
        const { name, value } = event.target
        setFormData({
            ...formData,
            [name]: value
        })
    }
 
    const handleSubmit = (event) => { 
        event.preventDefault()
        const inventoryData = {
            productId: Number(formData.productId),
            availableQuantity: Number(formData.availableQuantity),
            reservedQuantity: Number(formData.reservedQuantity)
        }
 
        if (editProductId) {
            updateInventory(editProductId, inventoryData)
                .then(() => {
                    setShowForm(false)
                    setEditProductId(null)
                    loadInventory()
                })
                .catch((error) => {
                    console.error("Error updating inventory", error)
                })
 
        } else {
            createInventory(inventoryData)
                .then(() => {
                    setShowForm(false)
                    loadInventory()
                })
                .catch((error) => {
                    console.error("Error creating inventory", error)
                })
        }
    }
 
    const handleDelete = (productId) => {
 
        if (!window.confirm("Are you sure you want to delete this inventory?")) {
            return
        }
        deleteInventory(productId)
            .then(() => {
                loadInventory()
            })
            .catch((error) => {
                console.error("Error deleting inventory", error)
            })
    }
 
    const handleCancel = () => {
        setShowForm(false)
        setEditProductId(null)
        setFormData({
            productId: "",
            availableQuantity: "",
            reservedQuantity: ""
        })
    }
 
    return (
        <div>
            <h1>Inventory Management</h1>
            <button onClick={() => navigate("/admin")}>Back to Dashboard</button>
 
            <button onClick={handleAddInventory}>Add Inventory</button>
 
            {showForm && (
                <div>
                    <h2>
                        {editProductId ? "Edit Inventory" : "Add Inventory"}
                    </h2>
 
                    <form onSubmit={handleSubmit}>
                        <div>
                            <label>Product ID</label>
                            <input
                                type="number"
                                name="productId"
                                value={formData.productId}
                                onChange={handleChange}
                                disabled={editProductId !== null}
                                required
                            />
                        </div>
 
                        <div>
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
 
                        <div>
                            <label>Reserved Quantity</label>
                            <input
                                type="number"
                                name="reservedQuantity"
                                value={formData.reservedQuantity}
                                onChange={handleChange}
                                min="0"
                                required
                            />
                        </div>
 
                        <button type="submit">
                            {editProductId ? "Update Inventory" : "Save Inventory"}
                        </button>
 
                        <button type="button" onClick={handleCancel}>
                            Cancel
                        </button>
                    </form>
                </div>
            )}
 
            <br />
 
            <table border="1">
                <thead>
                    <tr>
                        <th>Inventory ID</th>
                        <th>Product ID</th>
                        <th>Available Quantity</th>
                        <th>Reserved Quantity</th>
                        <th>Actions</th>
                    </tr>
                </thead>
 
                <tbody>
                    {inventory.map((item) => (
                        <tr key={item.inventoryId}>
                            <td>{item.inventoryId}</td>
                            <td>{item.productId}</td>
                            <td>{item.availableQuantity}</td>
                            <td>{item.reservedQuantity}</td>
                            <td>
 
                                <button onClick={() => handleEdit(item)}>
                                    Edit
                                </button>
 
                                <button onClick={() => handleDelete(item.productId)}>
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
 
                </tbody>
            </table>
        </div>
    )}
 
export default AdminInventory