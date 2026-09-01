import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import {
    getAllCategories,
    createCategory,
    updateCategory,
    deleteCategory
} from "../services/categoryService"
 
function AdminCategories() {
 
    const navigate = useNavigate()
 
    const [categories, setCategories] = useState([])
    const [showForm, setShowForm] = useState(false)
    const [editCategoryId, setEditCategoryId] = useState(null)
 
    const [formData, setFormData] = useState({
        categoryName: "",
        description: ""
    })
 
    useEffect(() => {
        loadCategories()
    }, [])
 
    const loadCategories = () => {
        getAllCategories()
            .then((response) => {
                setCategories(response.data)
            })
            .catch((error) => {
                console.error("Error fetching categories", error)
            })
    }
 
    const handleAddCategory = () => {
 
        setEditCategoryId(null)
 
        setFormData({
            categoryName: "",
            description: ""
        })
 
        setShowForm(true)
    }
 
    const handleEdit = (category) => {
 
        setEditCategoryId(category.categoryId)
 
        setFormData({
            categoryName: category.categoryName,
            description: category.description
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
 
        if (editCategoryId) {
 
            updateCategory(editCategoryId, formData)
                .then(() => {
                    setShowForm(false)
                    setEditCategoryId(null)
                    loadCategories()
                })
                .catch((error) => {
                    console.error("Error updating category", error)
                })
 
        } else {
 
            createCategory(formData)
                .then(() => {
                    setShowForm(false)
                    loadCategories()
                })
                .catch((error) => {
                    console.error("Error creating category", error)
                })
        }
    }
 
    const handleDelete = (categoryId) => {
 
        if (!window.confirm("Are you sure you want to delete this category?")) {
            return
        }
 
        deleteCategory(categoryId)
            .then(() => {
                loadCategories()
            })
            .catch((error) => {
                console.error("Error deleting category", error)
            })
    }
 
    const handleCancel = () => {
 
        setShowForm(false)
        setEditCategoryId(null)
 
        setFormData({
            categoryName: "",
            description: ""
        })
    }
 
    return (
        <div>
 
            <h1>Category Management</h1>
 
            <button onClick={() => navigate("/admin")}>Back to Dashboard</button>
 
            <button onClick={handleAddCategory}>Add Category</button>
 
            {showForm && (
                <div>
                    <h2>
                        {editCategoryId ? "Edit Category" : "Add Category"}
                    </h2>
 
                    <form onSubmit={handleSubmit}>
                        <div>
                            <label>Category Name</label>
                            <input
                                type="text"
                                name="categoryName"
                                value={formData.categoryName}
                                onChange={handleChange}
                                required
                            />
                        </div>
 
                        <div>
                            <label>Description</label>
                            <input
                                type="text"
                                name="description"
                                value={formData.description}
                                onChange={handleChange}
                            />
                        </div>
 
                        <button type="submit">
                            {editCategoryId ? "Update Category" : "Save Category"}
                        </button>
 
                        <button type="button"onClick={handleCancel}>
                            Cancel
                        </button>
                    </form>
                </div>
            )}
 
            <br />
            <table border="1">
                <thead>
                    <tr>
                        <th>Category ID</th>
                        <th>Category Name</th>
                        <th>Description</th>
                        <th>Actions</th>
                    </tr>
                </thead>
 
                <tbody>
                    {categories.map((category) => (
                        <tr key={category.categoryId}>
                            <td>{category.categoryId}</td>
                            <td>{category.categoryName}</td>
                            <td>{category.description}</td>
                            <td>
                                <button onClick={() => handleEdit(category)}>
                                    Edit
                                </button>
 
                                <button onClick={() => handleDelete(category.categoryId)}>
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
 
export default AdminCategories