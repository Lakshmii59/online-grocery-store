import { useState } from "react"
import { useNavigate, useLocation } from "react-router-dom"
 
function Login() {
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
 
  const navigate = useNavigate()
  const location = useLocation()
 
  const handleLogin = async (e) => {e.preventDefault()
 
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
        }
      )
 
      if (!response.ok) {
        throw new Error("Invalid email or password")
      }
 
      const data = await response.json()
 
      localStorage.setItem("customer", JSON.stringify(data))
 
      console.log("Login successful:", data)
 
      navigate(location.state?.from || "/")
    } catch (error) {
      console.error("Login failed:", error)
    }
  }

  return (
    <div>
      <h2>Customer Login</h2>
 
      <form onSubmit={handleLogin}>
        <div>
          <label htmlFor="email">Email:</label>
 
          <input type="email" value={email}
            onChange={(e) => setEmail(e.target.value)}
            required/>
        </div>
 
        <div>
          <label htmlFor="password">Password:</label>
 
          <input type="password" value={password}
            onChange={(e) => setPassword(e.target.value)}
            required/>
        </div>
 
        <button type="submit">Login</button>
        <p>New User?{" "}
            <button type="button" onClick={() => 
                navigate("/register")}>Register</button>
        </p>
      </form>
    </div>
  )
}
 
export default Login