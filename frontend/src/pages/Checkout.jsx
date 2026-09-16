import { useState } from "react";
import { useCart } from "../CartContext";
import "../scss/Checkout.scss";
import { useNavigate } from "react-router-dom";

function Checkout() {
  const { cart, clearCart } = useCart();
  const navigate = useNavigate();

  const loggedInCustomer = JSON.parse(localStorage.getItem("customer"));

  const [customer, setCustomer] = useState({
    name: loggedInCustomer?.customerName || "",
    phone: loggedInCustomer?.phone || "",
    address: "",
  });

  const [errors, setErrors] = useState({
    name: "",
    phone: "",
    address: "",
  });

  const totalPrice = cart.reduce(
    (total, item) => total + item.price * item.quantity,
    0,
  );

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === "name") {
      if (!/^[A-Za-z ]*$/.test(value)) {
        return;
      }
    }

    if (name === "phone") {
      if (!/^[0-9]*$/.test(value)) {
        return;
      }

      if (value.length > 10) {
        return;
      }
    }

    setCustomer((previousCustomer) => ({
      ...previousCustomer,
      [name]: value,
    }));

    setErrors((previousErrors) => ({
      ...previousErrors,
      [name]: "",
    }));
  };

  const validateField = (name, value) => {
    let message = "";

    if (name === "name") {
      if (!value.trim()) {
        message = "Recipient name is required";
      } else if (!/^[A-Za-z ]+$/.test(value.trim())) {
        message = "Name should contain only letters";
      }
    }

    if (name === "phone") {
      if (!value.trim()) {
        message = "Phone number is required";
      } else if (!/^[0-9]{10}$/.test(value)) {
        message = "Phone number must contain exactly 10 digits";
      }
    }

    if (name === "address") {
      const address = value.trim();
      if (!address) {
        message = "Delivery address is required";
      } else if (address.length < 20) {
        message = "Please enter a complete delivery address";
      } else if (!/\d/.test(address)) {
        message = "Address must contain a house number or pincode";
      }
    }

    setErrors((previousErrors) => ({
      ...previousErrors,
      [name]: message,
    }));

    return message;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const nameError = validateField("name", customer.name);
    const phoneError = validateField("phone", customer.phone);
    const addressError = validateField("address", customer.address);

    if (nameError || phoneError || addressError) {
      return;
    }

    try {
      const orderRequest = {
        customerId: loggedInCustomer.customerId,
        orderItems: cart.map((item) => ({
          productId: item.productId,
          quantity: item.quantity,
        })),
      };

      console.log("Order request:", orderRequest);
      console.log("Delivery details:", customer);

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
        <p>Complete your delivery details and place your order</p>
      </div>

      <div className="checkout-content">
        <div className="customer-details">
          <div className="section-header">
            <div>
              <h2>Delivery Details</h2>
              <p>Enter the details for the person receiving the order</p>
            </div>
          </div>

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="name">Recipient Name</label>

              <input
                id="name"
                type="text"
                name="name"
                placeholder="Enter recipient name"
                value={customer.name}
                onChange={handleChange}
                onBlur={(e) => validateField("name", e.target.value)}
              />

              {errors.name && <p className="error-message">{errors.name}</p>}
            </div>

            <div className="form-group">
              <label htmlFor="phone">Recipient Phone Number</label>

              <input
                id="phone"
                type="tel"
                name="phone"
                placeholder="Enter recipient phone number"
                value={customer.phone}
                onChange={handleChange}
                onBlur={(e) => validateField("phone", e.target.value)}
                maxLength="10"
              />

              {errors.phone && <p className="error-message">{errors.phone}</p>}
            </div>

            <div className="form-group">
              <label htmlFor="address">Delivery Address</label>

              <textarea
                id="address"
                name="address"
                placeholder="Enter house number, apartment, area, street, landmark, city, and pincode"
                value={customer.address}
                onChange={handleChange}
                onBlur={(e) => validateField("address", e.target.value)}
                rows="5"
              />

              {errors.address && (
                <p className="error-message">{errors.address}</p>
              )}
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
