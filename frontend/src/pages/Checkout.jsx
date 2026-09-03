import { useState } from "react";
import { useCart } from "../CartContext";
import "../scss/Checkout.scss";
import { useNavigate } from "react-router-dom";

function Checkout() {
  const { cart, clearCart } = useCart();
  const navigate = useNavigate();

  const [customer, setCustomer] = useState({
    name: "",
    email: "",
    phone: "",
    address: "",
  });

  const totalPrice = cart.reduce(
    (total, item) => total + item.price * item.quantity,
    0,
  );

  const handleChange = (e) => {
    setCustomer({
      ...customer,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const loggedInCustomer = JSON.parse(localStorage.getItem("customer"));

      const orderRequest = {
        customerId: loggedInCustomer.customerId,
        orderItems: cart.map((item) => ({
          productId: item.productId,
          quantity: item.quantity,
        })),
      };

      console.log("Order request:", orderRequest);

      const response = await fetch("http://localhost:8084/api/orders", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(orderRequest),
      });

      if (!response.ok) {
        throw new Error("Failed to place order");
      }

      const data = await response.json();

      console.log("Order placed successfully:", data);
      clearCart();
      alert("Order placed successfully!");

      navigate("/");
    } catch (error) {
      console.error("Order failed:", error);
      alert("Failed to place order");
    }
  };

  return (
    <div className="checkout-page">
      <div className="checkout-header">
        <h1>Checkout</h1>
        <p>Complete your details and place your order</p>
      </div>

      <div className="checkout-content">
        <div className="customer-details">
          <div className="section-header">
            <div>
              <h2>Customer Details</h2>
              <p>Enter your delivery information</p>
            </div>
          </div>

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Full Name</label>
              <input
                type="text"
                name="name"
                placeholder="Enter your full name"
                value={customer.name}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label>Email</label>
              <input
                type="email"
                name="email"
                placeholder="Enter your email"
                value={customer.email}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label>Phone Number</label>
              <input
                type="tel"
                name="phone"
                placeholder="Enter your phone number"
                value={customer.phone}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label>Delivery Address</label>
              <textarea
                name="address"
                placeholder="Enter your delivery address"
                value={customer.address}
                onChange={handleChange}
                rows="5"
                required
              />
            </div>

            <button type="submit" className="place-order-button">
              Place Order
            </button>
          </form>
        </div>

        <div className="checkout-right">
          <div className="order-summary">
            <div className="section-header">
              <div>
                <h2>Order Summary</h2>
                <p>Review your order</p>
              </div>
            </div>

            <div className="summary-items">
              {cart.map((item) => (
                <div className="summary-item" key={item.productId}>
                  <div className="item-details">
                    <span className="item-name">{item.productName}</span>

                    <span className="item-quantity">
                      Quantity: {item.quantity}
                    </span>
                  </div>

                  <span className="item-price">
                    ₹{item.price * item.quantity}
                  </span>
                </div>
              ))}
            </div>

            <div className="summary-total">
              <span>Total Amount</span>
              <strong>₹{totalPrice}</strong>
            </div>
          </div>

          <div className="delivery-info">
            <div className="delivery-icon">🚚</div>

            <div>
              <h3>Estimated Delivery</h3>
              <p>Within 30 minutes</p>
            </div>
          </div>

          <div className="checkout-note">
            <span>🔒</span>

            <div>
              <h3>Secure Checkout</h3>
              <p>Your order information is handled securely.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Checkout;
