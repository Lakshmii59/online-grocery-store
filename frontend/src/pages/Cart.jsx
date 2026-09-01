import { useNavigate } from "react-router-dom"
import { useCart } from "../CartContext"
 
function Cart() {
  const { cart, increaseQuantity, decreaseQuantity, removeFromCart } = useCart()
 
  const navigate = useNavigate()
 
  const totalPrice = cart.reduce(
    (total, item) => total + item.price * item.quantity,0)
 
  const handleCheckout = () => {
    const customer = localStorage.getItem("customer")
 
    if (customer) {
      navigate("/checkout");
    } else {
      navigate("/login", { state: { from: "/checkout" } })
    }
  }
 
  return (
    <div>
      <h1>Cart</h1>
      {cart.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <>
          {cart.map((item) => (
            <div key={item.productId}>
              <h3>{item.productName}</h3>
              <p>Price: ₹{item.price}</p>
              <div className="quantity-controls">
                <button type="button"
                  onClick={() => decreaseQuantity(item.productId)}>−</button>
 
                <span>{item.quantity}</span>
 
                <button type="button"
                  onClick={() => increaseQuantity(item.productId)}>+</button>
              </div>
 
              <button type="button"
                onClick={() => removeFromCart(item.productId)}>Remove</button>
            </div>
          ))}
 
          <h2>Total: ₹{totalPrice}</h2>
 
          <button type="button" onClick={handleCheckout}>Proceed to Checkout</button>
        </>
      )}
    </div>
  )
}
 
export default Cart