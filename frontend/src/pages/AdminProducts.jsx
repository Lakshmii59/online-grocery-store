import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { getAllProducts,createProduct,updateProduct,deleteProduct } from "../services/productService"
import { getAllCategories } from "../services/categoryService"
 
function AdminProducts() {
 
    const navigate = useNavigate()
 
    const [products, setProducts] = useState([])
    const [categories, setCategories] = useState([])
 
    const [showForm, setShowForm] = useState(false)
    const [editProductId, setEditProductId] = useState(null)
 
    const [formData, setFormData] = useState({
        productName: "",
        sku: "",
        price: "",
        availableQuantity: "",
        active: true,
        categoryId: ""
    })
 
    useEffect(() => {
        loadProducts()
        loadCategories()
    }, [])
 
    const loadProducts = () => {
        getAllProducts()
            .then((response) => {
                setProducts(response.data)
            })
            .catch((error) => {
                console.error("Error fetching products", error)
            })
    }
 
    const loadCategories = () => {
        getAllCategories()
            .then((response) => {
                setCategories(response.data)
            })
            .catch((error) => {
                console.error("Error fetching categories", error)
            })
    }
 
    const handleChange = (event) => {
        const { name, value } = event.target
        setFormData({
            ...formData,
            [name]: value})
    }
 
    const handleAddProduct = () => {
        setEditProductId(null)
        setFormData({
            productName: "",
            sku: "",
            price: "",
            availableQuantity: "",
            active: true,
            categoryId: ""
        })
 
        setShowForm(true)
    }
 
    const handleEdit = (product) => {
        setEditProductId(product.productId)
        setFormData({
            productName: product.productName,
            sku: product.sku,
            price: product.price,
            availableQuantity: product.availableQuantity,
            active: product.active,
            categoryId: product.categoryId
        })
        setShowForm(true)
    }
 
    const handleSubmit = (event) => {
        event.preventDefault()

        const product = {
            productName: formData.productName,
            sku: formData.sku,
            price: Number(formData.price),
            availableQuantity: Number(formData.availableQuantity),
            active: formData.active === true || formData.active === "true",
            categoryId: Number(formData.categoryId)
        }
 
        if (editProductId) {
 
            updateProduct(editProductId, product)
                .then(() => {
                    alert("Product updated successfully")
                    setShowForm(false)
                    loadProducts()
                })
                .catch((error) => {
                    console.error("Error updating product", error)
                })
 
        } else {
            createProduct(product)
                .then(() => {
                    alert("Product created successfully")
                    setShowForm(false)
                    loadProducts()
                })
                .catch((error) => {
                    console.error("Error creating product", error)
                })
        }
    }
 
    const handleDelete = (productId) => {
 
        const confirmDelete = window.confirm(
            "Are you sure you want to delete this product?"
        )
 
        if (!confirmDelete) {
            return
        }
 
        deleteProduct(productId)
            .then(() => {
                alert("Product deleted successfully")
                loadProducts()
            })
            .catch((error) => {
                console.error("Error deleting product", error)
            })
    }
 
    return (
        <div>
            <h1>Product Management</h1>
            <button type="button" onClick={() => navigate("/admin")}>Back to Dashboard</button>
 
            <button type="button" onClick={handleAddProduct}>Add Product</button>
 
            {showForm && (
                <div>
                    <h2>
                        {editProductId ? "Edit Product" : "Add Product"}
                    </h2>
 
                    <form onSubmit={handleSubmit}>
                        <div>
                            <label>Product Name</label>
                            <input
                                type="text"
                                name="productName"
                                value={formData.productName}
                                onChange={handleChange}
                                required
                            />
                        </div>
 
                        <div>
                            <label>SKU</label>
                            <input
                                type="text"
                                name="sku"
                                value={formData.sku}
                                onChange={handleChange}
                                required
                            />
                        </div>
 
                        <div>
                            <label>Price</label>
                            <input
                                type="number"
                                name="price"
                                value={formData.price}
                                onChange={handleChange}
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
                                required
                            />
                        </div>
 
                        <div>
                            <label>Category</label>
                            <select
                                name="categoryId"
                                value={formData.categoryId}
                                onChange={handleChange}
                                required
                            >
                                <option value="">
                                    Select Category
                                </option>
 
                                {categories.map((category) => (
                                    <option
                                        key={category.categoryId}
                                        value={category.categoryId}>
                                        {category.categoryName}
                                    </option>
                                ))}
                            </select>
                        </div>
 
                        <div>
                            <label>Active</label>
                            <select
                                name="active"
                                value={formData.active}
                                onChange={handleChange}>
                                <option value={true}>Active</option>
                                <option value={false}>Inactive</option>
                            </select>
                        </div>
 
                        <button type="submit">{editProductId ? "Update Product" : "Save Product"}</button>
 
                        <button type="button" onClick={() => setShowForm(false)}>
                            Cancel
                        </button>
                    </form>
                </div>
            )}
 
            <br />
            <table border="1">
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>SKU</th>
                        <th>Price</th>
                        <th>Available Quantity</th>
                        <th>Active</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
 
                    {products.map((product) => (
                        <tr key={product.productId}>
                            <td>{product.productId}</td>
                            <td>{product.productName}</td>
                            <td>{product.sku}</td>
                            <td>₹{product.price}</td>
                            <td>{product.availableQuantity}</td>
                            <td>
                                {product.active ? "Yes" : "No"}
                            </td>
                            <td>
 
                                <button type="button" onClick={() => handleEdit(product)}>
                                    Edit
                                </button>
 
                                <button type="button" onClick={() =>
                                        handleDelete(product.productId)}>
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}
 
export default AdminProducts