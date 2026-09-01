import { useState } from "react"
import { useCart } from "../CartContext"
import "../scss/ProductCard.scss"
 
function ProductCard({ product, image }) {
    const { addToCart } = useCart()
    const [message, setMessage] = useState("")
 
    const handleAddToCart = () => {
        addToCart(product);
        setMessage("Product added to cart!")
 
        setTimeout(() => {
            setMessage("")
        }, 2000)
    }
 
    return (
        <div className="product-card">
            <img src={image} alt={product.productName} />
 
            <h3>{product.productName}</h3>
            <p>Price: ₹{product.price}</p>
            <p>Available Quantity: {product.availableQuantity}</p>
 
            <button type="button" onClick={handleAddToCart}>Add to Cart</button>
 
            {message && <p className="cart-message">{message}</p>}
        </div>
    )
}
export default ProductCard
 
 
