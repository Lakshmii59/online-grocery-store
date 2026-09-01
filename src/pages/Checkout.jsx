import { useState } from "react"
import { useCart } from "../CartContext"
import "../scss/Checkout.scss"
import { useNavigate } from "react-router-dom"
 
function Checkout() {
  const { cart } = useCart()
  const navigate = useNavigate()
 
  const [customer, setCustomer] = useState({
    name: "",
    email: "",
    phone: "",
    address: "",
  })
 
  const totalPrice = cart.reduce(
    (total, item) => total + item.price * item.quantity,0)
 
  const handleChange = (e) => {
    setCustomer({...customer,[e.target.name]: e.target.value,})
  }
 
  const handleSubmit = async (e) => {e.preventDefault()
 
try {
   const loggedInCustomer = JSON.parse(
     localStorage.getItem("customer")
   )
 
   const orderRequest = {
     customerId: loggedInCustomer.customerId,
 
     orderItems: cart.map((item) => ({
       productId: item.productId,
       quantity: item.quantity,
     })),
   }
 
   console.log("Order request:", orderRequest)
 
   const response = await fetch(
     "http://localhost:8084/api/orders",
     {
       method: "POST",
       headers: {
         "Content-Type": "application/json",
       },
       body: JSON.stringify(orderRequest),
     }
   )
 
   if (!response.ok) {
     throw new Error("Failed to place order")
   }
 
   const data = await response.json()
 
   console.log("Order placed successfully:", data)
 
   alert("Order placed successfully!")
 
   navigate("/")
 
} catch (error) {
   console.error("Order failed:", error)
   alert("Failed to place order")
}
}
 
  return (
    <div className="checkout-container">
      <h1>Checkout</h1>
 
      <div className="checkout-content">
        <div className="customer-details">
          <h2>Customer Details</h2>
 
          <form onSubmit={handleSubmit}>
            <input type="text" name="name" placeholder="Full Name"
              value={customer.name}
              onChange={handleChange}
              required/>
 
            <input type="email" name="email" placeholder="Email"
              value={customer.email}
              onChange={handleChange}
              required/>
 
            <input type="tel" name="phone" placeholder="Phone Number"
              value={customer.phone}
              onChange={handleChange}
              required/>
 
            <textarea name="address" placeholder="Delivery Address"
              value={customer.address}
              onChange={handleChange}
              required/>
 
            <button type="submit">Place Order</button>
          </form>
        </div>
 
        <div className="order-summary">
          <h2>Order Summary</h2>
 
          {cart.map((item) => (
            <div className="summary-item" key={item.productId}>
              <span>
                {item.productName} × {item.quantity}
              </span>
              <span>₹{item.price * item.quantity}</span>
            </div>
          ))}
 
          <hr />
 
          <h3>Total: ₹{totalPrice}</h3>
        </div>
      </div>
    </div>
  )
}
 
export default Checkout