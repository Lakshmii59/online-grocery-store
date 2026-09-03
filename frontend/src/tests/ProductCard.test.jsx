import { render, screen, fireEvent, waitFor } from "@testing-library/react"
import ProductCard from "../components/ProductCard"
import { getInventoryByProductId } from "../services/inventoryService"
import { useCart } from "../CartContext"
 
jest.mock("../services/inventoryService")
jest.mock("../CartContext")
 
const product = {
  productId: 1,
  productName: "Apple",
  price: 120,
  imageUrl: "/apple.jpg"
}
 
describe("ProductCard", () => {
  const addToCart = jest.fn()
 
  beforeEach(() => {
    jest.clearAllMocks()
    useCart.mockReturnValue({ addToCart })
  })
 
  test("shows checking stock initially", () => {
    getInventoryByProductId.mockReturnValue(
      new Promise(() => {})
    )
 
    render(<ProductCard product={product} />)
 
    expect(
      screen.getByText("Checking stock...")
    ).toBeInTheDocument()
  })
 
  test("shows Add to Cart when product is in stock", async () => {
    getInventoryByProductId.mockResolvedValue({
      data: {
        availableQuantity: 10
      }
    })
 
    render(<ProductCard product={product} />)
 
    expect(
      await screen.findByRole("button", {
        name: "Add to Cart"
      })
    ).toBeInTheDocument()
  })
 
  test("adds product to cart", async () => {
    getInventoryByProductId.mockResolvedValue({
      data: {
        availableQuantity: 10
      }
    })
 
    render(<ProductCard product={product} />)
 
    const button = await screen.findByRole("button", {
      name: "Add to Cart"
    })
 
    fireEvent.click(button)
 
    expect(addToCart).toHaveBeenCalledWith(product)
 
    expect(
      screen.getByText("Product added to cart!")
    ).toBeInTheDocument()
  })
 
  test("shows Out of Stock when quantity is zero", async () => {
    getInventoryByProductId.mockResolvedValue({
      data: {
        availableQuantity: 0
      }
    })
 
    render(<ProductCard product={product} />)
 
    expect(
      await screen.findByText("Out of Stock")
    ).toBeInTheDocument()
 
    expect(
      screen.queryByRole("button", {
        name: "Add to Cart"
      })
    ).not.toBeInTheDocument()
  })
 
  test("shows Out of Stock when inventory request fails", async () => {
    getInventoryByProductId.mockRejectedValue(
      new Error("Inventory service unavailable")
    )
 
    render(<ProductCard product={product} />)
 
    expect(
      await screen.findByText("Out of Stock")
    ).toBeInTheDocument()
  })
})