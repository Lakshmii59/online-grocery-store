import { useState } from "react"
import { useNavigate, useLocation } from "react-router-dom"
import "../scss/Login.scss"

function Login() {
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [errors, setErrors] = useState({})
  const [showPopup, setShowPopup] = useState(false)

  const navigate = useNavigate()
  const location = useLocation()

  const handleLogin = async (e) => {
    e.preventDefault()

    if (!email.trim()) {
      setErrors({
        email: "Email is required",
        password: "",
        login: "",
      })
      return
    }

    if (!password) {
      setErrors({
        email: "",
        password: "Password is required",
        login: "",
      })
      return
    }

    setErrors({})

    try {
      const response = await fetch(
        "http://localhost:8084/api/customers/login",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            email: email,
            password: password,
          }),
        },
      )

      if (!response.ok) {
        throw new Error("Invalid email or password")
      }

      const data = await response.json()

      localStorage.setItem("customer", JSON.stringify(data))
      window.dispatchEvent(new Event("customerChanged"))

      console.log("Login successful:", data)

      if (data.role === "ADMIN") {
        navigate("/admin")
      } else {
        navigate(location.state?.from || "/")
      }
    } catch (error) {
      console.error("Login failed:", error)
      setShowPopup(true)
    }
  }

  return (
    <div className="login-page">
      <div className="login-card">
        <div className="login-header">
          <h1>Welcome Back</h1>
          <p>Login to your FreshNest account</p>
        </div>

        <form onSubmit={handleLogin} className="login-form">
          <div className="form-group">
            <label htmlFor="email">Email Address</label>

            <input
              id="email"
              type="email"
              name="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => {
                setEmail(e.target.value);
                setErrors((previousErrors) => ({
                  ...previousErrors,
                  email: "",
                  login: "",
                }));
              }}
              onBlur={() => {
                if (!email.trim()) {
                  setErrors((previousErrors) => ({
                    ...previousErrors,
                    email: "Email is required",
                  }));
                }
              }}
            />

            {errors.email && <p className="error-message">{errors.email}</p>}
          </div>

          <div className="form-group">
            <label htmlFor="password">Password</label>

            <input
              id="password"
              type="password"
              name="password"
              placeholder="Enter your password"
              value={password}
              onChange={(e) => {
                setPassword(e.target.value);
                setErrors((previousErrors) => ({
                  ...previousErrors,
                  password: "",
                  login: "",
                }));
              }}
              onBlur={() => {
                if (!password) {
                  setErrors((previousErrors) => ({
                    ...previousErrors,
                    password: "Password is required",
                  }));
                }
              }}
            />
            </div>

          <button type="submit" className="login-button">
            Login
          </button>
        </form>

        <div className="register-section">
          <span>New to FreshNest?</span>

          <button type="button" onClick={() => navigate("/register")}>
            Create an account
          </button>
        </div>
      </div>
      {showPopup && (
        <div className="popup-overlay">
          <div className="error-popup">
            <div className="popup-icon">!</div>

            <h3>Login Failed</h3>

            <p>Invalid email or password</p>

            <button type="button" onClick={() => setShowPopup(false)}>
              OK
            </button>
          </div>
        </div>
      )}
    </div>
  )
}

export default Login
