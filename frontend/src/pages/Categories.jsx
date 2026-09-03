import { useEffect, useState } from "react";
import { getAllCategories } from "../services/categoryService";
import { Link } from "react-router-dom";
import "../scss/Categories.scss";

function Categories() {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    getAllCategories()
      .then((response) => {
        setCategories(response.data);
      })
      .catch((error) => {
        console.error("Error fetching categories:", error);
      });
  }, []);

  return (
    <div className="categories-container">
      <h1>Shop by Category</h1>

      <div className="categories-grid">
        {categories.map((category) => (
          <div className="category-card" key={category.categoryId}>
            <h2>{category.categoryName}</h2>
            <p>{category.description}</p>
            <Link to={`/products?category=${category.categoryId}`}>
              <button>View Products</button>
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
}
export default Categories;
