import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { getAllProducts } from "../services/productService";
import { getAllCategories } from "../services/categoryService";
import ProductCard from "../components/ProductCard";
import "../scss/Home.scss";

function Home() {
  const navigate = useNavigate();

  const [categories, setCategories] = useState([]);
  const [products, setProducts] = useState([]);

  useEffect(() => {
    getAllCategories()
      .then((response) => {
        setCategories(response.data);
      })
      .catch((error) => {
        console.error("Error fetching categories:", error);
      });

    getAllProducts()
      .then((response) => {
        setProducts(response.data);
      })
      .catch((error) => {
        console.error("Error fetching products:", error);
      });
  }, []);

  return (
    <div className="home">
      {/* Hero Section */}
      <section className="hero">
        <div className="hero-content">
          <span className="hero-tag">Fresh • Quality • Delivered</span>

          <h1>
            Fresh Groceries,
            <br />
            Delivered to Your Door
          </h1>

          <p>
            Shop fresh fruits, vegetables, kitchen essentials and more from the
            comfort of your home.
          </p>

          <Link to="/products" className="shop-button">
            Shop Now
          </Link>
        </div>
      </section>

      {/* Shop by Category */}
      <section className="category-section">
        <h2 className="section-title">Shop by Category</h2>

        <div className="category-list">
          {categories.map((category) => (
            <button
              key={category.categoryId}
              className="category-item"
              onClick={() =>
                navigate(`/products?category=${category.categoryId}`)
              }
            >
              {category.categoryName}
            </button>
          ))}
        </div>
      </section>

      <section className="home-products">
        <div className="products-heading">
          <h2 className="section-title">Fresh Products</h2>

          <Link to="/products" className="view-all">
            View All →
          </Link>
        </div>

        <div className="products-grid">
          {products
            .filter((product) => {
              const category = categories.find(
                (category) => category.categoryId === product.categoryId,
              );
              return (
                category?.categoryName === "Fruits" ||
                category?.categoryName === "Vegetables"
              );
            })
            .slice(0, 4)
            .map((product) => (
              <ProductCard key={product.productId} product={product} />
            ))}
        </div>
      </section>
    </div>
  );
}

export default Home;
