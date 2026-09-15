import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../scss/Register.scss";
 
function Register() {
  const navigate = useNavigate();
 
  const [customer, setCustomer] = useState({
    customerName: "",
    email: "",
    phone: "",
    password: "",
  });
 
  const [errors, setErrors] = useState({});
 
  const getErrorMessage = (name, value) => {
    let message = "";
 
    if (name === "customerName") {
      if (!value.trim()) {
        message = "Name is required";
      } else if (!/^[A-Za-z]+$/.test(value)) {
        message = "Name should contain only letters and spaces";
      } else if (value.length > 50) {
        message = "Name cannot exceed 50 characters";
      }
    }
 
  if (name === "email") {
  if (!value.trim()) {
    message = "Email is required";
  } else if (/\s/.test(value)) {
    message = "Email cannot contain spaces";
  } else if (
    !/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/.test(value)
  ) {
    message = "Enter a valid email address with a domain";
  }
}
 
    if (name === "phone") {
      if (!value.trim()) {
        message = "Phone number is required";
      } else if (!/^[6-9]\d{9}$/.test(value)) {
        message =
          "Phone number must contain exactly 10 digits and start with 6-9";
      }
    }
 
  if (name === "password") {
  if (!value) {
    message = "Password is required";
  } else if (value.length < 8) {
    message = "Password should contain atleast 8 characters";
  } else if (!/[A-Z]/.test(value)) {
    message = "Password must contain at least one uppercase letter";
  } else if (!/[a-z]/.test(value)) {
    message = "Password must contain at least one lowercase letter";
  } else if (!/[0-9]/.test(value)) {
    message = "Password must contain at least one number";
  } else if (!/[!@#$%^&*(),.?":{}|<>_\-\\[\]']/ .test(value)) {
    message = "Password must contain at least one special character";
  }
}
    return message;
  };
 
  const validateField = (event) => {
    const { name, value } = event.target;
    const message = getErrorMessage(name, value);
 
    setErrors((previousErrors) => ({
      ...previousErrors,
      [name]: message,
    }));
  };
 
  const handleChange = (e) => {
    const { name, value } = e.target;
 
    setCustomer({
      ...customer,
      [name]: value,
    });
  };
 
  const handleRegister = async (e) => {
    e.preventDefault();
 
    const validationErrors = {};
 
    Object.entries(customer).forEach(([name, value]) => {
      const message = getErrorMessage(name, value);
 
      if (message) {
        validationErrors[name] = message;
      }
    });
 
    setErrors(validationErrors);
 
    if (Object.keys(validationErrors).length > 0) {
      return;
    }
 
    try {
      const response = await fetch(
        "http://localhost:8084/api/customers",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(customer),
        }
      );
 
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(
          errorData.message || "Registration failed"
        );
      }
 
      const data = await response.json();
 
      console.log("Registration successful:", data);
      alert("Registration successful! Please login.");
      navigate("/login");
    } catch (error) {
      console.error("Registration failed:", error);
      alert(`Registration failed: ${error.message}`);
    }
  };
 
  return (
    <div className="register-page">
      <div className="register-card">
        <div className="register-header">
          <h1>Create Account</h1>
          <p>Join FreshNest and start shopping</p>
        </div>
 
        <form
          onSubmit={handleRegister}
          className="register-form"
          noValidate
        >
          <div className="form-group">
            <label htmlFor="customerName">Full Name</label>
 
            <input
              id="customerName"
              type="text"
              name="customerName"
              placeholder="Enter your full name"
              value={customer.customerName}
              onChange={handleChange}
              onBlur={validateField}
            />
 
            {errors.customerName && (
              <p className="error-message">
                {errors.customerName}
              </p>
            )}
          </div>
 
          <div className="form-group">
            <label htmlFor="email">Email Address</label>
 
            <input
              id="email"
              type="email"
              name="email"
              placeholder="Enter your email"
              value={customer.email}
              onChange={handleChange}
              onBlur={validateField}
            />
 
            {errors.email && (
              <p className="error-message">
                {errors.email}
              </p>
            )}
          </div>
 
          <div className="form-group">
            <label htmlFor="phone">Phone Number</label>
 
            <input
              id="phone"
              type="tel"
              name="phone"
              placeholder="Enter your phone number"
              value={customer.phone}
              onChange={handleChange}
              onBlur={validateField}
              maxLength={10}
            />
 
            {errors.phone && (
              <p className="error-message">
                {errors.phone}
              </p>
            )}
          </div>
 
          <div className="form-group">
            <label htmlFor="password">Password</label>
 
            <input
              id="password"
              type="password"
              name="password"
              placeholder="Create a password"
              value={customer.password}
              onChange={handleChange}
              onBlur={validateField}
            />
 
            {errors.password && (
              <p className="error-message">
                {errors.password}
              </p>
            )}
          </div>
 
          <button type="submit" className="register-button">
            Create Account
          </button>
        </form>
 
        <div className="login-section">
          <span>Already have an account?</span>
 
          <button
            type="button"
            onClick={() => navigate("/login")}
          >
            Login
          </button>
        </div>
      </div>
    </div>
  );
}
 
export default Register;
