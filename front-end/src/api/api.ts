import axios from "axios";

// ✅ Backend URL (Spring Boot runs on port 8080)
const API_BASE_URL = "http://localhost:8080/api";

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// ✅ Attach token automatically (if available)
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// ✅ Generic API executor (used by API explorer or debugging tools)
export const executeAPI = async (
  method: string,
  endpoint: string,
  data?: any
) => {
  const url = endpoint.startsWith("/") ? endpoint : `/${endpoint}`;

  switch (method.toUpperCase()) {
    case "GET":
      return await api.get(url);
    case "POST":
      return await api.post(url, data);
    case "PUT":
      return await api.put(url, data);
    case "DELETE":
      return await api.delete(url);
    default:
      throw new Error(`Unsupported method: ${method}`);
  }
};

// ✅ AUTH APIs
export const authAPI = {
  login: async (username: string, password: string) => {
    const response = await api.post("/auth/login", { username, password });
    return response.data;
  },

  register: async (username: string, email: string, password: string) => {
    const response = await api.post("/auth/register", {
      username,
      email,
      password,
    });
    return response.data;
  },
};

// ✅ STUDENT APIs
export const studentAPI = {
  addStudent: async (studentData: any) => {
    const response = await api.post("/students", studentData);
    return response.data;
  },

  getAllStudents: async () => {
    const response = await api.get("/students");
    return response.data;
  },

  getReport: async (studentId: number) => {
    const response = await api.get(`/reports/${studentId}`);
    return response.data;
  },
};

// ✅ PERFORMANCE PREDICTION API
export const predictionAPI = {
  predictPerformance: async (studentData: any) => {
    const response = await api.post("/predict", studentData);
    return response.data;
  },
};

export default api;
