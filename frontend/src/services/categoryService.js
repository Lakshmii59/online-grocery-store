import api from "./api";

export const getAllCategories = () => {
  return api.get("/api/categories");
};

export const getCategoryById = (categoryId) => {
  return api.get(`/api/categories/${categoryId}`);
};

export const createCategory = (category) => {
  return api.post("/api/categories", category);
};

export const updateCategory = (categoryId, category) => {
  return api.put(`/api/categories/${categoryId}`, category);
};

export const deleteCategory = (categoryId) => {
  return api.delete(`/api/categories/${categoryId}`);
};
