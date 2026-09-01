import { useState } from "react"
import { useNavigate } from "react-router-dom"

function Register(){
    const [customer, setCustomer] = useState({
        customerName: "",
        email: "",
        phone: "",
        password: "",
    })

    const navigate = useNavigate()

    const handleChange = (e) => {
        setCustomer({
            ...customer,[e.target.name]: e.target.value,
        })
    }

    const handleRegister = async (e) => {
        e.preventDefault()

        try{
            const response = await fetch(
                "http://localhost:8084/api/customers",
                {
                    method:"POST",
                    headers: { "Content-Type": "application/json"},
                    body: JSON.stringify(customer)
                }
            )

            if(!response.ok){
                throw new Error("Registration failed")
            }
            const data = await response.json()
            console.log("Registration successful: ",data)
            alert("Registration successful! Please login.")
            navigate("/login")
        }catch(error){
            console.error("Registration failed:",error)
            alert("Registration failed. Please try again.")
        }
    }

    return(
        <div>
            <h2>Create Account</h2>
            <form onSubmit={handleRegister}>
                <div>
                    <label htmlFor="customerName">Name:</label>
                    <input type="text" name="customerName" value={customer.customerName}
                    onChange={handleChange}
                    required/>
                </div>

                <div>
                    <label htmlFor="email">Email:</label>
                    <input type="email" name="email" value={customer.email}
                    onChange={handleChange}
                    required/>
                </div>

                <div>
                    <label htmlFor="phone">Phone:</label>
                    <input type="tel" name="phone" value={customer.phone}
                    onChange={handleChange}
                    required/>
                </div>

                <div>
                    <label htmlFor="password">Password:</label>
                    <input type="password" name="password" value={customer.password}
                    onChange={handleChange}
                    required/>
                </div>
                <button type="submit">Register</button>
            </form>
        </div>
    )
}

export default Register